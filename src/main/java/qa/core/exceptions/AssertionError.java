package qa.core.exceptions;

import org.testng.Assert;

public class AssertionError extends java.lang.AssertionError {
    public <T> AssertionError(T actual,T expected,String message,String ex){
        super( message + "Assertion Failed : " + actual + " != " +expected + " Trace : "+ ex);
    }
    public AssertionError(boolean actual,String message,String ex){
        super( message + "Assertion Failed : " + actual + " Trace : "+ ex);
    }
    public AssertionError(String actual,String expected,String message,String ex){
        super( message + "Assertion Failed : " + actual + " != " +expected + " Trace : "+ ex);
    }
}
