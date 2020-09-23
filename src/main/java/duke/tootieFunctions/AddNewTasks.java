package duke.tootieFunctions;

import duke.exceptions.*;
import duke.constants.TootieInputMarkers;
import duke.constants.TootieNormalMsgs;
import duke.constants.TootieRegex;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.parsers.Parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Add new Events, Todos or Deadlines to the list
 */
public class AddNewTasks {

    /**
     * Adds an event task to the allTasks list
     *
     * @param userInput raw user input
     * @param allTasks  list of all Tasks
     * @param numTasks  total number of tasks in the list
     * @throws EventInputWrongFormatException
     * @throws InvalidStartDateException
     * @throws InvalidEndTimeException
     * @throws StartTimeWrongFormatException
     * @throws EndTimeWrongFormatException
     * @throws EndTimeBeforeStartTimeException
     * @throws TaskNameEmptyException
     */
    public static void addEvent(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks) throws EventInputWrongFormatException, InvalidStartDateException, InvalidEndTimeException, StartTimeWrongFormatException, EndTimeWrongFormatException, EndTimeBeforeStartTimeException, TaskNameEmptyException {
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
            startTime = Parsers.parseDateWithTime(startTimeUnformatted);
        } else if (isStartDateWithoutTime) {
            startTime = Parsers.parseDateWithoutTime(startTimeUnformatted);
        } else {
            throw new StartTimeWrongFormatException();
        }
        if (startTime == null) {
            throw new StartTimeWrongFormatException();
        }

        // try to parse end time
        if (isEndDateWithTime) {
            endTime = Parsers.parseDateWithTime(endTimeUnformatted);
        } else if (isEndDateWithoutTime) {
            endTime = Parsers.parseDateWithoutTime(endTimeUnformatted);
        } else {
            throw new EndTimeWrongFormatException();
        }
        if (endTime == null) {
            throw new EndTimeWrongFormatException();
        }

        // check if date entered is valid
        if (!isValidDate(startTime)) {
            throw new InvalidStartDateException();
        }
        if (!isValidDate(endTime)) {
            throw new InvalidEndTimeException();
        }

        // check if start and end time are in chronological order
        if (startTime.after(endTime)) {
            throw new EndTimeBeforeStartTimeException();
        }

        if (taskName.isBlank()) {
            throw new TaskNameEmptyException();
        }

        // add event to list
        allTasks.add(new Event(taskName.trim(), startTime, endTime));
        System.out.println(String.format(TootieNormalMsgs.ADDED_EVENT_FORMAT,
                allTasks.get(numTasks.get()).getTaskDescription()));
        numTasks.getAndIncrement();
    }

    /**
     * Adds a deadline task to the allTasks list
     *
     * @param userInput raw user input
     * @param allTasks  list of all Tasks
     * @param numTasks  total number of tasks in the list
     * @throws DeadlineInputWrongFormatException
     * @throws DueDateWrongFormatException
     * @throws TaskNameEmptyException
     * @throws InvalidDueDateException
     */
    public static void addDeadline(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks) throws DeadlineInputWrongFormatException, DueDateWrongFormatException, TaskNameEmptyException, InvalidDueDateException {
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
            dueDate = Parsers.parseDateWithTime(dueDateUnformatted);
        } else if (isDueDateWithoutTime) {
            dueDate = Parsers.parseDateWithoutTime(dueDateUnformatted);
        } else {
            throw new DueDateWrongFormatException();
        }
        if (dueDate == null) {
            throw new DueDateWrongFormatException();
        }

        if (taskName.isBlank()) {
            throw new TaskNameEmptyException();
        }

        // check if date entered is valid
        if (!isValidDate(dueDate)) {
            throw new InvalidDueDateException();
        }

        // add event to list
        allTasks.add(new Deadline(taskName.trim(), dueDate));
        System.out.println(String.format(TootieNormalMsgs.ADDED_DEADLINE_FORMAT,
                allTasks.get(numTasks.get()).getTaskDescription()));
        numTasks.getAndIncrement();
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

    /**
     * /check if the date entered is correctly formatted with a date but no time
     *
     * @param timeUnformmated
     * @return
     */
    private static boolean isDateWithoutTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITHOUT_TIME_REGEX);
    }

    /**
     * Check if the date entered is correctly formatted with a date and time
     *
     * @param timeUnformmated
     * @return
     */
    private static boolean isDateWithTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITH_TIME_REGEX);
    }

    /**
     * Adds a toto task to the allTasks list
     *
     * @param userInput raw user input
     * @param allTasks  list of all Tasks
     * @param numTasks  total number of tasks in the list
     * @throws ToDoInputWrongFormatException
     * @throws TaskNameEmptyException
     */
    public static void addToDo(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks) throws ToDoInputWrongFormatException, TaskNameEmptyException {
        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        if (taskNamePosition == -1) {
            throw new ToDoInputWrongFormatException();
        }
        String taskName = userInput.substring(taskNamePosition + 2);

        if (taskName.isBlank()) {
            throw new TaskNameEmptyException();
        }

        // add task to list
        allTasks.add(new ToDo(taskName.trim()));
        numTasks.getAndIncrement();
        System.out.println(String.format(TootieNormalMsgs.ADDED_TODO_FORMAT, taskName.trim()));
    }
}
