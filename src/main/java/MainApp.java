import dataStructures.FileContent;
import dataStructures.probability.ProbabilityFileContent;
import utils.Futil;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        String userPath = System.getProperty("user.dir");
        String testFilePath = userPath + "\\src\\main\\resources\\iris_test.txt";
        String trainFilePath = userPath + "\\src\\main\\resources\\iris_training.txt";

        // String sampleTrainFilePath = userPath + "\\src\\main\\resources\\sample_train.txt";
        FileContent trainFileContent = Futil.retrieveFileContent("\t", false, trainFilePath);
        FileContent testFileContent = Futil.retrieveFileContent("\t", false, testFilePath);

        ProbabilityFileContent probabilityFileContent = new ProbabilityFileContent(trainFileContent);
        probabilityFileContent.printContent();
        // Double[] arr = {6.3, 3.3, 6.0, 2.5};
        Tester tester = new Tester(probabilityFileContent);
        tester.test(testFileContent);

        System.out.println("================================================");
        System.out.println("\nNow test your own data, enter 4 numbers using ';' as separator");
        System.out.println("To quit type Q\n");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.next();
        while (!userResponse.equals("Q")) {
            String[] tmp = userResponse.split(";");
            System.out.println("Tmp length: " + tmp.length);
            Double[] usrData = new Double[tmp.length];
            for (int i = 0; i < usrData.length; i++) {
                usrData[i] = Double.parseDouble(tmp[i]);
            }
            System.out.println("Magical predicition: " + probabilityFileContent.getPrediction(usrData));
            userResponse = scanner.next();
        }

        // System.out.println("Prediction: " + probabilityFileContent.getPrediction(arr));
        //  6,3    	 3,3    	 6,0    	 2,5    	 Iris-virginica
    }
}