package duke.tootieFunctions;

import duke.constants.TootieInputMarkers;
import duke.constants.TootieNormalMsgs;
import duke.constants.TootieRegex;
import duke.exceptions.DeadlineInputWrongFormatException;
import duke.exceptions.DueDateWrongFormatException;
import duke.exceptions.EndTimeBeforeStartTimeException;
import duke.exceptions.EndTimeWrongFormatException;
import duke.exceptions.EventInputWrongFormatException;
import duke.exceptions.InvalidDateException;
import duke.exceptions.InvalidDueDateException;
import duke.exceptions.InvalidEndTimeException;
import duke.exceptions.InvalidStartTimeException;
import duke.exceptions.MissingParamsException;
import duke.exceptions.StartTimeWrongFormatException;
import duke.exceptions.TaskNameEmptyException;
import duke.exceptions.ToDoInputWrongFormatException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.parsers.Parsers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static duke.parsers.Parsers.parseSingleCharacterTaggedParamsFromUserInput;

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
     * @throws EventInputWrongFormatException  the command for adding event was wrongly formatted
     * @throws InvalidStartTimeException       the start time entered is invalid
     * @throws InvalidEndTimeException         the end time entered is invalid
     * @throws StartTimeWrongFormatException   the start time entered has formatting errors
     * @throws EndTimeWrongFormatException     the end time entered has formatting errors
     * @throws EndTimeBeforeStartTimeException the end time entered should not be before the start time
     * @throws TaskNameEmptyException          the task name field is empty
     */
    public static void addEvent(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks) throws EventInputWrongFormatException, InvalidStartTimeException, InvalidEndTimeException, StartTimeWrongFormatException, EndTimeWrongFormatException, EndTimeBeforeStartTimeException, TaskNameEmptyException {
        Date startTime;
        Date endTime;

        // for returning filter options parsed from the user input
        HashMap<String, String> filterOptions = new HashMap<>();

        // parse filter command into segments
        try {
            parseSingleCharacterTaggedParamsFromUserInput(userInput, filterOptions);
        } catch (MissingParamsException e) {
            throw new EventInputWrongFormatException();
        }

        if (!filterOptions.containsKey("t") || !filterOptions.containsKey("s") || !filterOptions.containsKey("e")) {
            throw new EventInputWrongFormatException();
        }

        String taskName;
        String startTimeUnformatted;
        String endTimeUnformatted;

        taskName = filterOptions.get("t").trim();
        startTimeUnformatted = filterOptions.get("s").trim();
        endTimeUnformatted = filterOptions.get("e").trim();

        // check format start time
        boolean isStartDateWithTime = isDateWithTime(startTimeUnformatted);
        boolean isStartDateWithoutTime = isDateWithoutTime(startTimeUnformatted);
        // check format end time
        boolean isEndDateWithTime = isDateWithTime(endTimeUnformatted);
        boolean isEndDateWithoutTime = isDateWithoutTime(endTimeUnformatted);

        // try to parse start time
        if (isStartDateWithTime) {
            try {
                startTime = Parsers.parseDateWithTime(startTimeUnformatted);
            } catch (InvalidDateException e) {
                throw new InvalidStartTimeException();
            }
        } else if (isStartDateWithoutTime) {
            try {
                startTime = Parsers.parseDateWithoutTime(startTimeUnformatted);
            } catch (InvalidDateException e) {
                throw new InvalidStartTimeException();
            }
        } else {
            throw new StartTimeWrongFormatException();
        }
        if (startTime == null) {
            throw new StartTimeWrongFormatException();
        }

        // try to parse end time
        if (isEndDateWithTime) {
            try {
                endTime = Parsers.parseDateWithTime(endTimeUnformatted);
            } catch (InvalidDateException e) {
                throw new InvalidEndTimeException();
            }
        } else if (isEndDateWithoutTime) {
            try {
                endTime = Parsers.parseDateWithoutTime(endTimeUnformatted);
            } catch (InvalidDateException e) {
                throw new InvalidEndTimeException();
            }
        } else {
            throw new EndTimeWrongFormatException();
        }
        if (endTime == null) {
            throw new EndTimeWrongFormatException();
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
        System.out.printf((TootieNormalMsgs.ADDED_EVENT_FORMAT) + "%n",
                allTasks.get(numTasks.get()).getTaskDescription());
        numTasks.getAndIncrement();
    }

    /**
     * Adds a deadline task to the allTasks list
     *
     * @param userInput raw user input
     * @param allTasks  list of all Tasks
     * @param numTasks  total number of tasks in the list
     * @throws DeadlineInputWrongFormatException the deadline command was wrongly formatted
     * @throws DueDateWrongFormatException       the due date is wrongly formatted
     * @throws TaskNameEmptyException            the task name field is empty
     * @throws InvalidDueDateException           due date entered wrong format
     */
    public static void addDeadline(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks) throws DeadlineInputWrongFormatException, DueDateWrongFormatException, TaskNameEmptyException, InvalidDueDateException {
        Date dueDate;

        // for returning filter options parsed from the user input
        HashMap<String, String> filterOptions = new HashMap<>();

        // parse filter command into segments
        try {
            parseSingleCharacterTaggedParamsFromUserInput(userInput, filterOptions);
        } catch (MissingParamsException e) {
            throw new DeadlineInputWrongFormatException();
        }

        if (!filterOptions.containsKey("t") || !filterOptions.containsKey("d")) {
            throw new DeadlineInputWrongFormatException();

        }

        String taskName;
        String dueDateUnformatted;

        taskName = filterOptions.get("t").trim();
        dueDateUnformatted = filterOptions.get("d").trim();

        // check format due date
        boolean isDueDateWithTime = isDateWithTime(dueDateUnformatted);
        boolean isDueDateWithoutTime = isDateWithoutTime(dueDateUnformatted);

        // try to parse due date
        if (isDueDateWithTime) {
            try {
                dueDate = Parsers.parseDateWithTime(dueDateUnformatted);
            } catch (InvalidDateException e) {
                throw new InvalidDueDateException();
            }
        } else if (isDueDateWithoutTime) {
            try {
                dueDate = Parsers.parseDateWithoutTime(dueDateUnformatted);
            } catch (InvalidDateException e) {
                throw new InvalidDueDateException();
            }
        } else {
            throw new DueDateWrongFormatException();
        }
        if (dueDate == null) {
            throw new DueDateWrongFormatException();
        }

        if (taskName.isBlank()) {
            throw new TaskNameEmptyException();
        }

        // add event to list
        allTasks.add(new Deadline(taskName.trim(), dueDate));
        System.out.printf((TootieNormalMsgs.ADDED_DEADLINE_FORMAT) + "%n",
                allTasks.get(numTasks.get()).getTaskDescription());
        numTasks.getAndIncrement();
    }

    /**
     * /check if the date entered is correctly formatted with a date but no time
     *
     * @param timeUnformmated a string containing a date without the time included
     * @return true if it matches the correct format
     */
    public static boolean isDateWithoutTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITHOUT_TIME_REGEX);
    }

    /**
     * Check if the date entered is correctly formatted with a date and time
     *
     * @param timeUnformmated a string containing a date with the time included
     * @return true if it matches the correct format
     */
    public static boolean isDateWithTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITH_TIME_REGEX);
    }

    /**
     * Adds a toto task to the allTasks list
     *
     * @param userInput raw user input
     * @param allTasks  list of all Tasks
     * @param numTasks  total number of tasks in the list
     * @throws ToDoInputWrongFormatException the format of the todo command is incorrect
     * @throws TaskNameEmptyException        the task name field is empty
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
        System.out.printf((TootieNormalMsgs.ADDED_TODO_FORMAT) + "%n", taskName.trim());
    }
}
