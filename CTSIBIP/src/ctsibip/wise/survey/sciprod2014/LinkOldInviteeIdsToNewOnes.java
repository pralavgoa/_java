package ctsibip.wise.survey.sciprod2014;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;

public class LinkOldInviteeIdsToNewOnes {

    public static final String BASE_PATH = "C:\\_Pralav\\projects\\WISE and LOFTS\\Current Surveys\\SCIPROD 2014\\";

    public static final String PATH = BASE_PATH + "invitees\\2013_inviteeId_to_email.csv";
    public static final String PATH2 = BASE_PATH + "invitees\\2014_inviteeId_to_email.csv";

    public static void main(String[] args) throws IOException {

        Map<String, InviteeId> emailToInviteeId = new HashMap<>();

        CSVReader reader = new CSVReader(new FileReader(PATH), ',');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String oldId = nextLine[0];
            String email = nextLine[1].trim().toLowerCase();
            InviteeId invId = new InviteeId();
            invId.oldId = oldId;
            emailToInviteeId.put(email, invId);
        }

        CSVReader reader2 = new CSVReader(new FileReader(PATH2), ',');
        String[] nextLine2;
        int repeatedInvitees = 0;
        while ((nextLine2 = reader2.readNext()) != null) {
            String newId = nextLine2[0];
            String email = nextLine2[1].trim().toLowerCase();
            InviteeId invId = emailToInviteeId.get(email);

            if (invId != null) {
                invId.newId = newId;
                repeatedInvitees++;
            }
        }

        System.out.println("There are " + repeatedInvitees + " repeated invitees");

        System.out.println("There are " + emailToInviteeId.size() + " emails in the map");
        for (Entry<String, InviteeId> entry : emailToInviteeId.entrySet()) {
            System.out.print(entry.getKey() + ",");
            System.out.println(entry.getValue());
        }

    }

    static class InviteeId {
        String oldId;
        String newId;

        @Override
        public String toString() {
            return this.oldId + "," + this.newId;
        }
    }
}

/**
 * There are 257 repeated invitees
 */
