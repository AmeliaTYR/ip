import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

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
                "_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_" +
                "|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
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

    // prints the line divider
    public static void printDivider(){
        System.out.println("─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────");
    }

    // prints the hello when starting
    public static void printHelloMessage(){
        String helloGreeting = "Hello! I'm Tootie!\n" +
                "What can I do for you?\n";
        printDivider();
        System.out.print(helloGreeting);
        printDivider();
    }

    // prints farewell message
    public static void printFarewellMessage(){
        String farewellGreeting = "Bye! Hope to see you again soon! (◠‿◠✿)\n";
        System.out.print(farewellGreeting);
        printDivider();
    }

    // prints all list items with index and check
    public static void printListItems(int numListItems, LinkedList<Task> taskList){
        for (int i = 0; i < numListItems; i++) {
            System.out.print((i + 1) + ". ");
            if (taskList.get(i).getIsComplete()){
                System.out.print("[✓]");
            } else {
                System.out.print("[✗]");
            }
            System.out.println(taskList.get(i).getTaskName());
        }

        // TODO: add "all done ʕ•ᴥ•ʔ" if all tasks done for now
    }

    // TODO: add a help option to print list of commands
    public static void help(){

    }

    // updates tasks isComplete to true, returns -1 if userInput invalid
    public static int updateTaskStatus(LinkedList<Task> taskList, String userInput, int numListItems) {
        int taskNum;
        try {
            taskNum = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (taskNum > numListItems){
                return -1;
            } else {
                taskList.get(taskNum - 1).setComplete(true);
                return taskNum;
            }
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public static void main(String[] args) {
        String userInput;
        Scanner in = new Scanner(System.in);
        LinkedList<Task> taskList = new LinkedList<Task>();
        int numListItems = 0;
        int taskNum;

        printTootieLogo();
        printHelloMessage();

        userInput = in.nextLine();

        // process all user inputs
        while (!userInput.equals("bye")){
            if (userInput.equals("list")){
                // print items from the list if any
                printDivider();
                if (numListItems == 0){
                    System.out.println("No tasks found! （´・ω・ `）");
                } else {
                    printListItems(numListItems, taskList);
                }
            } else if (userInput.startsWith("done")) {
                // update isComplete status of that task
                taskNum = updateTaskStatus(taskList, userInput, numListItems);
                if (taskNum == -1){
                    printDivider();
                    System.out.println("No such task? (・∧‐)ゞ");
                } else {
                    printDivider();
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println("    [✓]" + taskList.get(taskNum - 1).getTaskName());
                    System.out.println("(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
                }
            } else {
                // add new task to list
                taskList.addLast(new Task(userInput, false));
                numListItems++;
                printDivider();
                System.out.println("added: " + userInput);
            }
            printDivider();
            userInput = in.nextLine();
        }

        printDivider();
        printFarewellMessage();
    }


}
