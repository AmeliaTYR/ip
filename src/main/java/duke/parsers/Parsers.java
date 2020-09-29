package duke.parsers;

import duke.constants.TaskType;
import duke.constants.DividerChoice;

import duke.exceptions.CompletionStatusInvalidException;
import duke.exceptions.DateWronglyFormattedError;
import duke.exceptions.DividerNonexistantException;
import duke.exceptions.InvalidDateException;
import duke.exceptions.MissingParamsException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.exceptions.TotalTasksNumInvalidException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static duke.constants.CommandKeywords.DEADLINE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DONE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.EVENT_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.PLAIN_KEYWORD;
import static duke.constants.CommandKeywords.SIMPLE_KEYWORD;
import static duke.constants.CommandKeywords.SPARKLY_KEYWORD;
import static duke.constants.CommandKeywords.TODO_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.UNDONE_COMMAND_KEYWORD;

import static duke.constants.DateFormats.DEFAULT_COMPLEX_DATE_FORMAT;
import static duke.constants.DateFormats.OUTPUT_DATE_FORMAT;
import static duke.constants.DateFormats.USER_INPUT_DATE_WITHOUT_TIME_FORMAT;
import static duke.constants.DateFormats.USER_INPUT_DATE_WITH_TIME_FORMAT;

import static duke.constants.Tags.TOTAL_TASKS_TAG_FORMAT;

import static duke.parsers.Checks.isDateWithTime;
import static duke.parsers.Checks.isDateWithoutTime;

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
        if (dividerChoiceString.trim().toLowerCase().equals(SPARKLY_KEYWORD)){
            return DividerChoice.SPARKLY;
        } else if (dividerChoiceString.trim().toLowerCase().equals(SIMPLE_KEYWORD)){
            return DividerChoice.SIMPLE;
        } else if (dividerChoiceString.trim().toLowerCase().equals(PLAIN_KEYWORD)){
            return DividerChoice.PLAIN;
        } else {
            return DividerChoice.DOUBLE;
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
     * @param unformattedDate the string containing an unformatted date
     * @return the date object extracted from the unformatted date
     */
    public static Date parseComplexDate(String unformattedDate) throws InvalidDateException {
        Date formattedDate;
        SimpleDateFormat complexDateFormat = new SimpleDateFormat(DEFAULT_COMPLEX_DATE_FORMAT);
        try {
            complexDateFormat.setLenient(false);
            formattedDate = complexDateFormat.parse(unformattedDate.trim());
            return formattedDate;
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }

    /**
     * Parses the default date format and returns a Date object
     *
     * @param unformattedDate a string containing a date object
     * @return date object parsed from the string
     * @throws InvalidDateException date detected was invalid
     */
    public static Date parseSimpleDate(String unformattedDate) throws InvalidDateException {
        Date formattedDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(OUTPUT_DATE_FORMAT);
        try {
            simpleDateFormat.setLenient(false);
            formattedDate = simpleDateFormat.parse(unformattedDate.trim());
            return formattedDate;
        } catch (ParseException e) {
            throw new InvalidDateException();
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
            throw new DividerNonexistantException();
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

        if (fileLine.trim().toLowerCase().equals(SPARKLY_KEYWORD)){
            newDividerChoice = DividerChoice.SPARKLY;
        } else if (fileLine.trim().toLowerCase().equals(SIMPLE_KEYWORD)){
            newDividerChoice = DividerChoice.SIMPLE;
        } else if (fileLine.trim().toLowerCase().equals(PLAIN_KEYWORD)){
            newDividerChoice = DividerChoice.PLAIN;
        } else {
            newDividerChoice = DividerChoice.DOUBLE;
        }
        return newDividerChoice;
    }

    /**
     * parse the date if time is not included in input
     *
     * @param unformattedDate a string containing date information
     * @return return the date object extracted
     */
    public static Date parseDateWithoutTime(String unformattedDate) throws InvalidDateException {
        Date formattedDate;
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat(USER_INPUT_DATE_WITHOUT_TIME_FORMAT);
        try {
            dateWithoutTime.setLenient(false);
            formattedDate = dateWithoutTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }

    /**
     * Parse the date if time is included in input
     *
     * @param unformattedDate a string containing date information
     * @return return the date object extracted
     */
    public static Date parseDateWithTime(String unformattedDate) throws InvalidDateException {
        Date formattedDate;
        SimpleDateFormat dateWithTime = new SimpleDateFormat(USER_INPUT_DATE_WITH_TIME_FORMAT);
        try {
            dateWithTime.setLenient(false);
            formattedDate = dateWithTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }

    /**
     * Parse parameters for double letter tags
     *
     * @param userInput    line read from the console
     * @param parsedParams parameters parsed from the line with the tag as key and argument as value
     * @throws MissingParamsException line has no missing parameters
     */
    public static void parseDoubleCharacterTaggedParamsFromUserInput(String userInput,
                                                                     HashMap<String, String> parsedParams)
            throws MissingParamsException {

        String parsedOption;
        String optionIndicator;

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
            parsedOption = userInput.substring(startPositionIndex + 1, endPositionIndex - 2);
            optionIndicator = userInput.substring(startPositionIndex - 2, startPositionIndex);
            // store the option
            parsedParams.put(optionIndicator, parsedOption);
        }

        // extract the option
        parsedOption = userInput.substring(startPositionIndex + 1);
        optionIndicator = userInput.substring(startPositionIndex - 2, startPositionIndex);
        // store the option
        parsedParams.put(optionIndicator.toLowerCase(), parsedOption);
    }

    /**
     * Parse parameters for single letter tags
     *
     * @param userInput    line read from the console
     * @param parsedParams parameters parsed from the line with the tag as key and argument as value
     * @throws MissingParamsException line has no missing parameters
     */
    public static void parseSingleCharacterTaggedParamsFromUserInput(String userInput,
                                                                     HashMap<String, String> parsedParams)
            throws MissingParamsException {

        String parsedOption;
        String optionIndicator;

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
        parsedParams.put(optionIndicator.toLowerCase(), parsedOption);
    }

    /**
     * Parses the string to return number of tasks as an int
     *
     * @param totalTasks line containing total tasks in the save file
     * @return number of tasks in the save file
     * @throws TotalTasksNumInvalidException number of tasks cannot be parsed
     */
    public static int getNumTasks(String totalTasks) throws TotalTasksNumInvalidException {
        Pattern pattern = Pattern.compile(TOTAL_TASKS_TAG_FORMAT);
        Matcher matcher = pattern.matcher(totalTasks);
        if (matcher.matches()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException exception) {
                throw new TotalTasksNumInvalidException();
            }
        } else {
            throw new TotalTasksNumInvalidException();
        }
    }

    /**
     * Checks the task type for each task in the allTasks.txt file
     *
     * @param fileInput line read from the file containing a saved task
     * @return task type detected
     */
    public static TaskType getTaskType(String fileInput) {
        if (fileInput.trim().startsWith("[T]")){
            return TaskType.TODO;
        } else if (fileInput.trim().startsWith("[D]")){
            return TaskType.DEADLINE;
        } else if (fileInput.trim().startsWith("[E]")){
            return TaskType.EVENT;
        } else {
            return TaskType.INVALID;
        }
    }

    /**
     * convert the dividerChoice into a string to save in tootieSettings.txt
     *
     * @param dividerChoice          divider chosen by user
     * @return return the divider choice as a string
     */
    public static String dividerChoiceToString(DividerChoice dividerChoice) {
        switch (dividerChoice){
        case SIMPLE:
            return "SIMPLE";
        case SPARKLY:
            return "SPARKLY";
        case DOUBLE:
            return "DOUBLE";
        default:
            return "PLAIN";
        }
    }

    /**
     * Parse the task types from the task type param
     *
     * @param taskTypesUserInput the user input for the parameter task types
     * @param taskTypes          the list of task types detected
     */
    public static void parseTaskTypes(String taskTypesUserInput, ArrayList<TaskType> taskTypes) {
        String userInputLowerCase = taskTypesUserInput.toLowerCase();
        if (userInputLowerCase.contains(EVENT_COMMAND_KEYWORD)) {
            taskTypes.add(TaskType.EVENT);
        }
        if (userInputLowerCase.contains(DEADLINE_COMMAND_KEYWORD)) {
            taskTypes.add(TaskType.DEADLINE);
        }
        if (userInputLowerCase.contains(TODO_COMMAND_KEYWORD)) {
            taskTypes.add(TaskType.TODO);
        }
    }

    /**
     * Check if the param argument is done or undone
     *
     * @param componentUserInput component of the user input that contains the argument
     * @return true if it contains done, and false if it contains undone
     * @throws CompletionStatusInvalidException the completion status in the user input is invalid
     */
    public static boolean parseCompletionStatusOption(String componentUserInput)
            throws CompletionStatusInvalidException {
        if (componentUserInput.toLowerCase().trim().equals(DONE_COMMAND_KEYWORD)){
            return true;
        } else if (componentUserInput.toLowerCase().trim().equals(UNDONE_COMMAND_KEYWORD)){
            return false;
        }
        throw new CompletionStatusInvalidException();
    }

    /**
     * Check the date format and try to parse it
     *
     * @param dateUnformatted string containing the date
     * @return parsed Date object
     * @throws DateWronglyFormattedError the string does not contain a correctly formatted date
     */
    public static Date parseDateIfExists(String dateUnformatted) throws DateWronglyFormattedError, InvalidDateException {
        Date dateObj;

        // check format of date
        boolean isDateWithTimeBool = isDateWithTime(dateUnformatted);
        boolean isDateWithoutTimeBool = isDateWithoutTime(dateUnformatted);

        // try to parse date
        if (isDateWithTimeBool) {
            dateObj = parseDateWithTime(dateUnformatted);
        } else if (isDateWithoutTimeBool) {
            dateObj = parseDateWithoutTime(dateUnformatted);
        } else {
            throw new DateWronglyFormattedError();
        }

        return dateObj;
    }
}
