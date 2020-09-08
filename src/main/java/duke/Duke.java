package duke;

import duke.exceptions.*;
import duke.finalObjects.*;

import duke.printers.TootiePrintFunctions;
import duke.task.ToDo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;

public class Duke {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static final String NEWLINE = System.lineSeparator();

    // array containing all tasks the user has input
    private static ArrayList<Task> allTasks = new ArrayList<Task>(TootieConstants.MAX_TASKS);

    private static int numTasks = 0;

    public static void main(String[] args) {
        String userInput;
        CommandType commandType = CommandType.START;

        TootiePrintFunctions.printTootieLogo();
        TootiePrintFunctions.printHelloMessage();

        while(commandType != CommandType.BYE){
            userInput = getUserInput();
            echoUserInput(userInput);
            TootiePrintFunctions.printDivider();
            commandType = extractCommandType(userInput);
            executeCommand(commandType, userInput, allTasks);
            TootiePrintFunctions.printDivider();
        }
    }

    // get user input, ignore comments and blank lines
    private static String getUserInput() {
        String userInput;
        do {
            userInput = SCANNER.nextLine();
        } while (userInput.matches(TootieRegex.BLANK_STRING_REGEX)
                || userInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return userInput;
    }

    // echo the userInput for testing
    private static void echoUserInput(String userInput) {
        System.out.println(userInput);
    }

    // prints all list items with index and check
    public static void printAllTasks (ArrayList<Task> taskList) throws TasklistEmptyException {
        if (numTasks == 0) {
            throw new TasklistEmptyException();
        }

        System.out.println(String.format(TootieNormalMsgs.NUMTASKS_PRINT_FORMAT, numTasks));
        for (int i = 0; i < numTasks; i++) {
            System.out.print((i + 1) + ". ");
            taskList.get(i).printTaskType();
            taskList.get(i).printCompletionIndicator();
            taskList.get(i).printTaskDescription();
        }

        // TODO: print "all done ʕ•ᴥ•ʔ" if all tasks done for now
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
            TootiePrintFunctions.printHelpInfo();
            break;
        case ADD_TODO:
            try {
                addToDo(userInput);
            } catch (ToDoInputWrongFormatException e){
                System.out.println(TootieErrorMsgs.TODO_WRONG_FORMAT_MSG);
            } catch (TaskNameEmptyException e){
                System.out.println(TootieErrorMsgs.TODO_TASKNAME_EMPTY_MSG);
            }
            break;
        case ADD_DEADLINE:
            try {
                addDeadline(userInput);
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
                addEvent(userInput);
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
                printAllTasks(allTasks);
            } catch (TasklistEmptyException e) {
                System.out.println(TootieErrorMsgs.TasklistEmptyMsg);
            }
            break;
        case DONE:
            try {
                markTaskComplete(userInput, allTasks);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case BYE:
            TootiePrintFunctions.printFarewellMessage();
            break;
        case SAVE:
            saveTasks(allTasks);
            break;
        case DELETE:
            deleteTask(userInput, allTasks);
            break;
        default:
            TootiePrintFunctions.printConfusedMessage();
        }
    }

    // TODO: implement save tasks function
    private static void saveTasks(ArrayList<Task> allTasks) {
    }

    // TODO: implement delete task function
    private static void deleteTask(String userInput, ArrayList<Task> allTasks) {
    }

    // add an event task to the allTasks list
    private static void addEvent(String userInput) throws EventInputWrongFormatException, InvalidStartDateException, InvalidEndTimeException, StartTimeWrongFormatException, EndTimeWrongFormatException, EndTimeBeforeStartTimeException, TaskNameEmptyException {
        Date startTime = null;
        Date endTime = null;

        String taskName = "";
        String startTimeUnformatted = "";
        String endTimeUnformatted = "";

        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        int startTimePosition = userInput.indexOf(TootieInputMarkers.STARTTIME_MARKER);
        int endTimePosition = userInput.indexOf(TootieInputMarkers.ENDTIME_MARKER);

        // check if placement is correct
        if (taskNamePosition == -1 || startTimePosition == -1 || endTimePosition == -1) {
            throw new EventInputWrongFormatException();
        } else {
            try {
                taskName = userInput.substring(taskNamePosition + 2, startTimePosition);
                startTimeUnformatted = userInput.substring(startTimePosition + 2, endTimePosition).trim();
                endTimeUnformatted = userInput.substring(endTimePosition + 2).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                throw new EventInputWrongFormatException();
            }
        }

        // check format start time
        boolean isStartDateWithTime = isDateWithTime(startTimeUnformatted);
        boolean isStartDateWithoutTime = isDateWithoutTime(startTimeUnformatted);
        // check format end time
        boolean isEndDateWithTime = isDateWithTime(endTimeUnformatted);
        boolean isEndDateWithoutTime = isDateWithoutTime(endTimeUnformatted);

        // try to parse start time
        if (isStartDateWithTime) {
            startTime = parseDateWithTime(startTimeUnformatted);
        } else if(isStartDateWithoutTime){
            startTime = parseDateWithoutTime(startTimeUnformatted);
        } else {
            throw new StartTimeWrongFormatException();
        }
        if (startTime == null){
            throw new StartTimeWrongFormatException();
        }

        // try to parse end time
        if (isEndDateWithTime) {
            endTime = parseDateWithTime(endTimeUnformatted);
        } else if(isEndDateWithoutTime){
            endTime = parseDateWithoutTime(endTimeUnformatted);
        } else {
            throw new EndTimeWrongFormatException();
        }
        if (endTime == null){
            throw new EndTimeWrongFormatException();
        }

        // check if date entered is valid
        if(!isValidDate(startTime)){
            throw new InvalidStartDateException();
        }
        if(!isValidDate(endTime)) {
            throw new InvalidEndTimeException();
        }

        // check if start and end time are in chronological order
        if(startTime.after(endTime)){
            throw new EndTimeBeforeStartTimeException();
        }

        if (taskName.isBlank()){
            throw new TaskNameEmptyException();
        }

        // add event to list
        allTasks.add(new Event(taskName.trim(), startTime, endTime));
        System.out.println("added event:");
        allTasks.get(numTasks).printTaskDescription();
        numTasks++;
    }

    // adds a deadline task to the allTasks list
    private static void addDeadline(String userInput) throws DeadlineInputWrongFormatException, DueDateWrongFormatException, TaskNameEmptyException, InvalidDueDateException {
        Date dueDate = null;

        String taskName = "";
        String dueDateUnformatted = "";

        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        int dueDatePosition = userInput.indexOf(TootieInputMarkers.DUEDATE_MARKER);

        // check if placement is correct, split if correct
        if (taskNamePosition == -1 || dueDatePosition == -1) {
            throw new DeadlineInputWrongFormatException();
        } else {
            try {
                taskName = userInput.substring(taskNamePosition + 2, dueDatePosition);
                dueDateUnformatted = userInput.substring(dueDatePosition + 2).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                throw new DeadlineInputWrongFormatException();
            }
        }

        // check format due date
        boolean isDueDateWithTime = isDateWithTime(dueDateUnformatted);
        boolean isDueDateWithoutTime = isDateWithoutTime(dueDateUnformatted);

        // try to parse due date
        if (isDueDateWithTime) {
            dueDate = parseDateWithTime(dueDateUnformatted);
        } else if(isDueDateWithoutTime){
            dueDate = parseDateWithoutTime(dueDateUnformatted);
        } else {
            throw new DueDateWrongFormatException();
        }
        if (dueDate == null){
            throw new DueDateWrongFormatException();
        }

        if (taskName.isBlank()){
            throw new TaskNameEmptyException();
        }

        // check if date entered is valid
        if(!isValidDate(dueDate)){
            throw new InvalidDueDateException();
        }

        // add event to list
        allTasks.add(new Deadline(taskName.trim(), dueDate));
        System.out.println("added deadline:");
        allTasks.get(numTasks).printTaskDescription();
        numTasks++;
    }


    // TODO fix the date validator it doesn't work properly :(
    // check if the date entered is a valid calendar date
    private static boolean isValidDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.setTime(date);
        try {
            cal.getTime();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // parse the date if time is not included in input
    private static Date parseDateWithoutTime(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formattedDate = dateWithoutTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // parse the date if time is included in input
    private static Date parseDateWithTime(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            formattedDate = dateWithTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // check if the date entered is correctly formatted with a date but no time
    private static boolean isDateWithoutTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITHOUT_TIME_REGEX);
    }

    // check if the date entered is correctly formatted with a date and time
    private static boolean isDateWithTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITH_TIME_REGEX);
    }

    // adds a toto task to the allTasks list
    private static void addToDo(String userInput) throws ToDoInputWrongFormatException, TaskNameEmptyException {
        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        if (taskNamePosition == -1){
            throw new ToDoInputWrongFormatException();
        }
        String taskName = userInput.substring(taskNamePosition + 2);

        if (taskName.isBlank()){
            throw new TaskNameEmptyException();
        }

        // add task to list
        allTasks.add(new ToDo(taskName.trim()));
        numTasks++;
        System.out.println("added todo: " + taskName.trim());
    }
    
    // process the user input and mark the
    private static void markTaskComplete(String userInput, ArrayList<Task> allTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        try {
            taskNum = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (taskNum > numTasks || taskNum < 1) {
                throw new TaskNonexistantException();
            } else {
                allTasks.get(taskNum - 1).setComplete(true);
            }
        } catch (NumberFormatException exception) {
            throw new TaskNonexistantException();
        }

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_MARKED_DONE_RESPONSE_MSG, allTasks.get(taskNum - 1).getTaskName()));
    }
}



