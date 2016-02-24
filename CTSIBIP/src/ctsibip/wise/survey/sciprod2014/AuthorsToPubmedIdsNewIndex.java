package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import au.com.bytecode.opencsv.CSVReader;
import ctsibip.shared.db.MySQLDataBank;

public class AuthorsToPubmedIdsNewIndex {

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static final String BASE_PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\SCIPROD 2014\\";

    public static final String PATH = BASE_PATH + "data\\169_new_publications_with_citation.csv";
    public static final String PATH2 = BASE_PATH + "data\\169_new_pubmed_ids.txt";

    public static void main(String[] args) throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader(PATH), '|');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String pubmedId = nextLine[0].trim();
            String[] authors = nextLine[1].split(",");

            for (String author : authors) {
                System.out.println(author.trim() + "," + pubmedId);
            }
        }

        // Connection conn = db.getConnection();
        // try {
        // List<String> extraPubmedIDs = Files.readLines(new File(PATH2),
        // Charsets.UTF_8);
        // linkPubmedIdsToCitations(extraPubmedIDs, conn);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // conn.close();
    }

    private static void linkPubmedIdsToCitations(List<String> newPubmedIds, Connection conn) throws SQLException {
        for (String pubmedId : newPubmedIds) {

            String citation = StringEscapeUtils.unescapeHtml4(getCitationFromDb(conn, pubmedId));

            String[] citationComponents = citation.split("\\.");
            System.out.println(pubmedId + " | " + citationComponents[0] + " | " + citationComponents[1]);

        }
    }

    private static String getCitationFromDb(Connection conn, String pubmedId) throws SQLException {

        String sql = "SELECT * FROM sciprod2014.publications_extra where pubmedId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, pubmedId);
        ResultSet rs = ps.executeQuery();

        String citation = "";

        boolean singleResult = true;

        while (rs.next()) {
            citation = rs.getString(3);
            if (singleResult == false) {
                throw new IllegalArgumentException("Duplicate pubmed id " + pubmedId);
            }
            singleResult = false;
        }

        return citation;
    }
}
