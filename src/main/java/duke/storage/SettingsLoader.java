package duke.storage;

import duke.exceptions.FileEmptyException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.constants.DividerChoice;
import duke.parsers.Checks;
import duke.parsers.Parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static duke.constants.Tags.ALL_TASKS_FILEPATH_TAG;
import static duke.constants.Tags.DIVIDER_CHOICE_TAG;
import static duke.constants.Tags.LOADING_SETTINGS_MSG;
import static duke.constants.Tags.TOOTIE_SETTINGS_FILEPATH_TAG;
import static duke.constants.Tags.USERNAME_TAG;

import static duke.constants.TootieNormalMsgs.ERROR_READING_FILE_ON_LINE_MSG_FORMAT;
import static duke.constants.TootieNormalMsgs.SETTINGS_FILE_EMPTY_MSG;
import static duke.constants.TootieNormalMsgs.SETTINGS_FILE_NOT_FOUND_MSG;
import static duke.storage.AllTasksLoader.getFileNextLine;
import static duke.ui.Printers.changeDivider;

public class SettingsLoader {

    /**
     * Attempts to load and store the tootieSettings.txt variables
     *
     * @param savedSettings          array of saved settings
     * @param tootieSettingsFilePath file path to the settings save file
     * @param allTasksFilePath       file path to the save file for all tasks
     * @param dividerChoice          divider chosen by user
     * @param username               user input name
     */
    public static void loadTootieSettingsFile(ArrayList<String> savedSettings, String tootieSettingsFilePath,
                                              String allTasksFilePath, DividerChoice dividerChoice, String username) {

        System.out.println(LOADING_SETTINGS_MSG);

        try {
            File tootieSettingsFile = fileFunctions.getFileFromFilePath(tootieSettingsFilePath);
            fileFunctions.checkFileExists(tootieSettingsFile);
            readTootieSettingsFile(savedSettings, tootieSettingsFile, tootieSettingsFilePath, allTasksFilePath,
                    dividerChoice, username);
        } catch (FileNotFoundException e) {
            System.out.println(SETTINGS_FILE_NOT_FOUND_MSG);
            tootieSettingsFilePath = fileFunctions.autoCreateNewFile(duke.constants.TootieFilePaths.DEFAULT_TOOTIE_SETTINGS_FILE_PATH);
        } catch (FileEmptyException e) {
            System.out.println(SETTINGS_FILE_EMPTY_MSG);
        }
    }

    /**
     * Read and parse all the settings from the tootieSettings.txt file
     *
     * @param savedSettings          array of saved settings
     * @param tootieSettingsFile     the save file containing the saved settings
     * @param tootieSettingsFilePath file path to the settings save file
     * @param allTasksFilePath       file path to the save file for all tasks
     * @param dividerChoice          divider chosen by user
     * @param username               user input name
     * @throws FileEmptyException the settings file is empty
     * @throws FileNotFoundException the settings file cannot be found
     */
    private static void readTootieSettingsFile(ArrayList<String> savedSettings, File tootieSettingsFile,
                                               String tootieSettingsFilePath, String allTasksFilePath,
                                               DividerChoice dividerChoice, String username) throws FileEmptyException, FileNotFoundException {

        Scanner SETTINGS_FILE_SCANNER = new Scanner(tootieSettingsFile);
        String fileLine = "";

        if (!SETTINGS_FILE_SCANNER.hasNext()) {
            setSavedSettings(savedSettings, tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);
            throw new FileEmptyException();
        }

        try {
            String parsedString;

            fileLine = readFileUntilLineContainsString(TOOTIE_SETTINGS_FILEPATH_TAG, SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, TOOTIE_SETTINGS_FILEPATH_TAG);
            if (!parsedString.isBlank()) {
                tootieSettingsFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString(ALL_TASKS_FILEPATH_TAG, SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, ALL_TASKS_FILEPATH_TAG);
            if (!parsedString.isBlank()) {
                allTasksFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString(DIVIDER_CHOICE_TAG, SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, DIVIDER_CHOICE_TAG);
            dividerChoice = Parsers.parseDividerChoice(parsedString);
            changeDivider(dividerChoice);

            fileLine = readFileUntilLineContainsString(USERNAME_TAG, SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, USERNAME_TAG);
            if (!parsedString.isBlank()) {
                username = parsedString;
            }

            setSavedSettings(savedSettings, tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);

        } catch (SettingObjectWrongFormatException e) {
            System.out.printf(ERROR_READING_FILE_ON_LINE_MSG_FORMAT, fileLine);
        }
    }

    /**
     * Adds the default settings to saved settings array
     *
     * @param savedSettings          array of saved settings
     * @param tootieSettingsFilePath file path to the settings save file
     * @param allTasksFilePath       file path to the save file for all tasks
     * @param dividerChoice          divider chosen by user
     * @param username               user input name
     */
    public static void addSavedSettings(ArrayList<String> savedSettings, String tootieSettingsFilePath,
                                        String allTasksFilePath, DividerChoice dividerChoice, String username) {
        savedSettings.add(Checks.pathReplaceIllegalCharacters(tootieSettingsFilePath));
        savedSettings.add(Checks.pathReplaceIllegalCharacters(allTasksFilePath));
        savedSettings.add(dividerChoice.toString());
        savedSettings.add(username);
    }

    /**
     * Sets the default settings to saved settings array
     *
     * @param savedSettings          array of saved settings
     * @param tootieSettingsFilePath file path to the settings save file
     * @param allTasksFilePath       file path to the save file for all tasks
     * @param dividerChoice          divider chosen by user
     * @param username               user input name
     */
    public static void setSavedSettings(ArrayList<String> savedSettings, String tootieSettingsFilePath,
                                        String allTasksFilePath, DividerChoice dividerChoice, String username) {
        savedSettings.set(0, Checks.pathReplaceIllegalCharacters(tootieSettingsFilePath));
        savedSettings.set(1, Checks.pathReplaceIllegalCharacters(allTasksFilePath));
        savedSettings.set(2, dividerChoice.toString());
        savedSettings.set(3, username);
    }

    /**
     * continue reading through a file until a specific string is found
     *
     * @param stringSearched indicator string
     * @param fileScanner    scanner for scanning a file
     * @return return the line the string is on
     */
    public static String readFileUntilLineContainsString(String stringSearched, Scanner fileScanner) {
        String fileInput = "";
        // read each setting and return the variables accordingly
        while (fileScanner.hasNext()) {
            fileInput = getFileNextLine(fileScanner);
            if (fileInput.contains(stringSearched)) {
                break;
            }
        }

        return fileInput;
    }
}
