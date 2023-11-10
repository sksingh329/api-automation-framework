package qa.core.reports;

public class ConsoleReport extends Report{
    public void log(ReportLevel level,String step,String detail){
        System.out.println(level + " : " + step + " : " + detail);
    }
    public void log(ReportLevel level,String message){
        System.out.println(level + " : " + message);
    }
}
