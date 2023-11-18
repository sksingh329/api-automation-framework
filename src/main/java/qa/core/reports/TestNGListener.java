package qa.core.reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import qa.core.utils.FrameworkProperties;

import java.util.List;

public class TestNGListener implements ITestListener {
    private Report report;
    private final String reportLogSplit = FrameworkProperties.getFrameworkProperties().getProperty("reportLogSplit");

    @Override
    public void onStart(ITestContext context){
        report = GetReport.getReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        report.testStart(methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result){
        log(result);
        report.log(ReportLevel.PASS,"Test Method " + result.getName() + " passed.");
    }
    @Override
    public void onTestFailure(ITestResult result){
        log(result);
        report.log(ReportLevel.FAIL,"Test Method " + result.getName() + " failed.");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        report.log(ReportLevel.FAIL,"Test Method " + result.getName() + " skipped.");
    }

    @Override
    public void onFinish(ITestContext context) {
        report.save();
    }

    private void log(ITestResult result){
        List<String> reporterMessages = Reporter.getOutput(result);
        for(String reportMessage:reporterMessages) {
            String[] log = reportMessage.split(reportLogSplit);
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
