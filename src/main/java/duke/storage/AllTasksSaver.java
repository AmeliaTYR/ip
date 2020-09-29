package duke.storage;

import duke.constants.TootieFileMsgs;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static duke.constants.TootieFileMsgs.TASKS_COMPLETED_FILE_LINE;
import static duke.constants.TootieFileMsgs.TOTAL_TASKS_FILE_LINE;
import static duke.constants.TootieNormalMsgs.NEW_FILE_CREATED_MSG_FORMAT;

public class AllTasksSaver {

    public static final String NEWLINE = System.lineSeparator();

    /**
     * saves all tasks from the allTasks ArrayList to the allTasks.txt file
     *
     * @param allTasks          list of all tasks
     * @param allTasksFilePath  file path for the allTasks.txt file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks in the list completed
     * @return absolute path to the allTasks.txt file
     * @throws IOException unable to write to file
     */
    public static String saveAllTasks(ArrayList<Task> allTasks, String allTasksFilePath,
                                      AtomicInteger numTasks, AtomicInteger numTasksCompleted)
            throws IOException {
        File allTasksFile = new File(allTasksFilePath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy hh:mm aa");

        if (allTasksFile.createNewFile()){
            System.out.println(String.format(NEW_FILE_CREATED_MSG_FORMAT, allTasksFile.getName()));
        }

        // clear the file
        new FileWriter(allTasksFilePath, false).close();

        // write file header
        fileFunctions.appendsStringToFile(String.format(TOTAL_TASKS_FILE_LINE, numTasks.get()), allTasksFilePath);
        fileFunctions.appendsStringToFile(NEWLINE, allTasksFilePath);
        fileFunctions.appendsStringToFile(String.format(TASKS_COMPLETED_FILE_LINE,
                numTasksCompleted.get()), allTasksFilePath);
        fileFunctions.writeDoubleNewlineToFile(allTasksFilePath);
        fileFunctions.appendsStringToFile(TootieFileMsgs.ALL_TASKS_FILE_INSTRUCTIONS_HEADER, allTasksFilePath);

        String printOut = "";
        for (int i = 0; i < numTasks.get(); i++){
            if (allTasks.get(i) instanceof ToDo) {
                printOut = "[T][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "] " + allTasks.get(i).getTaskName();
            }
            if (allTasks.get(i) instanceof Deadline) {
                printOut = "[D][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "] " + allTasks.get(i).getTaskName() +
                        " (by: " + dateFormat.format(((Deadline) allTasks.get(i)).getDueDate()) + ")";
            }
            if (allTasks.get(i) instanceof Event) {
                printOut = "[E][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "] " + allTasks.get(i).getTaskName() +
                        " (from: " + dateFormat.format(((Event) allTasks.get(i)).getStartTime()) + " to " +
                        dateFormat.format(((Event) allTasks.get(i)).getEndTime()) + ")";
            }
            fileFunctions.appendsStringToFile(printOut,allTasksFilePath);
            fileFunctions.appendsStringToFile(NEWLINE, allTasksFilePath);
        }

        return allTasksFile.getAbsolutePath();
    }
}
