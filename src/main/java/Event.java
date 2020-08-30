import java.util.Date;

public class Event extends Task {
    protected String startTime;
    protected String endTime;

    public Event(String taskName, String startTime, String endTime) {
        super(taskName, false);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public void printTaskType() {
        System.out.print("[E]");
    }

    @Override
    public void printTaskDescription() {
        System.out.println(super.getTaskName() + " (from: " + startTime + " to " + endTime + ")");
    }
}
