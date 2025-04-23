package dataStructures.probability;

import lombok.Getter;

@Getter
public class SingleColumnProbability {
    private static final double RANGE = 0.0;
    /*
        0.0 <- 4 wrong, 26 correct
        0.1 <- 6 wrong, 24 correct
        0.2 <- 2 wrong, 28 correct
        0.3 <- 1 wrong, 29 correct
        0.4 <- 3 wrong, 27 correct
        0.5 <- 4 wrong, 26 correct
    */
    private final String decisionAttribute;
    private int matchingAttributesCounter;
    private final Double attribute;

    public SingleColumnProbability(Double attribute, String decisionAttribute) {
        this.matchingAttributesCounter = 1;
        this.decisionAttribute = decisionAttribute;
        this.attribute = attribute;
    }

    public boolean checkIfFits(Double parAttribute, String parDecisionAttribute) {
        if (parDecisionAttribute.equals(this.decisionAttribute)) {
            return checkIfFits(parAttribute);
        }
        return false; // new SingleColumnProbability required
    }

    public boolean checkIfFits(Double parAttribute) {
        return (this.attribute - RANGE <= parAttribute && this.attribute + RANGE >= parAttribute);
    }

    public void increaseMatchingAttributesCounter() {
        this.matchingAttributesCounter++;
    }

    @Override
    public String toString() {
        return "SCP[ DecisionAttrib: " + this.decisionAttribute + " | attribute: " + attribute + " | count: " + this.matchingAttributesCounter + "]";
    }
}