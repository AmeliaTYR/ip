package duke.parsers;

import duke.exceptions.DividerNonexistantException;
import duke.exceptions.MissingFilterOptionsException;
import duke.exceptions.MissingParamsException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.constants.DividerChoice;
import duke.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parse information from user inputs and file lines
 */
public class Parsers {
    /**
     * Parse the dividerChoice enum from the string read from file
     *
     * @param dividerChoiceString file line containing saved divider choice
     * @return return the DividerChoice enum from a given string
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
     * @param fileLine    a line read from the file
     * @param objectTitle the string indicating the type of object
     * @return returns the setting extracted from line in the settings file
     * @throws SettingObjectWrongFormatException the linel in the settings file was wrongly formatted
     */
    public static String parseFileObject(String fileLine, String objectTitle) throws SettingObjectWrongFormatException {

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
     * @param unformattedDate the string containing an unformatted date
     * @return the date object extracted from the unformatted date
     */
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

    /**
     * Extract the desired line divider from user input command
     *
     * @param userInput raw user input command
     * @return return DividerChoice extracted from the user input
     * @throws DividerNonexistantException an invalid divider choice was indicated
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

        switch (dividerIndex) {
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
     * @return return the DividerChoice enum extracted from the file
     */
    public static DividerChoice parseLineDividerFromString(String fileLine) {
        DividerChoice newDividerChoice;
        switch (fileLine) {
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
     * @return return the date object extracted
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
     * @return return the date object extracted
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
     * @return return the path without illegal characters
     */
    public static String pathReplaceIllegalCharacters(String path) {
        return path.replace('\\', '/');
    }

    public static void parseDoubleCharacterTaggedParamsFromUserInput(String userInput,
                                                                     HashMap<String, String> filterOptions)
            throws MissingFilterOptionsException {

        String parsedOption = "";
        String optionIndicator = "";

        int startPositionIndex = 0;
        int endPositionIndex = 0;

        // clear filter options
        filterOptions.clear();

        if (!userInput.contains("/")) {
            throw new MissingFilterOptionsException();
        }

        while (userInput.indexOf("/", startPositionIndex) != -1) {
            // identify placement
            startPositionIndex = userInput.indexOf("/", startPositionIndex + 1);
            endPositionIndex = userInput.indexOf("/", startPositionIndex + 1);
            // if reached end of string
            if (endPositionIndex == -1) {
                break;
            }
            // extract the option
            parsedOption = userInput.substring(startPositionIndex + 1, endPositionIndex - 2);
            optionIndicator = userInput.substring(startPositionIndex - 2, startPositionIndex);
            // store the option
            filterOptions.put(optionIndicator, parsedOption);
        }

        // extract the option
        parsedOption = userInput.substring(startPositionIndex + 1);
        optionIndicator = userInput.substring(startPositionIndex - 2, startPositionIndex);
        // store the option
        filterOptions.put(optionIndicator, parsedOption);
    }

    public static void parseSingleCharacterTaggedParamsFromUserInput(String userInput,
                                                                     HashMap<String, String> parsedParams)
            throws MissingParamsException {

        String parsedOption = "";
        String optionIndicator = "";

        int startPositionIndex = 0;
        int endPositionIndex = 0;

        // clear filter options
        parsedParams.clear();

        if (!userInput.contains("/")) {
            throw new MissingParamsException();
        }

        while (userInput.indexOf("/", startPositionIndex) != -1) {
            // identify placement
            startPositionIndex = userInput.indexOf("/", startPositionIndex + 1);
            endPositionIndex = userInput.indexOf("/", startPositionIndex + 1);
            // if reached end of string
            if (endPositionIndex == -1) {
                break;
            }
            // extract the option
            parsedOption = userInput.substring(startPositionIndex + 1, endPositionIndex - 1);
            optionIndicator = userInput.substring(startPositionIndex - 1, startPositionIndex);
            // store the option
            parsedParams.put(optionIndicator, parsedOption);
        }

        // extract the option
        parsedOption = userInput.substring(startPositionIndex + 1);
        optionIndicator = userInput.substring(startPositionIndex - 1, startPositionIndex);
        // store the option
        parsedParams.put(optionIndicator, parsedOption);
    }
}
