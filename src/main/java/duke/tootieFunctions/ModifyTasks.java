package duke.tootieFunctions;

import duke.exceptions.TaskNonexistantException;
import duke.constants.TootieNormalMsgs;
import duke.task.Task;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Modifies the tasks in the list
 */
public class ModifyTasks {

    /**
     * deletes the selected task based on user input
     *
     * @param userInput         raw user input
     * @param allTasks          list of all Tasks
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks completed
     * @throws TaskNonexistantException
     */
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

    /**
     * process the user input and mark the task complete
     *
     * @param userInput         raw user input
     * @param allTasks          list of all Tasks
     * @param numTasksCompleted total number of tasks completed
     * @param numTasks          total number of tasks in the list
     * @throws TaskNonexistantException
     */
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

    /**
     * Extract the task number from the user input
     *
     * @param userInput raw user input
     * @param numTasks total number of tasks in the list
     * @return task number as indicated by user
     * @throws TaskNonexistantException
     */
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

    /**
     * process the user input and mark the task incomplete
     *
     * @param userInput         raw user input
     * @param allTasks          list of all Tasks
     * @param numTasksCompleted total number of tasks completed
     * @param numTasks          total number of tasks in the list
     * @throws TaskNonexistantException
     */
    public static void markTaskIncomplete(String userInput, ArrayList<Task> allTasks, AtomicInteger numTasksCompleted
            , AtomicInteger numTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, numTasks);

        if (allTasks.get(taskNum - 1).getComplete()) {
            numTasksCompleted.getAndDecrement();
        }

        allTasks.get(taskNum - 1).setComplete(false);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_MARKED_UNDONE_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(), allTasks.get(taskNum - 1).getTaskDescription()));
    }
}
