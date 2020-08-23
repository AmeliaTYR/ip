import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    // prints Tootie logo (text art randomized)
    public static void printTootieLogo(){
        String[] logos = new String[3];
        logos[0] = " _____           _   _      \n" +
                "|_   _|         | | (_)     \n" +
                "  | | ___   ___ | |_ _  ___ \n" +
                "  | |/ _ \\ / _ \\| __| |/ _ \\\n" +
                "  | | (_) | (_) | |_| |  __/\n" +
                "  \\_/\\___/ \\___/ \\__|_|\\___|\n";
        logos[1] = "████████╗ ██████╗  ██████╗ ████████╗██╗███████╗\n" +
                "╚══██╔══╝██╔═══██╗██╔═══██╗╚══██╔══╝██║██╔════╝\n" +
                "   ██║   ██║   ██║██║   ██║   ██║   ██║█████╗  \n" +
                "   ██║   ██║   ██║██║   ██║   ██║   ██║██╔══╝  \n" +
                "   ██║   ╚██████╔╝╚██████╔╝   ██║   ██║███████╗\n" +
                "   ╚═╝    ╚═════╝  ╚═════╝    ╚═╝   ╚═╝╚══════╝\n";
        logos[2] = "  _____                    _        _            \n" +
                " |_   _|   ___     ___    | |_     (_)     ___   \n" +
                "   | |    / _ \\   / _ \\   |  _|    | |    / -_)  \n" +
                "  _|_|_   \\___/   \\___/   _\\__|   _|_|_   \\___|  \n" +
                "_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
                "\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' \n";
        Random rand = new Random(System.currentTimeMillis());
        System.out.print("Hello from\n" + logos[rand.nextInt()%3]);
    }

    // print the line divider
    public static void printDivider(){
        System.out.println("____________________________________________________________");
    }

    // prints the hello when starting
    public static void printHelloMessage(){
        String helloGreeting = " Hello! I'm Tootie\n" +
                " What can I do for you?\n";
        printDivider();
        System.out.print(helloGreeting);
        printDivider();
    }

    // print farewell message
    public static void printFarewellMessage(){
        String farewellGreeting = " Bye. Hope to see you again soon!\n";
        System.out.print(farewellGreeting);
        printDivider();
    }

    public static void main(String[] args) {
        String line;
        Scanner in = new Scanner(System.in);

        printTootieLogo();
        printHelloMessage();

        line = in.nextLine();
        while (!line.equals("bye")){
            printDivider();
            System.out.println(line);
            printDivider();
            line = in.nextLine();
        }
        printDivider();
        printFarewellMessage();

    }
}
