import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;

public class Duke {

    /**
     * Version info of the program.
     */
    private static final String VERSION = "Tootie - Version 1.2";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final char INPUT_COMMENT_MARKER = '#';

    public static final String NEWLINE = System.lineSeparator();

    // Help command descriptions
    public static final String HELP_COMMAND_DESCRIPTION =
            "help: displays a list of commands tootie understands" + NEWLINE + "  Example:  help" + NEWLINE;
    public static final String TODO_COMMAND_DESCRIPTION = "todo: add a todo task to the" + " list" + NEWLINE + "  " +
            "Parameters:  todo t/TASKNAME" + NEWLINE + "  Example:  todo t/clean room " + NEWLINE;
    public static final String DEADLINE_COMMAND_DESCRIPTION =
            "deadline: add a task with a deadline to the list" + NEWLINE + "  Parameters:  deadline t/TASKNAME " +
                    "d/DUE_DATE" + NEWLINE + "  Example:  deadline t/write essay d/31-12-2020 04:55" + NEWLINE + "  " +
                    "Example:  deadline t/submit report d/30-10-2020" + NEWLINE;
    public static final String EVENT_COMMAND_DESCRIPTION =
            "event: add a scheduled event task to the list" + NEWLINE + "  Parameters:  event t/TASKNAME s/START_TIME " +
                    "e/END_TIME" + NEWLINE + "  Example:  event t/clean room s/31-12-2020 04:55 e/31-12-2020 05:45"
                    + NEWLINE + "  Example:  event t/clean room s/31-12-2020 e/31-12-2020" + NEWLINE;
    public static final String LIST_COMMAND_DESCRIPTION =
            "list: displays the complete list of tasks entered" + NEWLINE + "  Example:  list" + NEWLINE;
    public static final String BYE_COMMAND_DESCRIPTION =
            "bye: closes the program" + NEWLINE + "  Example:  bye" + NEWLINE;
    public static final String HELP_COMMAND_TEXT =
            HELP_COMMAND_DESCRIPTION + NEWLINE + TODO_COMMAND_DESCRIPTION + NEWLINE + DEADLINE_COMMAND_DESCRIPTION
                    + NEWLINE + EVENT_COMMAND_DESCRIPTION + NEWLINE + LIST_COMMAND_DESCRIPTION + NEWLINE
                    + BYE_COMMAND_DESCRIPTION + NEWLINE + "-----" + NEWLINE
                    + "NOTE: datetime entries can be of the format \"dd-MM-yyyy HH:mm\" " + NEWLINE
                    + "    OR \"dd-MM-yyyy\"";

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
            + "\u2588\u2588\u2551\u2588\u2588\u2588\u2588" + "\u2588\u2557  " + NEWLINE + "   \u2588\u2588\u2551 "
            + "  \u2588\u2588\u2551   " + "\u2588\u2588\u2551\u2588\u2588\u2551   \u2588\u2588\u2551   "
            + "\u2588\u2588\u2551   " + "\u2588\u2588\u2551\u2588\u2588\u2554\u2550\u2550\u255d  " + NEWLINE + " "
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

    public static final String SPARKLY_TEXT_DIVIDER = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500 "
            + "\u2731\u002a\u002e\uff61\u003a\uff61" + "\u2731\u002a\u002e\u003a\uff61\u2727\u002a\u002e\uff61"
            + "\u2730\u002a\u002e\u003a\uff61\u2727" + "\u002a\u002e\uff61\u003a\uff61\u002a\u002e\uff61\u2731"
            + " " + "\u2500\u2500\u2500\u2500\u2500" + "\u2500\u2500";

    public static final int MAX_TASKS = 100;
    /**
     * List of all tasks.
     */
    private static ArrayList<Task> allTasks = new ArrayList<>(MAX_TASKS);

    /**
     * Total number of tasks in the list
     */
    private static int numTasks = 0;

    public static void main(String[] args) {
        String userInput;
        CommandType commandType = CommandType.START;

        printTootieLogo();
        printHelloMessage();

        while(commandType != CommandType.BYE){
            userInput = SCANNER.nextLine();
            printDivider();
            commandType = extractCommandType(userInput);
            executeCommand(commandType, userInput, allTasks);
            printDivider();
        }
    }

    // prints Tootie logo (text art randomized each run)
    public static void printTootieLogo() {
        String[] logos = new String[4];
        logos[0] = SIMPLE_TOOTIE_LOGO;
        logos[1] = BLOCKY_TOOTIE_LOGO;
        logos[2] = TRAIN_THEME_TOOTIE_LOGO;
        logos[3] = THICK_TOOTIE_LOGO;
        Random rand = new Random(System.currentTimeMillis());
        System.out.println("Hello from" + NEWLINE + logos[Math.abs(rand.nextInt() % 4)] + NEWLINE + VERSION);
    }

    // prints the line divider
    public static void printDivider() {
        System.out.println(SPARKLY_TEXT_DIVIDER);
    }

    // prints the hello when starting
    public static void printHelloMessage() {
        String helloGreeting = "Hello! I'm Tootie!" + NEWLINE + "What can I do for you?" + NEWLINE;
        printDivider();
        System.out.print(helloGreeting);
        printDivider();
    }

    // prints farewell message
    public static void printFarewellMessage() {
        String farewellGreeting = "Bye! Hope to see you again soon! " + FLOWER_SMILE_EMOTICON + NEWLINE;
        System.out.print(farewellGreeting);
    }

    // prints all list items with index and check
    public static void printAllTasks(ArrayList<Task> taskList) {
        if (numTasks == 0) {
            System.out.println("No tasks found! " + HAPPY_EMOTICON);
        } else {
            for (int i = 0; i < numTasks; i++) {
                System.out.print((i + 1) + ". ");
                taskList.get(i).printTaskType();
                if (taskList.get(i).isComplete()) {
                    System.out.print(TICK_SYMBOL + " ");
                } else {
                    System.out.print(CROSS_SYMBOL + " ");
                }
                taskList.get(i).printTaskDescription();
            }
        }

        // TODO: add "all done ʕ•ᴥ•ʔ" if all tasks done for now
    }

    // print the message when command is not understood
    private static void printConfusedMessage() {
        System.out.println("Command not found? " + CONFUSED_EMOTICON);
        System.out.println("Type \"help\" for a list of commands!");
    }

    // print list of commands and example usage
    private static void printHelpInfo() {
        System.out.println("Here is the list of commands I understand:" + NEWLINE + NEWLINE + HELP_COMMAND_TEXT);
    }

    // figure out the command type from userInput
    private static CommandType extractCommandType(String userInput) {
        if (userInput.trim().startsWith("help")){
            return CommandType.HELP;
        } else if (userInput.trim().startsWith("todo")){
            return CommandType.ADD_TODO;
        } else if (userInput.trim().startsWith("deadline")){
            return CommandType.ADD_DEADLINE;
        } else if (userInput.trim().startsWith("event")){
            return CommandType.ADD_EVENT;
        } else if (userInput.trim().startsWith("list")){
            return CommandType.LIST;
        } else if (userInput.trim().startsWith("done")){
            return CommandType.DONE;
        } else if (userInput.trim().startsWith("bye")){
                return CommandType.BYE;
        } else {
            return CommandType.UNRECOGNISED;
        }
    }

    // execute the command as required
    private static void executeCommand(CommandType commandType, String userInput, ArrayList<Task> allTasks) {
        switch (commandType) {
        case HELP:
            printHelpInfo();
            break;
        case ADD_TODO:
            addToDo(userInput);
            break;
        case ADD_DEADLINE:
            addDeadLine(userInput);
            break;
        case ADD_EVENT:
            addEvent(userInput);
            break;
        case LIST:
            printAllTasks(allTasks);
            break;
        case DONE:
            markTaskComplete(userInput, allTasks);
            break;
        case BYE:
            printFarewellMessage();
            break;
        default:
            printConfusedMessage();
        }
    }

    // add an event task to the allTasks list
    private static void addEvent(String userInput) {
        boolean placementCorrect = true;

        Date startTime = null;
        Date endTime;

        String taskName = "";
        String startTimeUnformatted = "";
        String endTimeUnformatted = "";

        SimpleDateFormat dateWithTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("dd-MM-yyyy");

        // identify placements
        int taskNamePosition = userInput.indexOf("t/");
        int startTimePosition = userInput.indexOf("s/");
        int endTimePosition = userInput.indexOf("e/");

        // check if placement is correct
        if (taskNamePosition == -1 || startTimePosition == -1 || endTimePosition == -1) {
            placementCorrect = false;
        } else {
            try {
                taskName = userInput.substring(taskNamePosition + 2, startTimePosition);
                startTimeUnformatted = userInput.substring(startTimePosition + 2, endTimePosition).trim();
                endTimeUnformatted = userInput.substring(endTimePosition + 2).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                placementCorrect = false;
            }
        }

        // checks if date is correctly formatted and valid
        if (placementCorrect) {
            // for start time
            boolean isStartDateWithTime =
                    startTimeUnformatted.matches("([0-9]{2})-([0-9]{2})-([0-9]{4}) ([0-9]{2}):([0-9]{2})");
            boolean isStartDateWithoutTime = startTimeUnformatted.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");
            // for end time
            boolean isEndDateWithTime =
                    endTimeUnformatted.matches("([0-9]{2})-([0-9]{2})-([0-9]{4}) ([0-9]{2}):([0-9]{2})");
            boolean isEndDateWithoutTime = endTimeUnformatted.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");

            if (isStartDateWithTime) {
                try {
                    startTime = dateWithTime.parse(startTimeUnformatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if(isStartDateWithoutTime){
                try {
                    startTime = dateWithoutTime.parse(startTimeUnformatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Check start date formatting!" + NEWLINE + NEWLINE + EVENT_COMMAND_DESCRIPTION);
                return;
            }

            if (isEndDateWithTime) {
                try {
                    endTime = dateWithTime.parse(endTimeUnformatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
            } else if(isEndDateWithoutTime){
                try {
                    endTime = dateWithoutTime.parse(endTimeUnformatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                System.out.println("Check end date formatting!" + NEWLINE + NEWLINE + EVENT_COMMAND_DESCRIPTION);
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.setTime(startTime);
            try {
                cal.getTime();
            }
            catch (Exception e) {
                System.out.println("Invalid start date");
                return;
            }

            cal.setLenient(false);
            cal.setTime(endTime);
            try {
                cal.getTime();
            }
            catch (Exception e) {
                System.out.println("Invalid end date");
                return;
            }

            if(startTime.after(endTime)){
                System.out.println("Error! End time cannot be before start time!");
                return;
            }

        } else {
            System.out.println("Check event input formatting!" + NEWLINE + NEWLINE + EVENT_COMMAND_DESCRIPTION);
            return;
        }

        // add event to list
        allTasks.add(new Event(taskName.trim(), startTime, endTime));
        System.out.println("added: ");
        allTasks.get(numTasks).printTaskDescription();
        numTasks++;
    }

    // adds a deadline task to the allTasks list
    private static void addDeadLine(String userInput) {
        boolean placementCorrect = true;

        Date dueDate = null;

        String taskName = "";
        String dueDateUnformatted = "";

        SimpleDateFormat dateWithTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("dd-MM-yyyy");

        // identify placements
        int taskNamePosition = userInput.indexOf("t/");
        int dueDatePosition = userInput.indexOf("d/");

        // check if placement is correct
        if (taskNamePosition == -1 || dueDatePosition == -1) {
            placementCorrect = false;
        } else {
            try {
                taskName = userInput.substring(taskNamePosition + 2, dueDatePosition);
                dueDateUnformatted = userInput.substring(dueDatePosition + 2).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                placementCorrect = false;
            }
        }

        // checks if date is correctly formatted and valid
        if (placementCorrect) {
            // for due date
            boolean isDueDateWithTime =
                    dueDateUnformatted.matches("([0-9]{2})-([0-9]{2})-([0-9]{4}) ([0-9]{2}):([0-9]{2})");
            boolean isDueDateWithoutTime = dueDateUnformatted.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");

            if (isDueDateWithTime) {
                try {
                    dueDate = dateWithTime.parse(dueDateUnformatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if(isDueDateWithoutTime){
                try {
                    dueDate = dateWithoutTime.parse(dueDateUnformatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Check due date formatting!" + NEWLINE + NEWLINE + DEADLINE_COMMAND_DESCRIPTION);
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.setTime(dueDate);
            try {
                cal.getTime();
            }
            catch (Exception e) {
                System.out.println("Invalid due date");
                return;
            }

        } else {
            System.out.println("Check deadline input formatting!" + NEWLINE + NEWLINE + DEADLINE_COMMAND_DESCRIPTION);
            return;
        }

        // add event to list
        allTasks.add(new Deadline(taskName.trim(), dueDate));
        System.out.println("added: ");
        allTasks.get(numTasks).printTaskDescription();
        numTasks++;
    }

    // adds a toto task to the allTasks list
    private static void addToDo(String userInput) {
        // identify placements
        int taskNamePosition = userInput.indexOf("t/");
        if (taskNamePosition == -1){
            System.out.println("todo wrong format :(" + NEWLINE + NEWLINE + TODO_COMMAND_DESCRIPTION);
            return;
        }
        String taskName = userInput.substring(taskNamePosition + 2);

        // add task to list
        allTasks.add(new ToDo(taskName.trim()));
        numTasks++;
        System.out.println("added: " + taskName.trim());
    }


    // process the user input and mark the
    private static void markTaskComplete(String userInput, ArrayList<Task> allTasks) {
        int taskNum;
        // mark task in allTasks as complete
        try {
            taskNum = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (taskNum > numTasks || taskNum < 1) {
                taskNum = -1;
            } else {
                allTasks.get(taskNum - 1).setComplete(true);
            }
        } catch (NumberFormatException exception) {
            taskNum = -1;
        }

        // print response
        if (taskNum == -1) {
            System.out.println("No such task? " + CONFUSED_EMOTICON);
        } else {
            System.out.println("Nice! I've marked this task as done: ");
            System.out.println("    " + TICK_SYMBOL + allTasks.get(taskNum - 1).getTaskName());
            System.out.println(SPARKLY_EMOTICON);
        }
    }
}



