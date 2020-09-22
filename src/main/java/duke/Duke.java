package duke;

import duke.exceptions.DeadlineInputWrongFormatException;
import duke.exceptions.DueDateWrongFormatException;
import duke.exceptions.EndTimeBeforeStartTimeException;
import duke.exceptions.EndTimeWrongFormatException;
import duke.exceptions.EventInputWrongFormatException;
import duke.exceptions.FileEmptyException;
import duke.exceptions.InvalidDueDateException;
import duke.exceptions.InvalidEndTimeException;
import duke.exceptions.InvalidStartDateException;
import duke.exceptions.SavedTaskFormatWrongException;
import duke.exceptions.SettingObjectWrongFormatException;
import duke.exceptions.StartTimeWrongFormatException;
import duke.exceptions.TaskNameEmptyException;
import duke.exceptions.TaskNonexistantException;
import duke.exceptions.TaskTypeInvalidException;
import duke.exceptions.TasklistEmptyException;
import duke.exceptions.ToDoInputWrongFormatException;
import duke.exceptions.TotalTasksNumInvalidException;

import duke.finalObjects.CommandType;
import duke.finalObjects.DividerChoice;
import duke.finalObjects.TaskType;
import duke.finalObjects.TootieErrorMsgs;
import duke.finalObjects.TootieFileMsgs;
import duke.finalObjects.TootieFilePaths;
import duke.finalObjects.TootieInputMarkers;
import duke.finalObjects.TootieNormalMsgs;
import duke.finalObjects.TootieRegex;
import duke.finalObjects.TootieSymbols;

import duke.task.ToDo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import duke.tootieFunctions.TextUi;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duke {

    private static TextUi ui;

    private static final Scanner SCANNER = new Scanner(System.in);

    public static final String NEWLINE = System.lineSeparator();

    // array containing all tasks the user has input
    private static ArrayList<Task> allTasks = new ArrayList<>();

    // number of Tasks in the allTasks array
    private static int numTasks = 0;
    private static int numTasksCompleted = 0;

    // settings set to defaults
    private static String tootieSettingsFilePath = TootieFilePaths.DEFAULT_TOOTIE_SETTINGS_FILE_PATH;
    private static String allTasksFilePath = TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH;
    private static DividerChoice dividerChoice = DividerChoice.SPARKLY;
    private static String username = "user";

    public static void main(String[] args) {
        String userInput;
        CommandType commandType = CommandType.START;

        ui.printTootieLogo();
        ui.printHelloMessage();

        loadTootieSettingsFile();

        loadAllTasksFile();

        ui.printDivider();

        // process commands
        while(commandType != CommandType.BYE){
            userInput = getUserInput();
            echoUserInput(userInput);
            ui.printDivider();
            commandType = extractCommandType(userInput);
            executeCommand(commandType, userInput, allTasks, allTasksFilePath);
            ui.printDivider();
        }

        try {
            saveAllTasks(allTasks, allTasksFilePath);
            saveTootieSettings(tootieSettingsFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // attempt to load and store the tootieSettings.txt variables
    private static void loadTootieSettingsFile() {
        System.out.println("Loading tootieSettings.txt save file...");

        try{
            File tootieSettingsFile = getFileFromFilePath(tootieSettingsFilePath);
            checkFileExists(tootieSettingsFile);
            readTootieSettingsFile(tootieSettingsFile);
        } catch (FileNotFoundException e) {
            System.out.println("tootieSettings.txt save file not found" + NEWLINE + "Creating new file");
            tootieSettingsFilePath = autoCreateNewFile(TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH);
        } catch (FileEmptyException e) {
            System.out.println("tootieSettings.txt save file empty" + NEWLINE + "No previous settings loaded");
        }
    }

    // check if a file exists
    private static void checkFileExists(File tootieSettingsFile) throws FileNotFoundException {
        if (!tootieSettingsFile.exists()){
            throw new FileNotFoundException();
        }
    }

    // read and parse all the settings from the tootieSettings.txt file
    private static void readTootieSettingsFile(File tootieSettingsFile) throws FileEmptyException,
            FileNotFoundException {

        Scanner SETTINGS_FILE_SCANNER = new Scanner(tootieSettingsFile);
        String fileLine = "";

        if (!SETTINGS_FILE_SCANNER.hasNext()) {
            throw new FileEmptyException();
        }

        try {
            String parsedString;

            fileLine = readFileUntilLineContainsString("+ Tootie settings:", SETTINGS_FILE_SCANNER);
            parsedString = parseFileObject(fileLine, "+ Tootie settings:");
            if (!parsedString.isBlank()){
                tootieSettingsFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString("+ All Tasks:", SETTINGS_FILE_SCANNER);
            parsedString = parseFileObject(fileLine, "+ All Tasks:");
            if (!parsedString.isBlank()){
                allTasksFilePath = parsedString;
            }

            fileLine = readFileUntilLineContainsString("+ Divider choice:", SETTINGS_FILE_SCANNER);
            parsedString = parseFileObject(fileLine, "+ Divider choice:");
            dividerChoice = parseDividerChoice(parsedString);

            fileLine = readFileUntilLineContainsString("+ Username:", SETTINGS_FILE_SCANNER);
            parsedString = parseFileObject(fileLine, "+ Username:");
            if (!parsedString.isBlank()){
                username = parsedString;
            }
        } catch (SettingObjectWrongFormatException e) {
            System.out.println(String.format("Error reading settings file! Error on line:" + NEWLINE + "%1$s",
                    fileLine));
        }
    }

    // parse the dividerChoice enum from the string
    private static DividerChoice parseDividerChoice(String dividerChoiceString) {
        switch (dividerChoiceString) {
        case "SPARKLY":
            return DividerChoice.SPARKLY;
        case "SIMPLE":
            return DividerChoice.SIMPLE;
        default:
            return DividerChoice.PLAIN;
        }
    }

    // read the value from the setting
    private static String parseFileObject(String fileLine, String objectTitle)
            throws SettingObjectWrongFormatException {

        int settingTitleLength = objectTitle.length();
        String fileObject;

        // identify placements
        int settingObjectPosition = fileLine.indexOf(objectTitle);

        // check if placement is correct
        if (settingObjectPosition == -1) {
            throw new SettingObjectWrongFormatException();
        } else {
            try {
                fileObject = fileLine.substring(settingObjectPosition + settingTitleLength).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                throw new SettingObjectWrongFormatException();
            }
        }
        return fileObject;
    }

    // continue reading through a file until a specific string is found
    private static String readFileUntilLineContainsString(String stringSearched,
                                                          Scanner fileScanner) {
        String fileInput = "";
        // read each setting and return the variables accordingly
        while (fileScanner.hasNext()) {
            fileInput = getFileNextLine(fileScanner);
            if (fileInput.contains(stringSearched)) {
                break;
            }
        }

        return fileInput;
    }

    // create a new file at the specified file path
    private static String autoCreateNewFile(String filePath) {
        File newFile = new File(filePath);
        System.out.println("Auto creating new file using path: " + filePath);
        try {
            if (newFile.createNewFile()){
                System.out.println("File created: " + newFile.getAbsoluteFile());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile.getAbsolutePath();
    }


    // save settings to tootieSettings.txt file
    private static void saveTootieSettings(String tootieSettingsFilePath) throws IOException {
        File tootieSettingsFile = new File(tootieSettingsFilePath);

        if (tootieSettingsFile.createNewFile()){
            System.out.println("New file created: " + tootieSettingsFile.getName());
        }

        // clear the file
        new FileWriter(tootieSettingsFilePath, false).close();

        appendsStringToFile(TootieFileMsgs.TOOTIE_SETTINGS_FILE_INSTRUCTIONS_HEADER, tootieSettingsFilePath);
        appendsStringToFile(String.format(TootieFileMsgs.TOOTIE_SETTINGS_FILE_PATHS_FORMAT, tootieSettingsFilePath,
                allTasksFilePath), tootieSettingsFilePath);
        String dividerChoiceString = dividerChoiceToString(dividerChoice);
        appendsStringToFile(String.format(TootieFileMsgs.TOOTIE_SETTINGS_USER_PREFERENCES_FORMAT, dividerChoiceString,
                username), tootieSettingsFilePath);

        System.out.println("All settings saved.");
    }

    // convert the dividerChoice into a string to save in tootieSettings.txt
    private static String dividerChoiceToString(DividerChoice dividerChoice) {
        switch (dividerChoice){
        case SIMPLE:
            return "SIMPLE";
        case SPARKLY:
            return "SPARKLY";
        default:
            return "PLAIN";
        }
    }

    // attempt to load the all tasks file
    private static void loadAllTasksFile() {
        boolean isFileNotRead = true;
        Scanner allTasksFileScanner;

        System.out.println("Searching for allTasks.txt save file...");

        while (isFileNotRead){
            try{
                File allTasksFile = getFileFromFilePath(allTasksFilePath);
                checkFileExists(allTasksFile);
                allTasksFileScanner = new Scanner(allTasksFile);
                readAllTasksFile(allTasksFileScanner);
                isFileNotRead = false;
            } catch (FileNotFoundException e) {
                System.out.println("Save file not found? " + TootieSymbols.CONFUSED_EMOTICON);
                boolean isNewFilePathObtained = true;
                while (isNewFilePathObtained) {
                    isNewFilePathObtained = getNewAllTasksFilePath();
                }
            } catch (FileEmptyException e) {
                System.out.println("Save file empty? " + TootieSymbols.CONFUSED_EMOTICON);
                isFileNotRead = false;
            }
        }
    }

    // get a new file path from user
    private static boolean getNewAllTasksFilePath() {
        String path = "";

        System.out.println("Options:" + NEWLINE + "(1)Find existing file" + NEWLINE +
                "(2)Manually create directory for file" + NEWLINE + "(3)Automatically create directory and file" +
                NEWLINE + "(type \"1\" \"2\" or \"3\")");

        String response = SCANNER.next();

        if (response.trim().equals("1")){
            System.out.println("Enter the full path to existing file: ");
            path = SCANNER.next();
        } else if (response.equals("2")){
            // make new file
            System.out.println("Enter the path to new directory location: ");
            path = SCANNER.next();
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
                return true;
            }
        } else if (response.equals("3")){
            System.out.println("Automatically creating directory and file");
            allTasksFilePath = autoCreateNewFile(TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH);
        } else {
            System.out.println("Response not recognised? " + TootieSymbols.CONFUSED_EMOTICON);
        }
        return false;
    }

    // try to read the allTasks.txt file into allTasks array
    private static void readAllTasksFile(Scanner allTasksFileScanner) throws FileEmptyException {
            // get total number of tasks stored
            int numTasksInList = 0;
            numTasksInList = getNumTasksInList(allTasksFileScanner);

            readFileUntilLineContainsString("Tasks:", allTasksFileScanner);

            addReadTasksToAllTasks(allTasksFileScanner);

            System.out.println(String.format("%1$d of %2$d tasks read successfully!",
                    numTasks, numTasksInList));
    }

    // read each task and add to the allTasks arraylist
    private static void addReadTasksToAllTasks(Scanner FILE_SCANNER) {
        while (FILE_SCANNER.hasNext()) {
            String fileInput = getFileNextLine(FILE_SCANNER);
            try {
                addTaskToAllTasksArrayList(fileInput);
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

    // Checks if the file with the given file path exists 
    private static File getFileFromFilePath(String filePath) {
        File allTasksFile = new File(filePath);
        System.out.println("full path: " + allTasksFile.getAbsolutePath());
        ui.printDivider();
        return allTasksFile;
    }

    // parses lines from the allTasks.txt file and adds the corresponding task to the allTasks ArrayList
    private static void addTaskToAllTasksArrayList(String fileInput) throws TaskTypeInvalidException, SavedTaskFormatWrongException {
        TaskType taskType = getTaskType(fileInput);
        switch (taskType) {
        case TODO:
            addToDoToList(fileInput);
            break;
        case DEADLINE:
            addDeadlineToList(fileInput);
            break;
        case EVENT:
            addEventToList(fileInput);
            break;
        default:
            throw new TaskTypeInvalidException();
        }
    }

    // adds a ToDo task read from the file to the allTasks ArrayList
    private static void addToDoToList(String fileInput) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[T\\]\\[([0-1]{1})\\](.*)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            allTasks.add(new ToDo(matcher.group(2).trim()));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks).setComplete(true);
                numTasksCompleted++;
            }
            numTasks++;
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // adds a Deadline task read from the file to the allTasks ArrayList
    private static void addDeadlineToList(String fileInput) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[D\\]\\[([0-1]{1})\\](.*)\\(by:(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date dueDate = parseComplexDate(matcher.group(3));
            allTasks.add(new Deadline(matcher.group(2).trim(),dueDate));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks).setComplete(true);
                numTasksCompleted++;
            }
            numTasks++;
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // adds a Event task read from the file to the allTasks ArrayList
    private static void addEventToList(String fileInput) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[E\\]\\[([0-1]{1})\\](.*)\\(from:(.*)to(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date startDate = parseComplexDate(matcher.group(3));
            Date endDate = parseComplexDate(matcher.group(4));
            allTasks.add(new Event(matcher.group(2).trim(), startDate, endDate));
            if(matcher.group(1).equals("1")){
                allTasks.get(numTasks).setComplete(true);
                numTasksCompleted++;
            }
            numTasks++;
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // parses the default date format and returns a Date object
    private static Date parseComplexDate(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        try {
            formattedDate = dateWithoutTime.parse(unformattedDate.trim());
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
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
    private static String getFileNextLine(Scanner FILE_SCANNER) {
        String fileInput;
        do {
            fileInput = FILE_SCANNER.nextLine();
        } while (fileInput.matches(TootieRegex.BLANK_STRING_REGEX)
                || fileInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return fileInput;
    }



    // saves all tasks from the allTasks ArrayList to the allTasks.txt file
    private static void saveAllTasks(ArrayList<Task> allTasks, String allTasksFilePath) throws IOException {
        File allTasksFile = new File(allTasksFilePath);

        if (allTasksFile.createNewFile()){
            System.out.println("New file created: " + allTasksFile.getName());
        }

        // clear the file
        new FileWriter(allTasksFilePath, false).close();

        // write file header
        appendsStringToFile(String.format("Total tasks: %1$d", numTasks), allTasksFilePath);
        appendsStringToFile(String.format("Tasks completed: %1$d", numTasksCompleted), allTasksFilePath);
        writeDoubleNewlineToFile(allTasksFilePath);
        appendsStringToFile(TootieFileMsgs.ALL_TASKS_FILE_INSTRUCTIONS_HEADER, allTasksFilePath);

        String printOut = "";
        for (int i = 0; i < numTasks; i++){
            if (allTasks.get(i) instanceof ToDo) {
                printOut = "[T][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "]" + allTasks.get(i).getTaskName();
            }
            if (allTasks.get(i) instanceof Deadline) {
                printOut = "[D][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "]" + allTasks.get(i).getTaskName() +
                        " (by:" + ((Deadline) allTasks.get(i)).getDueDate() + ")";
            }
            if (allTasks.get(i) instanceof Event) {
                printOut = "[E][" + ((allTasks.get(i).getComplete()) ? 1 : 0) + "]" + allTasks.get(i).getTaskName() +
                        " (from:" + ((Event) allTasks.get(i)).getStartTime() + " to " +
                        ((Event) allTasks.get(i)).getEndTime() + ")";
            }
            appendsStringToFile(printOut,allTasksFilePath);
            appendsStringToFile(NEWLINE, allTasksFilePath);
        }

        System.out.println("All tasks saved.");
    }

    // writes a double new line to the file to create one blank line of space
    private static void writeDoubleNewlineToFile(String filePath) throws IOException {
        appendsStringToFile(NEWLINE, filePath);
        appendsStringToFile(NEWLINE, filePath);
    }

    // appends the string to the given file specified by filePath
    private static void appendsStringToFile(String textToAppend, String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
        fw.write(textToAppend);
        fw.close();
    }

    // get user input, ignore comments and blank lines
    private static String getUserInput() {
        String userInput;
        do {
            userInput = SCANNER.nextLine();
        } while (userInput.matches(TootieRegex.BLANK_STRING_REGEX)
                || userInput.startsWith(TootieInputMarkers.INPUT_COMMENT_MARKER));
        return userInput;
    }

    // echo the userInput for testing
    private static void echoUserInput(String userInput) {
        System.out.println(userInput);
    }

    // prints all list items with index and check
    public static void printAllTasks (ArrayList<Task> allTasks) throws TasklistEmptyException {
        if (numTasks == 0) {
            throw new TasklistEmptyException();
        }

        System.out.println(String.format(TootieNormalMsgs.NUMTASKS_PRINT_FORMAT, numTasks));
        for (int i = 0; i < numTasks; i++) {
            System.out.println(String.format(TootieNormalMsgs.LIST_TASK_FORMAT,
                    (i + 1),
                    allTasks.get(i).getTaskType(),
                    allTasks.get(i).getCompletionIndicator(),
                    allTasks.get(i).getTaskDescription())
            );
        }

        if (numTasks == numTasksCompleted){
            System.out.println("all done " + TootieSymbols.BEAR_EMOTICON);
        }
    }

    // figure out the command type from userInput
    private static CommandType extractCommandType(String userInput) {
        if (userInput.toLowerCase().trim().startsWith("help")) {
            return CommandType.HELP;
        } else if (userInput.toLowerCase().trim().startsWith("todo")) {
            return CommandType.ADD_TODO;
        } else if (userInput.toLowerCase().trim().startsWith("deadline")) {
            return CommandType.ADD_DEADLINE;
        } else if (userInput.toLowerCase().trim().startsWith("event")) {
            return CommandType.ADD_EVENT;
        } else if (userInput.toLowerCase().trim().startsWith("list")) {
            return CommandType.LIST;
        } else if (userInput.toLowerCase().trim().startsWith("done")) {
            return CommandType.MARK_TASK_DONE;
        } else if (userInput.toLowerCase().trim().startsWith("bye")) {
                return CommandType.BYE;
        } else if (userInput.toLowerCase().trim().startsWith("delete")) {
            return CommandType.DELETE_TASK;
        } else if (userInput.toLowerCase().trim().startsWith("undone")) {
            return CommandType.MARK_TASK_UNDONE;
//        } else if (userInput.toLowerCase().trim().startsWith("divider")) {
//            return CommandType.CHOOSE_DIVIDER;
        } else if (userInput.toLowerCase().trim().startsWith("save")) {
            return CommandType.SAVE;
        } else {
            return CommandType.UNRECOGNISED;
        }
    }

    // execute the command as required
    private static void executeCommand(CommandType commandType, String userInput, ArrayList<Task> allTasks, String filePath) {
        switch (commandType) {
        case HELP:
            ui.printHelpInfo();
            break;
        case ADD_TODO:
            try {
                addToDo(userInput);
            } catch (ToDoInputWrongFormatException e){
                System.out.println(TootieErrorMsgs.TODO_WRONG_FORMAT_MSG);
            } catch (TaskNameEmptyException e){
                System.out.println(TootieErrorMsgs.TODO_TASKNAME_EMPTY_MSG);
            }
            break;
        case ADD_DEADLINE:
            try {
                addDeadline(userInput);
            } catch (DeadlineInputWrongFormatException e) {
                System.out.println(TootieErrorMsgs.DEADLINE_WRONG_FORMAT_MSG);
            } catch (DueDateWrongFormatException e) {
                System.out.println(TootieErrorMsgs.DUEDATE_WRONG_FORMAT_MSG);
            } catch (TaskNameEmptyException e) {
                System.out.println(TootieErrorMsgs.DEADLINE_TASKNAME_EMPTY_MSG);
            } catch (InvalidDueDateException e){
                System.out.println(TootieErrorMsgs.INVALID_DUE_DATE_MSG);
            }
            break;
        case ADD_EVENT:
            try {
                addEvent(userInput);
            } catch (EventInputWrongFormatException e) {
                System.out.println(TootieErrorMsgs.EVENT_WRONG_FORMAT_MSG);
            } catch (InvalidStartDateException e) {
                System.out.println(TootieErrorMsgs.INVALID_START_DATE_MSG);
            } catch (InvalidEndTimeException e) {
                System.out.println(TootieErrorMsgs.INVALID_END_DATE_MSG);
            } catch (StartTimeWrongFormatException e ) {
                System.out.println(TootieErrorMsgs.STARTDATE_WRONG_FORMAT_MSG);
            } catch (EndTimeWrongFormatException e) {
                System.out.println(TootieErrorMsgs.ENDDATE_WRONG_FORMAT_MSG);
            } catch (EndTimeBeforeStartTimeException e) {
                System.out.println(TootieErrorMsgs.ENDTIME_BEFORE_STARTIME_ERROR_MSG);
            } catch (TaskNameEmptyException e) {
                System.out.println(TootieErrorMsgs.EVENT_TASKNAME_EMPTY_MSG);
            }
            break;
        case LIST:
            try {
                printAllTasks(allTasks);
            } catch (TasklistEmptyException e) {
                System.out.println(TootieErrorMsgs.TasklistEmptyMsg);
            }
            break;
        case MARK_TASK_DONE:
            try {
                markTaskComplete(userInput, allTasks);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case MARK_TASK_UNDONE:
            try {
                markTaskIncomplete(userInput, allTasks);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case BYE:
            ui.printFarewellMessage();
            break;
        case SAVE:
            try {
                saveAllTasks(allTasks, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case DELETE_TASK:
            try {
                deleteTask(userInput, allTasks);
            } catch (TaskNonexistantException e){
                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
            }
            break;
        case CHOOSE_DIVIDER:
//            try {
//                changeDivider(userInput, allTasks);
//            } catch (TaskNonexistantException e){
//                System.out.println(TootieErrorMsgs.TASK_NOT_FOUND_ERROR_MSG);
//            }
            break;
        default:
            ui.printConfusedMessage();
        }
    }

    // deletes the selected task based on user input
    private static void deleteTask(String userInput, ArrayList<Task> allTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, allTasks);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_DELETED_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(),
                allTasks.get(taskNum - 1).getCompletionIndicator(),
                allTasks.get(taskNum - 1).getTaskDescription()));
        if (allTasks.get(taskNum - 1).getComplete()){
            numTasksCompleted--;
        }
        allTasks.remove(taskNum -1);
        numTasks--;
    }

    // add an event task to the allTasks list
    private static void addEvent(String userInput) throws EventInputWrongFormatException, InvalidStartDateException, InvalidEndTimeException, StartTimeWrongFormatException, EndTimeWrongFormatException, EndTimeBeforeStartTimeException, TaskNameEmptyException {
        Date startTime = null;
        Date endTime = null;

        String taskName = "";
        String startTimeUnformatted = "";
        String endTimeUnformatted = "";

        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        int startTimePosition = userInput.indexOf(TootieInputMarkers.STARTTIME_MARKER);
        int endTimePosition = userInput.indexOf(TootieInputMarkers.ENDTIME_MARKER);

        // check if placement is correct
        if (taskNamePosition == -1 || startTimePosition == -1 || endTimePosition == -1) {
            throw new EventInputWrongFormatException();
        } else {
            try {
                taskName = userInput.substring(taskNamePosition + 2, startTimePosition);
                startTimeUnformatted = userInput.substring(startTimePosition + 2, endTimePosition).trim();
                endTimeUnformatted = userInput.substring(endTimePosition + 2).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                throw new EventInputWrongFormatException();
            }
        }

        // check format start time
        boolean isStartDateWithTime = isDateWithTime(startTimeUnformatted);
        boolean isStartDateWithoutTime = isDateWithoutTime(startTimeUnformatted);
        // check format end time
        boolean isEndDateWithTime = isDateWithTime(endTimeUnformatted);
        boolean isEndDateWithoutTime = isDateWithoutTime(endTimeUnformatted);

        // try to parse start time
        if (isStartDateWithTime) {
            startTime = parseDateWithTime(startTimeUnformatted);
        } else if(isStartDateWithoutTime){
            startTime = parseDateWithoutTime(startTimeUnformatted);
        } else {
            throw new StartTimeWrongFormatException();
        }
        if (startTime == null){
            throw new StartTimeWrongFormatException();
        }

        // try to parse end time
        if (isEndDateWithTime) {
            endTime = parseDateWithTime(endTimeUnformatted);
        } else if(isEndDateWithoutTime){
            endTime = parseDateWithoutTime(endTimeUnformatted);
        } else {
            throw new EndTimeWrongFormatException();
        }
        if (endTime == null){
            throw new EndTimeWrongFormatException();
        }

        // check if date entered is valid
        if(!isValidDate(startTime)){
            throw new InvalidStartDateException();
        }
        if(!isValidDate(endTime)) {
            throw new InvalidEndTimeException();
        }

        // check if start and end time are in chronological order
        if(startTime.after(endTime)){
            throw new EndTimeBeforeStartTimeException();
        }

        if (taskName.isBlank()){
            throw new TaskNameEmptyException();
        }

        // add event to list
        allTasks.add(new Event(taskName.trim(), startTime, endTime));
        System.out.println(String.format(TootieNormalMsgs.ADDED_EVENT_FORMAT, allTasks.get(numTasks).getTaskDescription()));
        numTasks++;
    }

    // adds a deadline task to the allTasks list
    private static void addDeadline(String userInput) throws DeadlineInputWrongFormatException, DueDateWrongFormatException, TaskNameEmptyException, InvalidDueDateException {
        Date dueDate = null;

        String taskName = "";
        String dueDateUnformatted = "";

        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        int dueDatePosition = userInput.indexOf(TootieInputMarkers.DUEDATE_MARKER);

        // check if placement is correct, split if correct
        if (taskNamePosition == -1 || dueDatePosition == -1) {
            throw new DeadlineInputWrongFormatException();
        } else {
            try {
                taskName = userInput.substring(taskNamePosition + 2, dueDatePosition);
                dueDateUnformatted = userInput.substring(dueDatePosition + 2).trim();
            } catch (StringIndexOutOfBoundsException exception) {
                throw new DeadlineInputWrongFormatException();
            }
        }

        // check format due date
        boolean isDueDateWithTime = isDateWithTime(dueDateUnformatted);
        boolean isDueDateWithoutTime = isDateWithoutTime(dueDateUnformatted);

        // try to parse due date
        if (isDueDateWithTime) {
            dueDate = parseDateWithTime(dueDateUnformatted);
        } else if(isDueDateWithoutTime){
            dueDate = parseDateWithoutTime(dueDateUnformatted);
        } else {
            throw new DueDateWrongFormatException();
        }
        if (dueDate == null){
            throw new DueDateWrongFormatException();
        }

        if (taskName.isBlank()){
            throw new TaskNameEmptyException();
        }

        // check if date entered is valid
        if(!isValidDate(dueDate)){
            throw new InvalidDueDateException();
        }

        // add event to list
        allTasks.add(new Deadline(taskName.trim(), dueDate));
        System.out.println(String.format(TootieNormalMsgs.ADDED_DEADLINE_FORMAT, allTasks.get(numTasks).getTaskDescription()));
        numTasks++;
    }


    // TODO fix the date validator it doesn't work properly :(
    // check if the date entered is a valid calendar date
    private static boolean isValidDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.setTime(date);
        try {
            cal.getTime();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // parse the date if time is not included in input
    private static Date parseDateWithoutTime(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithoutTime = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formattedDate = dateWithoutTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // parse the date if time is included in input
    private static Date parseDateWithTime(String unformattedDate) {
        Date formattedDate;
        SimpleDateFormat dateWithTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            formattedDate = dateWithTime.parse(unformattedDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // check if the date entered is correctly formatted with a date but no time
    private static boolean isDateWithoutTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITHOUT_TIME_REGEX);
    }

    // check if the date entered is correctly formatted with a date and time
    private static boolean isDateWithTime(String timeUnformmated) {
        return timeUnformmated.matches(TootieRegex.DATE_WITH_TIME_REGEX);
    }

    // adds a toto task to the allTasks list
    private static void addToDo(String userInput) throws ToDoInputWrongFormatException, TaskNameEmptyException {
        // identify placements
        int taskNamePosition = userInput.indexOf(TootieInputMarkers.TASKNAME_MARKER);
        if (taskNamePosition == -1){
            throw new ToDoInputWrongFormatException();
        }
        String taskName = userInput.substring(taskNamePosition + 2);

        if (taskName.isBlank()){
            throw new TaskNameEmptyException();
        }

        // add task to list
        allTasks.add(new ToDo(taskName.trim()));
        numTasks++;
        System.out.println(String.format(TootieNormalMsgs.ADDED_TODO_FORMAT, taskName.trim()));
    }

    // process the user input and mark the task complete
    private static void markTaskComplete(String userInput, ArrayList<Task> allTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, allTasks);

        allTasks.get(taskNum - 1).setComplete(true);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_MARKED_DONE_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(),
                allTasks.get(taskNum - 1).getTaskDescription()));

        numTasksCompleted++;
    }

    private static int getTaskNumFromInput(String userInput, ArrayList<Task> allTasks) throws TaskNonexistantException {
        int taskNum;
        try {
            taskNum = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
            if (taskNum > numTasks || taskNum < 1) {
                throw new TaskNonexistantException();
            }
        } catch (NumberFormatException exception) {
            throw new TaskNonexistantException();
        }
        return taskNum;
    }

    // process the user input and mark the task incomplete
    private static void markTaskIncomplete(String userInput, ArrayList<Task> allTasks) throws TaskNonexistantException {
        int taskNum = 0;

        // try to parse task and check if it exists
        taskNum = getTaskNumFromInput(userInput, allTasks);

        allTasks.get(taskNum - 1).setComplete(false);

        // print response
        System.out.println(String.format(TootieNormalMsgs.TASK_MARKED_UNDONE_RESPONSE_MSG,
                allTasks.get(taskNum - 1).getTaskType(),
                allTasks.get(taskNum - 1).getTaskDescription()));

        numTasksCompleted--;
    }
}



