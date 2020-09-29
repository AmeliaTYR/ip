package duke.ui;

import duke.constants.TootieInputMarkers;
import duke.constants.TootieRegex;

import java.util.Scanner;

/**
 * For user input detection and echoing
 */
public class UserInputHandlers {
    /**
     * Gets user input, ignore comments and blank lines
     *
     * @param SCANNER Scanner object for console inputs
     * @return raw user input
     */
    public static String getUserInput(Scanner SCANNER) {
        String userInput;
        do {
            userInput = SCANNER.nextLine();
        } while (userInput.matches(TootieRegex.BLANK_STRING_REGEX)
                || userInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return userInput;
    }

    /**
     * Echos the userInput for testing
     *
     * @param userInput raw user input
     */
    public static void echoUserInput(String userInput) {
        System.out.println(userInput);
    }
}
