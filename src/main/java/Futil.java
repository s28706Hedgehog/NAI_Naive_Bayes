import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Futil {
    public static String loadFileData(Path filePath){
        String fileContent = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
            fileContent =  br.lines().collect(Collectors.joining(System.lineSeparator()));

        }catch(IOException e){
            System.out.println("Failed to load file data: " + e.getMessage());
        }

        return fileContent;
    }
}
