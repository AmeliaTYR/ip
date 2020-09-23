package duke.ui;

import duke.exceptions.TasklistEmptyException;
import duke.constants.*;
import duke.task.Task;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Printers {
    private final Scanner in;
    private final PrintStream out;


    public Printers(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public static String currentLineDivider = TootieSymbols.STANDARD_TEXT_DIVIDER;

    public static void changeDivider(DividerChoice dividerChoice) {
        switch (dividerChoice){
        case SIMPLE:
            currentLineDivider = TootieSymbols.SIMPLE_TEXT_DIVIDER;
            break;
        case SPARKLY:
            currentLineDivider = TootieSymbols.SPARKLY_TEXT_DIVIDER;
            break;
        default:
            currentLineDivider = TootieSymbols.PLAIN_TEXT_DIVIDER;
            break;
        }
    }

    // prints the line divider
    public static void printDivider() {
        System.out.println(currentLineDivider);
    }

    // prints the hello when starting
    public static void printHelloMessage(String username) {
        printDivider();
        System.out.print(String.format(TootieNormalMsgs.HELLO_GREETING, username));
        printDivider();
    }

    // prints farewell message
    public static void printFarewellMessage(String username) {
        System.out.print(String.format(TootieNormalMsgs.FAREWELL_GREETING, username));
    }

    // prints Tootie logo (text art randomized each run)
    public static void printTootieLogo() {
        String[] logos = new String[TootieConstants.NUM_LOGOS_AVAILABLE];
        logos[0] = TootieSymbols.SIMPLE_TOOTIE_LOGO;
        logos[1] = TootieSymbols.BLOCKY_TOOTIE_LOGO;
        logos[2] = TootieSymbols.TRAIN_THEME_TOOTIE_LOGO;
        logos[3] = TootieSymbols.THICK_TOOTIE_LOGO;
        Random rand = new Random(System.currentTimeMillis());
        System.out.println(String.format(TootieNormalMsgs.LOGO_PRINT_FORMAT, logos[Math.abs(rand.nextInt() % 4)]));
    }

    // print the message when command is not understood
    public static void printConfusedMessage() {
        System.out.println(TootieErrorMsgs.COMMAND_NOT_FOUND_MSG);
        System.out.println(TootieErrorMsgs.SUGGEST_HELP_OPTION_MSG);
    }

    // print list of commands and example usage
    public static void printHelpInfo() {
        System.out.println(TootieNormalMsgs.HELP_INFO_MSG);
    }

    // prints all list items with index and check
    public static void printAllTasks (ArrayList<Task> allTasks, int numTasks, int numTasksCompleted)
            throws TasklistEmptyException {

        if (numTasks == 0) {
            throw new TasklistEmptyException();
        }

        System.out.println(String.format(TootieNormalMsgs.NUMTASKS_PRINT_FORMAT, numTasks, numTasks - numTasksCompleted));
        for (int i = 0; i < numTasks; i++) {
            System.out.println(String.format(TootieNormalMsgs.LIST_TASK_FORMAT,
                    (i + 1),
                    allTasks.get(i).getTaskType(),
                    allTasks.get(i).getCompletionIndicator(),
                    allTasks.get(i).getTaskDescription())
            );
        }

        if (numTasks == numTasksCompleted){
            System.out.println(TootieNormalMsgs.TASKS_ALL_DONE_MSG);
        }
    }
}
