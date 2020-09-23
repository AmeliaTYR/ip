package duke.tootieFunctions;

import duke.exceptions.UsernameCommandInvalidException;
import duke.exceptions.UsernameEmptyException;

import static duke.ui.TextUi.printDivider;

public class SetPreferences {
    public static void setUsername(String userInput, String username)
            throws UsernameCommandInvalidException, UsernameEmptyException {

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

        System.out.println("Hello " + username);
        printDivider();
    }
}
