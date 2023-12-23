package com.framework.core.report;

import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.helper.Timestamp;

public class GetReport {
    private GetReport(){}
    private static Report report;

    private static void loadReport(){
        String reportType = FrameworkProperties.getFrameworkProperties().getProperty("reportType");
        String reportDir = FrameworkProperties.getFrameworkProperties().getProperty("reportDir");
        String reportFilePrefix = FrameworkProperties.getFrameworkProperties().getProperty("reportFilePrefix");
        String currentTimestamp = Timestamp.getCurrentTimeStamp();
        String reportFileName = reportFilePrefix + currentTimestamp;

        switch(reportType){
            case "text":
                report = new TextReport(reportDir,reportFileName);
                break;
            case "extent":
                report = ExtentReport.getInstance(reportDir,reportFileName);
                break;
            default:
                report = new ConsoleReport();
        }
    }

    public static Report getReport(){
        if(report == null){
            loadReport();
        }
        return report;
    }
}
