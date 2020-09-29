package duke.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
    protected Date startTime;
    protected Date endTime;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy hh:mm aa");

    /**
     * Constructs Event task
     */
    public Event(String taskName, Date startTime, Date endTime) {
        super(taskName, false);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    /**
     * Getter for startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Getter for endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Setter for startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Setter for endTime
     */
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
