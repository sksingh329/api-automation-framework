package qa.core.reports;

import org.testng.Reporter;

public class ReporterUtils {
    public static void log(ReportLevel level, String message, String detail){
        Reporter.log(level + ";" + message + ";" + detail);
    }
    public static void log(ReportLevel level, String message){
        Reporter.log(level + ";" + message);
    }
}
