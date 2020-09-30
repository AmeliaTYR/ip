package duke.tootieFunctions;

import duke.constants.CommandType;
import duke.constants.DividerChoice;
import duke.constants.TootieErrorMsgs;
import duke.exceptions.DeadlineInputWrongFormatException;
import duke.exceptions.DividerNonexistantException;
import duke.exceptions.DueDateWrongFormatException;
import duke.exceptions.EndTimeBeforeStartTimeException;
import duke.exceptions.EndTimeWrongFormatException;
import duke.exceptions.EventInputWrongFormatException;
import duke.exceptions.InvalidDueDateException;
import duke.exceptions.InvalidEndTimeException;
import duke.exceptions.InvalidStartTimeException;
import duke.exceptions.MissingFilterOptionsException;
import duke.exceptions.NoTasksFilteredException;
import duke.exceptions.StartTimeWrongFormatException;
import duke.exceptions.TaskNameEmptyException;
import duke.exceptions.TaskNonexistantException;
import duke.exceptions.TasklistEmptyException;
import duke.exceptions.ToDoInputWrongFormatException;
import duke.exceptions.UsernameCommandInvalidException;
import duke.exceptions.UsernameEmptyException;

import duke.parsers.Parsers;
import duke.storage.AllTasksSaver;
import duke.task.Task;
import duke.ui.Printers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static duke.constants.CommandKeywords.BYE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DEADLINE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DELETE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DIVIDER_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DONE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.EVENT_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.FILEPATH_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.FILTER_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.HELP_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.LIST_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.LOAD_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.SAVE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.TODO_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.UNDONE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.USERNAME_COMMAND_KEYWORD;

import static duke.constants.TootieConstants.NUMBER_OF_FILE_PATHS;
import static duke.constants.TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG;
import static duke.constants.TootieNormalMsgs.ALL_TASKS_SAVED_MSG;
import static duke.constants.TootieNormalMsgs.DIVIDER_CHANGED_MSG;
import static duke.constants.TootieNormalMsgs.DIVIDER_CHOICE_NOT_FOUND_MSG;
import static duke.constants.TootieNormalMsgs.FILTER_COMMAND_MISSING_ARGS_MSG;
import static duke.constants.TootieNormalMsgs.FILTER_COMMAND_PARAMS_UNMATCHED_MSG;
import static duke.constants.TootieNormalMsgs.HELLO_NAME_FORMAT;
import static duke.constants.TootieNormalMsgs.USERNAME_BLANK_MSG;
import static duke.constants.TootieNormalMsgs.USERNAME_COMMAND_INVALID_MSG;

import static duke.storage.AllTasksLoader.loadAllTasksFile;
import static duke.tootieFunctions.Filters.filterTasks;

/**
 * Contains functions that detect and execute commands
 */
public class CommandExecutor {
    /**
     * figure out the command type from userInput
     *
     * @param userInput raw user input
     * @return command type object
     */
    public static CommandType extractCommandType(String userInput) {
        if (userInput.toLowerCase().trim().startsWith(HELP_COMMAND_KEYWORD)) {
            return CommandType.HELP;
        } else if (userInput.toLowerCase().trim().startsWith(TODO_COMMAND_KEYWORD)) {
            return CommandType.ADD_TODO;
        } else if (userInput.toLowerCase().trim().startsWith(DEADLINE_COMMAND_KEYWORD)) {
            return CommandType.ADD_DEADLINE;
        } else if (userInput.toLowerCase().trim().startsWith(EVENT_COMMAND_KEYWORD)) {
            return CommandType.ADD_EVENT;
        } else if (userInput.toLowerCase().trim().startsWith(LIST_COMMAND_KEYWORD)) {
            return CommandType.LIST;
        } else if (userInput.toLowerCase().trim().startsWith(DONE_COMMAND_KEYWORD)) {
            return CommandType.MARK_TASK_DONE;
        } else if (userInput.toLowerCase().trim().startsWith(BYE_COMMAND_KEYWORD)) {
            return CommandType.BYE;
        } else if (userInput.toLowerCase().trim().startsWith(DELETE_COMMAND_KEYWORD)) {
            return CommandType.DELETE_TASK;
        } else if (userInput.toLowerCase().trim().startsWith(UNDONE_COMMAND_KEYWORD)) {
            return CommandType.MARK_TASK_UNDONE;
        } else if (userInput.toLowerCase().trim().startsWith(DIVIDER_COMMAND_KEYWORD)) {
            return CommandType.CHOOSE_DIVIDER;
        } else if (userInput.toLowerCase().trim().startsWith(USERNAME_COMMAND_KEYWORD)) {
            return CommandType.SET_USERNAME;
        } else if (userInput.toLowerCase().trim().startsWith(FILTER_COMMAND_KEYWORD)) {
            return CommandType.FILTER_TASKS;
        } else if (userInput.toLowerCase().trim().startsWith(SAVE_COMMAND_KEYWORD)) {
            return CommandType.SAVE;
        } else if (userInput.toLowerCase().trim().startsWith(FILEPATH_COMMAND_KEYWORD)) {
            return CommandType.PRINT_FILE_PATHS;
        } else if (userInput.toLowerCase().trim().startsWith(LOAD_COMMAND_KEYWORD)) {
            return CommandType.LOAD_MORE_TASKS;
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
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks completed
     * @param username          user provided name
     */
    public static void executeCommand(ArrayList<String> savedSettings, CommandType commandType, String userInput,
                                      ArrayList<Task> allTasks, String tootieSettingsFilePath,
                                      String allTasksFilePath, AtomicInteger numTasks,
                                      AtomicInteger numTasksCompleted, String username, Scanner SCANNER) {
        switch (commandType) {
        case HELP:
            GetHelp.getHelp(userInput);
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
            } catch (InvalidStartTimeException e) {
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
                System.out.println(TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case MARK_TASK_UNDONE:
            try {
                ModifyTasks.markTaskIncomplete(userInput, allTasks, numTasksCompleted, numTasks);
            } catch (TaskNonexistantException e) {
                System.out.println(TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case BYE:
            Printers.printFarewellMessage(username);
            break;
        case SAVE:
            try {
                AllTasksSaver.saveAllTasks(allTasks, allTasksFilePath, numTasks, numTasksCompleted);
                System.out.println(ALL_TASKS_SAVED_MSG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case DELETE_TASK:
            try {
                ModifyTasks.deleteTask(userInput, allTasks, numTasks, numTasksCompleted);
            } catch (TaskNonexistantException e) {
                System.out.println(TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case CHOOSE_DIVIDER:
            try {
                DividerChoice dividerChoice = Parsers.parseLineDividerFromUserInput(userInput);
                Printers.changeDivider(dividerChoice);
                savedSettings.set(2, dividerChoice.toString());
                System.out.println(DIVIDER_CHANGED_MSG);
            } catch (DividerNonexistantException e) {
                System.out.println(DIVIDER_CHOICE_NOT_FOUND_MSG);
            }
            break;
        case SET_USERNAME:
            try {
                username = SetPreferences.setUsername(userInput);
                System.out.println(String.format(HELLO_NAME_FORMAT, username));
                savedSettings.set(3, username);
            } catch (UsernameCommandInvalidException e) {
                System.out.println(USERNAME_COMMAND_INVALID_MSG);
            } catch (UsernameEmptyException e) {
                System.out.println(USERNAME_BLANK_MSG);
            }
            break;
        case FILTER_TASKS:
            try {
                filterTasks(userInput, allTasks);
            } catch (MissingFilterOptionsException e) {
                System.out.println(FILTER_COMMAND_MISSING_ARGS_MSG);
            } catch (NoTasksFilteredException e) {
                System.out.println(FILTER_COMMAND_PARAMS_UNMATCHED_MSG);
            }
            break;
        case PRINT_FILE_PATHS:
            Printers.printFilePaths(tootieSettingsFilePath, allTasksFilePath);
            break;
        case LOAD_MORE_TASKS:
            ArrayList<String> allTasksFilePathReturn = new ArrayList<>(NUMBER_OF_FILE_PATHS);
            loadAllTasksFile(true, allTasks, SCANNER, allTasksFilePath, numTasks, numTasksCompleted,
                    allTasksFilePathReturn);
            break;
        default:
            Printers.printConfusedMessage();
        }
    }

}
