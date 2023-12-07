package qa.core.report;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import qa.core.utils.properties.FrameworkProperties;

import java.util.List;

public class TestNGListener implements ITestListener {
    private Report report;
    private final String reportLogSplit = FrameworkProperties.getFrameworkProperties().getProperty("reportLogSplit");

    @Override
    public synchronized void onStart(ITestContext context){
        report = GetReport.getReport();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        report.testStart(methodName);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result){
        log(result);
        report.log(result.getName(),ReportLevel.PASS,"Test Method " + result.getName() + " passed.");
    }
    @Override
    public synchronized void onTestFailure(ITestResult result){
        log(result);
        report.log(result.getName(),ReportLevel.FAIL,"Test Method " + result.getName() + " failed.");
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        report.log(result.getName(),ReportLevel.FAIL,"Test Method " + result.getName() + " skipped.");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        report.save();
    }

    private synchronized void log(ITestResult result){
        List<String> reporterMessages = Reporter.getOutput(result);
        for(String reportMessage:reporterMessages) {
            String[] log = reportMessage.split(reportLogSplit);
            ReportLevel logLevel = ReportLevel.valueOf(log[0]);
            String logMessage = log[1];
            if (log.length == 2) {
                report.log(result.getName(),logLevel,logMessage);
            } else if (log.length == 3) {
                String logDetail = log[2];
                report.log(result.getName(),logLevel, logMessage, logDetail);
            }
        }
    }
}
