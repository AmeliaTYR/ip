package duke.tootieFunctions;

import duke.constants.TaskType;
import duke.constants.TootieNormalMsgs;
import duke.exceptions.MissingFilterOptionsException;
import duke.exceptions.NoTasksFilteredException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static duke.parsers.Parsers.parseDoubleCharacterTaggedParamsFromUserInput;

/**
 * Filters the tasks in the list
 */
public class Filters {

    // parse the user input and filter out the suitable tasks
    public static void filterTasks(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks) throws MissingFilterOptionsException, NoTasksFilteredException {
        // for sorting
        int taskIndexInList = 1;
        boolean isDoneFiltering = false;

        // flags for filters
        boolean hasAtLeastOneFilterOption = false;
        boolean containsSearchTerm = false;
        boolean taskTypeIndicated = false;

        ArrayList<TaskType> taskTypes = new ArrayList<>();

        String componentUserInput = "";
        String searchTerm = "";

        HashMap<Integer, Task> filteredTasks = new HashMap<>();

        // for returning filter options parsed from the user input
        HashMap<String, String> filterOptions = new HashMap<>();

        // parse filter command into segments
        parseDoubleCharacterTaggedParamsFromUserInput(userInput, filterOptions);

        if (filterOptions.containsKey("tt")){
            componentUserInput = filterOptions.get("tt");
            taskTypeIndicated = true;
            parseTaskTypes(componentUserInput, taskTypes);
            hasAtLeastOneFilterOption = true;
        }

        if (filterOptions.containsKey("st")){
            containsSearchTerm = true;
            searchTerm = filterOptions.get("st").trim();
            hasAtLeastOneFilterOption = true;
        }

        if (!hasAtLeastOneFilterOption){
            throw new MissingFilterOptionsException();
        }

        int i = 1;
        HashMap<Integer, Task> numberedTasks = new HashMap<>();
        Set<Map.Entry<Integer, Task>> entries = numberedTasks.entrySet();
        Stream<Map.Entry<Integer, Task>> tempStream;

        // add all the numbered tasks to a hashmap
        for (i = 1; i <= allTasks.size(); i++){
            numberedTasks.put(i, allTasks.get(i -1));
        }

        String finalSearchTerm = searchTerm;

        /** apply filters */
        if (!taskTypeIndicated || taskTypes.contains(TaskType.TODO)){
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            // then filter for todo specifications, add to list
            entriesStream.filter(entry -> entry.getValue() instanceof ToDo)
                    .filter(containsSearchTerm ? entry -> entry.getValue().getTaskName().contains(finalSearchTerm) : entry -> true)
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (!taskTypeIndicated || taskTypes.contains(TaskType.DEADLINE)){
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            // then filter for deadline specifications, add to list
            entriesStream.filter(entry -> entry.getValue() instanceof Deadline)
                    .filter(entry -> entry.getValue().getTaskName().contains(finalSearchTerm))
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (!taskTypeIndicated || taskTypes.contains(TaskType.EVENT)){
            Stream<Map.Entry<Integer, Task>> entriesStream = entries.stream();
            // then filter for event specifications, add to list
            entriesStream.filter(entry -> entry.getValue() instanceof Event)
                    .filter(entry -> entry.getValue().getTaskName().contains(finalSearchTerm))
                    .forEach(entry -> filteredTasks.put(entry.getKey(), entry.getValue()));
        }

        if (filteredTasks.isEmpty()){
            throw new NoTasksFilteredException();
        }

        // then sort everything (or just retrieve in order
        Set<Map.Entry<Integer, Task>> filteredEntries = filteredTasks.entrySet();

        // then print it out
        int tasksFound = 0;
        int tasksComplete = 0;
        for (i = 1; i <= allTasks.size(); i++){
            if (filteredTasks.containsKey(i)){
                System.out.println(String.format(TootieNormalMsgs.LIST_TASK_FORMAT,
                        i,
                        filteredTasks.get(i).getTaskType(),
                        filteredTasks.get(i).getCompletionIndicator(),
                        filteredTasks.get(i).getTaskDescription())
                );
                tasksFound++;
                if (filteredTasks.get(i).getComplete()){
                    tasksComplete++;
                }
            }
        }

        // print filter summary
        if (tasksComplete == tasksFound) {
            System.out.println(String.format("Filtered! %1$s task%2$s found, all complete!", tasksFound, (tasksFound == 1? "" : "s")));
        } else {
            System.out.println(String.format("Filtered! %1$s task%2$s found, %3$s incomplete.",
                    tasksFound, (tasksFound == 1? "" : "s"), tasksFound - tasksComplete));
        }

    }

    private static void parseTaskTypes(String taskTypesUserInput, ArrayList<TaskType> taskTypes) {
        String userInputLowerCase = taskTypesUserInput.toLowerCase();
        if (userInputLowerCase.contains("event")){
            taskTypes.add(TaskType.EVENT);
        }
        if (userInputLowerCase.contains("deadline")){
            taskTypes.add(TaskType.DEADLINE);
        }
        if (userInputLowerCase.contains("todo")){
            taskTypes.add(TaskType.TODO);
        }
    }
}
