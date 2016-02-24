package ctsibip.wise.survey.sciprod2014;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import ctsibip.shared.db.MySQLDataBank;
import ctsibip.shared.web.WebRequester;

public class CorrectPublicationCitations {
    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());
    public static final String PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\SCIPROD 2014\\data\\remaining_ids.txt";

    public static void main(String[] args) throws IOException, SQLException {

        Connection conn = db.getConnection();
        try {
            List<String> newPubmedIds = Files.readLines(new File(PATH), Charsets.UTF_8);

            for (String pubmedId : newPubmedIds) {
                WebRequester webRequester = new WebRequester(
                        "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=" + pubmedId
                                + "&retmode=text&rettype=docsum");

                String response = webRequester.getResponseUsingGET();

                insertCitation(conn, pubmedId, StringEscapeUtils.escapeHtml4(response.substring(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

    public static void insertCitation(Connection conn, String pubmedId, String citation) throws SQLException {
        String sql = "INSERT INTO `sciprod2014`.`publications_65` (`pubmedId`, `citation`) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pubmedId);
        stmt.setString(2, citation);
        stmt.execute();
    }
}
