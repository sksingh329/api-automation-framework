package qa.core.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import qa.core.utils.files.FileUtils;
import qa.core.utils.properties.FrameworkProperties;


public class ExtentReport extends Report{

    public static ExtentReport report;

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    private static void setExtentReports(String reportDir, String reportFileName){
        String extentReportSubDir = FrameworkProperties.getFrameworkProperties().getProperty("extentReportSubDir");
        String extentReportDir = reportDir + extentReportSubDir;
        FileUtils.createDir(extentReportDir);
        String htmlReportFileName =  extentReportDir + reportFileName + ".html";
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(htmlReportFileName);
        reporter.config().setReportName("API Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "MAC");
        extentReports.setSystemInfo("Author", "Subodh");
        extentReports.setSystemInfo("Build#", "1.1");
        extentReports.setSystemInfo("Team", "QA Team");
        extentReports.setSystemInfo("Customer Name", "NAL");
    }

    public static ExtentReport getInstance(String reportDir, String reportFileName){
        if(extentReports == null){
            setExtentReports(reportDir,reportFileName);
            report = new ExtentReport();
        }
        return report;
    }

    private ExtentReport() {

    }


    public void testStart(String testMethodName){
        extentTest = extentReports.createTest(testMethodName);
    }

    public void log(ReportLevel level,String step,String detail){
        extentTest.log(Status.valueOf(level.toString()),step + detail);
    }
    public void log(ReportLevel level,String message){
        extentTest.log(Status.valueOf(level.toString()),message);
    }
    public void save(){
        extentReports.flush();
    }
}
