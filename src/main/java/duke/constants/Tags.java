package duke.constants;

/**
 * Tags to mark arguments
 */
public class Tags {
    /** Settings file loader tags */
    public static final String TOOTIE_SETTINGS_FILEPATH_TAG = "+ Tootie settings:";
    public static final String ALL_TASKS_FILEPATH_TAG = "+ All Tasks:";
    public static final String DIVIDER_CHOICE_TAG = "+ Divider choice:";
    public static final String USERNAME_TAG = "+ Username:";

    /** All tasks file loader tags */
    public static final String TASK_COMPLETED_FLAG = "1";
    public static final String NUM_TASKS_TAG = "Tasks:";

    /** Load file options */
    public static final String LOAD_OPTION_1 = "1";
    public static final String LOAD_OPTION_2 = "2";
    public static final String LOAD_OPTION_CANCEL = "cancel";

    /** Tags in parsers */
    public static final String TOTAL_TASKS_TAG_FORMAT = "Total tasks: (\\d+)";

}
