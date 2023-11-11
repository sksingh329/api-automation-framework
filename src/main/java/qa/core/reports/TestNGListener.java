package qa.core.reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import qa.core.utils.Timestamp;

import java.util.List;

public class TestNGListener implements ITestListener {
    Report report;
    @Override
    public void onStart(ITestContext context){
        String reportDir = "reports";
        String reportFilePrefix = "report_";
        String currentTimestamp = Timestamp.getCurrentTimeStamp();
        String reportFileName = reportFilePrefix+currentTimestamp;
        report = new TextReport(reportDir,reportFileName);
        report.log(ReportLevel.INFO,"Test Started : " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        report.log(ReportLevel.INFO,"Test Method "+result.getName()+" started.");
    }

    @Override
    public void onTestSuccess(ITestResult result){
        log(result);
        report.log(ReportLevel.PASS,"Test Method "+result.getName()+" passed.");
    }
    @Override
    public void onTestFailure(ITestResult result){
        log(result);
        report.log(ReportLevel.FAIL,"Test Method "+result.getName()+" failed.");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        report.log(ReportLevel.FAIL,"Test Method "+result.getName()+" skipped.");
    }

    @Override
    public void onFinish(ITestContext context) {
        report.save();
        report.log(ReportLevel.INFO,"Test Finished : " + context.getName());
    }

    private void log(ITestResult result){
        List<String> reporterMessages = Reporter.getOutput(result);
        for(String reportMessage:reporterMessages) {
            //TODO - Parameterize ; splitter
            String[] log = reportMessage.split(";");
            ReportLevel logLevel = ReportLevel.valueOf(log[0]);
            String logMessage = log[1];
            if (log.length == 2) {
                report.log(logLevel,logMessage);
            } else if (log.length == 3) {
                String logDetail = log[2];
                report.log(logLevel, logMessage, logDetail);
            }
        }
    }
}
