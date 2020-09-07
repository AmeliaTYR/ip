package duke.finalObjects;

public class TootieStrings {

    public static final String NEWLINE = System.lineSeparator();
    
    // Help command descriptions
    public static final String HELP_COMMAND_DESCRIPTION =
            "help: displays a list of commands tootie understands" + NEWLINE + "  Example:  help" + NEWLINE;
    public static final String TODO_COMMAND_DESCRIPTION = "todo: add a todo task to the" + " list" + NEWLINE + 
            "  " + "Parameters:  todo t/TASKNAME" + NEWLINE + "  Example:  todo t/clean room" + NEWLINE;
    public static final String DEADLINE_COMMAND_DESCRIPTION =
            "deadline: add a task with a deadline to the list" + NEWLINE + "  Parameters:  deadline t/TASKNAME " +
                    "d/DUE_DATE" + NEWLINE + "  Example:  deadline t/write essay d/31-12-2020 04:55" + NEWLINE + "  " +
                    "Example:  deadline t/submit report d/30-10-2020" + NEWLINE;
    public static final String EVENT_COMMAND_DESCRIPTION =
            "event: add a scheduled event task to the list" + NEWLINE + "  Parameters:  event t/TASKNAME s/START_TIME "
                    + "e/END_TIME" + NEWLINE + "  Example:  event t/clean room s/31-12-2020 04:55 e/31-12-2020 05:45"
                    + NEWLINE + "  Example:  event t/clean room s/31-12-2020 e/31-12-2020" + NEWLINE;
    public static final String LIST_COMMAND_DESCRIPTION =
            "list: displays the complete list of tasks entered" + NEWLINE + "  Example:  list" + NEWLINE;
    public static final String BYE_COMMAND_DESCRIPTION =
            "bye: closes the program" + NEWLINE + "  Example:  bye" + NEWLINE;
    public static final String DATE_FORMAT_MESSAGE = "NOTE: datetime entries can be of the format \"dd-MM-yyyy " +
            "HH:mm\"" + NEWLINE + "    OR \"dd-MM-yyyy\"";
    public static final String HELP_COMMAND_TEXT =
            HELP_COMMAND_DESCRIPTION + NEWLINE + TODO_COMMAND_DESCRIPTION + NEWLINE + DEADLINE_COMMAND_DESCRIPTION
                    + NEWLINE + EVENT_COMMAND_DESCRIPTION + NEWLINE + LIST_COMMAND_DESCRIPTION + NEWLINE
                    + BYE_COMMAND_DESCRIPTION + NEWLINE + "-----" + NEWLINE + DATE_FORMAT_MESSAGE + NEWLINE;
    
    // Tootie logos
    public static final String THICK_TOOTIE_LOGO = "88888888888                888    d8b          " + NEWLINE
            + "    888                    " + "888" +
            "    Y8P          " + NEWLINE + "    888                    888                 " + NEWLINE + "    " +
            "888   .d88b.   .d88b.  888888 888  .d88b.  " + NEWLINE + "    888  d88\"\"88b d88\"\"88b 888    888 " +
            "d8P  Y8b " + NEWLINE + "    888  888  888 888  888 888    888 88888888 " + NEWLINE + "    888  Y88." +
            ".88P Y88..88P Y88b.  888 Y8b.     " + NEWLINE + "    888   \"Y88P\"   \"Y88P\"   \"Y888 888  \"Y8888" +
            "  " + NEWLINE;
    public static final String TRAIN_THEME_TOOTIE_LOGO = "  _____                    _        _            " + NEWLINE
            + " |_   _|   ___     ___    " + "| " +
            "|_     (_)     ___   " + NEWLINE + "   | |    / _ \\   / _ \\  " + " |" + "  _|    | |   " + " /" +
            " -_)  " + NEWLINE + "  _|_|_   \\___/   \\___/   _\\__|   _|_|_   \\___|  " + NEWLINE + "_" +
            "|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_" + "|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| " + NEWLINE +
            "\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' " + NEWLINE;
    public static final String BLOCKY_TOOTIE_LOGO = "\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557 "
            + "\u2588\u2588\u2588\u2588\u2588\u2588" + "\u2557" + "  \u2588\u2588\u2588\u2588\u2588\u2588\u2557 "
            + "\u2588\u2588\u2588\u2588\u2588" + "\u2588"
            + "\u2588\u2588\u2557\u2588\u2588\u2557\u2588\u2588\u2588\u2588" + "\u2588\u2588" + "\u2588\u2557"
            + NEWLINE + "\u255a\u2550\u2550\u2588\u2588\u2554\u2550\u2550\u255d"
            + "\u2588\u2588\u2554\u2550\u2550\u2550\u2588\u2588\u2557\u2588\u2588\u2554\u2550\u2550\u2550" + "\u2588"
            + "\u2588\u2557\u255a\u2550\u2550\u2588\u2588\u2554\u2550\u2550\u255d\u2588\u2588" + "\u2551\u2588\u2588"
            + "\u2554\u2550\u2550\u2550\u2550\u255d" + NEWLINE + "   \u2588" + "\u2588\u2551   \u2588\u2588\u2551"
            + "   \u2588\u2588\u2551\u2588\u2588\u2551   " + "\u2588\u2588\u2551   \u2588\u2588\u2551   "
            + "\u2588\u2588\u2551\u2588\u2588\u2588\u2588" + "\u2588\u2557" + NEWLINE + "   \u2588\u2588\u2551 "
            + "  \u2588\u2588\u2551   " + "\u2588\u2588\u2551\u2588\u2588\u2551   \u2588\u2588\u2551   "
            + "\u2588\u2588\u2551   " + "\u2588\u2588\u2551\u2588\u2588\u2554\u2550\u2550\u255d" + NEWLINE + " "
            + "  \u2588" + "\u2588\u2551   \u255a\u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d\u255a\u2588\u2588"
            + "\u2588\u2588\u2588\u2588\u2554\u255d   \u2588\u2588\u2551   " + "\u2588\u2588\u2551\u2588"
            + "\u2588\u2588\u2588\u2588\u2588\u2588\u2557" + NEWLINE + "   \u255a" + "\u2550\u255d  "
            + "  \u255a\u2550\u2550\u2550\u2550\u2550\u255d  " + "\u255a\u2550\u2550\u2550\u2550\u2550"
            + "\u255d    \u255a\u2550\u255d   " + "\u255a\u2550\u255d\u255a\u2550\u2550\u2550\u2550\u2550"
            + "\u2550\u255d" + NEWLINE;
    public static final String SIMPLE_TOOTIE_LOGO =
            " _____           _   _      " + NEWLINE + "|_   _|         | | (_)     " + NEWLINE + "  | | ___  " +
            " ___ | |_ _  ___ " + NEWLINE + "  | |/ _ \\ / _ \\| __| |/ _ \\" + NEWLINE + "  | | (_) | (_) | |_| " +
            "|  __/" + NEWLINE + "  \\_/\\___/ \\___/ \\__|_|\\___|" + NEWLINE;

    // is complete indicator symbols
    public static final String TICK_SYMBOL = "[\u2713]";
    public static final String CROSS_SYMBOL = "[\u2717]";

    // Emoticons in unicode
    public static final String SPARKLY_EMOTICON =
            "\u0028\uff89\u25d5\u30ee\u25d5\u0029\uff89\u002a\u003a\uff65\uff9f\u2727";
    public static final String CONFUSED_EMOTICON = "\u0028\u30fb\u2227\u2010\u0029\u309e";
    public static final String HAPPY_EMOTICON = "\uff08\u00b4\u30fb\u03c9\u30fb \u0060\uff09";
    public static final String FLOWER_SMILE_EMOTICON = "(\u25e0\u203f\u25e0\u273f)";

    public static final String FAREWELL_GREETING =
            "Bye! Hope to see you again soon! " + FLOWER_SMILE_EMOTICON + NEWLINE;

    public static final String SPARKLY_TEXT_DIVIDER = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500 "
            + "\u2731\u002a\u002e\uff61\u003a\uff61" + "\u2731\u002a\u002e\u003a\uff61\u2727\u002a\u002e\uff61"
            + "\u2730\u002a\u002e\u003a\uff61\u2727" + "\u002a\u002e\uff61\u003a\uff61\u002a\u002e\uff61\u2731"
            + " " + "\u2500\u2500\u2500\u2500\u2500" + "\u2500\u2500";


    public static final String HELLO_GREETING = "Hello! I'm Tootie!" + NEWLINE + "What can I do for you?" + NEWLINE;


    public static final String VERSION = "Tootie - Version 1.4";
}
