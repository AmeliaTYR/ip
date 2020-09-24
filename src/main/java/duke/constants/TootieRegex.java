package duke.constants;

/**
 * Regex strings used for comparison and format checking
 */
public class TootieRegex {
    /** Regex to detect a blank line */
    public static final String BLANK_STRING_REGEX = "(\r\n|[\n\r\u2028\u2029\u0085])?";

    /** Regex for date formats */
    public static final String DATE_WITHOUT_TIME_REGEX = "([0-9]{2})-([0-9]{2})-([0-9]{4})";
    public static final String DATE_WITH_TIME_REGEX = "([0-9]{2})-([0-9]{2})-([0-9]{4}) ([0-9]{2}):([0-9]{2})";
}
