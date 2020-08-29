public class Event extends Task {
    protected String startTime;
    protected String endTime;

    public Event(String taskName, boolean isComplete, String startTime, String endTime) {
        super(taskName, isComplete);
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
}
