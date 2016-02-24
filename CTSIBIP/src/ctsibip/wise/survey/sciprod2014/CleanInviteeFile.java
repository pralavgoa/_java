package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import au.com.bytecode.opencsv.CSVReader;
import ctsibip.shared.db.MySQLDataBank;

public class CleanInviteeFile {

    public static final String PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\"
            + "Current Surveys\\SCIPROD 2014\\invitees\\Invitee_List_rearranged.csv";

    public static MySQLDataBank db = new MySQLDataBank(new DataBankProperties());

    public static void main(String[] args) throws IOException, SQLException {
        Connection conn = db.getConnection();
        readInviteeFile(PATH, conn);
        conn.close();
    }

    public static void readInviteeFile(String path, Connection conn) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(path), ',');
        String[] nextLine;
        int lineNumber = 0;
        while ((nextLine = reader.readNext()) != null) {
            if (lineNumber == 0) {
                lineNumber++;
                continue;
            }
            Invitee invitee = new Invitee(nextLine);
            System.out.print(invitee.getFirstname());
            System.out.print(",");
            System.out.print(invitee.getLastname());
            System.out.print(",");
            System.out.print(invitee.getSalutation());
            System.out.print(",");
            System.out.print(invitee.getEmail());
            System.out.print(",");
            System.out.print(invitee.getPhone());
            System.out.print(",");
            System.out.print(invitee.getIrbId());
            System.out.print(",");
            System.out.print(invitee.getY1_voucher());
            System.out.print(",");
            System.out.print(invitee.getY2_voucher());
            System.out.print(",");
            System.out.print(invitee.getY1_salary());
            System.out.print(",");
            System.out.print(invitee.getY2_salary());
            System.out.print(",");
            System.out.print(invitee.getY1_pilot());
            System.out.print(",");
            System.out.print(invitee.getY2_pilot());
            System.out.print(",");
            System.out.print(invitee.getY1_biostats());
            System.out.print(",");
            System.out.print(invitee.getY2_biostats());
            System.out.print(",");
            System.out.print(invitee.getCtrc_support());
            System.out.print(",");
            System.out.print(invitee.getSupportText());
            System.out.print(",");
            System.out.print(invitee.getPubs_prepopulated());
            System.out.print(",");
            System.out.print(invitee.getGrants_prepopulated());
            System.out.print(",");
            System.out.print(invitee.getTech_xfers());
            System.out.print(",");
            System.out.println("");
            lineNumber++;
        }
    }

}
