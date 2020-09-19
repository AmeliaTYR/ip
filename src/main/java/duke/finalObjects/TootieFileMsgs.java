package duke.finalObjects;

public class TootieFileMsgs {
    public static final String NEWLINE = System.lineSeparator();

    public static final String INDICATOR_INSTRUCTION = "Task completion status:" + NEWLINE + "0 is not done(" +
            TootieSymbols.CROSS_SYMBOL + "), 1 is done(" + TootieSymbols.TICK_SYMBOL + ")" + NEWLINE;
    public static final String TASK_TYPE_INSTRUCTION = "Task types (T,D,E):" + NEWLINE +
            "T is to-do, D is deadline, E is event" + NEWLINE;
    public static final String EXAMPLE_INSTRUCTION = "Examples:" + NEWLINE + "[T][0] clean shoes" + NEWLINE +
            "[D][1] do project (by: Thu Jan 30 04:55:00 SGT 2020)" + NEWLINE + "" +
            "[E][1] clean kitchen (from: Sat Dec 12 00:00:00 SGT 2020 to Thu Dec 31 00:00:00 SGT 2020)" + NEWLINE;
    public static final String TASK_LIST_START = "--------------------------------------------------------" +
            NEWLINE+ "Tasks:" + NEWLINE;

    public static final String FILE_INSTRUCTIONS_HEADER = INDICATOR_INSTRUCTION + NEWLINE +
            TASK_TYPE_INSTRUCTION + NEWLINE + EXAMPLE_INSTRUCTION + NEWLINE + TASK_LIST_START;

}
