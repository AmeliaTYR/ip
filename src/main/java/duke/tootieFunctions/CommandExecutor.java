package duke.tootieFunctions;

import duke.constants.TootieSymbols;
import duke.exceptions.*;
import duke.storage.AllTasksSaver;
import duke.constants.CommandType;
import duke.constants.DividerChoice;
import duke.constants.TootieErrorMsgs;
import duke.parsers.Parsers;
import duke.task.Task;
import duke.ui.Printers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static duke.tootieFunctions.Filters.filterTasks;
import static duke.ui.Printers.printDivider;

/**
 * Contains functions that detect and execute commands
 */
public class CommandExecutor {
    /** Newline object specific to system used  */
    public static final String NEWLINE = System.lineSeparator();

    /**
     * figure out the command type from userInput
     *
     * @param userInput raw user input
     * @return command type object
     */
    public static CommandType extractCommandType(String userInput) {
        if (userInput.toLowerCase().trim().startsWith("help")) {
            return CommandType.HELP;
        } else if (userInput.toLowerCase().trim().startsWith("todo")) {
            return CommandType.ADD_TODO;
        } else if (userInput.toLowerCase().trim().startsWith("deadline")) {
            return CommandType.ADD_DEADLINE;
        } else if (userInput.toLowerCase().trim().startsWith("event")) {
            return CommandType.ADD_EVENT;
        } else if (userInput.toLowerCase().trim().startsWith("list")) {
            return CommandType.LIST;
        } else if (userInput.toLowerCase().trim().startsWith("done")) {
            return CommandType.MARK_TASK_DONE;
        } else if (userInput.toLowerCase().trim().startsWith("bye")) {
            return CommandType.BYE;
        } else if (userInput.toLowerCase().trim().startsWith("delete")) {
            return CommandType.DELETE_TASK;
        } else if (userInput.toLowerCase().trim().startsWith("undone")) {
            return CommandType.MARK_TASK_UNDONE;
        } else if (userInput.toLowerCase().trim().startsWith("divider")) {
            return CommandType.CHOOSE_DIVIDER;
        } else if (userInput.toLowerCase().trim().startsWith("username")) {
            return CommandType.SET_USERNAME;
        } else if (userInput.toLowerCase().trim().startsWith("filter")) {
            return CommandType.FILTER_TASKS;
        } else if (userInput.toLowerCase().trim().startsWith("save")) {
            return CommandType.SAVE;
        } else {
            return CommandType.UNRECOGNISED;
        }
    }

    /**
     * Parses the user command and executes it
     *
     * @param savedSettings     array containing saved settings for updates
     * @param commandType       command type detected
     * @param userInput         raw user input
     * @param allTasks          list of all Tasks
     * @param allTasksFilePath  file path to allTasks.txt
     * @param dividerChoice     divider chosen by user
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks completed
     * @param username          user provided name
     */
    public static void executeCommand(ArrayList<String> savedSettings, CommandType commandType, String userInput,
                                      ArrayList<Task> allTasks, String allTasksFilePath, DividerChoice dividerChoice,
                                      AtomicInteger numTasks, AtomicInteger numTasksCompleted, String username) {
        switch (commandType) {
        case HELP:
            Printers.printHelpInfo();
            break;
        case ADD_TODO:
            try {
                AddNewTasks.addToDo(userInput, allTasks, numTasks);
            } catch (ToDoInputWrongFormatException e) {
                System.out.println(TootieErrorMsgs.TODO_WRONG_FORMAT_MSG);
            } catch (TaskNameEmptyException e) {
                System.out.println(TootieErrorMsgs.TODO_TASKNAME_EMPTY_MSG);
            }
            break;
        case ADD_DEADLINE:
            try {
                AddNewTasks.addDeadline(userInput, allTasks, numTasks);
            } catch (DeadlineInputWrongFormatException e) {
                System.out.println(TootieErrorMsgs.DEADLINE_WRONG_FORMAT_MSG);
            } catch (DueDateWrongFormatException e) {
                System.out.println(TootieErrorMsgs.DUEDATE_WRONG_FORMAT_MSG);
            } catch (TaskNameEmptyException e) {
                System.out.println(TootieErrorMsgs.DEADLINE_TASKNAME_EMPTY_MSG);
            } catch (InvalidDueDateException e) {
                System.out.println(TootieErrorMsgs.INVALID_DUE_DATE_MSG);
            }
            break;
        case ADD_EVENT:
            try {
                AddNewTasks.addEvent(userInput, allTasks, numTasks);
            } catch (EventInputWrongFormatException e) {
                System.out.println(TootieErrorMsgs.EVENT_WRONG_FORMAT_MSG);
            } catch (InvalidStartDateException e) {
                System.out.println(TootieErrorMsgs.INVALID_START_DATE_MSG);
            } catch (InvalidEndTimeException e) {
                System.out.println(TootieErrorMsgs.INVALID_END_DATE_MSG);
            } catch (StartTimeWrongFormatException e) {
                System.out.println(TootieErrorMsgs.STARTDATE_WRONG_FORMAT_MSG);
            } catch (EndTimeWrongFormatException e) {
                System.out.println(TootieErrorMsgs.ENDDATE_WRONG_FORMAT_MSG);
            } catch (EndTimeBeforeStartTimeException e) {
                System.out.println(TootieErrorMsgs.ENDTIME_BEFORE_STARTIME_ERROR_MSG);
            } catch (TaskNameEmptyException e) {
                System.out.println(TootieErrorMsgs.EVENT_TASKNAME_EMPTY_MSG);
            }
            break;
        case LIST:
            try {
                Printers.printAllTasks(allTasks, numTasks.get(), numTasksCompleted.get());
            } catch (TasklistEmptyException e) {
                System.out.println(TootieErrorMsgs.TasklistEmptyMsg);
            }
            break;
        case MARK_TASK_DONE:
            try {
                ModifyTasks.markTaskComplete(userInput, allTasks, numTasksCompleted, numTasks);
            } catch (TaskNonexistantException e) {
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case MARK_TASK_UNDONE:
            try {
                ModifyTasks.markTaskIncomplete(userInput, allTasks, numTasksCompleted, numTasks);
            } catch (TaskNonexistantException e) {
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case BYE:
            Printers.printFarewellMessage(username);
            break;
        case SAVE:
            try {
                AllTasksSaver.saveAllTasks(allTasks, allTasksFilePath, numTasks, numTasksCompleted);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case DELETE_TASK:
            try {
                ModifyTasks.deleteTask(userInput, allTasks, numTasks, numTasksCompleted);
            } catch (TaskNonexistantException e) {
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case CHOOSE_DIVIDER:
            try {
                dividerChoice = Parsers.parseLineDividerFromUserInput(userInput);
                Printers.changeDivider(dividerChoice);
                savedSettings.set(2, dividerChoice.toString());
            } catch (DividerNonexistantException e) {
                System.out.println("Divider choice not found!" + NEWLINE);
            }
            break;
        case SET_USERNAME:
            try {
                username = SetPreferences.setUsername(userInput, username);
                System.out.println("Hello " + username);
                printDivider();
            } catch (UsernameCommandInvalidException e) {
                System.out.println("Username command invalid! " + TootieSymbols.ANGRY_EMOTICON + NEWLINE);
            } catch (UsernameEmptyException e) {
                System.out.println("Username is blank? " + TootieSymbols.CONFUSED_EMOTICON + NEWLINE);
            }
            break;
        case FILTER_TASKS:
            filterTasks(userInput, allTasks, numTasks);
            break;
        default:
            Printers.printConfusedMessage();
        }
    }

}
