package duke.storage;

import duke.constants.TaskType;
import duke.constants.TootieFilePaths;
import duke.constants.TootieInputMarkers;
import duke.constants.TootieRegex;
import duke.constants.TootieSymbols;

import duke.exceptions.CancelLoadSavedTasksException;
import duke.exceptions.FileEmptyException;
import duke.exceptions.InvalidDateException;
import duke.exceptions.InvalidDueDateException;
import duke.exceptions.InvalidEndTimeException;
import duke.exceptions.InvalidStartTimeException;
import duke.exceptions.SavedTaskFormatWrongException;
import duke.exceptions.TaskTypeInvalidException;
import duke.exceptions.TotalTasksNumInvalidException;

import duke.parsers.Checks;
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

import static duke.constants.Tags.LOAD_OPTION_1;
import static duke.constants.Tags.LOAD_OPTION_2;
import static duke.constants.Tags.LOAD_OPTION_CANCEL;
import static duke.constants.Tags.NUM_TASKS_TAG;
import static duke.constants.Tags.TASK_COMPLETED_FLAG;

import static duke.constants.TootieNormalMsgs.ALL_TASKS_FILE_NOT_FOUND_MSG;
import static duke.constants.TootieNormalMsgs.ALL_TASKS_LOAD_OPTIONS_MSG;
import static duke.constants.TootieNormalMsgs.AUTO_CREATE_DIRECTORY_AND_FILE_MSG;
import static duke.constants.TootieNormalMsgs.CANCEL_LOAD_ALL_TASKS_OPERATION_MSG;
import static duke.constants.TootieNormalMsgs.ENTER_THE_FULL_PATH_TO_EXISTING_FILE_MSG;
import static duke.constants.TootieNormalMsgs.ERROR_READING_DUEDATE_FROM_FILE_MSG;
import static duke.constants.TootieNormalMsgs.ERROR_READING_ENDTIME_FROM_FILE_MSG;
import static duke.constants.TootieNormalMsgs.ERROR_READING_FILE_LINE_MSG;
import static duke.constants.TootieNormalMsgs.ERROR_READING_STARTTIME_FROM_FILE_MSG;
import static duke.constants.TootieNormalMsgs.FILE_HEADER_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.FILE_PATH_NO_FILE_ERROR_MSG;
import static duke.constants.TootieNormalMsgs.LOADING_ALL_TASKS_MSG;
import static duke.constants.TootieNormalMsgs.RESPONSE_NOT_RECOGNISED_MSG;
import static duke.constants.TootieNormalMsgs.SAVE_FILE_EMPTY_MSG;
import static duke.constants.TootieNormalMsgs.TASKS_EXPECTED_SUMMARY_FORMAT;
import static duke.constants.TootieNormalMsgs.TASKS_READ_SUCCESSFULLY_SUMMARY_FORMAT;
import static duke.constants.TootieNormalMsgs.TOTAL_TASKS_LOADED_SUMMARY_FORMAT;

import static duke.constants.TootieRegex.DEADLINE_FROM_FILE_REGEX;
import static duke.constants.TootieRegex.EVENT_FROM_FILE_REGEX;
import static duke.constants.TootieRegex.TODO_FROM_FILE_REGEX;
import static duke.storage.fileFunctions.autoCreateNewFile;

/**
 * Contains functions to find, check and process the allTasks.txt file
 */
public class AllTasksLoader {
    /**
     * Search for the allTasks.txt file and try to load it
     *
     * @param allTasks          list of all tasks
     * @param SCANNER           scanner to read from the console
     * @param allTasksFilePath  file path for the allTasks.txt file
     * @param numTasks          total number of tasks in the list
     * @param numTasksCompleted total number of tasks in the list completed
     */
    public static void loadAllTasksFile(Boolean inLoadMoreMode, ArrayList<Task> allTasks, Scanner SCANNER,
                                        String allTasksFilePath, AtomicInteger numTasks,
                                        AtomicInteger numTasksCompleted, ArrayList<String> allTasksFilePathReturn) {
        boolean isFileNotRead = true;
        boolean isNewFilePathObtained = true;
        Scanner allTasksFileScanner;

        if (inLoadMoreMode) {
            isNewFilePathObtained = false;
        }

        System.out.println(LOADING_ALL_TASKS_MSG);

        while (isFileNotRead) {
            try {
                if (isNewFilePathObtained) {
                    File allTasksFile = fileFunctions.getFileFromFilePath(allTasksFilePath);
                    fileFunctions.checkFileExists(allTasksFile);
                    allTasksFileScanner = new Scanner(allTasksFile);
                    readAllTasksFile(inLoadMoreMode, allTasks, allTasksFileScanner, numTasks, numTasksCompleted);
                    isFileNotRead = false;
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                if (!inLoadMoreMode) {
                    System.out.println(ALL_TASKS_FILE_NOT_FOUND_MSG);
                }
                isNewFilePathObtained = false;
                while (!isNewFilePathObtained) {
                    try {
                        isNewFilePathObtained = getNewAllTasksFilePath(inLoadMoreMode, SCANNER, allTasksFilePathReturn);
                    } catch (CancelLoadSavedTasksException cancelLoadSavedTasksException) {
                        System.out.println(CANCEL_LOAD_ALL_TASKS_OPERATION_MSG);
                        return;
                    }
                    allTasksFilePath = allTasksFilePathReturn.get(0);
                    Printers.printDivider();
                }
            } catch (FileEmptyException e) {
                System.out.println(SAVE_FILE_EMPTY_MSG);
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
    private static boolean getNewAllTasksFilePath(boolean loadMore, Scanner SCANNER,
                                                  ArrayList<String> allTasksFilePathReturn)
            throws CancelLoadSavedTasksException {
        String path = "";
        String response = "";

        if (!loadMore) {
            System.out.println(ALL_TASKS_LOAD_OPTIONS_MSG);
            response = UserInputHandlers.getUserInput(SCANNER);
        }

        if (loadMore || response.trim().equals(LOAD_OPTION_1)) {
            System.out.println(ENTER_THE_FULL_PATH_TO_EXISTING_FILE_MSG);
            path = UserInputHandlers.getUserInput(SCANNER);
            if (path.trim().toLowerCase().equals(LOAD_OPTION_CANCEL)) {
                throw new CancelLoadSavedTasksException();
            }
        } else if (response.equals(LOAD_OPTION_2)) {
            System.out.println(AUTO_CREATE_DIRECTORY_AND_FILE_MSG);
            path = autoCreateNewFile(TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH);
            System.out.println(path + TootieSymbols.CONFUSED_EMOTICON);
        } else {
            System.out.println(RESPONSE_NOT_RECOGNISED_MSG);
        }

        path = Checks.pathReplaceIllegalCharacters(path);
        allTasksFilePathReturn.add(0, path);

        // check if the file path is valid
        File allTasksFile = fileFunctions.getFileFromFilePath(path);
        try {
            fileFunctions.checkFileExists(allTasksFile);
        } catch (FileNotFoundException e) {
            System.out.println(String.format(FILE_PATH_NO_FILE_ERROR_MSG, path));
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
    private static void readAllTasksFile(boolean inLoadMoreMode, ArrayList<Task> allTasks,
                                         Scanner allTasksFileScanner, AtomicInteger numTasks,
                                         AtomicInteger numTasksCompleted) throws FileEmptyException {
        // get total number of tasks stored
        int numTasksInList;
        AtomicInteger newTasksRead = new AtomicInteger(0);
        numTasksInList = getNumTasksInList(allTasksFileScanner);

        System.out.println(String.format(TASKS_EXPECTED_SUMMARY_FORMAT, numTasksInList, (numTasksInList == 1 ?
                "" : "s")));

        SettingsLoader.readFileUntilLineContainsString(NUM_TASKS_TAG, allTasksFileScanner);

        addReadTasksToAllTasks(newTasksRead, allTasks, allTasksFileScanner, numTasks, numTasksCompleted);

        System.out.printf(String.format(TASKS_READ_SUCCESSFULLY_SUMMARY_FORMAT, newTasksRead.get(),
                (newTasksRead.get() == 1 ? "" : "s")));
        if (inLoadMoreMode) {
            System.out.printf(String.format(TOTAL_TASKS_LOADED_SUMMARY_FORMAT, numTasks.get(),
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
    private static void addReadTasksToAllTasks(AtomicInteger newTasksRead, ArrayList<Task> allTasks,
                                               Scanner FILE_SCANNER, AtomicInteger numTasks,
                                               AtomicInteger numTasksCompleted) {
        while (FILE_SCANNER.hasNext()) {
            String fileInput = getFileNextLine(FILE_SCANNER);
            try {
                addTaskToAllTasksArrayList(newTasksRead, allTasks, fileInput, numTasks, numTasksCompleted);
            } catch (TaskTypeInvalidException | SavedTaskFormatWrongException e) {
                System.out.printf(ERROR_READING_FILE_LINE_MSG, fileInput);
                break;
            } catch (InvalidStartTimeException e) {
                System.out.printf(ERROR_READING_STARTTIME_FROM_FILE_MSG, fileInput);
                break;
            } catch (InvalidDueDateException e) {
                System.out.printf(ERROR_READING_DUEDATE_FROM_FILE_MSG, fileInput);
                break;
            } catch (InvalidEndTimeException e) {
                System.out.printf(ERROR_READING_ENDTIME_FROM_FILE_MSG, fileInput);
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
                System.out.println(FILE_HEADER_INVALID_MSG);
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
    private static void addTaskToAllTasksArrayList(AtomicInteger newTasksRead, ArrayList<Task> allTasks,
                                                   String fileInput, AtomicInteger numTasks,
                                                   AtomicInteger numTasksCompleted) throws TaskTypeInvalidException,
            SavedTaskFormatWrongException, InvalidDueDateException, InvalidEndTimeException, InvalidStartTimeException {
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
    private static void addToDoToList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput,
                                      AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile(TODO_FROM_FILE_REGEX);
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            allTasks.add(new ToDo(matcher.group(2).trim()));
            if (matcher.group(1).equals(TASK_COMPLETED_FLAG)) {
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
    private static void addDeadlineToList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput,
                                          AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException, InvalidDueDateException {
        Pattern pattern = Pattern.compile(DEADLINE_FROM_FILE_REGEX);
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date dueDate;
            try {
                dueDate = Parsers.parseSimpleDate(matcher.group(3).trim());
            } catch (InvalidDateException e) {
                throw new InvalidDueDateException();
            }
            allTasks.add(new Deadline(matcher.group(2).trim(), dueDate));
            if (matcher.group(1).equals(TASK_COMPLETED_FLAG)) {
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
    private static void addEventToList(AtomicInteger newTasksRead, ArrayList<Task> allTasks, String fileInput,
                                       AtomicInteger numTasks, AtomicInteger numTasksCompleted) throws SavedTaskFormatWrongException, InvalidStartTimeException, InvalidEndTimeException {
        Pattern pattern = Pattern.compile(EVENT_FROM_FILE_REGEX);
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
            if (matcher.group(1).equals(TASK_COMPLETED_FLAG)) {
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
        } while (fileInput.matches(TootieRegex.BLANK_STRING_REGEX) ||
                fileInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return fileInput;
    }
}
