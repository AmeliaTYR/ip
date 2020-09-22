package duke.tootieFunctions;

import duke.Duke;
import duke.exceptions.*;
import duke.fileHandlers.AllTasksSaver;
import duke.finalObjects.CommandType;
import duke.finalObjects.DividerChoice;
import duke.finalObjects.TootieErrorMsgs;
import duke.parsers.Parsers;
import duke.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandExecutor {
    public static final String NEWLINE = System.lineSeparator();

    // figure out the command type from userInput
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
        } else if (userInput.toLowerCase().trim().startsWith("save")) {
            return CommandType.SAVE;
        } else {
            return CommandType.UNRECOGNISED;
        }
    }

    // execute the command as required
    public static void executeCommand(CommandType commandType, String userInput, ArrayList<Task> allTasks,
                                      String filePath, DividerChoice dividerChoice, AtomicInteger numTasks, AtomicInteger numTasksCompleted, String username) {
        switch (commandType) {
        case HELP:
            TextUi.printHelpInfo();
            break;
        case ADD_TODO:
            try {
                AddNewTasks.addToDo(userInput, allTasks, numTasks);
            } catch (ToDoInputWrongFormatException e){
                System.out.println(TootieErrorMsgs.TODO_WRONG_FORMAT_MSG);
            } catch (TaskNameEmptyException e){
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
            } catch (InvalidDueDateException e){
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
            } catch (StartTimeWrongFormatException e ) {
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
                TextUi.printAllTasks(allTasks, numTasks.get(), numTasksCompleted.get());
            } catch (TasklistEmptyException e) {
                System.out.println(TootieErrorMsgs.TasklistEmptyMsg);
            }
            break;
        case MARK_TASK_DONE:
            try {
                ModifyTasks.markTaskComplete(userInput, allTasks, numTasksCompleted, numTasks);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case MARK_TASK_UNDONE:
            try {
                ModifyTasks.markTaskIncomplete(userInput, allTasks, numTasksCompleted, numTasks);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case BYE:
            TextUi.printFarewellMessage(username);
            break;
        case SAVE:
            try {
                AllTasksSaver.saveAllTasks(allTasks, filePath, numTasks, numTasksCompleted);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case DELETE_TASK:
            try {
                ModifyTasks.deleteTask(userInput, allTasks, numTasks, numTasksCompleted);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case CHOOSE_DIVIDER:
            try {
                dividerChoice = Parsers.parseLineDividerFromUserInput(userInput);
                TextUi.changeDivider( dividerChoice);
            } catch (DividerNonexistantException e){
                System.out.println("Divider choice not found!" + NEWLINE);
            }
            break;
        case SET_USERNAME:
            try {
                SetPreferences.setUsername(userInput, username);
            } catch (UsernameCommandInvalidException e){
                System.out.println("Username command invalid!" + NEWLINE);
            } catch (UsernameEmptyException e) {
                System.out.println("Username is blank!" + NEWLINE);
            }
            break;
        default:
            TextUi.printConfusedMessage();
        }
    }
}
