package qa.core.utils.testdata;

import qa.core.exceptions.FrameworkException;
import qa.core.utils.files.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReadTestData {
    private ReadTestData(){}

    public static Object[][] readTestData(String testDataFilePath){

        List<HashMap<String,String>> result;

        // Find the last occurrence of '.'
        String fileType = null;
        int lastDotIndex = testDataFilePath.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < testDataFilePath.length() - 1) {
            fileType  = testDataFilePath.substring(lastDotIndex + 1);
        }

        switch (Objects.requireNonNull(fileType)){
            case "csv":
                FileReader testDataFileReader = FileUtils.getFileReader(testDataFilePath);
                result = CsvDataReader.readCsv(testDataFileReader);
                break;
            case "json":
                File testDataFile = FileUtils.getFile(testDataFilePath);
                result = JsonDataReader.readJson(testDataFile);
                break;
            default:
                throw new FrameworkException("Invalid test data file type.");
        }

        // Convert List<HashMap<String, String>> to Object[][]
        Object[][] testData = new Object[result.size()][1];
        for (int i = 0; i < result.size(); i++) {
            testData[i][0] = result.get(i);
        }
        return testData;
    }
}
