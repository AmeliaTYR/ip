import java.util.Random;
import java.util.Scanner;

public class Duke {
    // prints Tootie logo (text art randomized each run)
    public static void printTootieLogo(){
        String[] logos = new String[4];
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
        logos[3] = "88888888888                888    d8b          \n" +
                "    888                    888    Y8P          \n" +
                "    888                    888                 \n" +
                "    888   .d88b.   .d88b.  888888 888  .d88b.  \n" +
                "    888  d88\"\"88b d88\"\"88b 888    888 d8P  Y8b \n" +
                "    888  888  888 888  888 888    888 88888888 \n" +
                "    888  Y88..88P Y88..88P Y88b.  888 Y8b.     \n" +
                "    888   \"Y88P\"   \"Y88P\"   \"Y888 888  \"Y8888  \n";
        Random rand = new Random(System.currentTimeMillis());
        System.out.println("Hello from\n" + logos[Math.abs(rand.nextInt()%4)]);

    }

    // print the line divider
    public static void printDivider(){
        System.out.println("─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────");
    }

    // prints the hello when starting
    public static void printHelloMessage(){
        String helloGreeting = " Hello! I'm Tootie!\n" +
                " What can I do for you?\n";
        printDivider();
        System.out.print(helloGreeting);
        printDivider();
    }

    // print farewell message
    public static void printFarewellMessage(){
        String farewellGreeting = " Bye! Hope to see you again soon! (◠‿◠✿)\n";
        System.out.print(farewellGreeting);
        printDivider();
    }

    // print all list items with index and check
    public static void printListItems(int numListItems, Task[] taskList){
        for (int i = 0; i < numListItems; i++) {
            System.out.print((i + 1) + ". ");
            if (taskList[i].getIsComplete()){
                System.out.print("[✓]");
            } else {
                System.out.print("[✗]");
            }
            System.out.println(taskList[i].getTaskName());
        }

        //TODO: add all done ʕ•ᴥ•ʔ if all tasks done
    }

    public static int updateTaskStatus(Task[] taskList, String line, int numListItems) {
        int taskNum;
        try {
            taskNum = Integer.parseInt(line.replaceAll("[\\D]", ""));
            if (taskNum > numListItems){
                return -1;
            } else {
                taskList[taskNum - 1].setIsComplete(true);
                return taskNum;
            }
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public static void main(String[] args) {
        String line;
        Scanner in = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int numListItems = 0;
        int taskNum;

        printTootieLogo();
        printHelloMessage();

        line = in.nextLine();
        while (!line.equals("bye")){
            if (line.equals("list")){
                // print items from the list if any
                if (numListItems == 0){
                    printDivider();
                    System.out.println("No tasks found! （´・ω・ `）");
                } else {
                    printDivider();
                    printListItems(numListItems, taskList);
                }
            } else if (line.startsWith("done")) {
                // update isComplete status of that tast
                taskNum = updateTaskStatus(taskList, line, numListItems);
                if (taskNum == -1){
                    printDivider();
                    System.out.println("No such task? (・∧‐)ゞ");
                } else {
                    printDivider();
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println("    [✓] " + taskList[taskNum - 1].getTaskName());
                    System.out.println("(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
                }
            } else {
                // add new task to list
                taskList[numListItems] = new Task(line, false);
                numListItems++;
                printDivider();
                System.out.println("added: " + line);
            }
            printDivider();
            line = in.nextLine();
        }

        printDivider();
        printFarewellMessage();
    }


}
