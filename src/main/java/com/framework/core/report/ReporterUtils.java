package com.framework.core.report;

import org.testng.Reporter;
import com.framework.core.utils.properties.FrameworkProperties;

public class ReporterUtils {
    private static final String reportLogSplit = FrameworkProperties.getFrameworkProperties().getProperty("reportLogSplit");
    public static void log(ReportLevel level, String message, String detail){
//        System.out.println(detail);
        Reporter.log(level + reportLogSplit + message + reportLogSplit + detail);
    }
    public static void log(ReportLevel level, String message){
        Reporter.log(level + reportLogSplit + message);
    }
}
