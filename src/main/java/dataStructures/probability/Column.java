package dataStructures.probability;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Column {
    private final ArrayList<SingleColumnProbability> probabilities = new ArrayList<>();

    // Add or count
    public void addProbability(Double attribute, String decisionAttribute) {
        for (SingleColumnProbability scp : this.probabilities) {
            if (scp.checkIfFits(attribute, decisionAttribute)) {
                scp.increaseMatchingAttributesCounter();
                return;
            }
        }
        this.probabilities.add(new SingleColumnProbability(attribute, decisionAttribute));
    }

    public int getUniqueColumnAttributesCount() {
        ArrayList<SingleColumnProbability> uniqueAttributes = new ArrayList<>();

        for (SingleColumnProbability scp : this.probabilities) {
            boolean found = false;
            for (SingleColumnProbability uniqueScp : uniqueAttributes) {
                // Check if the same attribute occurs
                if (uniqueScp.checkIfFits(scp.getAttribute())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                uniqueAttributes.add(scp);
            }
        }
        return uniqueAttributes.size();
    }
}