package duke.parsers;

import duke.exceptions.DividerNonexistantException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.constants.DividerChoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parsers {
    // parse the dividerChoice enum from the string
    public static DividerChoice parseDividerChoice(String dividerChoiceString) {
        switch (dividerChoiceString) {
        case "SPARKLY":
            return DividerChoice.SPARKLY;
        case "SIMPLE":
            return DividerChoice.SIMPLE;
        default:
            return DividerChoice.PLAIN;
        }
    }

    // read the value from the setting
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

    // parses the default date format and returns a Date object
    public static Date parseComplexDate(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat complexDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        try {
            formattedDate = complexDateFormat.parse(unformattedDate.trim());
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // parses the default date format and returns a Date object
    public static Date parseSimpleDate(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE d MMM yyyy hh:mm aa");
        try {
            formattedDate = simpleDateFormat.parse(unformattedDate.trim());
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DividerChoice parseLineDividerFromUserInput(String userInput) throws DividerNonexistantException {
        int dividerIndex;
        DividerChoice newDividerChoice = DividerChoice.SPARKLY;

        try {
            dividerIndex = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (dividerIndex > 4 || dividerIndex < 1) {
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
        default:
            newDividerChoice = DividerChoice.SPARKLY;
        }
        return newDividerChoice;
    }

    public static DividerChoice parseLineDividerFromString(String userInput) {
        DividerChoice newDividerChoice;
        switch (userInput){
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

    // parse the date if time is not included in input
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

    // parse the date if time is included in input
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

    public static String pathReplaceIllegalCharacters(String path) {
        return path.replace('\\', '/');
    }
}
