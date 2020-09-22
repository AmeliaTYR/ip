package duke.tootieFunctions;

import duke.finalObjects.*;

import java.util.Random;

public class UI {
    public static String currentLineDivider = TootieSymbols.PLAIN_TEXT_DIVIDER;

    // prints the line divider
    public static void printDivider() {
        System.out.println(currentLineDivider);
    }

    // prints the hello when starting
    public static void printHelloMessage() {
        printDivider();
        System.out.print(TootieNormalMsgs.HELLO_GREETING);
        printDivider();
    }

    // prints farewell message
    public static void printFarewellMessage() {
        System.out.print(TootieNormalMsgs.FAREWELL_GREETING);
    }

    // prints Tootie logo (text art randomized each run)
    public static void printTootieLogo() {
        String[] logos = new String[TootieConstants.NUM_LOGOS_AVAILABLE];
        logos[0] = TootieSymbols.SIMPLE_TOOTIE_LOGO;
        logos[1] = TootieSymbols.BLOCKY_TOOTIE_LOGO;
        logos[2] = TootieSymbols.TRAIN_THEME_TOOTIE_LOGO;
        logos[3] = TootieSymbols.THICK_TOOTIE_LOGO;
        Random rand = new Random(System.currentTimeMillis());
        System.out.println(String.format(TootieNormalMsgs.LOGO_PRINT_FORMAT, logos[Math.abs(rand.nextInt() % 4)]));
    }

    // print the message when command is not understood
    public static void printConfusedMessage() {
        System.out.println(TootieErrorMsgs.COMMAND_NOT_FOUND_MSG);
        System.out.println(TootieErrorMsgs.SUGGEST_HELP_OPTION_MSG);
    }

    // print list of commands and example usage
    public static void printHelpInfo() {
        System.out.println(TootieNormalMsgs.HELP_INFO_MSG);
    }
}
