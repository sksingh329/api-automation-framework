package qa.core.exceptions;

public class CustomAssertionError extends java.lang.AssertionError {
    public <T> CustomAssertionError(T actual,T expected,String message,String ex){
        super( message + "Assertion Failed : " + actual + " != " +expected + " Trace : "+ ex);
    }
    public CustomAssertionError(boolean actual,String message,String ex){
        super( message + "Assertion Failed : " + actual + " Trace : "+ ex);
    }
    public CustomAssertionError(String actual,String expected,String message,String ex){
        super( message + "Assertion Failed : " + actual + " != " +expected + " Trace : "+ ex);
    }
}
