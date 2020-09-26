package duke.storage;

import duke.constants.TaskType;
import duke.constants.TootieFilePaths;
import duke.constants.TootieInputMarkers;
import duke.constants.TootieRegex;
import duke.constants.TootieSymbols;

import duke.exceptions.*;

import duke.parsers.Parsers;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import duke.ui.Printers;
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

    /**
     * Search for the allTasks.txt file and try to load it
     *
     * @param allTasks          list of all tasks
     * @param SCANNER           scanner to read from the console
     * @param allTasksFilePath  file path for the allTasks.txt file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks in the list completed
     */
    public static void loadAllTasksFile(Boolean loadMore, ArrayList<Task> allTasks, Scanner SCANNER, String allTasksFilePath, AtomicInteger numTasks, AtomicInteger numTasksCompleted) {
        boolean isFileNotRead = true;
        boolean isNewFilePathObtained = true;
        Scanner allTasksFileScanner;

        if (loadMore){
            isNewFilePathObtained = false;
        }

        System.out.println("Loading allTasks.txt save file...");

        while (isFileNotRead){
            try{
                if (isNewFilePathObtained) {
                    File allTasksFile = fileFunctions.getFileFromFilePath(allTasksFilePath);
                    fileFunctions.checkFileExists(allTasksFile);
                    allTasksFileScanner = new Scanner(allTasksFile);
                    readAllTasksFile(loadMore, allTasks, allTasksFileScanner, numTasks, numTasksCompleted);
                    isFileNotRead = false;
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                if (!loadMore){
                    System.out.println("Save file not found? " + TootieSymbols.CONFUSED_EMOTICON);
                }
                isNewFilePathObtained = false;
                while (!isNewFilePathObtained) {
                    ArrayList<String> allTasksFilePathReturn = new ArrayList<>(1);
                    try {
                        isNewFilePathObtained = getNewAllTasksFilePath(loadMore, SCANNER, allTasksFilePathReturn);
                    } catch (CancelLoadSavedTasksException cancelLoadSavedTasksException) {
                        System.out.println("Cancelled \"load save file\" operation");
                        return;
                    }
                    allTasksFilePath = allTasksFilePathReturn.get(0);
                    Printers.printDivider();
                }
            } catch (FileEmptyException e) {
                System.out.println("Save file empty? " + TootieSymbols.CONFUSED_EMOTICON);
                isFileNotRead = false;
            }
        }
    }

    /**
     * Get a new file path from user
     *
     * @param SCANNER                scanner to read lines from the console
     * @param allTasksFilePathReturn file path for the allTasks.txt file
     * @return return true if a new file path for the allTasks.txt file was obtained
     */
    private static boolean getNewAllTasksFilePath(boolean loadMore, Scanner SCANNER, ArrayList<String> allTasksFilePathReturn) throws CancelLoadSavedTasksException {
        String path = "";
        String response = "";

        if (!loadMore) {
            System.out.println("Options:" + NEWLINE + "(1)Find existing file" + NEWLINE + "(2)Manually create directory for file" + NEWLINE + "(3)Automatically create directory and file" + NEWLINE + "(type \"1\" \"2\" or \"3\")");
            response = UserInputHandlers.getUserInput(SCANNER);
            UserInputHandlers.echoUserInput(response);
        }

        if (loadMore || response.trim().equals("1")){
            if (loadMore){
                System.out.println("Enter the full path to existing file (type \"cancel\" to cancel):");
                path = UserInputHandlers.getUserInput(SCANNER);
                if (path.trim().toLowerCase().equals("cancel")){
                    throw new CancelLoadSavedTasksException();
                }
            } else {
                System.out.println("Enter the full path to existing file: ");
                path = UserInputHandlers.getUserInput(SCANNER);
            }

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
        allTasksFilePathReturn.add(0, path);

        // check if the file path is valid
        File allTasksFile = fileFunctions.getFileFromFilePath(path);
        try {
            fileFunctions.checkFileExists(allTasksFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found uwu" + NEWLINE + "Failed to read from: " + path);
            return false;
        }
        return true;
    }



    /**
     * Try to read the allTasks.txt file into allTasks array
     *
     * @param allTasks            complete list of all tasks
     * @param allTasksFileScanner scanner to read lines from teh allTasks.txt file
     * @param numTasks            total number of tasks in the list
     * @param numTasksCompleted   total number of tasks in the list completed
     * @throws FileEmptyException file found was empty
     */
    private static void readAllTasksFile(boolean loadMore, ArrayList<Task> allTasks,Scanner allTasksFileScanner, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws FileEmptyException {
            // get total number of tasks stored
            int numTasksInList;
            AtomicInteger newTasksRead = new AtomicInteger(0);
            numTasksInList = getNumTasksInList(allTasksFileScanner);

            System.out.println(String.format("%1$s task%2$s expected from file.",
                    numTasksInList,
                    (numTasksInList == 1 ? "" : "s")));

            SettingsLoader.readFileUntilLineContainsString("Tasks:", allTasksFileScanner);

            addReadTasksToAllTasks(newTasksRead, allTasks, allTasksFileScanner, numTasks, numTasksCompleted);

        System.out.printf(String.format("%1$d task%2$s read successfully!" + NEWLINE, newTasksRead.get(),
                (newTasksRead.get() == 1 ? "" : "s")));
        if (loadMore){
            System.out.printf(String.format("Total task%2$s in list: %1$d" + NEWLINE, numTasks.get(),
                    (numTasks.get() == 1 ? "" : "s")));
        }
    }

    /**
     * Read each task and add to the allTasks arraylist
     *
     * @param allTasks          list of all tasks
     * @param FILE_SCANNER      scanner to read lines from the file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted number of tasks in the list completed
     */
    private static void addReadTasksToAllTasks(AtomicInteger newTasksRead, ArrayList<Task> allTasks, Scanner FILE_SCANNER, AtomicInteger numTasks, AtomicInteger numTasksCompleted) {
        while (FILE_SCANNER.hasNext()) {
            String fileInput = getFileNextLine(FILE_SCANNER);
            try {
                addTaskToAllTasksArrayList(newTasksRead, allTasks,fileInput, numTasks, numTasksCompleted);
            } catch (TaskTypeInvalidException | SavedTaskFormatWrongException e) {
                System.out.printf("Error reading file! Error on line:" + NEWLINE + "%1$s%n", fileInput);
                break;
            } catch (InvalidStartTimeException e) {
                System.out.printf("Error reading start time from line:" + NEWLINE + "  %1$s%n", fileInput);
                break;
            } catch (InvalidDueDateException e) {
                System.out.printf("Error reading due date from line:" + NEWLINE + "  %1$s%n", fileInput);
                break;
            } catch (InvalidEndTimeException e) {
                System.out.printf("Error reading end time from line:" + NEWLINE + "  %1$s%n", fileInput);
                break;
            }
        }
    }

    /**
     * Gets the number of tasks recorded in the allTasks.txt file from the header
     *
     * @param FILE_SCANNER scanner to read lines from the file
     * @return number of tasks in the list as read from the file
     * @throws FileEmptyException file found was empty
     */
    private static int getNumTasksInList(Scanner FILE_SCANNER) throws FileEmptyException {
        int numTasksInList = 0;

        if (FILE_SCANNER.hasNext()) {
            String totalTasks = getFileNextLine(FILE_SCANNER);
            try {
                numTasksInList = Parsers.getNumTasks(totalTasks);
            } catch (TotalTasksNumInvalidException e) {
                System.out.println("File header invalid!");
            }
        } else {
            throw new FileEmptyException();
        }
        return numTasksInList;
    }

    /**
     * Parses lines from the allTasks.txt file and adds the corresponding task to the allTasks ArrayList
     *
     * @param allTasks          complete list of all tasks
     * @param fileInput         line read from the file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted number of tasks in the list completed
     * @throws TaskTypeInvalidException      task saved in the save file has an invalid Task type
     * @throws SavedTaskFormatWrongException task saved in the save file was wrongly formatted
     * @throws InvalidDueDateException       due date of a deadline task was invalid
     * @throws InvalidEndTimeException       end time of an event task is invalid
     * @throws InvalidStartTimeException     start time of an event task is invalid
     */
    private static void addTaskToAllTasksArrayList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws TaskTypeInvalidException, SavedTaskFormatWrongException, InvalidDueDateException, InvalidEndTimeException, InvalidStartTimeException {
        TaskType taskType = Parsers.getTaskType(fileInput);
        switch (taskType) {
        case TODO:
            addToDoToList(newTasksRead, allTasks, fileInput, numTasks, numTasksCompleted);
            break;
        case DEADLINE:
            addDeadlineToList(newTasksRead, allTasks, fileInput, numTasks, numTasksCompleted);
            break;
        case EVENT:
            addEventToList(newTasksRead, allTasks, fileInput, numTasks, numTasksCompleted);
            break;
        default:
            throw new TaskTypeInvalidException();
        }
    }

    /**
     * Adds a ToDo task read from the file to the allTasks ArrayList
     *
     * @param allTasks          list of all tasks
     * @param fileInput         line read from the file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted number of tasks completed
     * @throws SavedTaskFormatWrongException due date of a deadline task was invalid
     */
    private static void addToDoToList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[T\\]\\[([0-1])\\](.*)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            allTasks.add(new ToDo(matcher.group(2).trim()));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks.get()).setComplete(true);
                numTasksCompleted.getAndIncrement();
            }
            numTasks.getAndIncrement();
            newTasksRead.getAndIncrement();
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    /**
     * Adds a Deadline task read from the file to the allTasks ArrayList
     *
     * @param allTasks          list of all tasks
     * @param fileInput         line read from the file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted number of tasks completed
     * @throws SavedTaskFormatWrongException saved task in the list is of the wrong format
     * @throws InvalidDueDateException       due date of deadline task was wrong format
     */
    private static void addDeadlineToList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException, InvalidDueDateException {
        Pattern pattern = Pattern.compile("\\[D\\]\\[([0-1])\\](.*)\\(by:(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date dueDate;
            try {
                dueDate = Parsers.parseSimpleDate(matcher.group(3).trim());
            } catch (InvalidDateException e) {
                throw new InvalidDueDateException();
            }
            allTasks.add(new Deadline(matcher.group(2).trim(),dueDate));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks.get()).setComplete(true);
                numTasksCompleted.getAndIncrement();
            }
            numTasks.getAndIncrement();
            newTasksRead.getAndIncrement();
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    /**
     * Adds a Event task read from the file to the allTasks ArrayList
     *
     * @param allTasks          list of all tasks
     * @param fileInput         line read from the file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted number of tasks completed
     * @throws SavedTaskFormatWrongException saved task in the list is of the wrong format
     * @throws InvalidStartTimeException     start time of an event task is invalid
     * @throws InvalidEndTimeException       end time of an event task is invalid
     */
    private static void addEventToList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput, AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException, InvalidStartTimeException, InvalidEndTimeException {
        Pattern pattern = Pattern.compile("\\[E\\]\\[([0-1])\\](.*)\\(from:(.*)to(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date startDate;
            try {
                startDate = Parsers.parseSimpleDate(matcher.group(3).trim());
            } catch (InvalidDateException e) {
                throw new InvalidStartTimeException();
            }
            Date endDate;
            try {
                endDate = Parsers.parseSimpleDate(matcher.group(4).trim());
            } catch (InvalidDateException e) {
                throw new InvalidEndTimeException();
            }
            allTasks.add(new Event(matcher.group(2).trim(), startDate, endDate));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks.get()).setComplete(true);
                numTasksCompleted.getAndIncrement();
            }
            numTasks.getAndIncrement();
            newTasksRead.getAndIncrement();
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    /**
     * Read non-blank lines of the file
     *
     * @param FILE_SCANNER scanner to read through lines in the file
     * @return return a non blank line read from the file
     */
    public static String getFileNextLine(Scanner FILE_SCANNER) {
        String fileInput;
        do {
            fileInput = FILE_SCANNER.nextLine();
        } while (fileInput.matches(TootieRegex.BLANK_STRING_REGEX)
                || fileInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return fileInput;
    }
}
