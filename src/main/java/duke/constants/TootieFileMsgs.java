package duke.constants;

/**
 * Messages formatted to save files
 */
public class TootieFileMsgs {
    /** Newline object specific to system used  */
    public static final String NEWLINE = System.lineSeparator();

    /** Instructions header for Tasks save file  */
    public static final String INDICATOR_INSTRUCTION = "Task completion status:" + NEWLINE + "0 is not done(" +
            TootieSymbols.CROSS_SYMBOL + "), 1 is done(" + TootieSymbols.TICK_SYMBOL + ")" + NEWLINE;
    public static final String TASK_TYPE_INSTRUCTION = "Task types (T,D,E):" + NEWLINE +
            "T is to-do, D is deadline, E is event" + NEWLINE;
    public static final String EXAMPLE_INSTRUCTION = "Examples:" + NEWLINE + "[T][0] clean shoes" + NEWLINE +
            "[D][0] do project (by: Thu 30 Jan 2020 04:55 PM)" + NEWLINE + "" +
            "[E][1] clean kitchen (from: Thu 31 Dec 2020 04:55 AM to Thu 31 Dec 2020 05:45 AM)" + NEWLINE;
    public static final String TASK_LIST_START = "--------------------------------------------------------" +
            NEWLINE+ "Tasks:" + NEWLINE;
    public static final String ALL_TASKS_FILE_INSTRUCTIONS_HEADER = INDICATOR_INSTRUCTION + NEWLINE +
            TASK_TYPE_INSTRUCTION + NEWLINE + EXAMPLE_INSTRUCTION + NEWLINE + TASK_LIST_START;
    public static final String TOTAL_TASKS_FILE_LINE = "Total tasks: %1$d";
    public static final String TASKS_COMPLETED_FILE_LINE = "Tasks completed: %1$d";

    /** Instructions header for settings save file  */
    public static final String TOOTIE_SETTINGS_FILE_DIVIDER = "==============================";
    public static final String TOOTIE_SETTINGS_FILE_INSTRUCTIONS_HEADER = "INFORMATION" + NEWLINE
            + TootieSymbols.VERSION + NEWLINE + NEWLINE + TOOTIE_SETTINGS_FILE_DIVIDER;
    public static final String TOOTIE_SETTINGS_FILE_PATHS_FORMAT = "FILE PATHS" + NEWLINE + "+ Tootie settings: %1$s"
            + NEWLINE + "+ All Tasks: %2$s" + NEWLINE + NEWLINE + TOOTIE_SETTINGS_FILE_DIVIDER;
    public static final String TOOTIE_SETTINGS_USER_PREFERENCES_FORMAT = "USER PREFERENCES" + NEWLINE
            + "+ Divider choice: %1$s" + NEWLINE + "+ Username: %2$s" + NEWLINE + NEWLINE
            + TOOTIE_SETTINGS_FILE_DIVIDER;


}
