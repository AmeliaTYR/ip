public class Task {
    private String taskName;
    private boolean isComplete;
    //TODO: implement typing for future levels
    //private String taskType;

    public Task(){
        this("no name", false);
    }

    public Task(String taskName, boolean isComplete) {
        setTaskName(taskName);
        setIsComplete(isComplete);
    }

    private void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    private void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getTaskName(){
        return taskName;
    }

    public boolean getIsComplete(){
        return isComplete;
    }



}

//TODO: implement Deadline
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