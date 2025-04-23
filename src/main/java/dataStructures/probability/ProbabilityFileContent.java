package dataStructures.probability;

import dataStructures.FileContent;
import lombok.Getter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ProbabilityFileContent {
    private final ArrayList<Column> columns;
    private final int totalObservations;
    private final Map<String, Integer> decisionAttribCounter = new HashMap<>();
    private final boolean printSmoothie = false;

    public ProbabilityFileContent(FileContent fileContent) {
        this.columns = new ArrayList<>();
        int columnsCount = fileContent.getColumns();

        // Initiate arr list
        for (int i = 0; i < columnsCount; i++) {
            this.columns.add(new Column());
        }

        // Count decisionAttrib
        for (String s : fileContent.getDecisionAttributes()) {
            if (this.decisionAttribCounter.containsKey(s)) {
                this.decisionAttribCounter.replace(s, this.decisionAttribCounter.get(s) + 1);
            } else {
                this.decisionAttribCounter.put(s, 1);
            }
        }

        ArrayList<Double[]> rowsData = fileContent.getRowsData();
        this.totalObservations = rowsData.size();
        for (int rowIndex = 0; rowIndex < rowsData.size(); rowIndex++) { // iterate over rows
            for (int columnIndex = 0; columnIndex < columnsCount; columnIndex++) {  // iterate over columns
                this.columns.get(columnIndex).addProbability(
                        rowsData.get(rowIndex)[columnIndex], // attribute
                        fileContent.getDecisionAttributes().get(rowIndex) // decision attribute
                );
            }
        }
    }

    public void printContent() {
        System.out.println("Total rows: " + this.totalObservations);
        System.out.println("Decision attrib count:");
        for (Map.Entry<String, Integer> entry : this.decisionAttribCounter.entrySet()) {
            System.out.println("\t" + entry.getKey() + " | " + entry.getValue());
        }

        for (int i = 0; i < columns.size(); i++) {
            System.out.println("Column: " + i);
            for (SingleColumnProbability scp : this.columns.get(i).getProbabilities()) {
                System.out.println("\t" + scp);
            }
            System.out.println();
        }
    }

    public String getPrediction(Double[] data) {
        Map<String, Double> decisAttribPrediction = new HashMap<>();
        for (String decAttrib : this.decisionAttribCounter.keySet()) {
            int countDecAttrib = this.decisionAttribCounter.get(decAttrib);
            double chance = (double) countDecAttrib / this.totalObservations;

            for (int colIndex = 0; colIndex < this.columns.size(); colIndex++) {
                boolean foundAny = false;
                for (SingleColumnProbability scp : this.columns.get(colIndex).getProbabilities()) {
                    if (scp.checkIfFits(data[colIndex], decAttrib)) {
                        // That's decision attribute we are looking for
                        chance *= (double) scp.getMatchingAttributesCounter() / countDecAttrib;
                        foundAny = true;
                        break;
                    }
                }
                if (!foundAny) {
                    // REQUIRES SMOOTHIE :D
                    if (printSmoothie) System.out.println("Before smoothie: 0");
                    double currentChance = (double) 1 / (countDecAttrib + this.columns.get(colIndex).getUniqueColumnAttributesCount());
                    if (printSmoothie) System.out.println("After delicious smoothie :D " + currentChance);
                    chance *= currentChance;
                }
            }
            decisAttribPrediction.put(decAttrib, chance);
        }
        // Now look for the biggest change and return it's decAttrib
        return findBiggest(decisAttribPrediction);
    }

    private String findBiggest(Map<String, Double> decAttribPrediction) {
        double max = 0.0;
        String maxDeciAttrib = "Not Found";
        for (String s : decAttribPrediction.keySet()) {
            if (max < decAttribPrediction.get(s)) {
                max = decAttribPrediction.get(s);
                maxDeciAttrib = s;
            }
        }
        DecimalFormat df = new DecimalFormat("#.######");
        System.out.println("Max chance: " + df.format(max));
        return maxDeciAttrib;
    }
}
