package duke.task;

public abstract class Task {
    // is complete indicator symbols
    public static final String TICK_SYMBOL = "[\u2713]";
    public static final String CROSS_SYMBOL = "[\u2717]";

    private String taskName;
    private boolean isComplete;

    public Task(){
        this("no name", false);
    }

    public Task(String taskName, boolean isComplete) {
        setTaskName(taskName);
        setComplete(isComplete);
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getTaskName(){
        return taskName;
    }

    public boolean isComplete(){
        return isComplete;
    }

    public String getTaskType(){
        return "[?]";
    }

    public String getTaskDescription(){
        return getTaskName();
    }

    public String getCompletionIndicator (){
        if (isComplete) {
            return TICK_SYMBOL;
        } else {
            return CROSS_SYMBOL;
        }
    }
}
