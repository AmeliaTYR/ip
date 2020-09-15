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

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public void printTaskType() {
        System.out.print("[D]");
    }

    @Override
    public void printTaskDescription() {
        System.out.println(super.getTaskName() + " (by: " + dueDate + ")");
    }
}
