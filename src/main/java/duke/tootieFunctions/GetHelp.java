package duke.tootieFunctions;

import duke.ui.Printers;

import static duke.constants.CommandKeywords.BYE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DEADLINE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DELETE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DIVIDER_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.DONE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.EVENT_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.FILEPATH_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.FILTER_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.HELP_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.HELP_FULL_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.LIST_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.SAVE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.TODO_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.UNDONE_COMMAND_KEYWORD;
import static duke.constants.CommandKeywords.USERNAME_COMMAND_KEYWORD;

import static duke.constants.TootieNormalMsgs.BYE_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.DEADLINE_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.DELETE_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.DIVIDER_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.DONE_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.EVENT_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.FILEPATH_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.FILTER_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.HELP_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.HELP_FULL_COMMAND_TEXT;
import static duke.constants.TootieNormalMsgs.LIST_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.LOAD_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.NO_SUCH_COMMAND_IN_LIST_ERROR_MSG;
import static duke.constants.TootieNormalMsgs.SAVE_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.TODO_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.UNDONE_COMMAND_DESCRIPTION;
import static duke.constants.TootieNormalMsgs.USERNAME_COMMAND_DESCRIPTION;

/**
 * Functions for the user get command descriptions
 */
public class GetHelp {

    /**
     * Get command description for a specific command or the whole command list
     * @param userInput user input command
     */
    static void getHelp(String userInput) {
        // identify placements
        int helpCommandArgsPosition = userInput.indexOf(HELP_COMMAND_KEYWORD);

        String helpCommandArgs = userInput.substring(helpCommandArgsPosition + HELP_COMMAND_KEYWORD.length()).trim();

        if (helpCommandArgs.toLowerCase().trim().startsWith(HELP_COMMAND_KEYWORD)) {
            System.out.println(HELP_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(TODO_COMMAND_KEYWORD)) {
            System.out.println(TODO_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(DEADLINE_COMMAND_KEYWORD)) {
            System.out.println(DEADLINE_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(EVENT_COMMAND_KEYWORD)) {
            System.out.println(EVENT_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(LIST_COMMAND_KEYWORD)) {
            System.out.println(LIST_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(DONE_COMMAND_KEYWORD)) {
            System.out.println(DONE_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(BYE_COMMAND_KEYWORD)) {
            System.out.println(BYE_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(DELETE_COMMAND_KEYWORD)) {
            System.out.println(DELETE_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(UNDONE_COMMAND_KEYWORD)) {
            System.out.println(UNDONE_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(DIVIDER_COMMAND_KEYWORD)) {
            System.out.println(DIVIDER_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(USERNAME_COMMAND_KEYWORD)) {
            System.out.println(USERNAME_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(FILTER_COMMAND_KEYWORD)) {
            System.out.println(FILTER_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(SAVE_COMMAND_KEYWORD)) {
            System.out.println(SAVE_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().trim().contains(FILEPATH_COMMAND_KEYWORD)) {
            System.out.println(FILEPATH_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().contains(LOAD_COMMAND_DESCRIPTION)) {
            System.out.println(LOAD_COMMAND_DESCRIPTION);
        } else if (helpCommandArgs.toLowerCase().contains(HELP_FULL_COMMAND_KEYWORD)) {
            System.out.println(HELP_FULL_COMMAND_TEXT);
        } else if (helpCommandArgs.isBlank()) {
            Printers.printHelpInfo();
        } else {
            System.out.println(NO_SUCH_COMMAND_IN_LIST_ERROR_MSG);
        }
    }
}
