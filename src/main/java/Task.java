public class Task {
    private String taskName;
    private boolean isComplete;
    // TODO: implement typing for future levels
//    private String taskType;

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

    public boolean getIsComplete(){
        return isComplete;
    }
}

// TODO: in the setting up of the T,D,E variable need to add a value check
//  in the setter to ensure either T, D or E is set


// TODO: implement Deadline
//public class Deadline extends Task {
//
//    protected String by;
//
//    public Deadline(String description, String by) {
//        super(description);
//        this.by = by;
//    }
//
//    @Override
//    public String toString() {
//        return "[D]" + super.toString() + " (by: " + by + ")";
//    }
//}