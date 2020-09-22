package duke.tootieFunctions;

import duke.Duke;
import duke.exceptions.UsernameCommandInvalidException;
import duke.exceptions.UsernameEmptyException;
import duke.finalObjects.TootieNormalMsgs;
import duke.task.Task;
import duke.task.ToDo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SetPreferences {
    public static void setUsername(String userInput, String username)
            throws UsernameCommandInvalidException, UsernameEmptyException {

        // identify placements
        int usernamePosition = userInput.indexOf("username:");
        if (usernamePosition == -1) {
            throw new UsernameCommandInvalidException();
        }
        String usernameInput = userInput.substring(usernamePosition + 9);

        if (usernameInput.isBlank()) {
            throw new UsernameEmptyException();
        }

        username = usernameInput;
    }
}
