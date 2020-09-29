package duke.task;

import static duke.constants.TootieSymbols.CROSS_SYMBOL;
import static duke.constants.TootieSymbols.TICK_SYMBOL;

public abstract class Task {

    private String taskName;
    private boolean isComplete;

    /**
     * Basic task constructor
     */
    public Task(){
        this("no name", false);
    }

    /**
     * Basic task constructor with parameters
     */
    public Task(String taskName, boolean isComplete) {
        setTaskName(taskName);
        setComplete(isComplete);
    }

    /**
     * Setter for taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Setter for completion status
     */
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    /**
     * Getter for taskName
     */
    public String getTaskName(){
        return taskName;
    }

    /**
     * Getter for completion status
     */
    public boolean getComplete(){
        return isComplete;
    }

    /**
     * Basic getter for taskType
     */
    public String getTaskType(){
        return "[?]";
    }

    /**
     * Basic printer for task description string
     */
    public String getTaskDescription(){
        return getTaskName();
    }

    /**
     * Basic printer for completion indication string
     */
    public String getCompletionIndicator (){
        if (isComplete) {
            return TICK_SYMBOL;
        } else {
            return CROSS_SYMBOL;
        }
    }
}
