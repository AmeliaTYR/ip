package duke.task;

import duke.task.Task;

public class ToDo extends Task {

    public ToDo(String taskName) {
        super(taskName, false);
    }

    @Override
    public void printTaskType() {
        System.out.print("[T]");
    }

    @Override
    public void printTaskDescription() {
        System.out.println(super.getTaskName());
    }

}
