package duke.constants;

/**
 * Date formats for user inputs and file lines
 */
public class DateFormats {
    /** Regex for date formats */
    public static final String DATE_WITHOUT_TIME_REGEX = "([0-9]{2})-([0-9]{2})-([0-9]{4})";
    public static final String DATE_WITH_TIME_REGEX = "([0-9]{2})-([0-9]{2})-([0-9]{4}) ([0-9]{2}):([0-9]{2})";

    /** Date formats for parsers */
    public static final String USER_INPUT_DATE_WITH_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String USER_INPUT_DATE_WITHOUT_TIME_FORMAT = "dd-MM-yyyy";
    public static final String OUTPUT_DATE_FORMAT = "EEE d MMM yyyy hh:mm aa";
    public static final String DEFAULT_COMPLEX_DATE_FORMAT = "EEE MMM d HH:mm:ss zzz yyyy";
}
