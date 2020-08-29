import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

public class Duke {

    /**
     * Version info of the program.
     */
    private static final String VERSION = "Tootie - Version 1.1";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final char INPUT_COMMENT_MARKER = '#';

    public static final String NEWLINE = System.lineSeparator();

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

    /**
     * List of all tasks.
     */
    private static LinkedList<Task> allTasks = new LinkedList<Task>();

    /**
     * Total number of tasks in the list
     */
    private static int numListItems = 0;


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
        printDivider();
    }

    // prints all list items with index and check
    public static void printListItems(int numListItems, LinkedList<Task> taskList) {
        for (int i = 0; i < numListItems; i++) {
            System.out.print((i + 1) + ". ");
            if (taskList.get(i).isComplete()) {
                System.out.print(TICK_SYMBOL);
            } else {
                System.out.print(CROSS_SYMBOL);
            }
            System.out.println(taskList.get(i).getTaskName());
        }

        // TODO: add "all done ʕ•ᴥ•ʔ" if all tasks done for now
    }

    // TODO: add a help option to print list of commands
    public static void help() {
        String helpCommand = "";
        System.out.println("Here is the list of commands I understand!" + NEWLINE);
    }

    // updates tasks isComplete to true, returns -1 if userInput invalid
    public static int updateTaskStatus(LinkedList<Task> taskList, String userInput, int numListItems) {
        int taskNum;
        try {
            taskNum = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (taskNum > numListItems || taskNum < 1) {
                return -1;
            } else {
                taskList.get(taskNum - 1).setComplete(true);
                return taskNum;
            }
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public static void main(String[] args) {
        // TODO: make get user input its own function
        String userInput;

        int taskNum;

        printTootieLogo();
        printHelloMessage();

        userInput = SCANNER.nextLine();

        // TODO: only add things to list when add command is used, otherwise ignore
        // process all user inputs
        while (!userInput.equals("bye")) {
            if (userInput.equals("list")) {
                // print items from the list if any
                printDivider();
                if (numListItems == 0) {
                    System.out.println("No tasks found! " + HAPPY_EMOTICON);
                } else {
                    printListItems(numListItems, allTasks);
                }
            } else if (userInput.startsWith("done")) {
                // update isComplete status of that task
                taskNum = updateTaskStatus(allTasks, userInput, numListItems);
                // TODO: move the printing into the function updateTaskStatus
                if (taskNum == -1) {
                    printDivider();
                    System.out.println("No such task? " + CONFUSED_EMOTICON);
                } else {
                    printDivider();
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println("    " + TICK_SYMBOL + allTasks.get(taskNum - 1).getTaskName());
                    System.out.println(SPARKLY_EMOTICON);
                }
            } else if (userInput.startsWith("done")){
                // add new task to list
                allTasks.addLast(new Task(userInput, false));
                numListItems++;
                printDivider();
                System.out.println("added: " + userInput);
            } else {
                System.out.println("Command not found? " + CONFUSED_EMOTICON);
            }
            printDivider();
            userInput = SCANNER.nextLine();
        }

        printDivider();
        printFarewellMessage();
    }

}



