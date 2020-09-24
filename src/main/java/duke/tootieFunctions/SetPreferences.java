package duke.tootieFunctions;

import duke.exceptions.UsernameCommandInvalidException;
import duke.exceptions.UsernameEmptyException;

/**
 * Functions for the user to set preferences such as
 */
public class SetPreferences {

    /**
     * Extracts the username from the raw user input
     *
     * @param userInput raw user input
     * @return updated username
     * @throws UsernameCommandInvalidException username command incorrectly formatted
     * @throws UsernameEmptyException user name field should not be empty
     */
    public static String setUsername(String userInput) throws UsernameCommandInvalidException,
            UsernameEmptyException {

        // identify placements
        int usernamePosition = userInput.indexOf("username");
        if (usernamePosition == -1) {
            throw new UsernameCommandInvalidException();
        }
        String usernameInput = userInput.substring(usernamePosition + 8).trim();

        if (usernameInput.isBlank()) {
            throw new UsernameEmptyException();
        }

        return usernameInput;
    }
}
