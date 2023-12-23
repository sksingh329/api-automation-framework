package com.framework.core.report;

import com.framework.core.utils.files.FileUtils;
import com.framework.core.utils.properties.FrameworkProperties;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextReport extends Report{
    private final String textReportFileName;
    private final StringBuilder logBuffer;

    public TextReport(String reportDir, String reportFileName){
        String textReportSubDir = FrameworkProperties.getFrameworkProperties().getProperty("textReportSubDir");
        String textReportDir = reportDir + textReportSubDir;
        FileUtils.createDir(textReportDir);
        textReportFileName =  textReportDir + reportFileName + ".txt";
        logBuffer = new StringBuilder();
    }
    public void log(String testMethodName,ReportLevel level,String step,String detail){
        String message = step + " : " + detail;
        String logEntry = String.format("%s : %s : %s%n", testMethodName,level, message);
        logBuffer.append(logEntry);
    }
    public void log(String testMethodName,ReportLevel level,String message){
        String logEntry = String.format("%s : %s : %s%n", testMethodName,level, message);
        logBuffer.append(logEntry);
    }
    public void save(){
        saveLogsToFile();
    }
    private void saveLogsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textReportFileName))) {
            writer.write(logBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void testStart(String testMethodName){
        logBuffer.append("***" + ReportLevel.INFO + " : " + "Test method " + testMethodName + " started." + "***\n");
    }
}
