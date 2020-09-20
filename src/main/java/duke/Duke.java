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
import duke.exceptions.StartTimeWrongFormatException;
import duke.exceptions.TaskNameEmptyException;
import duke.exceptions.TaskNonexistantException;
import duke.exceptions.TaskTypeInvalidException;
import duke.exceptions.TasklistEmptyException;
import duke.exceptions.ToDoInputWrongFormatException;
import duke.exceptions.TotalTasksNumInvalidException;

import duke.finalObjects.*;
import duke.task.ToDo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.tootieFunctions.Printer;

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

    private static final Scanner SCANNER = new Scanner(System.in);

    public static final String NEWLINE = System.lineSeparator();

    // array containing all tasks the user has input
    private static ArrayList<Task> allTasks = new ArrayList<Task>(TootieConstants.MAX_TASKS);

    // number of Tasks in the allTasks array
    private static int numTasks = 0;

    public static void main(String[] args) {
        String userInput;
        CommandType commandType = CommandType.START;

        Printer.printTootieLogo();
        Printer.printHelloMessage();

        // search for a tootieSettings save file
        String tootie_settings_save_path = TootieFilePaths.DEFAULT_TOOTIE_SETTINGS_FILE_PATH;
        System.out.println("Loading tootieSettings save file...");
        try{
            File tootieSettingsFile = getFile(tootie_settings_save_path);
            readTootieSettingsFile(tootieSettingsFile);
        } catch (FileNotFoundException | FileEmptyException e) {
            System.out.println("tootieSettings save file not found" + NEWLINE + "Creating new file");
            tootie_settings_save_path = getNewFile();
        }

        // search for a allTasks save file
        String all_tasks_file_path = TootieFilePaths.DEFAULT_ALL_TASKS_FILE_PATH;
        System.out.println("Searching for allTasks save file...");
        try{
            File allTasksFile = getFile(all_tasks_file_path);
            readAllTasksFile(allTasksFile);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found? " + TootieSymbols.CONFUSED_EMOTICON);
            all_tasks_file_path = getNewFile();
        } catch (FileEmptyException e) {
            System.out.println("Save file empty? " + TootieSymbols.CONFUSED_EMOTICON);
            all_tasks_file_path = getNewFile();
        }
        
        Printer.printDivider();

        // process commands
        while(commandType != CommandType.BYE){
            userInput = getUserInput();
            echoUserInput(userInput);
            Printer.printDivider();
            commandType = extractCommandType(userInput);
            executeCommand(commandType, userInput, allTasks, all_tasks_file_path);
            Printer.printDivider();
        }

        try {
            saveTasks(allTasks, all_tasks_file_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readTootieSettingsFile(File tootieSettingsFile) throws FileEmptyException,
            FileNotFoundException {
        // if it exists
        if (tootieSettingsFile.exists()){
            Scanner SETTINGS_FILE_SCANNER = new Scanner(tootieSettingsFile);

            // get total number of tasks stored
            int numTasksInList = 0;
            if (SETTINGS_FILE_SCANNER.hasNext()){
                String totalTasks = getFileNextLine(SETTINGS_FILE_SCANNER);
                try {
                    numTasksInList = getNumTasks(totalTasks);
                } catch (TotalTasksNumInvalidException e) {
                    System.out.println("File header invalid!");
                }
            } else {
                throw new FileEmptyException();
            }

            // read each setting and return the variables accordingly
            while (SETTINGS_FILE_SCANNER.hasNext()){
                String fileInput = getFileNextLine(SETTINGS_FILE_SCANNER);
                try {
                    addTaskToAllTasksArrayList(fileInput);
                } catch (TaskTypeInvalidException | SavedTaskFormatWrongException e) {
                    System.out.println(String.format("Error reading file! Error on line:" + NEWLINE + "%1$s",
                            fileInput));
                    break;
                }
            }
            System.out.println(String.format("%1$d of %2$d tasks read successfully!",
                    numTasks, numTasksInList));
        } else {
            throw new FileEmptyException();
        }
    }

    private static String getNewFile() {
        String path = "";
        System.out.println("Options:" + NEWLINE + "(1)Find existing file" + NEWLINE +
                "(2)Manually create directory for file" + NEWLINE + "(3)Automatically create directory and file" +
                NEWLINE + "(type \"1\" \"2\" or \"3\")");
        String response = SCANNER.next();
        if (response.equals("1")){
            System.out.println("Enter the full path to existing file: ");
            path = SCANNER.next();
            try {
                File allTasksFile = getFile(path);
                readAllTasksFile(allTasksFile);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (FileEmptyException fileEmptyException) {
                fileEmptyException.printStackTrace();
            }
        } else if (response.equals("2")){
            // make new file
            System.out.println("Enter the path to new directory location (without new directory name): ");
            path = SCANNER.next();
            System.out.println("Enter the name of the desired a directory: ");
            path = path+SCANNER.next();
            //Creating a File object
            File file = new File(path);
            //Creating the directory
            boolean bool = file.mkdir();
            if(bool){
                System.out.println("Directory created successfully " + TootieSymbols.HAPPY_EMOTICON);
                path = path+"/allTasks.txt";
            }else{
                System.out.println("Sorry couldn’t create specified directory");
            }
        } else if (response.equals("3")){
            System.out.println("Automatically creating directory and file");
            autoCreateNewFile(TootieFilePaths.DEFAULT_TOOTIE_SETTINGS_FILE_PATH);
        } else {
            System.out.println("Directory created successfully " + TootieSymbols.HAPPY_EMOTICON);
        }
        return path;
    }

    private static void autoCreateNewFile(String defaultTootieSettingsFilePath) {

    }


    // try to read the allTasks.txt file into allTasks array
    private static void readAllTasksFile(File allTasksFile) throws FileNotFoundException, FileEmptyException {
        // if it exists
        if (allTasksFile.exists()){
            Scanner FILE_SCANNER = new Scanner(allTasksFile);

            // get total number of tasks stored
            int numTasksInList = 0;
            numTasksInList = getNumTasksInList(FILE_SCANNER, numTasksInList);

            // skip past the instructions
            skipAllTasksInstructions(FILE_SCANNER);

            // read each task and add to the allTasks arraylist
            addReadTasksToAllTasks(FILE_SCANNER);
            System.out.println(String.format("%1$d of %2$d tasks read successfully!",
                    numTasks, numTasksInList));
        } else {
            throw new FileEmptyException();
        }

    }

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
    private static int getNumTasksInList(Scanner FILE_SCANNER, int numTasksInList) throws FileEmptyException {
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

    // skips past the instructions segment when reading the allTasks.txt file
    private static void skipAllTasksInstructions(Scanner FILE_SCANNER) {
        while (FILE_SCANNER.hasNext()) {
            String fileInput = getFileNextLine(FILE_SCANNER);
            if (fileInput.equals("Tasks:")) {
                break;
            }
        }
    }

    // Checks if the file with the given file path exists 
    private static File getFile(String filePath) {
        File allTasksFile = new File(filePath);
        System.out.println("full path: " + allTasksFile.getAbsolutePath());
        System.out.println("file exists?: " + allTasksFile.exists());
        System.out.println("is Directory?: " + allTasksFile.isDirectory());
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
            if(matcher.group(1) == "1"){
                allTasks.get(numTasks).setComplete(true);
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
            if(matcher.group(1) == "1"){
                allTasks.get(numTasks).setComplete(true);
            }
            numTasks++;
        } else {
            throw new SavedTaskFormatWrongException();
        }
    }

    // adds a Event task read from the file to the allTasks ArrayList
    private static void addEventToList(String fileInput) throws SavedTaskFormatWrongException {
        Pattern pattern = Pattern.compile("\\[D\\]\\[([0-1]{1})\\](.*)\\(from:(.*)to(.*)\\)");
        Matcher matcher = pattern.matcher(fileInput);
        if (matcher.matches()) {
            Date startDate = parseComplexDate(matcher.group(3));
            Date endDate = parseComplexDate(matcher.group(4));
            allTasks.add(new Event(matcher.group(2).trim(), startDate, endDate));
            if(matcher.group(1) == "1"){
                allTasks.get(numTasks).setComplete(true);
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
    private static void saveTasks(ArrayList<Task> allTasks, String filePath) throws IOException {
        File allTasksFile = new File(filePath);

        if (allTasksFile.createNewFile()){
            System.out.println("File created: " + allTasksFile.getName());
            //if it doesnt exist create it
        } else {
            System.out.println("File already exists.");
        }

        // clear the file
        new FileWriter(filePath, false).close();

        // write file header
        try {
            appendsStringToFile(String.format("Total tasks: %1$d", numTasks), filePath);
            writeDoubleNewlineToFile(filePath);
            appendsStringToFile(TootieFileMsgs.FILE_INSTRUCTIONS_HEADER, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String printOut = "";
        for (int i = 0; i < numTasks; i++){
            if (allTasks.get(i) instanceof ToDo) {
                printOut = "[T][" + ((allTasks.get(i).isComplete()) ? 1 : 0) + "]" + allTasks.get(i).getTaskName();
            }
            if (allTasks.get(i) instanceof Deadline) {
                printOut = "[D][" + ((allTasks.get(i).isComplete()) ? 1 : 0) + "]" + allTasks.get(i).getTaskName() +
                        " (by:" + ((Deadline) allTasks.get(i)).getDueDate() + ")";
            }
            if (allTasks.get(i) instanceof Event) {
                printOut = "[E][" + ((allTasks.get(i).isComplete()) ? 1 : 0) + "]" + allTasks.get(i).getTaskName() +
                        " (from:" + ((Event) allTasks.get(i)).getStartTime() + " to " +
                        ((Event) allTasks.get(i)).getEndTime() + ")";
            }
            appendsStringToFile(printOut,filePath);
            appendsStringToFile(NEWLINE, filePath);
        }
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
                    allTasks.get(i).getTaskDescription()));
        }

        // TODO: print "all done ʕ•ᴥ•ʔ" if all tasks done for now
    }

    // figure out the command type from userInput
    private static CommandType extractCommandType(String userInput) {
        if (userInput.toLowerCase().trim().startsWith("help")){
            return CommandType.HELP;
        } else if (userInput.toLowerCase().trim().startsWith("todo")){
            return CommandType.ADD_TODO;
        } else if (userInput.toLowerCase().trim().startsWith("deadline")){
            return CommandType.ADD_DEADLINE;
        } else if (userInput.toLowerCase().trim().startsWith("event")){
            return CommandType.ADD_EVENT;
        } else if (userInput.toLowerCase().trim().startsWith("list")){
            return CommandType.LIST;
        } else if (userInput.toLowerCase().trim().startsWith("done")){
            return CommandType.MARK_TASK_DONE;
        } else if (userInput.toLowerCase().trim().startsWith("bye")){
                return CommandType.BYE;
        } else if (userInput.toLowerCase().trim().startsWith("delete")){
            return CommandType.DELETE_TASK;
        } else if (userInput.toLowerCase().trim().startsWith("undone")){
            return CommandType.MARK_TASK_UNDONE;
        } else if (userInput.toLowerCase().trim().startsWith("save")){
            return CommandType.SAVE;
        } else {
            return CommandType.UNRECOGNISED;
        }
    }

    // execute the command as required
    private static void executeCommand(CommandType commandType, String userInput, ArrayList<Task> allTasks, String filePath) {
        switch (commandType) {
        case HELP:
            Printer.printHelpInfo();
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
            Printer.printFarewellMessage();
            break;
        case SAVE:
            try {
                saveTasks(allTasks, filePath);
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
        default:
            Printer.printConfusedMessage();
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
    }
}



