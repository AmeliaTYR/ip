package duke.tootieFunctions;

import duke.constants.TaskType;
import duke.constants.TootieNormalMsgs;
import duke.exceptions.*;
import duke.parsers.Parsers;
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

import static duke.constants.TootieSymbols.NEWLINE;
import static duke.parsers.Parsers.parseDoubleCharacterTaggedParamsFromUserInput;
import static duke.tootieFunctions.AddNewTasks.isDateWithTime;
import static duke.tootieFunctions.AddNewTasks.isDateWithoutTime;

/**
 * Filters the tasks in the list
 */
public class Filters {

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
        boolean dateWronglyFormatted = false;

        // dates for any of the indicators
        Date dueDateBefore = null;
        Date dueDateAfter = null;
        Date startTimeBefore = null;
        Date startTimeAfter = null;
        Date endTimeBefore = null;
        Date endTimeAfter = null;

        ArrayList<TaskType> taskTypes = new ArrayList<>(2);

        String componentUserInput;
        String searchTerm = "";

        HashMap<Integer, Task> filteredTasks = new HashMap<>();

        // for returning filter options parsed from the user input
        HashMap<String, String> filterOptions = new HashMap<>();

        // parse filter command into segments
        try {
            parseDoubleCharacterTaggedParamsFromUserInput(userInput, filterOptions);
        } catch (MissingParamsException e) {
            throw new MissingFilterOptionsException();
        }

        if (filterOptions.containsKey("tt")) {
            componentUserInput = filterOptions.get("tt");
            taskTypeIndicated = true;
            parseTaskTypes(componentUserInput, taskTypes);
            hasAtLeastOneFilterOption = true;
        }

        if (filterOptions.containsKey("st")) {
            containsSearchTerm = true;
            searchTerm = filterOptions.get("st").trim();
            hasAtLeastOneFilterOption = true;
        }

        if (filterOptions.containsKey("db")) {
            String dueDateUnformatted = filterOptions.get("db").trim();

            // check format due date
            boolean isDueDateWithTime = isDateWithTime(dueDateUnformatted);
            boolean isDueDateWithoutTime = isDateWithoutTime(dueDateUnformatted);

            // try to parse due date
            if (isDueDateWithTime) {
                try {
                    dueDateBefore = Parsers.parseDateWithTime(dueDateUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else if (isDueDateWithoutTime) {
                try {
                    dueDateBefore = Parsers.parseDateWithoutTime(dueDateUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else {
                dateWronglyFormatted = true;
            }

            if (dateWronglyFormatted) {
                System.out.println("Date for \"due date before\" wrongly formatted. Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE);
                dateWronglyFormatted = false;
            } else {
                hasAtLeastOneFilterOption = true;
                containsDueDateBeforeParam = true;
            }
        }

        if (filterOptions.containsKey("da")) {
            String dueDateUnformatted = filterOptions.get("da").trim();

            // check format due date
            boolean isDueDateWithTime = isDateWithTime(dueDateUnformatted);
            boolean isDueDateWithoutTime = isDateWithoutTime(dueDateUnformatted);

            // try to parse due date
            if (isDueDateWithTime) {
                try {
                    dueDateAfter = Parsers.parseDateWithTime(dueDateUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else if (isDueDateWithoutTime) {
                try {
                    dueDateAfter = Parsers.parseDateWithoutTime(dueDateUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else {
                dateWronglyFormatted = true;
            }

            if (dateWronglyFormatted) {
                System.out.println("Date for \"due date after\" wrongly formatted. Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE);
                dateWronglyFormatted = false;
            } else {
                hasAtLeastOneFilterOption = true;
                containsDueDateAfterParam = true;
            }
        }

        // compare dates before and after
        try {
            checkIfDateAfterPreceedsBefore(containsDueDateBeforeParam, containsDueDateAfterParam, dueDateBefore,
                    dueDateAfter);
            checkIfBeforeAndAfterDatesMatch(containsDueDateBeforeParam, containsDueDateAfterParam, dueDateBefore,
                    dueDateAfter);
        } catch (DateBeforeMatchesAfterException e) {
            System.out.println("Warning: date for \"due date before\" is the same as date for \"due date after\".");
        } catch (DateAfterPreceedsDateBefore dateAfterPreceedsDateBefore) {
            System.out.println("Date for \"due date before\" is before date for \"due date after\"." + NEWLINE + "Automatically swapping the two...");
            Date temp = dueDateAfter;
            dueDateAfter = dueDateBefore;
            dueDateBefore = temp;
        }

        if (filterOptions.containsKey("sb")) {
            String startTimeUnformatted = filterOptions.get("sb").trim();

            // check format start time
            boolean isStartTimeWithTime = isDateWithTime(startTimeUnformatted);
            boolean isStartTimeWithoutTime = isDateWithoutTime(startTimeUnformatted);

            // try to parse start time
            if (isStartTimeWithTime) {
                try {
                    startTimeBefore = Parsers.parseDateWithTime(startTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else if (isStartTimeWithoutTime) {
                try {
                    startTimeBefore = Parsers.parseDateWithoutTime(startTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else {
                dateWronglyFormatted = true;
            }

            if (dateWronglyFormatted) {
                System.out.println("Date for \"start time before\" wrongly formatted. Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE);
                dateWronglyFormatted = false;
            } else {
                hasAtLeastOneFilterOption = true;
                containsStartTimeBeforeParam = true;
            }
        }

        if (filterOptions.containsKey("sa")) {
            String startTimeUnformatted = filterOptions.get("sa").trim();

            // check format start time
            boolean isStartTimeWithTime = isDateWithTime(startTimeUnformatted);
            boolean isStartTimeWithoutTime = isDateWithoutTime(startTimeUnformatted);

            // try to parse start time
            if (isStartTimeWithTime) {
                try {
                    startTimeAfter = Parsers.parseDateWithTime(startTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else if (isStartTimeWithoutTime) {
                try {
                    startTimeAfter = Parsers.parseDateWithoutTime(startTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else {
                dateWronglyFormatted = true;
            }

            if (dateWronglyFormatted) {
                System.out.println("Date for \"start time after\" wrongly formatted. Ignoring param."  + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE);
                dateWronglyFormatted = false;
            } else {
                hasAtLeastOneFilterOption = true;
                containsStartTimeAfterParam = true;
            }
        }

        // compare dates before and after
        try {
            checkIfDateAfterPreceedsBefore(containsStartTimeBeforeParam, containsStartTimeAfterParam, startTimeBefore,
                    startTimeAfter);
            checkIfBeforeAndAfterDatesMatch(containsStartTimeBeforeParam, containsStartTimeAfterParam, startTimeBefore,
                    startTimeAfter);
        } catch (DateBeforeMatchesAfterException e) {
            System.out.println("Warning: date for \"start time before\" is the same as date for \"start time after\".");
        } catch (DateAfterPreceedsDateBefore dateAfterPreceedsDateBefore) {
            System.out.println("Date for \"start time before\" is before date for \"start time after\"." + NEWLINE +
                    "Automatically swapping the two...");
            Date temp = startTimeAfter;
            startTimeAfter = startTimeBefore;
            startTimeBefore = temp;
        }

        if (filterOptions.containsKey("eb")) {
            String endTimeUnformatted = filterOptions.get("eb").trim();

            // check format end time
            boolean isEndTimeWithTime = isDateWithTime(endTimeUnformatted);
            boolean isEndTimeWithoutTime = isDateWithoutTime(endTimeUnformatted);

            // try to parse end time
            if (isEndTimeWithTime) {
                try {
                    endTimeBefore = Parsers.parseDateWithTime(endTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else if (isEndTimeWithoutTime) {
                try {
                    endTimeBefore = Parsers.parseDateWithoutTime(endTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else {
                dateWronglyFormatted = true;
            }

            if (dateWronglyFormatted) {
                System.out.println("Date for \"end time before\" wrongly formatted. Ignoring param." + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE);
                dateWronglyFormatted = false;
            } else {
                hasAtLeastOneFilterOption = true;
                containsEndTimeBeforeParam = true;
            }
        }

        if (filterOptions.containsKey("ea")) {
            String endTimeUnformatted = filterOptions.get("ea").trim();

            // check format end time
            boolean isEndTimeWithTime = isDateWithTime(endTimeUnformatted);
            boolean isEndTimeWithoutTime = isDateWithoutTime(endTimeUnformatted);

            // try to parse end time
            if (isEndTimeWithTime) {
                try {
                    endTimeAfter = Parsers.parseDateWithTime(endTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else if (isEndTimeWithoutTime) {
                try {
                    endTimeAfter = Parsers.parseDateWithoutTime(endTimeUnformatted);
                } catch (InvalidDateException e) {
                    dateWronglyFormatted = true;
                }
            } else {
                dateWronglyFormatted = true;
            }

            if (dateWronglyFormatted) {
                System.out.println("Date for \"end time after\" wrongly formatted. Ignoring param."  + NEWLINE + TootieNormalMsgs.DATE_FORMAT_MESSAGE);
                dateWronglyFormatted = false;
            } else {
                hasAtLeastOneFilterOption = true;
                containsEndTimeAfterParam = true;
            }
        }

        // compare dates before and after
        try {
            checkIfDateAfterPreceedsBefore(containsEndTimeBeforeParam, containsEndTimeAfterParam, endTimeBefore,
                    endTimeAfter);
            checkIfBeforeAndAfterDatesMatch(containsEndTimeBeforeParam, containsEndTimeAfterParam, endTimeBefore,
                    endTimeAfter);
        } catch (DateBeforeMatchesAfterException e) {
            System.out.println("Warning: date for \"end time before\" is the same as date for \"end time after\".");
        } catch (DateAfterPreceedsDateBefore dateAfterPreceedsDateBefore) {
            System.out.println("Date for \"end time before\" is before date for \"end time after\"." + NEWLINE +
                    "Automatically swapping the two...");
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

        // then filter for todo specifications, add to list
        if (!taskTypeIndicated || taskTypes.contains(TaskType.TODO)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            entriesStream.filter(entry -> entry.getValue() instanceof ToDo).filter(containsSearchTerm ?
                            entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
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
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (filteredTasks.isEmpty()) {
            throw new NoTasksFilteredException();
        }

        printFilteredTasks(allTasks.size(), filteredTasks);

    }

    /**
     * Print the list of filtered tasks
     *
     * @param numTasks      total number of tasks in
     * @param filteredTasks list of filtered tasks
     */
    private static void printFilteredTasks(int numTasks, HashMap<Integer, Task> filteredTasks) {
        int i;
        int tasksFound = 0;
        int tasksComplete = 0;
        for (i = 1; i <= numTasks; i++) {
            if (filteredTasks.containsKey(i)) {
                System.out.printf((TootieNormalMsgs.LIST_TASK_FORMAT) + "%n", i, filteredTasks.get(i).getTaskType(),
                        filteredTasks.get(i).getCompletionIndicator(), filteredTasks.get(i).getTaskDescription());
                tasksFound++;
                if (filteredTasks.get(i).getComplete()) {
                    tasksComplete++;
                }
            }
        }


        Printers.printFilterSummary(tasksFound, tasksComplete);
    }

    /**
     * Check if the date after comes before the date before
     *
     * @param containsDateBeforeParam the date for before was detected in the command
     * @param containsDateAfterParam  the date for after was detected in the command
     * @param dateBefore              the date for before
     * @param dateAfter               the date for after
     * @throws DateAfterPreceedsDateBefore the date for after comes before the date for before
     */
    private static void checkIfDateAfterPreceedsBefore(boolean containsDateBeforeParam,
                                                       boolean containsDateAfterParam, Date dateBefore, Date dateAfter)
            throws DateAfterPreceedsDateBefore {
        if (containsDateAfterParam && containsDateBeforeParam) {
            if (dateBefore.compareTo(dateAfter) < 0) {
                throw new DateAfterPreceedsDateBefore();
            }
        }
    }

    /**
     * Check if the Date for Before is the same Date as the date for after
     *
     * @param containsDateBeforeParam the date for before was found
     * @param containsDateAfterParam  the date for after was found
     * @param dateBefore              the date for before
     * @param dateAfter               the date for after
     * @throws DateBeforeMatchesAfterException the date for before is the same as the date for after
     */
    private static void checkIfBeforeAndAfterDatesMatch(boolean containsDateBeforeParam,
                                                        boolean containsDateAfterParam, Date dateBefore, Date dateAfter)
            throws DateBeforeMatchesAfterException {
        if (containsDateAfterParam && containsDateBeforeParam) {
            if (dateBefore.compareTo(dateAfter) == 0) {
                throw new DateBeforeMatchesAfterException();
            }
        }
    }

    /**
     * Parse the task types from the task type param
     *
     * @param taskTypesUserInput the user input for the parameter task types
     * @param taskTypes          the list of task types detected
     */
    private static void parseTaskTypes(String taskTypesUserInput, ArrayList<TaskType> taskTypes) {
        String userInputLowerCase = taskTypesUserInput.toLowerCase();
        if (userInputLowerCase.contains("event")) {
            taskTypes.add(TaskType.EVENT);
        }
        if (userInputLowerCase.contains("deadline")) {
            taskTypes.add(TaskType.DEADLINE);
        }
        if (userInputLowerCase.contains("todo")) {
            taskTypes.add(TaskType.TODO);
        }
    }
}
