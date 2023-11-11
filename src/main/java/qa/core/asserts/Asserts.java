package qa.core.asserts;

import org.testng.Assert;
import qa.core.exceptions.AssertionError;
import qa.core.reports.ReportLevel;
import qa.core.reports.ReporterUtils;

public class Asserts {
    public static <T> void assertEquals(T actual, T expected, String message){
        try{
            Assert.assertEquals(actual, expected);
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed : " + actual + " == " +expected);
        } catch(AssertionError ex){
            throw new AssertionError(actual,expected,message,ex.toString());
        }
    }
    public static void assertContains(String actual, String expected, String message){
        try{
            Assert.assertTrue(actual.contains(expected));
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed : " + actual + " <contains> " +expected);
        } catch(AssertionError ex){
            throw new AssertionError(actual,expected,message,ex.toString());
        }
    }
    public static void assertTrue(boolean condition,String message){
        try{
            Assert.assertTrue(condition);
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed : " + true);
        }
        catch(AssertionError ex){
            throw new AssertionError(condition,message,ex.toString());
        }
    }
    public static void assertFalse(boolean condition,String message){
        try{
            Assert.assertFalse(condition);
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed : " + false);
        }
        catch(AssertionError ex){
            throw new AssertionError(condition,message,ex.toString());
        }
    }
}
