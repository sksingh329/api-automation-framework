package qa.core.report;

public class ConsoleReport extends Report{
    public ConsoleReport(){
        System.out.println("Report is displayed on console.");
    }
    public void log(String testMethodName,ReportLevel level,String step,String detail){
        System.out.println(testMethodName + " : " +level + " : " + step + " : " + detail);
    }
    public void log(String testMethodName,ReportLevel level,String message){
        System.out.println(testMethodName + " : " + level + " : " + message);
    }
    public void save(){
        // This is an empty method as console report is not needed to be saved.
    }
    public void testStart(String testMethodName){
        System.out.println(ReportLevel.INFO + " : " + "Test method " + testMethodName + " started.");
    }
}
