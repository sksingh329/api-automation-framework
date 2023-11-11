package qa.core.reports;

public abstract class Report {
    public abstract void log(ReportLevel level,String step,String detail);
    public abstract void log(ReportLevel level,String message);
    public abstract void save();
}
