package duke.tootieFunctions;

import duke.exceptions.UsernameCommandInvalidException;
import duke.exceptions.UsernameEmptyException;

import static duke.ui.Printers.printDivider;

/**
 * Functions for the user to set preferences such as
 */
public class SetPreferences {

    /**
     * Extracts the username from the raw user input
     *
     * @param userInput raw user input
     * @param username  current username
     * @return updated username
     * @throws UsernameCommandInvalidException
     * @throws UsernameEmptyException
     */
    public static String setUsername(String userInput, String username) throws UsernameCommandInvalidException,
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

        username = usernameInput;

        return username;
    }
}
