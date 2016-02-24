package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringEscapeUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Strings;

import ctsibip.shared.db.MySQLDataBank;

public class PopulateActualDataForLaunch {

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static final String BASE_PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\SCIPROD 2014\\";

    public static final String PATH1 = BASE_PATH + "data\\publications_2013.csv";

    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = db.getConnection();
        try {
            // pupulateDataForEachInvitee(conn);
            // pubsFrom2013(conn);
            pupulateMainDataTable(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    private static void pupulateMainDataTable(Connection conn) throws SQLException {
        ResultSet rs = getAllInvitees(conn);
        while (rs.next()) {
            Invitee invitee = new Invitee(rs);
            System.out.println("Invitee: " + invitee.getId() + "," + invitee.getFirstname() + " "
                    + invitee.getLastname());
            if ((invitee.getPubs_prepopulated() == 1) || (invitee.getGrants_prepopulated() == 1)
                    || (invitee.getTech_xfers() == 1)) {

                String sql = "INSERT INTO `sciprod2014`.`ctsi_scientificprod_data` (`invitee`, `status`,`peer_rev_pubs`,`grant_fund`,`tech_tr`) "
                        + "VALUES (?, 'module_1',?,?,?);";
                db.insert(sql, conn, invitee.getId(), "" + invitee.getPubs_prepopulated(),
                        "" + invitee.getGrants_prepopulated(), "" + invitee.getTech_xfers());
            }

        }
    }

    private static void pupulateDataForEachInvitee(Connection conn) throws SQLException {
        ResultSet rs = getAllInvitees(conn);
        while (rs.next()) {

            Invitee invitee = new Invitee(rs);
            System.out.println("Invitee: " + invitee.getId() + "," + invitee.getFirstname() + " "
                    + invitee.getLastname());
            System.out.println(invitee.constructSupportText());

            addSupportTextToInviteeTable(invitee, conn);
            // pupulateMainDataTable(invitee, conn);
            // populatePublicationsForInvitee(invitee.getId(),
            // invitee.getFirstname() + " " + invitee.getLastname(), conn);
            // populateGrantsForInvitee(invitee, conn);
            // populateTechTransfersForInvitee(invitee, conn);

        }

    }

    public static ResultSet getAllInvitees(Connection conn) throws SQLException {
        ResultSet rs = db.executeSelect("SELECT * FROM invitee where id>=18", conn);
        return rs;
    }

    private static void addSupportTextToInviteeTable(Invitee invitee, Connection conn) throws SQLException {
        String supportText = invitee.constructSupportText();
        String sql = "UPDATE `sciprod2014`.`invitee` SET `support_text`='" + supportText + "' WHERE `id`='"
                + invitee.getId() + "'";
        db.update(sql, conn);
    }

    private static void pubsFrom2013(Connection conn) throws IOException, SQLException {

        CSVReader reader = new CSVReader(new FileReader(PATH1));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String invitee = nextLine[1];
            String pubmedId = nextLine[2];
            String citation = nextLine[3];
            String newInviteeId = getNewInviteeId(conn, invitee);

            if (!Strings.isNullOrEmpty(newInviteeId)) {
                System.out.print(newInviteeId + ",");
                System.out.print(pubmedId + ",");
                System.out.println("\"" + StringEscapeUtils.escapeHtml4(citation.trim()) + "\"");
            }
        }

    }

    private static String getNewInviteeId(Connection conn, String invitee) throws SQLException {
        String sql = "SELECT newId FROM sciprod2014.invitees_old_new_link where oldId=" + invitee;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        String newId = "";
        boolean uniqueResult = true;
        while (rs.next()) {
            if (!uniqueResult) {
                throw new IllegalArgumentException("duplicate result for" + invitee);
            }
            newId = rs.getString(1);
            uniqueResult = false;

        }
        return newId;
    }

}
