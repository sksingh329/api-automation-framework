package qa.core.reports;

import org.testng.Reporter;

public class ReporterUtils {
    public enum Level{
        PASS,FAIL,INFO
    }
    public static void log(Level level,String step,String detail){
        Reporter.log(level.toString() + ";" + step + ";" + detail);
    }
}
