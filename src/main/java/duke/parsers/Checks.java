package duke.parsers;

import duke.exceptions.DateAfterPreceedsDateBefore;
import duke.exceptions.DateBeforeMatchesAfterException;

import java.util.Date;

public class Checks {
    /**
     * /check if the date entered is correctly formatted with a date but no time
     *
     * @param timeUnformmated a string containing a date without the time included
     * @return true if it matches the correct format
     */
    public static boolean isDateWithoutTime(String timeUnformmated) {
        return timeUnformmated.matches(duke.constants.DateFormats.DATE_WITHOUT_TIME_REGEX);
    }

    /**
     * Check if the date entered is correctly formatted with a date and time
     *
     * @param timeUnformmated a string containing a date with the time included
     * @return true if it matches the correct format
     */
    public static boolean isDateWithTime(String timeUnformmated) {
        return timeUnformmated.matches(duke.constants.DateFormats.DATE_WITH_TIME_REGEX);
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

    /**
     * Check if the date after comes before the date before
     *
     * @param containsDateBeforeParam the date for before was detected in the command
     * @param containsDateAfterParam  the date for after was detected in the command
     * @param dateBefore              the date for before
     * @param dateAfter               the date for after
     * @throws DateAfterPreceedsDateBefore the date for after comes before the date for before
     */
    public static void checkIfDateAfterPreceedsBefore(boolean containsDateBeforeParam, boolean containsDateAfterParam
            , Date dateBefore, Date dateAfter)
            throws DateAfterPreceedsDateBefore {
        if (containsDateAfterParam && containsDateBeforeParam) {
            if (dateBefore.compareTo(dateAfter) < 0) {
                throw new DateAfterPreceedsDateBefore();
            }
        }
    }

    /**
     * Check if the Date for Before is the same Date as the date for after
     *
     * @param containsDateBeforeParam the date for before was found
     * @param containsDateAfterParam  the date for after was found
     * @param dateBefore              the date for before
     * @param dateAfter               the date for after
     * @throws DateBeforeMatchesAfterException the date for before is the same as the date for after
     */
    public static void checkIfBeforeAndAfterDatesMatch(boolean containsDateBeforeParam, boolean containsDateAfterParam, Date dateBefore, Date dateAfter)
            throws DateBeforeMatchesAfterException {
        if (containsDateAfterParam && containsDateBeforeParam) {
            if (dateBefore.compareTo(dateAfter) == 0) {
                throw new DateBeforeMatchesAfterException();
            }
        }
    }
}
