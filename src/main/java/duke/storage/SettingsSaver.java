package duke.storage;

import duke.constants.DividerChoice;
import duke.constants.TootieFileMsgs;
import duke.parsers.Parsers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static duke.constants.TootieNormalMsgs.NEW_FILE_CREATED_MSG_FORMAT;

public class SettingsSaver {
    public static final String NEWLINE = System.lineSeparator();

    /**
     * save settings to tootieSettings.txt file
     *
     * @param tootieSettingsFilePath file path to the settings save file
     * @param allTasksFilePath  file path for the allTasks.txt file
     * @param username               user input name
     * @param dividerChoice          divider chosen by user
     * @throws IOException error writing to the saved settings file
     */
    public static void saveTootieSettings(String tootieSettingsFilePath, String allTasksFilePath, String username,DividerChoice dividerChoice) throws IOException {
        File tootieSettingsFile = new File(tootieSettingsFilePath);

        if (tootieSettingsFile.createNewFile()){
            System.out.println(String.format(NEW_FILE_CREATED_MSG_FORMAT, tootieSettingsFile.getAbsolutePath()));
        }

        // clear the file
        new FileWriter(tootieSettingsFilePath, false).close();

        fileFunctions.appendsStringToFile(TootieFileMsgs.TOOTIE_SETTINGS_FILE_INSTRUCTIONS_HEADER, tootieSettingsFilePath);
        fileFunctions.appendsStringToFile(NEWLINE, tootieSettingsFilePath);
        fileFunctions.appendsStringToFile(String.format(TootieFileMsgs.TOOTIE_SETTINGS_FILE_PATHS_FORMAT,
                tootieSettingsFile.getAbsolutePath(),
                allTasksFilePath), tootieSettingsFilePath);
        fileFunctions.appendsStringToFile(NEWLINE, tootieSettingsFilePath);
        String dividerChoiceString = Parsers.dividerChoiceToString(dividerChoice);
        fileFunctions.appendsStringToFile(String.format(TootieFileMsgs.TOOTIE_SETTINGS_USER_PREFERENCES_FORMAT, dividerChoiceString,
                username), tootieSettingsFilePath);
    }

}
