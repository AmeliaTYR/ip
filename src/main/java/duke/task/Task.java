package duke.task;

import static duke.constants.TootieSymbols.CROSS_SYMBOL;
import static duke.constants.TootieSymbols.TICK_SYMBOL;

public abstract class Task {

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

    public boolean getComplete(){
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
