package duke.constants;

public class TootieErrorMsgs {
    public static final String NEWLINE = System.lineSeparator();

    public static final String TasklistEmptyMsg = "No tasks found! " + TootieSymbols.HAPPY_EMOTICON;
    public static final String TODO_WRONG_FORMAT_MSG = "Check todo input formatting!" + NEWLINE + NEWLINE + TootieNormalMsgs.TODO_COMMAND_DESCRIPTION;
    public static final String DEADLINE_WRONG_FORMAT_MSG = "Check deadline input formatting!" + NEWLINE + NEWLINE + TootieNormalMsgs.DEADLINE_COMMAND_DESCRIPTION;
    public static final String DUEDATE_WRONG_FORMAT_MSG = "Check due date formatting!" + NEWLINE + NEWLINE + TootieNormalMsgs.DEADLINE_COMMAND_DESCRIPTION + NEWLINE + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE + NEWLINE;
    public static final String EVENT_WRONG_FORMAT_MSG = "Check event input formatting!" + NEWLINE + NEWLINE + TootieNormalMsgs.EVENT_COMMAND_DESCRIPTION;
    public static final String STARTDATE_WRONG_FORMAT_MSG = "Check start date formatting!" + NEWLINE + NEWLINE + TootieNormalMsgs.EVENT_COMMAND_DESCRIPTION + NEWLINE + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE + NEWLINE;
    public static final String ENDDATE_WRONG_FORMAT_MSG = "Check end date formatting!" + NEWLINE + NEWLINE + TootieNormalMsgs.EVENT_COMMAND_DESCRIPTION + NEWLINE + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE + NEWLINE;
    public static final String COMMAND_NOT_FOUND_MSG = "Command not found? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String SUGGEST_HELP_OPTION_MSG = "Type \"help\" for a list of commands!";
    public static final String TODO_TASKNAME_EMPTY_MSG = "todo taskname is empty? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String DEADLINE_TASKNAME_EMPTY_MSG = "deadline taskname is empty? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String INVALID_DUE_DATE_MSG = "Invalid due date";
    public static final String INVALID_START_DATE_MSG = "Invalid start date";
    public static final String INVALID_END_DATE_MSG = "Invalid end date";
    public static final String ENDTIME_BEFORE_STARTIME_ERROR_MSG = "Error! End time cannot be before start time!";
    public static final String EVENT_TASKNAME_EMPTY_MSG = "event taskname is empty? " + TootieSymbols.CONFUSED_EMOTICON;
    public static final String TASK_NOT_FOUND_ERROR_MSG = "No such task? " + TootieSymbols.CONFUSED_EMOTICON;
}
