package duke.fileHandlers;

import duke.Duke;
import duke.finalObjects.TootieFileMsgs;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AllTasksSaver {
    public AllTasksSaver() {
    }

    public static final String NEWLINE = System.lineSeparator();

    // saves all tasks from the allTasks ArrayList to the allTasks.txt file
    public static void saveAllTasks(ArrayList<Task> allTasks, String allTasksFilePath, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws IOException {
        File allTasksFile = new File(allTasksFilePath);

        if (allTasksFile.createNewFile()){
            System.out.println("New file created: " + allTasksFile.getName());
        }

        // clear the file
        new FileWriter(allTasksFilePath, false).close();

        // write file header
        fileFunctions.appendsStringToFile(String.format("Total tasks: %1$d", numTasks.get()), allTasksFilePath);
        fileFunctions.appendsStringToFile(NEWLINE, allTasksFilePath);
        fileFunctions.appendsStringToFile(String.format("Tasks completed: %1$d", numTasksCompleted.get()), allTasksFilePath);
        fileFunctions.writeDoubleNewlineToFile(allTasksFilePath);
        fileFunctions.appendsStringToFile(TootieFileMsgs.ALL_TASKS_FILE_INSTRUCTIONS_HEADER, allTasksFilePath);

        String printOut = "";
        for (int i = 0; i < numTasks.get(); i++){
            if (allTasks.get(i) instanceof ToDo) {
                printOut = "[T][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "] " + allTasks.get(i).getTaskName();
            }
            if (allTasks.get(i) instanceof Deadline) {
                printOut = "[D][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "] " + allTasks.get(i).getTaskName() +
                        " (by:" + ((Deadline) allTasks.get(i)).getDueDate() + ")";
            }
            if (allTasks.get(i) instanceof Event) {
                printOut = "[E][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "] " + allTasks.get(i).getTaskName() +
                        " (from:" + ((Event) allTasks.get(i)).getStartTime() + " to " +
                        ((Event) allTasks.get(i)).getEndTime() + ")";
            }
            fileFunctions.appendsStringToFile(printOut,allTasksFilePath);
            fileFunctions.appendsStringToFile(NEWLINE, allTasksFilePath);
        }

        System.out.println("All tasks saved.");

        allTasksFilePath = allTasksFile.getAbsolutePath();
    }
}
