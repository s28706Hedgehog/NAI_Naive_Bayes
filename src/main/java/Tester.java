import dataStructures.FileContent;
import dataStructures.probability.ProbabilityFileContent;

import javax.swing.*;
import java.util.*;

public class Tester {
    private ProbabilityFileContent trainProbFileCont;

    public Tester(ProbabilityFileContent trainProbabilityFileContent) {
        this.trainProbFileCont = trainProbabilityFileContent;
    }

    public void test(FileContent testFileContent) {
        ArrayList<Double[]> rowsData = testFileContent.getRowsData();
        int wrongCounter = 0, correctCounter = 0;

        // < realPred, <gotPred, count>>
        Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();

        for (int rowIndex = 0; rowIndex < rowsData.size(); rowIndex++) {
            String prediction = this.trainProbFileCont.getPrediction(rowsData.get(rowIndex));
            String realDecAttrib = testFileContent.getDecisionAttributes().get(rowIndex);

            Map<String, Integer> innerConf = confusionMatrix
                    .computeIfAbsent(realDecAttrib, k -> new HashMap<>());

            int currentCount = innerConf.getOrDefault(prediction, 0);
            innerConf.put(prediction, currentCount + 1); // just ++

            if (!prediction.equals(realDecAttrib)) {
                System.out.println("\tWrong! got: " + prediction + " | should be: " + realDecAttrib);
                wrongCounter++;
            } else {
                System.out.println("Expected: " + prediction + " | got: " + realDecAttrib);
                correctCounter++;
            }
        }
        System.out.println("Total wrong: " + wrongCounter + " | " + "Total correct: " + correctCounter);
        System.out.println("Accuracy: " + (double) correctCounter / (correctCounter + wrongCounter));
        showConfusionMatrixSwing(confusionMatrix);
    }

    private void showConfusionMatrixSwing(Map<String, Map<String, Integer>> confMatrix) {
        List<String> labelList = new ArrayList<>(confMatrix.keySet());

        String[] columns = new String[labelList.size() + 1];
        columns[0] = "Real decAttrib || Pred decAttrib";
        for (int i = 0; i < labelList.size(); i++) {
            columns[i + 1] = labelList.get(i);
        }

        Object[][] data = new Object[labelList.size()][labelList.size() + 1];
        for (int i = 0; i < labelList.size(); i++) {
            String real = labelList.get(i);
            data[i][0] = real;
            for (int j = 0; j < labelList.size(); j++) {
                String pred = labelList.get(j);
                int count = confMatrix
                        .getOrDefault(real, Collections.emptyMap())
                        .getOrDefault(pred, 0);
                data[i][j + 1] = count;
            }
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Confusion Matrix");

            JTable table = new JTable(data, columns);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(table));

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}