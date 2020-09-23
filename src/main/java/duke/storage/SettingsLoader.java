package duke.storage;

import duke.exceptions.FileEmptyException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.constants.DividerChoice;
import duke.constants.TootieFilePaths;
import duke.parsers.Parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static duke.storage.AllTasksLoader.getFileNextLine;
import static duke.ui.Printers.changeDivider;

public class SettingsLoader {
    public SettingsLoader() {
    }

    public static final String NEWLINE = System.lineSeparator();

    // attempt to load and store the tootieSettings.txt variables
    public static void loadTootieSettingsFile(ArrayList<String> savedSettings, String tootieSettingsFilePath,
                                              String allTasksFilePath, DividerChoice dividerChoice, String username) {

        System.out.println("Loading tootieSettings.txt save file...");

        try {
            File tootieSettingsFile = fileFunctions.getFileFromFilePath(tootieSettingsFilePath);
            fileFunctions.checkFileExists(tootieSettingsFile);
            readTootieSettingsFile(savedSettings, tootieSettingsFile, tootieSettingsFilePath, allTasksFilePath,
                    dividerChoice, username);
        } catch (FileNotFoundException e) {
            System.out.println("tootieSettings.txt save file not found" + NEWLINE + "Creating new file...");
            tootieSettingsFilePath = fileFunctions.autoCreateNewFile(TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH);
        } catch (FileEmptyException e) {
            System.out.println("tootieSettings.txt save file empty" + NEWLINE + "No previous settings loaded");
        }
    }

    // read and parse all the settings from the tootieSettings.txt file
    private static void readTootieSettingsFile(ArrayList<String> savedSettings, File tootieSettingsFile,
                                                            String tootieSettingsFilePath, String allTasksFilePath,
                                                            DividerChoice dividerChoice, String username)
            throws FileEmptyException, FileNotFoundException {

        Scanner SETTINGS_FILE_SCANNER = new Scanner(tootieSettingsFile);
        String fileLine = "";

        if (!SETTINGS_FILE_SCANNER.hasNext()) {
            setSavedSettings(savedSettings, tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);
            throw new FileEmptyException();
        }

        try {
            String parsedString;

            fileLine = readFileUntilLineContainsString("+ Tootie settings:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ Tootie settings:");
            if (!parsedString.isBlank()) {
                tootieSettingsFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString("+ All Tasks:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ All Tasks:");
            if (!parsedString.isBlank()) {
                allTasksFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString("+ Divider choice:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ Divider choice:");
            dividerChoice = Parsers.parseDividerChoice(parsedString);
            changeDivider(dividerChoice);

            fileLine = readFileUntilLineContainsString("+ Username:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ Username:");
            if (!parsedString.isBlank()) {
                username = parsedString;
            }

            setSavedSettings(savedSettings, tootieSettingsFilePath, allTasksFilePath, dividerChoice, username);

        } catch (SettingObjectWrongFormatException e) {
            System.out.println(String.format("Error reading settings file! Error on line:" + NEWLINE + "%1$s",
                    fileLine));
        }
    }

    /**
     *
     * @param savedSettings
     * @param tootieSettingsFilePath
     * @param allTasksFilePath
     * @param dividerChoice
     * @param username
     */
    public static void addSavedSettings(ArrayList<String> savedSettings, String tootieSettingsFilePath,
                                        String allTasksFilePath, DividerChoice dividerChoice, String username) {
        savedSettings.add(Parsers.pathReplaceIllegalCharacters(tootieSettingsFilePath));
        savedSettings.add(Parsers.pathReplaceIllegalCharacters(allTasksFilePath));
        savedSettings.add(dividerChoice.toString());
        savedSettings.add(username);
    }

    public static void setSavedSettings(ArrayList<String> savedSettings, String tootieSettingsFilePath,
                                        String allTasksFilePath, DividerChoice dividerChoice, String username) {
        savedSettings.set(0, Parsers.pathReplaceIllegalCharacters(tootieSettingsFilePath));
        savedSettings.set(1, Parsers.pathReplaceIllegalCharacters(allTasksFilePath));
        savedSettings.set(2, dividerChoice.toString());
        savedSettings.set(3, username);
    }

    // continue reading through a file until a specific string is found
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
