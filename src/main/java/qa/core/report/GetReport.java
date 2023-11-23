package qa.core.report;

import qa.core.utils.FrameworkProperties;
import qa.core.utils.Timestamp;

public class GetReport {
    public GetReport(){

    }
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
