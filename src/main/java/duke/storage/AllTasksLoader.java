package duke.storage;

import duke.exceptions.FileEmptyException;
import duke.exceptions.SavedTaskFormatWrongException;
import duke.exceptions.TaskTypeInvalidException;
import duke.exceptions.TotalTasksNumInvalidException;
import duke.constants.*;
import duke.parsers.Parsers;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.ui.TextUi;
import duke.ui.UserInputHandlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllTasksLoader {
    public AllTasksLoader() {
    }

    public static final String NEWLINE = System.lineSeparator();
    
    // attempt to load the all tasks file
    public static void loadAllTasksFile(ArrayList<Task> allTasks, Scanner SCANNER, String allTasksFilePath, AtomicInteger numTasks, AtomicInteger numTasksCompleted) {
        boolean isFileNotRead = true;
        Scanner allTasksFileScanner;

        System.out.println("Loading allTasks.txt save file...");

        while (isFileNotRead){
            try{
                File allTasksFile = fileFunctions.getFileFromFilePath(allTasksFilePath);
                fileFunctions.checkFileExists(allTasksFile);
                allTasksFileScanner = new Scanner(allTasksFile);
                readAllTasksFile(allTasks, allTasksFileScanner, numTasks, numTasksCompleted);
                isFileNotRead = false;
            } catch (FileNotFoundException e) {
                System.out.println("Save file not found? " + TootieSymbols.CONFUSED_EMOTICON);
                boolean isNewFilePathObtained = false;
                while (!isNewFilePathObtained) {
                    ArrayList<String> allTasksFilePathReturn = new ArrayList<String>(1);
                    isNewFilePathObtained = getNewAllTasksFilePath(SCANNER, allTasksFilePathReturn);
                    allTasksFilePath = allTasksFilePathReturn.get(1);
                    TextUi.printDivider();
                }
            } catch (FileEmptyException e) {
                System.out.println("Save file empty? " + TootieSymbols.CONFUSED_EMOTICON);
                isFileNotRead = false;
            }
        }
    }

    // get a new file path from user
    private static boolean getNewAllTasksFilePath(Scanner SCANNER, ArrayList<String> allTasksFilePathReturn) {
        String path = "";

        System.out.println("Options:" + NEWLINE + "(1)Find existing file" + NEWLINE +
                "(2)Manually create directory for file" + NEWLINE + "(3)Automatically create directory and file" +
                NEWLINE + "(type \"1\" \"2\" or \"3\")");

        String response = UserInputHandlers.getUserInput(SCANNER);
        UserInputHandlers.echoUserInput(response);

        if (response.trim().equals("1")){
            System.out.println("Enter the full path to existing file: ");
            path = UserInputHandlers.getUserInput(SCANNER);
        } else if (response.equals("2")){
            // make new file
            System.out.println("Enter the path to new directory location: ");
            path = UserInputHandlers.getUserInput(SCANNER);
            if (path.endsWith("/")) {
                path = path + "data";
            } else {
                path = path + "/data";
            }
            //Creating a File object
            File file = new File(path);
            //Creating the directory
            boolean isFileCreated = file.mkdir();
            if(isFileCreated){
                System.out.println("Directory created successfully " + TootieSymbols.HAPPY_EMOTICON);
                path = path + "/allTasks.txt";
            }else{
                System.out.println("Sorry, could not create specified directory");
                return false;
            }
        } else if (response.equals("3")){
            System.out.println("Automatically creating directory and file");
            path = fileFunctions.autoCreateNewFile(TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH);
            System.out.println(path + TootieSymbols.CONFUSED_EMOTICON);
        } else {
            System.out.println("Response not recognised? " + TootieSymbols.CONFUSED_EMOTICON);
        }

        path = Parsers.pathReplaceIllegalCharacters(path);
        allTasksFilePathReturn.set(0, path);

        // check if the file path is valid
        File allTasksFile = fileFunctions.getFileFromFilePath(path);
        try {
            fileFunctions.checkFileExists(allTasksFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found uwu " + path);
            return false;
        }
        return true;
    }

    // try to read the allTasks.txt file into allTasks array
    private static void readAllTasksFile(ArrayList<Task> allTasks,Scanner allTasksFileScanner, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws FileEmptyException {
            // get total number of tasks stored
            int numTasksInList = 0;
            numTasksInList = getNumTasksInList(allTasksFileScanner);

            SettingsLoader.readFileUntilLineContainsString("Tasks:", allTasksFileScanner);

            addReadTasksToAllTasks(allTasks, allTasksFileScanner, numTasks, numTasksCompleted);

            System.out.println(String.format("%1$d of %2$d tasks read successfully!",
                    numTasks.get(), numTasksInList));
    }

    // read each task and add to the allTasks arraylist
    private static void addReadTasksToAllTasks(ArrayList<Task> allTasks, Scanner FILE_SCANNER, AtomicInteger numTasks, AtomicInteger numTasksCompleted) {
        while (FILE_SCANNER.hasNext()) {
            String fileInput = getFileNextLine(FILE_SCANNER);
            try {
                addTaskToAllTasksArrayList(allTasks,fileInput, numTasks, numTasksCompleted);
            } catch (TaskTypeInvalidException | SavedTaskFormatWrongException e) {
                System.out.println(String.format("Error reading file! Error on line:" + NEWLINE + "%1$s", fileInput));
                break;
            }
        }
    }

    // gets the number of tasks recorded in the allTasks.txt file from the header
    private static int getNumTasksInList(Scanner FILE_SCANNER) throws FileEmptyException {
        int numTasksInList = 0;

        if (FILE_SCANNER.hasNext()) {
            String totalTasks = getFileNextLine(FILE_SCANNER);
            try {
                numTasksInList = getNumTasks(totalTasks);
            } catch (TotalTasksNumInvalidException e) {
                System.out.println("File header invalid!");
            }
        } else {
            throw new FileEmptyException();
        }
        return numTasksInList;
    }

    // parses the string to return number of tasks as an int
    private static int getNumTasks(String totalTasks) throws TotalTasksNumInvalidException {
        Pattern pattern = Pattern.compile("Total tasks: (\\d+)");
        Matcher matcher = pattern.matcher(totalTasks);
        if (matcher.matches()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException exception) {
                throw new TotalTasksNumInvalidException();
            }
        } else {
            throw new TotalTasksNumInvalidException();
        }
    }

    // parses lines from the allTasks.txt file and adds the corresponding task to the allTasks ArrayList
    private static void addTaskToAllTasksArrayList(ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws TaskTypeInvalidException, SavedTaskFormatWrongException {
        TaskType taskType = getTaskType(fileInput);
        switch (taskType) {
        case TODO:
            addToDoToList(allTasks,fileInput, numTasks, numTasksCompleted);
            break;
        case DEADLINE:
            addDeadlineToList(allTasks,fileInput, numTasks, numTasksCompleted);
            break;
        case EVENT:
            addEventToList(allTasks,fileInput, numTasks, numTasksCompleted);
            break;
        default:
            throw new TaskTypeInvalidException();
        }
    }

    // adds a ToDo task read from the file to the allTasks ArrayList
    private static void addToDoToList(ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[T\\]\\[([0-1]{1})\\](.*)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            allTasks.add(new ToDo(matcher.group(2).trim()));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks.get()).setComplete(true);
                numTasksCompleted.getAndIncrement();
            }
            numTasks.getAndIncrement();
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // adds a Deadline task read from the file to the allTasks ArrayList
    private static void addDeadlineToList(ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[D\\]\\[([0-1]{1})\\](.*)\\(by:(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date dueDate = Parsers.parseComplexDate(matcher.group(3));
            allTasks.add(new Deadline(matcher.group(2).trim(),dueDate));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks.get()).setComplete(true);
                numTasksCompleted.getAndIncrement();
            }
            numTasks.getAndIncrement();
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // adds a Event task read from the file to the allTasks ArrayList
    private static void addEventToList(ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[E\\]\\[([0-1]{1})\\](.*)\\(from:(.*)to(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date startDate = Parsers.parseComplexDate(matcher.group(3));
            Date endDate = Parsers.parseComplexDate(matcher.group(4));
            allTasks.add(new Event(matcher.group(2).trim(), startDate, endDate));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks.get()).setComplete(true);
                numTasksCompleted.getAndIncrement();
            }
            numTasks.getAndIncrement();
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // checks the task type for each task in the allTasks.txt file
    private static TaskType getTaskType(String fileInput) {
        if (fileInput.trim().startsWith("[T]")){
            return TaskType.TODO;
        } else if (fileInput.trim().startsWith("[D]")){
            return TaskType.DEADLINE;
        } else if (fileInput.trim().startsWith("[E]")){
            return TaskType.EVENT;
        } else {
            return TaskType.INVALID;
        }
    }

    // read non-blank lines of the file
    public static String getFileNextLine(Scanner FILE_SCANNER) {
        String fileInput;
        do {
            fileInput = FILE_SCANNER.nextLine();
        } while (fileInput.matches(TootieRegex.BLANK_STRING_REGEX)
                || fileInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return fileInput;
    }
}
