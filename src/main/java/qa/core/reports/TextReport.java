package qa.core.reports;

import qa.core.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextReport extends Report{
    private final String textReportFileName;
    private final String textReportSubDir = "/txt/";
    private final StringBuilder logBuffer;

    public TextReport(String reportDir, String reportFileName){
        String textReportDir = reportDir + textReportSubDir;
        FileUtils.createDir(textReportDir);
        textReportFileName =  textReportDir + reportFileName + ".txt";
        logBuffer = new StringBuilder();
    }
    public void log(ReportLevel level,String step,String detail){
        String message = step + " : " + detail;
        String logEntry = String.format("%s: %s%n", level, message);
        logBuffer.append(logEntry);
    }
    public void log(ReportLevel level,String message){
        String logEntry = String.format("%s: %s%n", level, message);
        logBuffer.append(logEntry);
    }
    public void save(){
        saveLogsToFile();
    }
    private void saveLogsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(textReportFileName)))) {
            writer.write(logBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
