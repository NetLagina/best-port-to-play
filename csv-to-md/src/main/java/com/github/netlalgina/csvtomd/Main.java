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
    private static final int CSV_COLUMNS = 9;
    private static final String CSV_FORBIDDEN_SYMBOLS_REGEX = "[ /\\:*?\"<>|\0]+";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(1);
            System.exit(1);
        }

        final Path path = Path.of(args[0]);
        if(!Files.exists(path)) {
            System.out.println(2);
            System.exit(2);
        }

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println(3);
            System.exit(3);
        }

        for (var line : lines) {
            var elements = line.split(String.valueOf(CSV_DELIMITER));
            if (elements.length != CSV_COLUMNS) {
                System.out.println(4);
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
                Files.writeString(writeFile, "## Also known as" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[1] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "## Platforms" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[2] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "## Fullest version" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[3] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "### Comment" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[4] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "## Most stable version" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[5] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "### Comment" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[6] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "## Best look" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[7] + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, "### Comment" + System.lineSeparator(), StandardOpenOption.APPEND);
                Files.writeString(writeFile, elements[8] + System.lineSeparator(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println(5);
                System.exit(5);
            }
        }

    }

    private static String makeFileName(String name) {
        return name.toLowerCase(Locale.ENGLISH).replaceAll(CSV_FORBIDDEN_SYMBOLS_REGEX, "_");
    }

}