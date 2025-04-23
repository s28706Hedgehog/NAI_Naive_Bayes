package utils;

import dataStructures.FileContent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Futil {
    public static String loadFileData(Path filePath) {
        String fileContent = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
            fileContent = br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            System.out.println("Failed to load file data: " + e.getMessage());
        }

        return Objects.requireNonNull(fileContent);
    }

    public static FileContent retrieveFileContent(String separator, boolean hasHeaders, String filePath) {
        String[] headers = null;
        ArrayList<Double[]> rowsData = null;
        ArrayList<String> decisionAttributes = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            if (hasHeaders) {
                headers = bufferedReader.readLine().split(separator);
            }
            String line;
            rowsData = new ArrayList<>();
            decisionAttributes = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(separator);
                int columns = split.length;
                Double[] rowAttributes = new Double[columns - 1];
                for (int i = 0; i < columns - 1; i++) {
                    rowAttributes[i] = Double.parseDouble(split[i].replace(",", ".").trim());
                }
                rowsData.add(rowAttributes);

                decisionAttributes.add(split[columns - 1].trim());
            }
        } catch (IOException e) {
            System.out.println("Failed to load rows data from file: " + e.getMessage());
        }

        return new FileContent(headers, rowsData, decisionAttributes); // idc if you are null or not :D
    }
}