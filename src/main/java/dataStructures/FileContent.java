package dataStructures;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class FileContent {
    private final int columns;
    private final String[] headers;
    private final ArrayList<Double[]> rowsData;
    private final ArrayList<String> decisionAttributes;

    public FileContent(String[] headers, ArrayList<Double[]> rowsData, ArrayList<String> decisionAttributes) {
        this.headers = (headers == null ? new String[rowsData.getFirst().length + 1] : headers);
        // if empty headers, JTable can handle this giving random names such as A,B,C

        this.rowsData = rowsData;
        this.decisionAttributes = decisionAttributes;
        this.columns = rowsData.getFirst().length;
    }
}