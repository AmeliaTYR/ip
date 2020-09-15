package duke.task;

import duke.task.Task;

public class ToDo extends Task {

    public ToDo(String taskName) {
        super(taskName, false);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }

}
