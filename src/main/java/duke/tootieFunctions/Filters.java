package duke.tootieFunctions;

import duke.constants.TaskType;
import duke.constants.TootieNormalMsgs;
import duke.exceptions.*;
import duke.parsers.Parsers;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.util.*;
import java.util.stream.Stream;

import static duke.constants.TootieSymbols.NEWLINE;
import static duke.parsers.Parsers.parseDoubleCharacterTaggedParamsFromUserInput;
import static duke.tootieFunctions.AddNewTasks.isDateWithTime;
import static duke.tootieFunctions.AddNewTasks.isDateWithoutTime;

/**
 * Filters the tasks in the list
 */
public class Filters {

    // parse the user input and filter out the suitable tasks
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

        Date dueDateBefore = null;
        Date dueDateAfter = null;
        Date startTimeBefore = null;
        Date startTimeAfter = null;
        Date endTimeBefore = null;
        Date endTimeAfter = null;

        ArrayList<TaskType> taskTypes = new ArrayList<>();

        String componentUserInput;
        String searchTerm = "";

        HashMap<Integer, Task> filteredTasks = new HashMap<>();

        // for returning filter options parsed from the user input
        HashMap<String, String> filterOptions = new HashMap<>();

        // parse filter command into segments
        parseDoubleCharacterTaggedParamsFromUserInput(userInput, filterOptions);

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

        if (containsDueDateAfterParam && containsDueDateBeforeParam) {
            if (dueDateBefore.compareTo(dueDateAfter) < 0) {
                System.out.println("Date for \"due date before\" is before date for \"due date after\"." + NEWLINE +
                        "Automatically swapping the two...");
                Date temp = dueDateAfter;
                dueDateAfter = dueDateBefore;
                dueDateBefore = temp;
            }
            if (dueDateBefore.compareTo(dueDateAfter) == 0) {
                System.out.println("Warning: date for \"due date before\" is the same as date for \"due date after\".");
            }
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

        if (containsStartTimeAfterParam && containsStartTimeBeforeParam) {
            if (startTimeBefore.compareTo(startTimeAfter) < 0) {
                System.out.println("Date for \"start time before\" is before date for \"start time after\"." + NEWLINE +
                        "Automatically swapping the two...");
                Date temp = startTimeAfter;
                startTimeAfter = startTimeBefore;
                startTimeBefore = temp;
            }
            if (startTimeBefore.compareTo(startTimeAfter) == 0) {
                System.out.println("Warning: date for \"start time before\" is the same as date for \"start time after\".");
            }
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

        if (containsEndTimeAfterParam && containsEndTimeBeforeParam) {
            if (endTimeBefore.compareTo(endTimeAfter) < 0) {
                System.out.println("Date for \"end time before\" is before date for \"end time after\"." + NEWLINE +
                        "Automatically swapping the two...");
                Date temp = endTimeAfter;
                endTimeAfter = endTimeBefore;
                endTimeBefore = temp;
            }
            if (endTimeBefore.compareTo(endTimeAfter) == 0) {
                System.out.println("Warning: date for \"end time before\" is the same as date for \"end time after\".");
            }
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

        if (!taskTypeIndicated || taskTypes.contains(TaskType.TODO)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            // then filter for todo specifications, add to list
            entriesStream.filter(entry -> entry.getValue() instanceof ToDo).filter(containsSearchTerm ?
                            entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (!taskTypeIndicated || taskTypes.contains(TaskType.DEADLINE)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            // then filter for deadline specifications, add to list
            entriesStream.filter(entry -> entry.getValue() instanceof Deadline).filter(containsSearchTerm ?
                            entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
                    .filter(containsDueDateBeforeParam ? entry -> ((Deadline) entry.getValue()).getDueDate()
                            .compareTo(finalDueDateBefore) < 0 : entry -> true)
                    .filter(containsDueDateAfterParam ? entry -> ((Deadline) entry.getValue()).getDueDate()
                            .compareTo(finalDueDateAfter) > 0 : entry -> true)
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (!taskTypeIndicated || taskTypes.contains(TaskType.EVENT)) {
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            // then filter for event specifications, add to list
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

        // then print it out
        int tasksFound = 0;
        int tasksComplete = 0;
        for (i = 1; i <= allTasks.size(); i++) {
            if (filteredTasks.containsKey(i)) {
                System.out.printf((TootieNormalMsgs.LIST_TASK_FORMAT) + "%n", i, filteredTasks.get(i).getTaskType(),
                        filteredTasks.get(i).getCompletionIndicator(), filteredTasks.get(i).getTaskDescription());
                tasksFound++;
                if (filteredTasks.get(i).getComplete()) {
                    tasksComplete++;
                }
            }
        }

        // print filter summary
        if (tasksComplete == tasksFound) {
            System.out.printf("Filtered! %1$s task%2$s found, all complete!%n", tasksFound, (tasksFound == 1 ? "" :
                    "s"));
        } else {
            System.out.printf("Filtered! %1$s task%2$s found, %3$s incomplete.%n", tasksFound, (tasksFound == 1 ? ""
                    : "s"), tasksFound - tasksComplete);
        }

    }

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
