package duke;

import duke.constants.CommandType;
import duke.constants.DividerChoice;
import duke.constants.TootieConstants;
import duke.constants.TootieFilePaths;
import duke.storage.AllTasksLoader;
import duke.storage.AllTasksSaver;
import duke.storage.SettingsLoader;
import duke.storage.SettingsSaver;

import duke.task.Task;

import duke.tootieFunctions.CommandExecutor;
import duke.ui.Printers;
import duke.ui.UserInputHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.concurrent.atomic.AtomicInteger;

import static duke.constants.TootieNormalMsgs.ALL_SETTINGS_SAVED_MSG;
import static duke.constants.TootieNormalMsgs.ALL_TASKS_SAVED_MSG;
import static duke.parsers.Parsers.parseLineDividerFromString;
import static duke.storage.SettingsLoader.addSavedSettings;

/**
 * Entry point of the Tootie Tasklist application.
 * Initializes the application and starts the interaction with the user.
 */
public class Duke {
    /**
     * Scanner to read input from the console
     **/
    public static final Scanner SCANNER = new Scanner(System.in);

    /**
     * An array containing all tasks the user has input
     **/
    public static ArrayList<Task> allTasks = new ArrayList<>();

    /**
     * Number of Tasks in the allTasks array
     **/
    public static AtomicInteger numTasks = new AtomicInteger(TootieConstants.INITIAL_NUMBER_OF_TASKS_IN_LIST);
    public static AtomicInteger numTasksCompleted = new AtomicInteger(TootieConstants.INITIAL_NUMBER_OF_TASKS_COMPLETED);

    /**
     * settings set to defaults
     **/
    public static String tootieSettingsFilePath = TootieFilePaths.DEFAULT_TOOTIE_SETTINGS_FILE_PATH;
    public static String allTasksFilePath = TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH;
    public static DividerChoice dividerChoice = DividerChoice.SPARKLY;
    public static String username = "user";
    public static ArrayList<String> savedSettings = new ArrayList<>(TootieConstants.NUMBER_OF_SETTINGS);

    public static void main(String[] args) {
        run();
    }

    /**
     * Runs the program until termination.
     */
    private static void run() {
        start();
        runCommandLoopUntilExitCommand();
        saveTasksAndSettings();
    }

    /**
     * Loads saved items, and prints the welcome message.
     */
    private static void start() {
        Printers.printTootieLogo();
        Printers.printDivider();
        loadTasksAndSettings();
        Printers.printHelloMessage(username);
    }

    /**
     * Loads all the saved Tasks and Settings
     */
    private static void loadTasksAndSettings() {
        addSavedSettings(savedSettings, tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);

        SettingsLoader.loadTootieSettingsFile(savedSettings, tootieSettingsFilePath,
                allTasksFilePath, dividerChoice, username);
        updateSettingsVariables(savedSettings);
        Printers.printDivider();
        ArrayList<String> allTasksFilePathReturn = new ArrayList<>(TootieConstants.NUMBER_OF_FILE_PATHS);
        AllTasksLoader.loadAllTasksFile(false, allTasks, SCANNER,
                allTasksFilePath, numTasks, numTasksCompleted,
                allTasksFilePathReturn);
    }

    /**
     * Loads the saved settings variables into the current session
     *
     * @param savedSettings list of saved settings parsed from settings file
     */
    private static void updateSettingsVariables(ArrayList<String> savedSettings) {
        tootieSettingsFilePath = savedSettings.get(TootieConstants.SETTINGS_FILE_PATH_ARRAY_INDEX);
        allTasksFilePath = savedSettings.get(TootieConstants.ALL_TASKS_FILE_PATH_ARRAY_INDEX);
        dividerChoice = parseLineDividerFromString(savedSettings.get(TootieConstants.DIVIDER_CHOICE_ARRAY_INDEX));
        username = savedSettings.get(TootieConstants.USERNAME_ARRAY_INDEX);
    }

    /**
     * Reads user commands until the "bye" command is entered
     */
    private static void runCommandLoopUntilExitCommand() {
        String userInput;
        CommandType commandType = CommandType.START;

        // process commands
        while (commandType != CommandType.BYE) {
            userInput = UserInputHandlers.getUserInput(SCANNER);
            UserInputHandlers.echoUserInput(userInput);
            Printers.printDivider();
            commandType = CommandExecutor.extractCommandType(userInput);
            CommandExecutor.executeCommand(savedSettings, commandType, userInput, allTasks, tootieSettingsFilePath,
                    allTasksFilePath, numTasks, numTasksCompleted, username, SCANNER);
            // load settings if any were changed
            updateSettingsVariables(savedSettings);
            Printers.printDivider();
        }
    }

    /**
     * Saves all the updated list of Tasks and Settings into the .txt files
     */
    private static void saveTasksAndSettings() {
        // Save tasks and settings
        try {
            allTasksFilePath = AllTasksSaver.saveAllTasks(allTasks, allTasksFilePath, numTasks, numTasksCompleted);
            System.out.println(ALL_TASKS_SAVED_MSG);
            SettingsSaver.saveTootieSettings(tootieSettingsFilePath, allTasksFilePath, username, dividerChoice);
            System.out.println(ALL_SETTINGS_SAVED_MSG);
            Printers.printDivider();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



