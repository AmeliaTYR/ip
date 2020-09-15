package duke.task;

import java.util.Date;

public class Deadline extends Task {

    protected Date dueDate;

    public Deadline(String taskName, Date dueDate) {
        super(taskName, false);
        setBy(dueDate);
    }

    public void setBy(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getTaskType() {
        return "[D]";
    }

    @Override
    public String getTaskDescription() {
        return super.getTaskName() + " (by: " + dueDate + ")";
    }
}
