package qa.core.reports;

public class ConsoleReport extends Report{
    public ConsoleReport(){
        System.out.println("Report is displayed on console.");
    }
    public void log(ReportLevel level,String step,String detail){
        System.out.println(level + " : " + step + " : " + detail);
    }
    public void log(ReportLevel level,String message){
        System.out.println(level + " : " + message);
    }
    public void save(){
        // This is an empty method as console report is not needed to be saved.
    }
}
