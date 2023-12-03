package qa.core.utils.testdata;

import qa.core.utils.files.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ReadTestData {
    private ReadTestData(){}

    public static Object[][] readTestData(String testDataFilePath){
        File testDataFile = FileUtils.getFile(testDataFilePath);
        List<HashMap<String,String>> result = CsvDataReader.readCSV(testDataFile.getAbsolutePath());

        // Convert List<HashMap<String, String>> to Object[][]
        Object[][] testData = new Object[result.size()][1];
        for (int i = 0; i < result.size(); i++) {
            testData[i][0] = result.get(i);
        }
        return testData;
    }
}
