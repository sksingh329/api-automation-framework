package qa.core.utils.testdata;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import qa.core.exceptions.FrameworkException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CsvDataReader {
    private CsvDataReader(){}
    public static List<HashMap<String, String>> readCsv(FileReader filePath) {
        List<HashMap<String,String>> result = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(String.valueOf(filePath)))){
            List<String[]> data = reader.readAll();

            for (int rowIndex = 1; rowIndex < data.size(); rowIndex++) {
                String[] row = data.get(rowIndex);

                HashMap<String, String> rowData = new HashMap<>();
                for(int i = 0; i < row.length; i++){
                    rowData.put(data.get(0)[i], row[i]);
                }
                result.add(rowData);
            }
            return result;
        } catch (IOException | CsvException ex) {
            throw new FrameworkException(ex.toString());
        }
    }
}
