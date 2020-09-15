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

    public void printTaskType(){
        System.out.println("[?]");
    }

    public void printTaskDescription(){
        System.out.println(taskName);
    }

    public void printCompletionIndicator (){
        if (isComplete) {
            System.out.print(TICK_SYMBOL + " ");
        } else {
            System.out.print(CROSS_SYMBOL + " ");
        }
    }
}
