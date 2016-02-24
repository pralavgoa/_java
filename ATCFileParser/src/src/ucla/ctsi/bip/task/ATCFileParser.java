package ucla.ctsi.bip.task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.io.Files;

public class ATCFileParser {

    public static final String DELIMITER = "|";
    public static final String ROOT_PATH = "\\SHRINE\\Medications - Anatomical Therapeutic Chemical  (ATC)\\";

    private static ArrayListMultimap<String, String> atcToDoseFormMap = ArrayListMultimap.create();

    public static void main(String[] args) throws IOException {

        populateAtcToDoseFormMap();

        generateModifiedFile();

    }

    private static void generateModifiedFile() throws IOException {

        File file = new File("data/dataFile.txt");
        List<String> lines = Files.readLines(file, Charsets.UTF_8);

        String currA = "", currB = "", currC = "", currD = "", currE = "", currF = "";

        for (String line : lines) {

            if (line.contains("#")) {
                continue;
            }

            if (line.charAt(0) == 'A') {
                line = "A " + line.substring(1);
            }

            String[] lineParts = line.split("\\s+");

            StringBuilder lineRemaining = new StringBuilder();
            for (int i = 1; i < lineParts.length; i++) {

                String modifiedString = lineParts[i];
                if ((i != 1)) {
                    if (i == 2) {
                        lineRemaining.append("- ");
                    }
                    modifiedString = modifyString(lineParts[i]);
                }
                lineRemaining.append(modifiedString + " ");
            }

            int atcLevel = (lineParts[0].charAt(0) - 64);
            String atcCode = lineParts[1].trim();

            // skip all 'F'
            if (atcLevel == 6) {
                continue;
            }

            System.out.print(atcLevel + DELIMITER);
            System.out.print(atcCode + DELIMITER);
            System.out.print(lineRemaining.toString().trim() + DELIMITER);

            switch (lineParts[0].charAt(0)) {
            case ('A'):
                currA = ROOT_PATH + lineRemaining.toString().trim();
                System.out.println(currA + "\\");
                break;
            case ('B'):
                currB = lineRemaining.toString().trim();
                System.out.println(currA + "\\" + currB + "\\");
                break;
            case ('C'):
                currC = lineRemaining.toString().trim();
                System.out.println(currA + "\\" + currB + "\\" + currC + "\\");
                break;
            case ('D'):
                currD = lineRemaining.toString().trim();
                System.out.println(currA + "\\" + currB + "\\" + currC + "\\" + currD + "\\");
                break;
            case ('E'):
                currE = lineRemaining.toString().trim();
                System.out.println(currA + "\\" + currB + "\\" + currC + "\\" + currD + "\\" + currE + "\\");
                List<String> doseForms = atcToDoseFormMap.get(atcCode);
                if (!doseForms.isEmpty()) {
                    for (String doseForm : doseForms) {
                        System.out.print((atcLevel + 1) + DELIMITER);
                        System.out.print(atcCode + DELIMITER);

                        String[] doseFormParts = doseForm.split("\\" + DELIMITER);
                        if (doseFormParts.length != 2) {
                            System.out.println("Dose form :" + doseForm);
                            throw new IllegalArgumentException();
                        }
                        System.out.print(doseFormParts[0] + DELIMITER);
                        System.out.print(currA + "\\" + currB + "\\" + currC + "\\" + currD + "\\" + currE + "\\"
                                + doseFormParts[0] + "\\" + DELIMITER + doseFormParts[1]);
                        System.out.println();
                    }
                }
                break;
            case ('F'):
                currF = lineRemaining.toString().trim();
                System.out.println();
                // System.out.println(currA + "\\" + currB + "\\" + currC + "\\"
                // + currD + "\\" + currE + "\\" + currF);
                break;
            default:
                System.out.println(lineParts[0]);
                throw new IllegalStateException();

            }
        }

    }

    private static void populateAtcToDoseFormMap() throws IOException {
        File file = new File("data/export_may8.csv");
        List<String> lines = Files.readLines(file, Charsets.UTF_8);

        System.out.println("The file has " + lines.size() + " lines");

        for (String line : lines) {
            String[] lineParts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if (lineParts.length != 4) {
                System.out.println(line);
                throw new IllegalArgumentException();
            }
            String firstLinePart = lineParts[0].trim();
            String atcCode = firstLinePart.substring(1, firstLinePart.length() - 1);

            String doseFormPart = lineParts[2].trim();
            String doseForm = doseFormPart.substring(1, doseFormPart.length() - 1);

            String doseFormCodePart = lineParts[3].trim();
            String doseFormCode = doseFormCodePart.substring(1, doseFormCodePart.length() - 1);

            atcToDoseFormMap.put(atcCode, doseForm + DELIMITER + doseFormCode);

        }

        System.out.println("The map has " + atcToDoseFormMap.size() + " keys");

    }

    private static String modifyString(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
}
