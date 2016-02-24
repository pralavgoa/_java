package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import au.com.bytecode.opencsv.CSVReader;
import ctsibip.shared.db.MySQLDataBank;

public class InsertAuthorsAndPubmedIdsInDatabase {
    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static void main(String[] args) throws SQLException, IOException {
        readAuthorsToPubsFile("files/sciprod2014/authorsToPubsIndex.pipe");
    }

    public static void readAuthorsToPubsFile(String inputFile) throws IOException, SQLException {
        CSVReader reader = new CSVReader(new FileReader(inputFile), ':');
        String[] nextLine;
        Connection conn = db.getConnection();
        while ((nextLine = reader.readNext()) != null) {
            String author = nextLine[0];
            String[] pubmedIds = nextLine[1].split(",");
            for (String pubmedId : pubmedIds) {
                System.out.println(author + "," + pubmedId);
                String sql = "INSERT INTO `sciprod2014`.`authors_pubmedId` (`author`, `pubmedId`) VALUES (?, ?)";
                db.insert(sql, conn, author.trim(), pubmedId.trim());

            }
        }
        conn.close();
    }
}
