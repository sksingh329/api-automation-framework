package qa.core.reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.List;

public class TestNGListener implements ITestListener {
    @Override
    public void onStart(ITestContext context){
        System.out.println("Test Started : " + context.getName());
    }
    @Override
    public void onTestSuccess(ITestResult result){
        System.out.println("Test Method "+result.getName()+" passed.");
        log(result);
    }
    @Override
    public void onTestFailure(ITestResult result){
        System.out.println("Test Method "+result.getName()+" failed.");
    }

    private void log(ITestResult result){
        List<String> reporterMessage = Reporter.getOutput(result);
        for(String report:reporterMessage){
            String[] log = report.split(";");
            String logLevel = log[0];
            String logStep = log[1];
            String logDetail = log[2];
            System.out.println(logLevel + ":" + logStep + ":" + logDetail);
        }
    }
}
