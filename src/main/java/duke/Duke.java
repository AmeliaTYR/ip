package duke;

import duke.storage.AllTasksLoader;
import duke.storage.AllTasksSaver;
import duke.storage.SettingsLoader;
import duke.storage.SettingsSaver;

import duke.constants.CommandType;
import duke.constants.DividerChoice;
import duke.constants.TootieFilePaths;

import duke.task.Task;

import duke.tootieFunctions.CommandExecutor;
import duke.ui.TextUi;
import duke.ui.UserInputHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.concurrent.atomic.AtomicInteger;

import static duke.parsers.Parsers.parseLineDividerFromString;
import static duke.storage.SettingsLoader.addSavedSettings;

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
        run();
    }

    private static void run() {
        loadTasksAndSettings();
        runCommandLoopUntilExitCommand();
        saveTasksAndSettings();
    }

    private static void loadTasksAndSettings() {
        ArrayList<String> savedSettings = new ArrayList<String>(4);
        addSavedSettings(savedSettings, tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);
        TextUi.printTootieLogo();
        TextUi.printDivider();

        SettingsLoader.loadTootieSettingsFile(savedSettings, tootieSettingsFilePath,
                allTasksFilePath, dividerChoice, username);
        tootieSettingsFilePath = savedSettings.get(0);
        allTasksFilePath = savedSettings.get(1);
        dividerChoice = parseLineDividerFromString(savedSettings.get(2));
        username = savedSettings.get(3);

        AllTasksLoader.loadAllTasksFile(allTasks, SCANNER, allTasksFilePath, numTasks, numTasksCompleted);

        TextUi.printHelloMessage(username);
    }

    private static void runCommandLoopUntilExitCommand() {
        String userInput;
        CommandType commandType = CommandType.START;

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
    }

    private static void saveTasksAndSettings() {
        // Save tasks and settings
        try {
            allTasksFilePath = AllTasksSaver.saveAllTasks(allTasks, allTasksFilePath, numTasks, numTasksCompleted);
            SettingsSaver.saveTootieSettings(tootieSettingsFilePath, allTasksFilePath, username, dividerChoice);
            TextUi.printDivider();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



