package duke.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {

    protected Date dueDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy hh:mm aa");

    /**
     * Constructs Deadline task
     */
    public Deadline(String taskName, Date dueDate) {
        super(taskName, false);
        setDueDate(dueDate);
    }

    /**
     * Setter for dueDate
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Getter for dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String getTaskType() {
        return "[D]";
    }

    @Override
    public String getTaskDescription() {
        return super.getTaskName() + " (by: " + dateFormat.format(dueDate) + ")";
    }
}
