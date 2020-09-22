package duke.fileHandlers;

import duke.Duke;
import duke.exceptions.FileEmptyException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.finalObjects.DividerChoice;
import duke.finalObjects.TootieFilePaths;
import duke.parsers.Parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static duke.fileHandlers.AllTasksLoader.getFileNextLine;
import static duke.tootieFunctions.TextUi.changeDivider;

public class SettingsLoader {
    public SettingsLoader() {
    }

    public static final String NEWLINE = System.lineSeparator();

    // attempt to load and store the tootieSettings.txt variables
    public static void loadTootieSettingsFile(String tootieSettingsFilePath,
                                              String allTasksFilePath,
                                              DividerChoice dividerChoice,
                                              String username) {
        System.out.println("Loading tootieSettings.txt save file...");

        try{
            File tootieSettingsFile = fileFunctions.getFileFromFilePath(tootieSettingsFilePath);
            fileFunctions.checkFileExists(tootieSettingsFile);
            readTootieSettingsFile(tootieSettingsFile, tootieSettingsFilePath, allTasksFilePath,
                    dividerChoice, username);
        } catch (FileNotFoundException e) {
            System.out.println("tootieSettings.txt save file not found" + NEWLINE + "Creating new file");
            tootieSettingsFilePath = fileFunctions.autoCreateNewFile(TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH);
        } catch (FileEmptyException e) {
            System.out.println("tootieSettings.txt save file empty" + NEWLINE + "No previous settings loaded");
        }
    }

    // read and parse all the settings from the tootieSettings.txt file
    private static void readTootieSettingsFile(File tootieSettingsFile,
                                               String tootieSettingsFilePath,
                                               String allTasksFilePath,
                                               DividerChoice dividerChoice,
                                               String username)
            throws FileEmptyException, FileNotFoundException {

        Scanner SETTINGS_FILE_SCANNER = new Scanner(tootieSettingsFile);
        String fileLine = "";

        if (!SETTINGS_FILE_SCANNER.hasNext()) {
            throw new FileEmptyException();
        }

        try {
            String parsedString;

            fileLine = readFileUntilLineContainsString("+ Tootie settings:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ Tootie settings:");
            if (!parsedString.isBlank()){
                tootieSettingsFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString("+ All Tasks:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ All Tasks:");
            if (!parsedString.isBlank()){
                allTasksFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString("+ Divider choice:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ Divider choice:");
            dividerChoice = Parsers.parseDividerChoice(parsedString);
            changeDivider(dividerChoice);

            fileLine = readFileUntilLineContainsString("+ Username:", SETTINGS_FILE_SCANNER);
            parsedString = Parsers.parseFileObject(fileLine, "+ Username:");
            if (!parsedString.isBlank()){
                username = parsedString;
            }
        } catch (SettingObjectWrongFormatException e) {
            System.out.println(String.format("Error reading settings file! Error on line:" + NEWLINE + "%1$s",
                    fileLine));
        }
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
