package duke.constants;

/**
 * Messages printed to console
 */
public class TootieNormalMsgs {
    /**
     * Newline object specific to system used
     */
    public static final String NEWLINE = System.lineSeparator();

    /**
     * Help command descriptions
     */
    public static final String DEADLINE_COMMAND_DESCRIPTION =
            "deadline: add a task with a deadline to the list" + NEWLINE + "  Parameters:  deadline t/TASKNAME "
                    + "d/DUE_DATE" + NEWLINE + "  Example:  deadline t/write essay d/31-12-2020 04:55" + NEWLINE + "  "
                    + "Example:  deadline t/submit report d/30-10-2020" + NEWLINE;
    public static final String EVENT_COMMAND_DESCRIPTION =
            "event: add a scheduled event task to the list" + NEWLINE + "  Parameters:  event t/TASKNAME " + "s" +
                    "/START_TIME" + " " + "e/END_TIME" + NEWLINE + "  Example:  event t/clean room s/31-12-2020 " +
                    "04:55 e/31-12-2020 " + "05:45" + NEWLINE + "  Example:  event t/clean room s/31-12-2020 " + "e"
                    + "/31-12-2020" + NEWLINE;
    public static final String DONE_COMMAND_DESCRIPTION =
            "done: marks indicated task done (choose number from list)" + NEWLINE + "  Parameters:  done TASK_INDEX"
                    + NEWLINE + "  Example:  done 1" + NEWLINE;
    public static final String UNDONE_COMMAND_DESCRIPTION = "undone: marks indicated task undone (choose number from "
            + "list)" + NEWLINE + "  Parameters:  undone TASK_INDEX" + NEWLINE + "  Example:  undone 1" + NEWLINE;
    public static final String DELETE_COMMAND_DESCRIPTION = "delete: deletes indicated task (choose number from list)"
            + NEWLINE + "  Parameters:  delete TASK_INDEX" + NEWLINE + "  Example:  delete 1" + NEWLINE;
    public static final String LIST_COMMAND_DESCRIPTION =
            "list: displays the complete list of tasks entered" + NEWLINE + "  Example:  list" + NEWLINE;
    public static final String BYE_COMMAND_DESCRIPTION =
            "bye: closes the program" + NEWLINE + "  Example:  bye" + NEWLINE;
    public static final String DATE_FORMAT_MESSAGE =
            "NOTE: datetime entries can be of the format" + NEWLINE + "    " + "\"dd-MM-yyyy " + "HH:mm\" with time "
                    + "in 24-Hour format" + NEWLINE + "    OR \"dd-MM-yyyy\"";
    public static final String TODO_COMMAND_DESCRIPTION =
            "todo: add a todo task to the" + " list" + NEWLINE + "  " + "Parameters:  todo t/TASKNAME" + NEWLINE + " "
                    + " Example:  todo t/clean room" + NEWLINE;
    public static final String HELP_COMMAND_DESCRIPTION =
            "help: displays a list of commands tootie understands" + NEWLINE
                    + "  or search for a specific command for the command description" + NEWLINE
                    + "  Parameters:  help [COMMAND]" + NEWLINE
                    + "  Example:  help" + NEWLINE
                    + "  Example:  help filter" + NEWLINE;
    public static final String USERNAME_COMMAND_DESCRIPTION = "username: allows user to set username" + NEWLINE + "  "
            + "Parameters:  username USERNAME" + NEWLINE + "  Example:  username Sophia" + NEWLINE;
    public static final String DIVIDER_COMMAND_DESCRIPTION =
            "divider: select a divider for customisation" + NEWLINE + "  dividers available:" + NEWLINE + "1) SPARKLY"
                    + " " + TootieSymbols.SPARKLY_TEXT_DIVIDER + NEWLINE + "2) PLAIN "
                    + TootieSymbols.PLAIN_TEXT_DIVIDER + NEWLINE + "3) SIMPLE "
                    + TootieSymbols.SIMPLE_TEXT_DIVIDER + NEWLINE + "4) DOUBLE " + TootieSymbols.DOUBLE_TEXT_DIVIDER
                    + NEWLINE + "  Parameters:  divider DIVIDER_INDEX" + NEWLINE + "  Example:  divider 1" + NEWLINE;
    public static final String FILEPATH_COMMAND_DESCRIPTION =
            "filepath: Display file paths of save files" + NEWLINE + "  Example:  filepath" + NEWLINE + "  Example:  "
                    + "filepaths" + NEWLINE + "  Note: The command can be spelled with or without the 's' at the end"
                    + NEWLINE;
    public static final String FILTER_COMMAND_DESCRIPTION =
            "filter: filters out tasks from the list according to " + "the parameters" + NEWLINE + "  Parameters:  "
                    + "filter st/SEARCH_TERM sb/START_BEFORE sa/START_AFTER eb/END_BEFORE" + NEWLINE
                    + "        " + "ea/END_AFTER db/DUE_BEFORE da/DUE_AFTER tt/TASK_TYPES cs/COMPLETION_STATUS"
                    + NEWLINE
                    + "  Example:  filter tt/event " + "sb/13-01-2019 ea/31-01-2020" + NEWLINE
                    + "  Example:  filter tt/event todo " + "st/homework" + NEWLINE
                    + "  Example:  filter tt/deadline,todo db/14-04-2020 16:40" + NEWLINE + "  Note: "
                    + "Check user guide for more verbose description" + NEWLINE;
    public static final String SAVE_COMMAND_DESCRIPTION =
            "save: manually save the list of tasks without closing " + "the" + " program" + NEWLINE + "  Example:  "
                    + "save" + NEWLINE;
    public static final String LOAD_COMMAND_DESCRIPTION = "load: add tasks from existing file" + NEWLINE + "  " +
            "Example:  load" + NEWLINE;
    public static final String HELP_COMMAND_TEXT =
            "help: displays a list of commands tootie understands" + NEWLINE
                    + "filepath: Display file paths of save files" + NEWLINE
                    + "save: manually save the list of tasks without closing" + NEWLINE
                    + "bye: closes the program"  + NEWLINE
                    + "todo: add a todo task to the list" + NEWLINE
                    + "deadline: add a task with a deadline to the list" + NEWLINE
                    + "event: add a scheduled event task to the list" + NEWLINE
                    + "load: add tasks from existing file" + NEWLINE
                    + "done: marks indicated task done (choose number from list)" + NEWLINE
                    + "undone: marks indicated task undone (choose number from list)" + NEWLINE
                    + "delete: deletes indicated task (choose number from list)" + NEWLINE
                    + "list: displays the complete list of tasks entered" + NEWLINE
                    + "filter: filters out tasks from the list according to the parameters" + NEWLINE
                    + "username: allows user to set username" + NEWLINE
                    + "divider: select a divider for customisation" + NEWLINE + NEWLINE
                    + "-----"  + NEWLINE
                    + "  NOTE: search for a specific definition by typing help [COMMAND]"  + NEWLINE
                    + "    Example: help filter" + NEWLINE
                    + "  NOTE: type \"full\" as the argument for a full list of commands" + NEWLINE
                    + "    Example: help full" ;
    public static final String HELP_FULL_COMMAND_TEXT =
            HELP_COMMAND_DESCRIPTION + NEWLINE + FILEPATH_COMMAND_DESCRIPTION + NEWLINE +
                    SAVE_COMMAND_DESCRIPTION + NEWLINE + BYE_COMMAND_DESCRIPTION + NEWLINE +
                    TODO_COMMAND_DESCRIPTION + NEWLINE + DEADLINE_COMMAND_DESCRIPTION + NEWLINE +
                    EVENT_COMMAND_DESCRIPTION + NEWLINE + LOAD_COMMAND_DESCRIPTION + NEWLINE +
                    DONE_COMMAND_DESCRIPTION + NEWLINE + UNDONE_COMMAND_DESCRIPTION + NEWLINE +
                    DELETE_COMMAND_DESCRIPTION + NEWLINE +
                    LIST_COMMAND_DESCRIPTION + NEWLINE + FILTER_COMMAND_DESCRIPTION + NEWLINE +
                    USERNAME_COMMAND_DESCRIPTION + NEWLINE + DIVIDER_COMMAND_DESCRIPTION + NEWLINE +
                    "-----" + NEWLINE + DATE_FORMAT_MESSAGE + NEWLINE;
    public static final String HELP_INFO_MSG =
            "Here is the list of commands I understand:" + NEWLINE + NEWLINE + HELP_COMMAND_TEXT;
    public static final String NO_SUCH_COMMAND_IN_LIST_ERROR_MSG = "No such command in command list!";


    /**
     * Greetings
     */
    public static final String FAREWELL_GREETING =
            "Bye %1$s! Hope to see you again soon! " + TootieSymbols.FLOWER_SMILE_EMOTICON + NEWLINE;
    public static final String HELLO_GREETING =
            "Hello %1$s! I'm Tootie!" + NEWLINE + "What can I do for you?" + NEWLINE;

    /**
     * Task modification response messages
     */
    public static final String TASK_MARKED_DONE_RESPONSE_MSG = "Nice! I've marked this task as done:" + NEWLINE + "  "
            + "  %1$s" + TootieSymbols.TICK_SYMBOL + " %2$s" + NEWLINE + TootieSymbols.SPARKLY_EMOTICON;
    public static final String TASK_MARKED_UNDONE_RESPONSE_MSG = "Aww... I've marked this task undone:" + NEWLINE +
            "    %1$s" + TootieSymbols.CROSS_SYMBOL + " %2$s" + NEWLINE + TootieSymbols.SAD_EMOTICON;
    public static final String TASK_DELETED_RESPONSE_MSG = "Yay! I have deleted this task:" + NEWLINE + "    " +
            "%1$s%2$s %3$s" + NEWLINE + TootieSymbols.SPARKLY_EMOTICON;

    /**
     * Message formats
     */
    public static final String LIST_TASK_FORMAT = "%1$d. %2$s%3$s %4$s";
    public static final String ADDED_EVENT_FORMAT = "added event:" + NEWLINE + "%1$s";
    public static final String ADDED_DEADLINE_FORMAT = "added deadline:" + NEWLINE + "%1$s";
    public static final String ADDED_TODO_FORMAT = "added todo: " + "%1$s";
    public static final String LOGO_PRINT_FORMAT = "Hello from" + NEWLINE + "%1$s" + NEWLINE + TootieSymbols.VERSION;
    public static final String NUMTASKS_PRINT_FORMAT = "You have %1$d task%2$s, %3$s not done" + NEWLINE;
    public static final String TASKS_ALL_DONE_MSG = "All done " + TootieSymbols.HAPPY_EMOTICON;
    public static final String HELLO_NAME_FORMAT = "Hello %1$s! " + TootieSymbols.FLOWER_SMILE_EMOTICON;

    /**
     * Command response messages
     */
    public static final String ALL_TASKS_SAVED_MSG = "All tasks saved! " + TootieSymbols.FLOWER_SMILE_EMOTICON;
    public static final String ALL_SETTINGS_SAVED_MSG = "All settings saved! " + TootieSymbols.FLOWER_SMILE_EMOTICON;
    public static final String DIVIDER_CHANGED_MSG = "Divider changed! " + TootieSymbols.FLOWER_SMILE_EMOTICON;
    public static final String DIVIDER_CHOICE_NOT_FOUND_MSG =
            "Divider choice not found? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String USERNAME_COMMAND_INVALID_MSG =
            "Username command invalid! " + TootieSymbols.ANGRY_EMOTICON;
    public static final String USERNAME_BLANK_MSG = "Username is blank? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String FILTER_COMMAND_MISSING_ARGS_MSG =
            "Filter command missing valid arguments? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String FILTER_COMMAND_PARAMS_UNMATCHED_MSG =
            "No tasks matching parameters found? " + TootieSymbols.CONFUSED_EMOTICON;

    /**
     * Filter function message formats
     */
    public static final String FILEPATH_PRINTER_FORMAT = "The list of saved tasks can be found at:" + NEWLINE + "%1$s"
            + NEWLINE + "The " + "list of saved settings can be found at:" + NEWLINE + "%2$s";
    public static final String ALL_FILTERED_TASKS_COMPLETE_MSG_FORMAT = "Filtered! %1$s task%2$s found, all complete!"
            + NEWLINE;
    public static final String FILTERED_TASKS_COUNT_MSG_FORMAT = "Filtered! %1$s task%2$s found, %3$s incomplete."
            + NEWLINE;
    public static final String COMPLETION_STATUS_PARAM_INVALID_ERROR_MSG =
            "Completion status param invalid. Ignoring param.";

    /**
     * Filter date parameters error messages
     */
    public static final String DUEDATE_BEFORE_WRONG_FORMAT_MSG = "Date for \"due date before\" wrongly " +
            "formatted" + ". Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE;
    public static final String DUEDATE_AFTER_WRONGLY_FORMATTED_MSG = "Date for \"due date after\" wrongly formatted. "
            + "Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE;
    public static final String DUEDATES_MATCH_WARNING_MSG =
            "Warning: date for \"due date before\" is the same as " + "date for \"due date after\".";
    public static final String DUEDATES_WRONG_ORDER_MSG = "Date for \"due date before\" is before date for \"due " +
            "date" + " after\"." + NEWLINE + "Automatically swapping the two...";
    public static final String STARTTIMES_WRONG_ORDER_MSG = "Date for \"start time before\" is before date for " +
            "\"start time after\"." + NEWLINE + "Automatically swapping the two...";
    public static final String ENDTIMES_WRONG_ORDER_MSG = "Date for \"end time before\" is before date for \"end " +
            "time" + " after\"." + NEWLINE + "Automatically swapping the two...";
    public static final String ENDDATES_MATCH_WARNING_MSG =
            "Warning: date for \"end time before\" is the same as " + "date for \"end time after\".";
    public static final String ENDDATE_AFTER_WRONG_FORMAT_MSG = "Date for \"end time after\" wrongly formatted. " +
            "Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE;
    public static final String ENDDATE_BEFORE_WRONG_FORMAT_MSG =
            "Date for \"end time before\" wrongly formatted. Ignoring param." + NEWLINE +
                    TootieNormalMsgs.DATE_FORMAT_MESSAGE;
    public static final String STARTTIMES_MATCH_WARNING_MSG =
            "Warning: date for \"start time before\" is the same as date for \"start time after\".";
    public static final String STARTTIME_AFTER_WRONG_FORMAT_MSG =
            "Date for \"end time after\" wrongly formatted. Ignoring param." + NEWLINE +
                    TootieNormalMsgs.DATE_FORMAT_MESSAGE;
    public static final String STARTTIME_BEFORE_WRONG_FORMAT_MSG =
            "Date for \"end time before\" wrongly formatted. Ignoring param." + NEWLINE +
                    TootieNormalMsgs.DATE_FORMAT_MESSAGE;
    public static final String DUEDATE_BEFORE_DATE_INVALID_MSG =
            "Date for \"due date before\" not real date. Ignoring param.";
    public static final String DUEDATE_AFTER_DATE_INVALID_MSG =
            "Date for \"due date after\" not real date. Ignoring param.";
    public static final String STARTTIME_BEFORE_DATE_INVALID_MSG =
            "Date for \"start time before\" not real date. Ignoring param.";
    public static final String STARTTIME_AFTER_DATE_INVALID_MSG =
            "Date for \"start time after\" not real date. Ignoring param.";
    public static final String ENDDATE_BEFORE_DATE_INVALID_MSG =
            "Date for \"due date before\" not real date. Ignoring param.";
    public static final String ENDDATE_AFTER_DATE_INVALID_MSG =
            "Date for \"due date after\" not real date. Ignoring param.";

    /**
     * File creator msg
     */
    public static final String NEW_FILE_CREATED_MSG_FORMAT = "New file created: %1$s";
    public static final String IO_ERROR_WHEN_MAKING_FILE_MSG = "IO error when making file!";
    public static final String FILE_ALREADY_EXISTS_MSG = "File already exists.";
    public static final String FILE_CREATED_PATH_MSG = "File created: ";
    public static final String DIRECTORY_CREATED_SUCCESSFULLY_MSG = "Directory created successfully " +
            TootieSymbols.HAPPY_EMOTICON;
    public static final String COULD_NOT_CREATE_DIRECTORY_MSG = "Sorry, could not create specified directory";
    public static final String FILE_PATH_TO_DIRECTORY_INVALID_MSG = "File path to directory invalid!";
    public static final String FILE_NOT_FOUND_MSG = "File does not exist.";
    public static final String FILE_AUTO_CREATED_MSG = "Auto creating new file using path: %1$s";

    /**
     * Settings loader messages
     */
    public static final String ERROR_READING_FILE_ON_LINE_MSG_FORMAT =
            "Error reading settings file! Error on line:" + NEWLINE + "%1$s";
    public static final String SETTINGS_FILE_EMPTY_MSG = "tootieSettings.txt save file empty" + NEWLINE + "No " +
            "previous settings loaded";
    public static final String SETTINGS_FILE_NOT_FOUND_MSG = "tootieSettings.txt save file not found" + NEWLINE +
            "Creating new file...";

    /**
     * All tasks loader messages
     */
    public static final String ERROR_READING_DUEDATE_FROM_FILE_MSG = "Error reading due date from line:" + NEWLINE +
            "  %1$s";
    public static final String ERROR_READING_ENDTIME_FROM_FILE_MSG = "Error reading end time from line:" + NEWLINE +
            "  %1$s";
    public static final String ERROR_READING_STARTTIME_FROM_FILE_MSG = "Error reading start time from line:" + NEWLINE + "  %1$s";
    public static final String ERROR_READING_FILE_LINE_MSG = "Error reading file! Error on line:" + NEWLINE + "%1$s";
    public static final String FILE_PATH_NO_FILE_ERROR_MSG = "File not found uwu" + NEWLINE +
            "Failed to read from: %1$s";
    public static final String ALL_TASKS_LOAD_OPTIONS_MSG = "Options:" + NEWLINE + "(1)Find existing file" + NEWLINE + "(2)Automatically " +
            "create " + "directory and file" + NEWLINE + "(type \"1\" or \"2\")";
    public static final String LOADING_ALL_TASKS_MSG = "Loading allTasks.txt save file...";
    public static final String LOADING_SETTINGS_MSG = "Loading tootieSettings.txt save file...";
    public static final String ALL_TASKS_FILE_NOT_FOUND_MSG = "Save file not found? " + duke.constants.TootieSymbols.CONFUSED_EMOTICON;
    public static final String CANCEL_LOAD_ALL_TASKS_OPERATION_MSG = "Cancelled \"load save file\" operation";
    public static final String SAVE_FILE_EMPTY_MSG = "Save file empty? " + duke.constants.TootieSymbols.CONFUSED_EMOTICON;
    public static final String ENTER_THE_FULL_PATH_TO_EXISTING_FILE_MSG =
            "Enter the full path to existing file (type \"cancel\" to cancel):";
    public static final String AUTO_CREATE_DIRECTORY_AND_FILE_MSG = "Automatically creating directory and file";
    public static final String RESPONSE_NOT_RECOGNISED_MSG = "Response not recognised? " + duke.constants.TootieSymbols.CONFUSED_EMOTICON;
    public static final String TASKS_EXPECTED_SUMMARY_FORMAT = "%1$s task%2$s expected from file.";

    public static final String TASKS_READ_SUCCESSFULLY_SUMMARY_FORMAT = "%1$d task%2$s read successfully!" + NEWLINE;
    public static final String TOTAL_TASKS_LOADED_SUMMARY_FORMAT = "Total task%2$s in list: %1$d" + NEWLINE;
    public static final String FILE_HEADER_INVALID_MSG = "File header invalid!";
}
