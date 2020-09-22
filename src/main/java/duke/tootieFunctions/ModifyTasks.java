package duke.tootieFunctions;

import duke.Duke;
import duke.exceptions.TaskNonexistantException;
import duke.finalObjects.TootieNormalMsgs;
import duke.task.Task;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ModifyTasks {
    // deletes the selected task based on user input
    public static void deleteTask(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasks,
                                  AtomicInteger numTasksCompleted) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, numTasks);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_DELETED_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(), allTasks.get(taskNum - 1).getCompletionIndicator(),
                allTasks.get(taskNum - 1).getTaskDescription()));
        if (allTasks.get(taskNum - 1).getComplete()) {
            numTasksCompleted.getAndDecrement();
        }
        allTasks.remove(taskNum - 1);
        numTasks.getAndDecrement();
    }

    // process the user input and mark the task complete
    public static void markTaskComplete(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasksCompleted,
                                        AtomicInteger numTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, numTasks);

        allTasks.get(taskNum - 1).setComplete(true);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_MARKED_DONE_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(), allTasks.get(taskNum - 1).getTaskDescription()));

        numTasksCompleted.getAndIncrement();
    }

    private static int getTaskNumFromInput(String userInput, AtomicInteger numTasks) throws TaskNonexistantException {
        int taskNum;
        try {
            taskNum = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (taskNum > numTasks.get() || taskNum < 1) {
                throw new TaskNonexistantException();
            }
        } catch (NumberFormatException exception) {
            throw new TaskNonexistantException();
        }
        return taskNum;
    }

    // process the user input and mark the task incomplete
    public static void markTaskIncomplete(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasksCompleted
            , AtomicInteger numTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, numTasks);

        allTasks.get(taskNum - 1).setComplete(false);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_MARKED_UNDONE_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(), allTasks.get(taskNum - 1).getTaskDescription()));

        numTasksCompleted.getAndDecrement();
    }
}
