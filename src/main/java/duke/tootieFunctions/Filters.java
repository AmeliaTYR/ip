package duke.tootieFunctions;

import duke.constants.TaskType;

import duke.exceptions.CompletionStatusInvalidException;
import duke.exceptions.DateAfterPreceedsDateBefore;
import duke.exceptions.DateBeforeMatchesAfterException;
import duke.exceptions.DateWronglyFormattedError;
import duke.exceptions.InvalidDateException;
import duke.exceptions.MissingFilterOptionsException;
import duke.exceptions.MissingParamsException;
import duke.exceptions.NoTasksFilteredException;

import duke.parsers.*;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.ui.Printers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static duke.constants.CommandKeywords.DUEDATE_AFTER_TAG;
import static duke.constants.CommandKeywords.DUEDATE_BEFORE_TAG;
import static duke.constants.CommandKeywords.ENDTIME_AFTER_TAG;
import static duke.constants.CommandKeywords.ENDTIME_BEFORE_TAG;
import static duke.constants.CommandKeywords.SEARCH_TERM_TAG;
import static duke.constants.CommandKeywords.STARTTIME_AFTER_TAG;
import static duke.constants.CommandKeywords.STARTTIME_BEFORE_TAG;
import static duke.constants.CommandKeywords.TASK_TYPE_TAG;

import static duke.constants.TootieNormalMsgs.COMPLETION_STATUS_PARAM_INVALID_ERROR_MSG;
import static duke.constants.TootieNormalMsgs.DUEDATES_MATCH_WARNING_MSG;
import static duke.constants.TootieNormalMsgs.DUEDATES_WRONG_ORDER_MSG;
import static duke.constants.TootieNormalMsgs.DUEDATE_AFTER_DATE_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.DUEDATE_AFTER_WRONGLY_FORMATTED_MSG;
import static duke.constants.TootieNormalMsgs.DUEDATE_BEFORE_DATE_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.DUEDATE_BEFORE_WRONG_FORMAT_MSG;
import static duke.constants.TootieNormalMsgs.ENDDATES_MATCH_WARNING_MSG;
import static duke.constants.TootieNormalMsgs.ENDDATE_AFTER_DATE_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.ENDDATE_AFTER_WRONG_FORMAT_MSG;
import static duke.constants.TootieNormalMsgs.ENDDATE_BEFORE_DATE_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.ENDDATE_BEFORE_WRONG_FORMAT_MSG;
import static duke.constants.TootieNormalMsgs.ENDTIMES_WRONG_ORDER_MSG;
import static duke.constants.TootieNormalMsgs.STARTTIMES_MATCH_WARNING_MSG;
import static duke.constants.TootieNormalMsgs.STARTTIMES_WRONG_ORDER_MSG;
import static duke.constants.TootieNormalMsgs.STARTTIME_AFTER_DATE_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.STARTTIME_AFTER_WRONG_FORMAT_MSG;
import static duke.constants.TootieNormalMsgs.STARTTIME_BEFORE_DATE_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.STARTTIME_BEFORE_WRONG_FORMAT_MSG;

import static duke.parsers.Parsers.parseDoubleCharacterTaggedParamsFromUserInput;

/**
 * Filters the tasks in the list
 */
public class Filters {

    /** dates for any of the indicators */
    public static Date dueDateBefore = null;
    public static Date dueDateAfter = null;
    public static Date startTimeBefore = null;
    public static Date startTimeAfter = null;
    public static Date endTimeBefore = null;
    public static Date endTimeAfter = null;

    /**
     * Parse the user input and filter out the suitable tasks
     *
     * @param userInput user input strings
     * @param allTasks  list of all tasks
     * @throws MissingFilterOptionsException the filter command has no parameters
     * @throws NoTasksFilteredException      the filter returned no tasks with desired parameters
     */
    public static void filterTasks(String userInput, ArrayList<Task> allTasks) throws MissingFilterOptionsException,
            NoTasksFilteredException {
        // flags for filters
        boolean hasAtLeastOneFilterOption = false;
        boolean containsSearchTerm = false;
        boolean taskTypeIndicated = false;
        boolean containsStartTimeBeforeParam = false;
        boolean containsEndTimeBeforeParam = false;
        boolean containsDueDateBeforeParam = false;
        boolean containsStartTimeAfterParam = false;
        boolean containsEndTimeAfterParam = false;
        boolean containsDueDateAfterParam = false;
        boolean containsCompletionStatusParam = false;

        ArrayList<TaskType> taskTypes = new ArrayList<>(2);

        String componentUserInput;
        String searchTerm = "";
        boolean completionStatus = false;

        HashMap<Integer, Task> filteredTasks = new HashMap<>();

        // for returning filter options parsed from the user input
        HashMap<String, String> filterOptions = new HashMap<>();

        // parse filter command into segments
        try {
            parseDoubleCharacterTaggedParamsFromUserInput(userInput, filterOptions);
        } catch (MissingParamsException e) {
            throw new MissingFilterOptionsException();
        }

        // check if there is task type param
        if (filterOptions.containsKey(TASK_TYPE_TAG)) {
            componentUserInput = filterOptions.get(TASK_TYPE_TAG);
            taskTypeIndicated = true;
            Parsers.parseTaskTypes(componentUserInput, taskTypes);
            hasAtLeastOneFilterOption = true;
        }

        // check for completion status param
        if (filterOptions.containsKey("cs")) {
            componentUserInput = filterOptions.get("cs");
            try {
                completionStatus = Parsers.parseCompletionStatusOption(componentUserInput);
                containsCompletionStatusParam = true;
                hasAtLeastOneFilterOption = true;
            } catch (CompletionStatusInvalidException e) {
                System.out.println(COMPLETION_STATUS_PARAM_INVALID_ERROR_MSG);
            }

        }

        if (filterOptions.containsKey(SEARCH_TERM_TAG)) {
            containsSearchTerm = true;
            searchTerm = filterOptions.get(SEARCH_TERM_TAG).trim();
            hasAtLeastOneFilterOption = true;
        }

        if (filterOptions.containsKey(DUEDATE_BEFORE_TAG)) {
            String dueDateUnformatted = filterOptions.get(DUEDATE_BEFORE_TAG).trim();
            try {
                dueDateBefore = Parsers.parseDateIfExists(dueDateUnformatted);
                hasAtLeastOneFilterOption = true;
                containsDueDateBeforeParam = true;
            } catch (DateWronglyFormattedError e) {
                System.out.println(DUEDATE_BEFORE_WRONG_FORMAT_MSG);
            } catch (InvalidDateException e) {
                System.out.println(DUEDATE_BEFORE_DATE_INVALID_MSG);
            }
        }

        if (filterOptions.containsKey(DUEDATE_AFTER_TAG)) {
            String dueDateUnformatted = filterOptions.get(DUEDATE_AFTER_TAG).trim();
            try {
                dueDateAfter = Parsers.parseDateIfExists(dueDateUnformatted);
                hasAtLeastOneFilterOption = true;
                containsDueDateAfterParam = true;
            } catch (DateWronglyFormattedError e) {
                System.out.println(DUEDATE_AFTER_WRONGLY_FORMATTED_MSG);
            } catch (InvalidDateException e) {
                System.out.println(DUEDATE_AFTER_DATE_INVALID_MSG);
            }
        }

        // compare dates before and after
        try {
            Checks.checkIfDateAfterPreceedsBefore(
                    containsDueDateBeforeParam, containsDueDateAfterParam, dueDateBefore, dueDateAfter);
            Checks.checkIfBeforeAndAfterDatesMatch(
                    containsDueDateBeforeParam, containsDueDateAfterParam, dueDateBefore, dueDateAfter);
        } catch (DateBeforeMatchesAfterException e) {
            System.out.println(DUEDATES_MATCH_WARNING_MSG);
        } catch (DateAfterPreceedsDateBefore dateAfterPreceedsDateBefore) {
            System.out.println(DUEDATES_WRONG_ORDER_MSG);
            Date temp = dueDateAfter;
            dueDateAfter = dueDateBefore;
            dueDateBefore = temp;
        }

        if (filterOptions.containsKey(STARTTIME_BEFORE_TAG)) {
            String startTimeUnformatted = filterOptions.get(STARTTIME_BEFORE_TAG).trim();
            try {
                startTimeBefore = Parsers.parseDateIfExists(startTimeUnformatted);
                hasAtLeastOneFilterOption = true;
                containsStartTimeBeforeParam = true;
            } catch (DateWronglyFormattedError e) {
                System.out.println(STARTTIME_BEFORE_WRONG_FORMAT_MSG);
            } catch (InvalidDateException e) {
                System.out.println(STARTTIME_BEFORE_DATE_INVALID_MSG);
            }
        }

        if (filterOptions.containsKey(STARTTIME_AFTER_TAG)) {
            String startTimeUnformatted = filterOptions.get(STARTTIME_AFTER_TAG).trim();
            try {
                startTimeAfter = Parsers.parseDateIfExists(startTimeUnformatted);
                hasAtLeastOneFilterOption = true;
                containsStartTimeAfterParam = true;
            } catch (DateWronglyFormattedError e) {
                System.out.println(STARTTIME_AFTER_WRONG_FORMAT_MSG);
            } catch (InvalidDateException e) {
                System.out.println(STARTTIME_AFTER_DATE_INVALID_MSG);
            }
        }

        // compare dates before and after
        try {
            Checks.checkIfDateAfterPreceedsBefore(
                    containsStartTimeBeforeParam, containsStartTimeAfterParam,
                    startTimeBefore, startTimeAfter);
            Checks.checkIfBeforeAndAfterDatesMatch(
                    containsStartTimeBeforeParam, containsStartTimeAfterParam,
                    startTimeBefore, startTimeAfter);
        } catch (DateBeforeMatchesAfterException e) {
            System.out.println(STARTTIMES_MATCH_WARNING_MSG);
        } catch (DateAfterPreceedsDateBefore dateAfterPreceedsDateBefore) {
            System.out.println(STARTTIMES_WRONG_ORDER_MSG);
            Date temp = startTimeAfter;
            startTimeAfter = startTimeBefore;
            startTimeBefore = temp;
        }

        if (filterOptions.containsKey(ENDTIME_BEFORE_TAG)) {
            String endTimeUnformatted = filterOptions.get(ENDTIME_BEFORE_TAG).trim();
            try {
                endTimeBefore = Parsers.parseDateIfExists(endTimeUnformatted);
                hasAtLeastOneFilterOption = true;
                containsEndTimeBeforeParam = true;
            } catch (DateWronglyFormattedError e) {
                System.out.println(ENDDATE_BEFORE_WRONG_FORMAT_MSG);
            } catch (InvalidDateException e) {
                System.out.println(ENDDATE_BEFORE_DATE_INVALID_MSG);
            }
        }

        if (filterOptions.containsKey(ENDTIME_AFTER_TAG)) {
            String endTimeUnformatted = filterOptions.get(ENDTIME_AFTER_TAG).trim();
            try {
                endTimeAfter = Parsers.parseDateIfExists(endTimeUnformatted);
                hasAtLeastOneFilterOption = true;
                containsEndTimeAfterParam = true;
            } catch (DateWronglyFormattedError e) {
                System.out.println(ENDDATE_AFTER_WRONG_FORMAT_MSG);
            } catch (InvalidDateException e) {
                System.out.println(ENDDATE_AFTER_DATE_INVALID_MSG);
            }
        }

        // compare dates before and after
        try {
            Checks.checkIfDateAfterPreceedsBefore(
                    containsEndTimeBeforeParam, containsEndTimeAfterParam, endTimeBefore, endTimeAfter);
            Checks.checkIfBeforeAndAfterDatesMatch(
                    containsEndTimeBeforeParam, containsEndTimeAfterParam, endTimeBefore, endTimeAfter);
        } catch (DateBeforeMatchesAfterException e) {
            System.out.println(ENDDATES_MATCH_WARNING_MSG);
        } catch (DateAfterPreceedsDateBefore dateAfterPreceedsDateBefore) {
            System.out.println(ENDTIMES_WRONG_ORDER_MSG);
            Date temp = endTimeAfter;
            endTimeAfter = endTimeBefore;
            endTimeBefore = temp;
        }

        if (!hasAtLeastOneFilterOption) {
            throw new MissingFilterOptionsException();
        }

        int i;
        HashMap<Integer, Task> numberedTasks = new HashMap<>();
        Set<Map.Entry<Integer, Task>> entries = numberedTasks.entrySet();

        // add all the numbered tasks to a hashmap
        for (i = 1; i <= allTasks.size(); i++) {
            numberedTasks.put(i, allTasks.get(i - 1));
        }

        // make filters final for filtering
        String finalSearchTerm = searchTerm;
        Date finalDueDateBefore = dueDateBefore;
        Date finalDueDateAfter = dueDateAfter;
        Date finalStartTimeAfter = startTimeAfter;
        Date finalStartTimeBefore = startTimeBefore;
        Date finalEndTimeBefore = endTimeBefore;
        Date finalEndTimeAfter = endTimeAfter;
        boolean finalCompletionStatus = completionStatus;

        // then filter for todo specifications, add to list
        if (!taskTypeIndicated || taskTypes.contains(TaskType.TODO)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            entriesStream.filter(entry -> entry.getValue() instanceof ToDo).filter(containsSearchTerm ?
                            entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
                    .filter(containsCompletionStatusParam ?
                            entry -> entry.getValue().getComplete() == finalCompletionStatus : entry -> true)
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        // then filter for deadline specifications, add to list
        if (!taskTypeIndicated || taskTypes.contains(TaskType.DEADLINE)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            entriesStream.filter(entry -> entry.getValue() instanceof Deadline).filter(containsSearchTerm ?
                            entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
                    .filter(containsDueDateBeforeParam ? entry -> ((Deadline) entry.getValue()).getDueDate()
                            .compareTo(finalDueDateBefore) < 0 : entry -> true)
                    .filter(containsDueDateAfterParam ? entry -> ((Deadline) entry.getValue()).getDueDate()
                            .compareTo(finalDueDateAfter) > 0 : entry -> true)
                    .filter(containsCompletionStatusParam ?
                            entry -> entry.getValue().getComplete() == finalCompletionStatus : entry -> true)
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        // then filter for event specifications, add to list
        if (!taskTypeIndicated || taskTypes.contains(TaskType.EVENT)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            entriesStream.filter(entry -> entry.getValue() instanceof Event).filter(containsSearchTerm ?
                            entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
                    .filter(containsStartTimeBeforeParam ? entry -> ((Event) entry.getValue()).getStartTime()
                            .compareTo(finalStartTimeBefore) < 0 : entry -> true)
                    .filter(containsStartTimeAfterParam ? entry -> ((Event) entry.getValue()).getStartTime()
                            .compareTo(finalStartTimeAfter) > 0 : entry -> true)
                    .filter(containsEndTimeBeforeParam ? entry -> ((Event) entry.getValue()).getEndTime()
                            .compareTo(finalEndTimeBefore) < 0 : entry -> true)
                    .filter(containsEndTimeAfterParam ? entry -> ((Event) entry.getValue()).getEndTime()
                            .compareTo(finalEndTimeAfter) > 0 : entry -> true)
                    .filter(containsCompletionStatusParam ?
                            entry -> entry.getValue().getComplete() == finalCompletionStatus : entry -> true)
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (filteredTasks.isEmpty()) {
            throw new NoTasksFilteredException();
        }

        Printers.printFilteredTasks(allTasks.size(), filteredTasks);

    }

}

