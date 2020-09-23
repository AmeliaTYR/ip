package duke.ui;

import duke.exceptions.TasklistEmptyException;
import duke.constants.*;
import duke.task.Task;

import java.util.ArrayList;
import java.util.Random;

/**
 * Functions to print to the console as part of the UI
 */
public class Printers {
    /**
     * Line divider set to default before settings are loaded
     */
    public static String currentLineDivider = TootieSymbols.DOUBLE_TEXT_DIVIDER;


    /**
     * Changes the line divider to the option chosen by user
     *
     * @param dividerChoice divider chosen by user
     */
    public static void changeDivider(DividerChoice dividerChoice) {
        switch (dividerChoice) {
        case SIMPLE:
            currentLineDivider = TootieSymbols.SIMPLE_TEXT_DIVIDER;
            break;
        case SPARKLY:
            currentLineDivider = TootieSymbols.SPARKLY_TEXT_DIVIDER;
            break;
        case DOUBLE:
            currentLineDivider = TootieSymbols.DOUBLE_TEXT_DIVIDER;
            break;
        default:
            currentLineDivider = TootieSymbols.PLAIN_TEXT_DIVIDER;
            break;
        }
    }

    /**
     * Prints the line divider to the console
     */
    public static void printDivider() {
        System.out.println(currentLineDivider);
    }

    /**
     * Prints the personalised hello message
     *
     * @param username
     */
    public static void printHelloMessage(String username) {
        printDivider();
        System.out.print(String.format(TootieNormalMsgs.HELLO_GREETING, username));
        printDivider();
    }

    /**
     * Prints the personalised farewell message
     *
     * @param username user indicated name
     */
    public static void printFarewellMessage(String username) {
        System.out.print(String.format(TootieNormalMsgs.FAREWELL_GREETING, username));
    }


    /**
     * Prints a randomised version of the Tootie logo
     */
    public static void printTootieLogo() {
        String[] logos = new String[TootieConstants.NUM_LOGOS_AVAILABLE];
        logos[0] = TootieSymbols.SIMPLE_TOOTIE_LOGO;
        logos[1] = TootieSymbols.BLOCKY_TOOTIE_LOGO;
        logos[2] = TootieSymbols.TRAIN_THEME_TOOTIE_LOGO;
        logos[3] = TootieSymbols.THICK_TOOTIE_LOGO;
        Random rand = new Random(System.currentTimeMillis());
        System.out.println(String.format(TootieNormalMsgs.LOGO_PRINT_FORMAT, logos[Math.abs(rand.nextInt() % 4)]));
    }

    /**
     * Prints message when command is not understood by the program
     */
    public static void printConfusedMessage() {
        System.out.println(TootieErrorMsgs.COMMAND_NOT_FOUND_MSG);
        System.out.println(TootieErrorMsgs.SUGGEST_HELP_OPTION_MSG);
    }

    /**
     * Prints list of commands and example usage
     */
    public static void printHelpInfo() {
        System.out.println(TootieNormalMsgs.HELP_INFO_MSG);
    }

    /**
     * Prints all list items with index and completion indicator
     *
     * @param allTasks          list of all Tasks
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks completed
     * @throws TasklistEmptyException
     */
    public static void printAllTasks(ArrayList<Task> allTasks, int numTasks, int numTasksCompleted) throws TasklistEmptyException {

        if (numTasks == 0) {
            throw new TasklistEmptyException();
        }
        System.out.println(String.format(TootieNormalMsgs.NUMTASKS_PRINT_FORMAT, numTasks,
                numTasks - numTasksCompleted));
        for (int i = 0; i < numTasks; i++) {
            System.out.println(String.format(TootieNormalMsgs.LIST_TASK_FORMAT, (i + 1),
                    allTasks.get(i).getTaskType(), allTasks.get(i).getCompletionIndicator(),
                    allTasks.get(i).getTaskDescription()));
        }

        if (numTasks == numTasksCompleted) {
            System.out.println(TootieNormalMsgs.TASKS_ALL_DONE_MSG);
        }
    }
}
