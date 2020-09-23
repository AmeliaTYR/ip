package duke.constants;

/**
 * Messages printed to console
 */
public class TootieNormalMsgs {
    /** Newline object specific to system used  */
    public static final String NEWLINE = System.lineSeparator();

    /** Help command descriptions */
    public static final String DEADLINE_COMMAND_DESCRIPTION =
            "deadline: add a task with a deadline to the list" + NEWLINE + "  Parameters:  deadline t/TASKNAME " +
                    "d/DUE_DATE" + NEWLINE + "  Example:  deadline t/write essay d/31-12-2020 04:55" + NEWLINE + "  " +
                    "Example:  deadline t/submit report d/30-10-2020" + NEWLINE;
    public static final String EVENT_COMMAND_DESCRIPTION =
            "event: add a scheduled event task to the list" + NEWLINE + "  Parameters:  event t/TASKNAME s/START_TIME "
                    + "e/END_TIME" + NEWLINE + "  Example:  event t/clean room s/31-12-2020 04:55 e/31-12-2020 05:45"
                    + NEWLINE + "  Example:  event t/clean room s/31-12-2020 e/31-12-2020" + NEWLINE;
    public static final String DONE_COMMAND_DESCRIPTION =
            "done: marks indicated task done (choose number from list)" + NEWLINE + "  Example:  done 1" + NEWLINE;
    public static final String UNDONE_COMMAND_DESCRIPTION =
            "undone: marks indicated task undone (choose number from list)" + NEWLINE + "  Example:  undone 1"
                    + NEWLINE;
    public static final String DELETE_COMMAND_DESCRIPTION =
            "delete: deletes indicated task (choose number from list)" + NEWLINE + "  Example:  delete 1" + NEWLINE;
    public static final String LIST_COMMAND_DESCRIPTION =
            "list: displays the complete list of tasks entered" + NEWLINE + "  Example:  list" + NEWLINE;
    public static final String BYE_COMMAND_DESCRIPTION =
            "bye: closes the program" + NEWLINE + "  Example:  bye" + NEWLINE;
    public static final String DATE_FORMAT_MESSAGE = "NOTE: datetime entries can be of the format \"dd-MM-yyyy " +
            "HH:mm\"" + NEWLINE + "    OR \"dd-MM-yyyy\"";
    public static final String TODO_COMMAND_DESCRIPTION = "todo: add a todo task to the" + " list" + NEWLINE +
            "  " + "Parameters:  todo t/TASKNAME" + NEWLINE + "  Example:  todo t/clean room" + NEWLINE;
    public static final String HELP_COMMAND_DESCRIPTION =
            "help: displays a list of commands tootie understands" + NEWLINE + "  Example:  help" + NEWLINE;
    public static final String USERNAME_COMMAND_DESCRIPTION =
            "username: allows user to set username" + NEWLINE + "  Example:  username Sophia" + NEWLINE;
    public static final String DIVIDER_COMMAND_DESCRIPTION =
            "divider: select a divider for customisation" + NEWLINE + "  dividers avaliable:" + NEWLINE
                    + "1) " + TootieSymbols.SPARKLY_TEXT_DIVIDER + NEWLINE
                    + "2) " + TootieSymbols.PLAIN_TEXT_DIVIDER + NEWLINE
                    + "3) " + TootieSymbols.SIMPLE_TEXT_DIVIDER + NEWLINE
                    + "4) " + TootieSymbols.DOUBLE_TEXT_DIVIDER + NEWLINE
                    + "  Example:  divider 1" + NEWLINE;
    public static final String HELP_COMMAND_TEXT =
            HELP_COMMAND_DESCRIPTION + NEWLINE + TODO_COMMAND_DESCRIPTION + NEWLINE + DEADLINE_COMMAND_DESCRIPTION
                    + NEWLINE + EVENT_COMMAND_DESCRIPTION + NEWLINE + DONE_COMMAND_DESCRIPTION + NEWLINE
                    + UNDONE_COMMAND_DESCRIPTION + NEWLINE + DELETE_COMMAND_DESCRIPTION+ NEWLINE
                    + DIVIDER_COMMAND_DESCRIPTION + NEWLINE + USERNAME_COMMAND_DESCRIPTION + NEWLINE
                    + LIST_COMMAND_DESCRIPTION + NEWLINE + BYE_COMMAND_DESCRIPTION + NEWLINE + "-----"
                    + NEWLINE + DATE_FORMAT_MESSAGE + NEWLINE;
    public static final String HELP_INFO_MSG = "Here is the list of commands I understand:" + NEWLINE + NEWLINE
            + HELP_COMMAND_TEXT;

    /** Greetings  */
    public static final String FAREWELL_GREETING =
            "Bye %1$s! Hope to see you again soon! " + TootieSymbols.FLOWER_SMILE_EMOTICON + NEWLINE;
    public static final String HELLO_GREETING = "Hello %1$s! I'm Tootie!" + NEWLINE + "What can I do for you?" + NEWLINE;

    public static final String TASK_MARKED_DONE_RESPONSE_MSG = "Nice! I've marked this task as done:" + NEWLINE +
            "    %1$s" + TootieSymbols.TICK_SYMBOL + " %2$s" + NEWLINE + TootieSymbols.SPARKLY_EMOTICON;
    public static final String TASK_MARKED_UNDONE_RESPONSE_MSG = "Aww... I've marked this task undone:" + NEWLINE +
            "    %1$s" + TootieSymbols.CROSS_SYMBOL + " %2$s" + NEWLINE + TootieSymbols.SAD_EMOTICON;

    public static final String TASK_DELETED_RESPONSE_MSG = "Yay! I have deleted this task:" + NEWLINE +
            "    " + "%1$s%2$s %3$s" + NEWLINE + TootieSymbols.SPARKLY_EMOTICON;

    /** Message formats  */
    public static final String LIST_TASK_FORMAT = "%1$d. %2$s%3$s %4$s";
    public static final String ADDED_EVENT_FORMAT = "added event:" + NEWLINE + "%1$s";
    public static final String ADDED_DEADLINE_FORMAT = "added deadline:" + NEWLINE + "%1$s";
    public static final String ADDED_TODO_FORMAT = "added todo: " + "%1$s";

    public static final String LOGO_PRINT_FORMAT = "Hello from" + NEWLINE + "%1$s" + NEWLINE + TootieSymbols.VERSION;
    public static final String NUMTASKS_PRINT_FORMAT = "You have %1$d tasks, %2$s not done";
    public static final String TASKS_ALL_DONE_MSG = "all done " + TootieSymbols.BEAR_EMOTICON;
}
