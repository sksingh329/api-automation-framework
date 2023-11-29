package qa.core.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import qa.core.exceptions.FrameworkException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVDataReader {
    private CSVDataReader(){

    }
    public static Object[][] readCSV(String filePath) {

        try (CSVReader reader = new CSVReader(new FileReader(filePath))){
            List<String[]> data = reader.readAll();

            // Skip the first row (header)
            data.remove(0);

            // Convert List<String[]> to Object[][]
            Object[][] result = new Object[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                result[i] = data.get(i);
            }

            return result;
        } catch (IOException | CsvException ex) {
            throw new FrameworkException(ex.toString());
        }

    }
}
