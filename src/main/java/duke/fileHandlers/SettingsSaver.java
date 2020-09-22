package duke.fileHandlers;

import duke.Duke;
import duke.finalObjects.DividerChoice;
import duke.finalObjects.TootieFileMsgs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsSaver {
    public static final String NEWLINE = System.lineSeparator();


    // save settings to tootieSettings.txt file
    public static void saveTootieSettings(String tootieSettingsFilePath, String allTasksFilePath, String username,DividerChoice dividerChoice) throws IOException {
        File tootieSettingsFile = new File(tootieSettingsFilePath);

        if (tootieSettingsFile.createNewFile()){
            System.out.println("New file created: " + tootieSettingsFile.getName());
        }

        // clear the file
        new FileWriter(tootieSettingsFilePath, false).close();

        fileFunctions.appendsStringToFile(TootieFileMsgs.TOOTIE_SETTINGS_FILE_INSTRUCTIONS_HEADER, tootieSettingsFilePath);
        fileFunctions.appendsStringToFile(NEWLINE, tootieSettingsFilePath);
        fileFunctions.appendsStringToFile(String.format(TootieFileMsgs.TOOTIE_SETTINGS_FILE_PATHS_FORMAT,
                tootieSettingsFile.getAbsolutePath(),
                allTasksFilePath), tootieSettingsFilePath);
        fileFunctions.appendsStringToFile(NEWLINE, tootieSettingsFilePath);
        String dividerChoiceString = dividerChoiceToString(dividerChoice);
        fileFunctions.appendsStringToFile(String.format(TootieFileMsgs.TOOTIE_SETTINGS_USER_PREFERENCES_FORMAT, dividerChoiceString,
                username), tootieSettingsFilePath);

        System.out.println("All settings saved.");
    }

    // convert the dividerChoice into a string to save in tootieSettings.txt
    private static String dividerChoiceToString(DividerChoice dividerChoice) {
        switch (dividerChoice){
        case SIMPLE:
            return "SIMPLE";
        case SPARKLY:
            return "SPARKLY";
        default:
            return "PLAIN";
        }
    }
}
