package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringEscapeUtils;

import au.com.bytecode.opencsv.CSVReader;
import ctsibip.shared.db.MySQLDataBank;

public class InsertPublicationsInDatabase {

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static void main(String[] args) throws SQLException, IOException {
        readAuthorsToPubsFile("files/sciprod2014/final_pubId_title_desc_auth.pipe");
    }

    public static void readAuthorsToPubsFile(String inputFile) throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader(inputFile), '|');
        String[] nextLine;
        Connection conn = db.getConnection();
        while ((nextLine = reader.readNext()) != null) {
            String pubmedId = nextLine[0];
            String title = nextLine[1];
            String authors = nextLine[2];
            String citation = StringEscapeUtils.escapeHtml4(nextLine[3]);
            System.out.print(pubmedId);
            String sql = "INSERT INTO `sciprod2014`.`publications` (`pubmed_id`, `title`, `authors`, `citation`)"
                    + " VALUES (?, ?, ?, ?)";
            db.insert(sql, conn, pubmedId, title, authors, citation);
        }
        conn.close();
    }
}
