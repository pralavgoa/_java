package pralav.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PubMedParser {

    public static void main(String[] args) throws IOException {
        File input = new File("testFiles/pubs.htm");

        // getPubMedIdsAndTitles(input);

        // getPubMedIdsAndAuthors(input);

        File file1 = new File("output/pubId_title.pipe");
        File file2 = new File("output/Title_Desc_Pub.pipe");
        mergeIdToDescFiles(file1, file2);

    }

    private static void mergeIdToDescFiles(File file1, File file2) throws IOException {
        List<String> file1Lines = Files.readLines(file1, Charsets.UTF_8);
        List<String> file2Lines = Files.readLines(file2, Charsets.UTF_8);

        if (file1Lines.size() != file2Lines.size()) {
            return;
        }

        for (int i = 0; i < file1Lines.size(); i++) {
            System.out.println(file1Lines.get(i) + " | " + file2Lines.get(i));
        }
    }

    private static void getPubMedIdsAndAuthors(File input) throws IOException {
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements rows = doc.select("tr");

        int rowNumber = 0;
        for (Element row : rows) {
            Elements columns = row.select("td");
            int columnNumber = 0;
            for (Element column : columns) {
                if ((columnNumber++ == 0) && (rowNumber != 0) && ((rowNumber++ % 3) == 1)) {
                    Elements anchors = column.select("a");
                    System.out.print(anchors.get(0).attr("href") + " | ");
                    System.out.print(column.text() + " | ");
                }
            }
            if ((rowNumber++ % 3) == 0) {
                System.out.println("");
            }
        }

    }

    private static void getPubMedIdsAndTitles(File input) throws IOException {
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements anchors = doc.select("a");

        for (Element element : anchors) {
            String link = element.attr("href");
            String[] split = link.split("/");
            System.out.print(split[split.length - 1] + " | ");
            System.out.println(element.text());
        }

        System.out.println("There are " + anchors.size() + " anchors");
    }
}
