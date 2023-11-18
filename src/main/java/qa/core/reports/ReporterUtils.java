package qa.core.reports;

import org.testng.Reporter;
import qa.core.utils.FrameworkProperties;

public class ReporterUtils {
    private static final String reportLogSplit = FrameworkProperties.getFrameworkProperties().getProperty("reportLogSplit");
    public static void log(ReportLevel level, String message, String detail){
        Reporter.log(level + reportLogSplit + message + reportLogSplit + detail);
    }
    public static void log(ReportLevel level, String message){
        Reporter.log(level + reportLogSplit + message);
    }
}
