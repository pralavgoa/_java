package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;
import ctsibip.shared.db.MySQLDataBank;

public class GenerateInviteeToPubmedIdIndex_new160 {

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static final String PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\"
            + "SCIPROD 2014\\invitees\\Invitee_List_Cleaned_1.csv";

    public static void main(String[] args) throws SQLException {

        Connection conn = db.getConnection();
        try {
            createAuthorToPubmedIdIndex(PATH, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        conn.close();
    }

    private static void createAuthorToPubmedIdIndex(String path, Connection conn) throws IOException, SQLException {

        Set<String> inviteesWithoutData = new HashSet<>();

        CSVReader reader = new CSVReader(new FileReader(path), ',');
        String[] nextLine;
        int lineNumber = 0;
        while ((nextLine = reader.readNext()) != null) {
            if (lineNumber++ == 0) {
                continue;
            }
            String firstname = nextLine[0];
            char firstInitial = firstname.charAt(0);
            String lastname = nextLine[1];

            ResultSet rs = getPublicationsForLastnameFirstInitial(lastname, firstInitial, conn);

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.print(firstname);
                System.out.print(",");
                System.out.print(lastname);
                System.out.print(",");
                System.out.print(rs.getString(2));
                System.out.print(",");
                System.out.print(rs.getString(3));
                System.out.print(",");
                System.out.print(rs.getString(5));
                System.out.print(",");
                System.out.print("\"" + rs.getString(6) + "\"");
                System.out.println("");
            }

            if (!hasData) {
                inviteesWithoutData.add(firstname + " " + lastname);
            }

        }
        System.out.println("There are " + inviteesWithoutData.size() + " invitees without data");

    }

    private static ResultSet getPublicationsForLastnameFirstInitial(String lastname, char firstInitial, Connection conn)
            throws SQLException {
        String sql = "SELECT * FROM sciprod2014.author_pubmedId_new,new_publications where author LIKE '"
                + lastname.replaceAll("'", "\\\\'") + " " + firstInitial
                + "%' AND author_pubmedId_new.pubmedId=new_publications.pubmedId";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }

}
