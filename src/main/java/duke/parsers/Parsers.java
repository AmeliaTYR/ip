package duke.parsers;

import duke.exceptions.DividerNonexistantException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.constants.DividerChoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parse information from user inputs and file lines
 */
public class Parsers {
    /**
     * Parse the dividerChoice enum from the string read from file
     *
     * @param dividerChoiceString file line containing saved divider choice
     * @return
     */
    public static DividerChoice parseDividerChoice(String dividerChoiceString) {
        switch (dividerChoiceString) {
        case "SPARKLY":
            return DividerChoice.SPARKLY;
        case "SIMPLE":
            return DividerChoice.SIMPLE;
        case "DOUBLE":
            return DividerChoice.DOUBLE;
        default:
            return DividerChoice.PLAIN;
        }
    }

    /**
     * Extract the value string from line in settings save file
     *
     * @param fileLine
     * @param objectTitle
     * @return
     * @throws SettingObjectWrongFormatException
     */
    public static String parseFileObject(String fileLine, String objectTitle)
            throws SettingObjectWrongFormatException {

        int settingTitleLength = objectTitle.length();
        String fileObject;

        // identify placements
        int settingObjectPosition = fileLine.indexOf(objectTitle);

        // check if placement is correct
        if (settingObjectPosition == -1) {
            throw new SettingObjectWrongFormatException();
        } else {
            try {
                fileObject = fileLine.substring(settingObjectPosition + settingTitleLength).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                throw new SettingObjectWrongFormatException();
            }
        }
        return fileObject;
    }

    /**
     * Parses the default date format and returns a Date object
     *
     * @param unformattedDate
     * @return
     */
    public static Date parseComplexDate(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        try {
            formattedDate = dateWithoutTime.parse(unformattedDate.trim());
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Extract the desired line divider from user input command
     *
     * @param userInput raw user input command
     * @return
     * @throws DividerNonexistantException
     */
    public static DividerChoice parseLineDividerFromUserInput(String userInput) throws DividerNonexistantException {
        int dividerIndex;
        DividerChoice newDividerChoice = DividerChoice.SPARKLY;

        try {
            dividerIndex = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (dividerIndex > 5 || dividerIndex < 1) {
                throw new DividerNonexistantException();
            }
        } catch (NumberFormatException exception) {
            throw new DividerNonexistantException();
        }

        switch (dividerIndex){
        case 1:
            newDividerChoice = DividerChoice.SPARKLY;
            break;
        case 2:
            newDividerChoice = DividerChoice.PLAIN;
            break;
        case 3:
            newDividerChoice = DividerChoice.SIMPLE;
            break;
        case 4:
            newDividerChoice = DividerChoice.DOUBLE;
            break;
        default:
            newDividerChoice = DividerChoice.DOUBLE;
        }
        return newDividerChoice;
    }

    /**
     * Extract the desired line divider from string
     *
     * @param fileLine raw line read from file
     * @return
     */
    public static DividerChoice parseLineDividerFromString(String fileLine) {
        DividerChoice newDividerChoice;
        switch (fileLine){
        case "PLAIN":
            newDividerChoice = DividerChoice.PLAIN;
            break;
        case "SIMPLE":
            newDividerChoice = DividerChoice.SIMPLE;
            break;
        default:
            newDividerChoice = DividerChoice.SPARKLY;
        }
        return newDividerChoice;
    }

    /**
     * parse the date if time is not included in input
     *
     * @param unformattedDate a string containing date information
     * @return
     */
    public static Date parseDateWithoutTime(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formattedDate = dateWithoutTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse the date if time is included in input
     *
     * @param unformattedDate a string containing date information
     * @return
     */
    public static Date parseDateWithTime(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            formattedDate = dateWithTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Replace '\' with '/' characters in file paths variables
     *
     * @param path a file path with illegal characters
     * @return
     */
    public static String pathReplaceIllegalCharacters(String path) {
        return path.replace('\\', '/');
    }
}
