package pralav.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;

public class PubMedIndexBuilder {

    public static void main(String[] args) throws IOException {
        File file1 = new File("output/final_pubId_title_desc_auth.pipe");

        List<String> lines = Files.readLines(file1, Charsets.UTF_8);

        Map<String, List<String>> authorsToPubsIndex = new TreeMap<>();

        for (String line : lines) {
            String[] lineSplit = line.split("\\|");

            String pubmedId = lineSplit[0].trim();
            String authorsCommaSeparated = lineSplit[2];
            String authors[] = authorsCommaSeparated.split(",");

            // System.out.println(pubmedId);

            for (String author : authors) {
                // System.out.print(author.trim() + " ");
                addAuthorAndPubToIndex(author.trim(), pubmedId, authorsToPubsIndex);
            }

            // System.out.println("");

        }

        printAuthorsToPubIndex(authorsToPubsIndex);
    }

    private static void printAuthorsToPubIndex(Map<String, List<String>> authorsToPubsIndex) {

        for (String author : authorsToPubsIndex.keySet()) {
            System.out.print(author + " : ");
            for (String pubmedId : authorsToPubsIndex.get(author)) {
                System.out.print(pubmedId + ",");
            }
            System.out.println("");
        }

    }

    private static void addAuthorAndPubToIndex(String author, String pubmedId,
            Map<String, List<String>> authorsToPubsIndex) {

        if (Strings.isNullOrEmpty(author)) {
            return;
        }
        if (authorsToPubsIndex.containsKey(author)) {

            authorsToPubsIndex.get(author).add(pubmedId);

        } else {
            List<String> list = new ArrayList<>();
            list.add(pubmedId);
            authorsToPubsIndex.put(author, list);
        }

    }
}
