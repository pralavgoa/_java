package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringEscapeUtils;

import au.com.bytecode.opencsv.CSVReader;
import ctsibip.shared.db.AbstractDataBank;
import ctsibip.shared.db.MySQLDataBank;

public class PopulateActualDataForPilot {

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = db.getConnection();
        ResultSet rs = getAllInvitees(conn);

        while (rs.next()) {

            Invitee invitee = new Invitee(rs);
            System.out.println("Invitee: " + invitee.getId() + "," + invitee.getFirstname() + " "
                    + invitee.getLastname());
            System.out.println(invitee.constructSupportText());

            // addSupportTextToInviteeTable(invitee, conn);
            // pupulateMainDataTable(invitee, conn);
            populatePublicationsForInvitee(invitee.getId(), invitee.getFirstname() + " " + invitee.getLastname(), conn);
            // populateGrantsForInvitee(invitee, conn);
            // populateTechTransfersForInvitee(invitee, conn);

        }

        conn.close();
    }

    private static void pupulateMainDataTable(Invitee invitee, Connection conn) throws SQLException {

        if ((invitee.getPubs_prepopulated() == 1) || (invitee.getGrants_prepopulated() == 1)
                || (invitee.getTech_xfers() == 1)) {

            String sql = "INSERT INTO `sciprod2014`.`ctsi_scientificprod_data` (`invitee`, `status`,`peer_rev_pubs`,`grant_fund`,`tech_tr`) "
                    + "VALUES (?, 'module_1',?,?,?);";
            db.insert(sql, conn, invitee.getId(), "" + invitee.getPubs_prepopulated(),
                    "" + invitee.getGrants_prepopulated(), "" + invitee.getTech_xfers());
        }

    }

    private static void addSupportTextToInviteeTable(Invitee invitee, Connection conn) throws SQLException {
        String supportText = invitee.constructSupportText();
        String sql = "UPDATE `sciprod2014`.`invitee` SET `support_text`='" + supportText + "' WHERE `id`='"
                + invitee.getId() + "'";
        db.update(sql, conn);
    }

    private static void populateTechTransfersForInvitee(Invitee invitee, Connection conn) throws SQLException {

        // get all tech transfers for invitee
        String sql = "SELECT * FROM sciprod2014.tech_transfers WHERE firstname='" + invitee.getFirstname()
                + "' AND lastname='" + invitee.getLastname() + "'";
        ResultSet rs = db.executeSelect(sql, conn);
        while (rs.next()) {
            String instanceName = rs.getString(6);
            String description = rs.getString(7);

            String sqlToInsert = "INSERT INTO `sciprod2014`.`repeat_set_tech_transfer1` (`invitee`, `instance_name`, `repeat_DESCRIPTION_MOD7`) VALUES (?, ?, ?)";
            db.insert(sqlToInsert, conn, invitee.getId(), instanceName, description);
        }
    }

    private static void populateGrantsForInvitee(Invitee invitee, Connection conn) throws SQLException {

        // get all grants for invitee
        String sql = "SELECT * FROM sciprod2014.grants WHERE firstname='" + invitee.getFirstname() + "' AND lastname='"
                + invitee.getLastname() + "'";
        ResultSet rs = db.executeSelect(sql, conn);
        while (rs.next()) {
            String instanceName = rs.getString(4);
            String grantTitle = rs.getString(5);
            String funder = rs.getString(6);
            String grantPI = rs.getString(7);
            String awardAmt = rs.getString(8);
            String grantNum = rs.getString(9);

            String sqlToInsert = "INSERT INTO `sciprod2014`.`repeat_set_grant` "
                    + "(`invitee`, `instance_name`, `repeat_GRANT_TITLE_MOD5`, `repeat_FUNDER_MOD5`, `repeat_GRANT_PI_MOD5`, `repeat_AWARD_AMT_MOD5`, `repeat_GRANT_NUM_MOD5`) VALUES (?, ?, ?, ?, ?, ?, ?);";
            db.insert(sqlToInsert, conn, invitee.getId(), instanceName, grantTitle, funder, grantPI, awardAmt, grantNum);
        }

    }

    public static void populateSupportTextForInvitee(Invitee invitee) {

        String supportText = "";

        String sql = "UPDATE `sciprod2014`.`invitee` SET `support_text`='" + invitee.constructSupportText()
                + "' WHERE `id`='" + invitee.getId() + "'";

    }

    public static void populatePublicationsForInvitee(String inviteeId, String inviteeName, Connection conn)
            throws SQLException {

        // get all publications
        String sql = "SELECT invitee,pubmedId, citation,authors,title FROM sciprod2014.invitee_pubmedId,publications where pubmedId = pubmed_id AND invitee='"
                + inviteeName + "'";
        ResultSet rs = db.executeSelect(sql, conn);
        while (rs.next()) {

            String inviteeName1 = rs.getString(1);
            String pubmedId = rs.getString(2);
            String citation = rs.getString(3);
            String authors = rs.getString(4);
            String title = rs.getString(5);

            System.out.println(inviteeName1 + "," + pubmedId + "," + authors + "," + title + "," + citation);
            String citationString = StringEscapeUtils.escapeHtml4(authors + "." + title + citation);
            // insert publication citations in the repeat_set_publication table
            String sqlToInsert = "INSERT INTO `sciprod2014`.`repeat_set_publication` (`invitee`, `instance_name`, `repeat_PUBMED_CITATION`) VALUES (?, ?, ?)";
            db.insert(sqlToInsert, conn, inviteeId, pubmedId, citationString);

            // insert grants in the repeat_set_grant table
        }
    }

    public static ResultSet getAllInvitees(Connection conn) throws SQLException {
        ResultSet rs = db.executeSelect("SELECT * FROM invitee", conn);
        return rs;
    }

    public static void test() throws SQLException, IOException {
        Connection conn = db.getConnection();
        ResultSet rs = db.executeSelect("SELECT * FROM invitee", conn);
        System.out.println(AbstractDataBank.printResultSet(rs, 3));
        conn.close();

        System.out.println(getInviteeId("Pralav", "Dessai"));
        readAuthorsToPubsFile("files/sciprod2014/SciProd_Survey_Pilot_Invitees.csv");
    }

    public static String getInviteeId(String firstname, String lastname) throws SQLException {
        Connection conn = db.getConnection();
        ResultSet rs = db.executeSelect("SELECT id FROM invitee WHERE firstname='" + firstname + "' AND lastname='"
                + lastname + "'", conn);
        String inviteeId = "";
        while (rs.next()) {
            inviteeId = rs.getString(1);
            break;
        }
        conn.close();
        return inviteeId;
    }

    public static void readAuthorsToPubsFile(String inputFile) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String name = nextLine[0];
            String pubmedId = nextLine[1];
            String citation = StringEscapeUtils.escapeHtml4(nextLine[2]);
            System.out.print(name + "," + pubmedId + "," + citation + "\n");
            String sql = "INSERT INTO `sciprod2014`.`repeat_set_publication` "
                    + "(`invitee`, `instance_name`, `repeat_PUBMED_CITATION`) " + "VALUES "
                    + "('insert_invitee_id', '99999998', 'Author, X., Author Y., Title T')";
        }
    }
}
