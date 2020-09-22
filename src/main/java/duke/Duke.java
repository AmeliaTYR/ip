package duke;

import duke.fileHandlers.AllTasksLoader;
import duke.fileHandlers.AllTasksSaver;
import duke.fileHandlers.SettingsLoader;
import duke.fileHandlers.SettingsSaver;

import duke.finalObjects.CommandType;
import duke.finalObjects.DividerChoice;
import duke.finalObjects.TootieFilePaths;

import duke.task.Task;

import duke.tootieFunctions.CommandExecutor;
import duke.tootieFunctions.TextUi;
import duke.tootieFunctions.UserInputHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.concurrent.atomic.AtomicInteger;

public class Duke {

    public static final Scanner SCANNER = new Scanner(System.in);
    public static final String NEWLINE = System.lineSeparator();

    // array containing all tasks the user has input
    public static ArrayList<Task> allTasks = new ArrayList<>();

    // number of Tasks in the allTasks array
    public static AtomicInteger numTasks = new AtomicInteger(0);
    public static AtomicInteger numTasksCompleted = new AtomicInteger(0);

    // settings set to defaults
    public static String tootieSettingsFilePath = TootieFilePaths.DEFAULT_TOOTIE_SETTINGS_FILE_PATH;
    public static String allTasksFilePath = TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH;
    public static DividerChoice dividerChoice = DividerChoice.SPARKLY;
    public static String username = "user";

    public static void main(String[] args) {
        String userInput;
        CommandType commandType = CommandType.START;

        TextUi.printTootieLogo();
        TextUi.printDivider();

        SettingsLoader.loadTootieSettingsFile(tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);

        AllTasksLoader.loadAllTasksFile(allTasks, SCANNER, allTasksFilePath, numTasks, numTasksCompleted);

        TextUi.printHelloMessage(username);

        // process commands
        while (commandType != CommandType.BYE) {
            userInput = UserInputHandlers.getUserInput(SCANNER);
            UserInputHandlers.echoUserInput(userInput);
            TextUi.printDivider();
            commandType = CommandExecutor.extractCommandType(userInput);
            CommandExecutor.executeCommand(commandType, userInput, allTasks, allTasksFilePath, dividerChoice,
                    numTasks, numTasksCompleted, username);
            TextUi.printDivider();
        }

        // Save tasks and settings
        try {
            AllTasksSaver.saveAllTasks(allTasks, allTasksFilePath, numTasks, numTasksCompleted);
            SettingsSaver.saveTootieSettings(tootieSettingsFilePath, allTasksFilePath, username, dividerChoice);
            TextUi.printDivider();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



