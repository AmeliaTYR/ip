package duke.ui;

import duke.constants.TootieInputMarkers;
import duke.constants.TootieRegex;

import java.util.Scanner;

public class UserInputHandlers {
    // get user input, ignore comments and blank lines
    public static String getUserInput(Scanner SCANNER) {
        String userInput;
        do {
            userInput = SCANNER.nextLine();
        } while (userInput.matches(TootieRegex.BLANK_STRING_REGEX) || userInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return userInput;
    }

    // echo the userInput for testing
    public static void echoUserInput(String userInput) {
        System.out.println(userInput);
    }
}
