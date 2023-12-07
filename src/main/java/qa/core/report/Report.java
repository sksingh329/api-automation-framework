package qa.core.report;

public abstract class Report {
    public abstract void log(String testMethodName,ReportLevel level,String step,String detail);
    public abstract void log(String testMethodName,ReportLevel level,String message);
    public abstract void save();
    public abstract void testStart(String testMethodName);
}
