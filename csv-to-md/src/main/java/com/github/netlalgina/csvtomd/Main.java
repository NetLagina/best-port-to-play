package com.github.netlalgina.csvtomd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    private static final char CSV_DELIMITER = ';';
    private static final int CSV_COLUMNS = 10;
    private static final String CSV_FORBIDDEN_SYMBOLS_REGEX = "[ /\\:*?\"<>|\0]+";
    private static final String CSV_LIST_MARKDOWN_REGEX = "(?<=.)(\\*)";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: args length should be 2, got " + args.length);
            System.exit(1);
        }

        final Path path = Path.of(args[0]);
        if(!Files.exists(path)) {
            System.out.println("ERROR: file " + path + " doesn't exists");
            System.exit(2);
        }

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("ERROR: while reading " + path + " file");
            System.exit(3);
        }

        for (var line : lines) {
            var elements = line.split(String.valueOf(CSV_DELIMITER));
            if (elements.length > CSV_COLUMNS) {
                System.out.println("ERROR: can't parse csv-file, too many columns in line " + elements[0] + ", should be less or equals to " + CSV_COLUMNS);
                System.exit(4);
            }
            Path writeFile = Path.of(args[1] + makeFileName(elements[0]) + ".md");
            try {
                Files.deleteIfExists(writeFile);
                Files.createFile(writeFile);
                Files.writeString(
                        writeFile,
                        "---" + System.lineSeparator() +
                            "title: \"" + elements[0] + "\"" + System.lineSeparator() +
                            "weight: 1" + System.lineSeparator() +
                            "draft: false" + System.lineSeparator() +
                            "---" + System.lineSeparator(),
                        StandardOpenOption.APPEND);
                Files.writeString(writeFile, "# " + elements[0] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);

                if (elements.length > 1 && !elements[1].isEmpty()) {
                    Files.writeString(writeFile, "## Also known as" + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, elements[1] + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                }

                Files.writeString(writeFile, "## Platforms" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[2] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);

                Files.writeString(writeFile, "## Fullest version" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[3] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);

                if (elements.length > 4 && !elements[4].isEmpty()) {
                    Files.writeString(writeFile, "### Comment" + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, elements[4] + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                }

                Files.writeString(writeFile, "## Most stable version" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[5] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);

                if (elements.length > 6 && !elements[6].isEmpty()) {
                    Files.writeString(writeFile, "### Comment" + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, elements[6] + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                }

                Files.writeString(writeFile, "## Best look" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[7] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);

                if (elements.length > 8 && !elements[8].isEmpty()) {
                    Files.writeString(writeFile, "### Comment" + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, elements[8] + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                }

                if (elements.length > 9 && !elements[9].isEmpty()) {
                    Files.writeString(writeFile, "## Additional links" + System.lineSeparator(), StandardOpenOption.APPEND);
                    Files.writeString(
                            writeFile,
                            elements[9].replaceAll(CSV_LIST_MARKDOWN_REGEX, System.lineSeparator() + "*") + System.lineSeparator(),
                            StandardOpenOption.APPEND);
                }
            } catch (IOException e) {
                System.out.println("ERROR: while writing to file " + writeFile);
                System.exit(5);
            }
        }

    }

    private static String makeFileName(String name) {
        return name.toLowerCase(Locale.ENGLISH).replaceAll(CSV_FORBIDDEN_SYMBOLS_REGEX, "_");
    }

}