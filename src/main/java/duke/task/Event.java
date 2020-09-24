package duke.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
    protected Date startTime;
    protected Date endTime;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy hh:mm aa");

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
    public String getTaskType() {
        return "[E]";
    }

    @Override
    public String getTaskDescription() {
        return super.getTaskName() + " (from: " + dateFormat.format(startTime) + " to "
                + dateFormat.format(endTime) + ")";
    }

}
