public class Deadline extends Task {

    protected String by;

    public Deadline(String taskName, String by) {
        super(taskName, false);
        setBy(by);
    }

    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public void printTaskType() {
        System.out.print("[D]");
    }

    @Override
    public void printTaskDescription() {
        System.out.println(super.getTaskName() + "(by: " + by + ")");
    }
}
