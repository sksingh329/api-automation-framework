package qa.core.asserts;

import org.testng.Assert;
import qa.core.exceptions.CustomAssertionError;
import qa.core.report.ReportLevel;
import qa.core.report.ReporterUtils;

public class Asserts {
    public static <T> void assertEquals(T actual, T expected, String message){
        try{
            Assert.assertEquals(actual, expected);
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed (Equal) : " + actual + " == " +expected);
        } catch(AssertionError ex){
            ReporterUtils.log(ReportLevel.FAIL,message,"Assertion Failed (Equal): Actual : " + actual + " , Expected : " + expected + " ,Trace : " + ex);
            throw new CustomAssertionError(actual,expected,message,ex.toString());
        }
    }
    public static void assertContains(String actual, String expected, String message){
        try{
            Assert.assertTrue(actual.contains(expected));
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed (Contains): " + actual + " <contains> " +expected);
        } catch(AssertionError ex){
            ReporterUtils.log(ReportLevel.FAIL,message,"Assertion Failed (Contains): Actual : " + actual + " , Expected : " + expected + " ,Trace : " + ex);
            throw new CustomAssertionError(actual,expected,message,ex.toString());
        }
    }
    public static void assertTrue(boolean condition,String message){
        try{
            Assert.assertTrue(condition);
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed (True): Actual : " + true + " , Expected : " + true);
        }
        catch(AssertionError ex){
            ReporterUtils.log(ReportLevel.FAIL,message,"Assertion Failed (True): Actual : " + false + " , Expected : " + true + " ,Trace : " + ex);
            throw new CustomAssertionError(condition,message,ex.toString());
        }
    }
    public static void assertFalse(boolean condition,String message){
        try{
            Assert.assertFalse(condition);
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Passed (False): Actual : " + false + " , Expected : " + false);
        }
        catch(AssertionError ex){
            ReporterUtils.log(ReportLevel.PASS,message,"Assertion Failed (False): Actual : " + true + " , Expected : " + false);
            throw new CustomAssertionError(condition,message,ex.toString());
        }
    }
}
