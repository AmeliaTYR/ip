package duke.task;

import java.util.Date;

public class Event extends Task {
    protected Date startTime;
    protected Date endTime;

    public Event(String taskName, Date startTime, Date endTime) {
        super(taskName, false);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
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
