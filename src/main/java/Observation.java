import lombok.Getter;

@Getter
public class Observation {
    // TODO: Make funny code that changes String, char, boolean into Float or Int
    private final Number[] numbers;
    private Number decisionAttribute;

    public Observation(Number[] numbers) {
        this.numbers = numbers;
    }
}