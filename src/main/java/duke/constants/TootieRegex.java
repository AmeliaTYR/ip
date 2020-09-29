package duke.constants;

/**
 * Regex strings used for comparison and format checking
 */
public class TootieRegex {
    /** Regex to detect a blank line */
    public static final String BLANK_STRING_REGEX = "(\r\n|[\n\r\u2028\u2029\u0085])?";

    /** Regex for loaded tasks */
    public static final String EVENT_FROM_FILE_REGEX = "\\[E\\]\\[([0-1])\\](.*)\\(from:(.*)to(.*)\\)";
    public static final String DEADLINE_FROM_FILE_REGEX = "\\[D\\]\\[([0-1])\\](.*)\\(by:(.*)\\)";
    public static final String TODO_FROM_FILE_REGEX = "\\[T\\]\\[([0-1])\\](.*)";
}
