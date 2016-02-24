package ucla.ctsi.bip.task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ATCFileFolderStructurer {

    private static final String INTERMEDIATE_FILE = "results/intermediate/may8.txt";
    private static final String FINAL_FILE = "results/may8_final.pipe";

    public static void main(String[] args) throws IOException {
        generateModifiedFile();
        reverseFile();
        System.out.println("done");
    }

    private static void generateModifiedFile() throws IOException {
        File file = new File("results/8May.pipe");
        List<String> lines = Files.readLines(file, Charsets.UTF_8);

        boolean active1 = false;
        boolean active2 = false;
        boolean active3 = false;
        boolean active4 = false;
        boolean active5 = false;

        boolean active6 = false;

        for (int i = lines.size() - 1; i >= 0; i--) {

            String line = lines.get(i);
            char rowLevel = line.charAt(0);

            switch (rowLevel) {
            case '1':
                printAccordingToCount(active1, line);
                active1 = false;
                active2 = false;
                active3 = false;
                active4 = false;
                active5 = false;
                active6 = false;
                break;
            case '2':
                printAccordingToCount(active2, line);
                active2 = false;
                active3 = false;
                active4 = false;
                active5 = false;
                active6 = false;
                break;
            case '3':
                printAccordingToCount(active3, line);
                active3 = false;
                active4 = false;
                active5 = false;
                active6 = false;
                break;
            case '4':
                printAccordingToCount(active4, line);
                active4 = false;
                active5 = false;
                active6 = false;
                break;
            case '5':
                printAccordingToCount(active5, line);
                active5 = false;
                active6 = false;
                break;
            case '6':
                if (active6) {
                    // no count update
                } else {
                    active1 = true;
                    active2 = true;
                    active3 = true;
                    active4 = true;
                    active5 = true;
                    active6 = true;
                }
                processLine(line + "|" + "LA");
                break;
            default:
                throw new IllegalStateException();
            }

        }
    }

    public static void printAccordingToCount(boolean active, String line) throws IOException {
        if (active) {
            processLine(line + "|" + "FA");
        } else {
            processLine(line + "|" + "FH");
        }
    }

    private static void reverseFile() throws IOException {
        File file = new File(INTERMEDIATE_FILE);
        List<String> lines = Files.readLines(file, Charsets.UTF_8);

        File resultFile = new File(FINAL_FILE);
        for (int i = lines.size() - 1; i >= 0; i--) {
            Files.append(lines.get(i) + "\n", resultFile, Charsets.UTF_8);
        }

    }

    private static void processLine(String outputLine) throws IOException {
        Files.append(outputLine + "\n", new File(INTERMEDIATE_FILE), Charsets.UTF_8);
    }
}
