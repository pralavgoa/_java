package ctsibip.wise.survey.sciprod2014;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class DummyDataPrepopulate {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readLines(new File("files/sciprod2014/dummyInviteeData.txt"), Charsets.UTF_8);

        for (int i = 24; i < 25; i++) {
            for (String line : lines) {
                System.out.println(line.replaceAll("insert_invitee_id", "" + i));
            }
        }

    }
}
