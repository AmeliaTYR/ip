package duke.task;

/**
 * Todo class, only contains taskname with no due date
 */
public class ToDo extends Task {

    /**
     * Constructs Todo task
     */
    public ToDo(String taskName) {
        super(taskName, false);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }

}
