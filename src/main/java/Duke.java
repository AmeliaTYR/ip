import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    // prints Duke logo
    public static void printDukeLogo(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.print("Hello from\n" + logo);
    }

    // print the line divider
    public static void printDivider(){
        System.out.println("____________________________________________________________");
    }

    // prints the hello when starting
    public static void printHelloMessage(){
        String helloGreeting = " Hello! I'm Duke\n" +
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

        printDukeLogo();
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
