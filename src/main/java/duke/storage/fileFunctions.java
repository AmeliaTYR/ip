package duke.storage;

import duke.constants.TootieSymbols;
import duke.exceptions.FilePathInvalidException;
import duke.ui.Printers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static duke.parsers.Parsers.pathReplaceIllegalCharacters;

public class fileFunctions {
    public fileFunctions() {
    }

    public static final String NEWLINE = System.lineSeparator();

    // check if a file exists
    public static void checkFileExists(File file) throws FileNotFoundException {
        if (!file.exists()){
            throw new FileNotFoundException();
        }
    }

    // create a new file at the specified file path
    public static String autoCreateNewFile(String filePath) {
        File newFile = new File(filePath);
        System.out.println("Auto creating new file using path: " + newFile.getAbsolutePath());

        filePath = pathReplaceIllegalCharacters(filePath);

        try {
            checkFileExists(newFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not exists.");
        }

        // make the directory
        try {
            String directoryPath;
            String txtFileName;

            // identify placements
            int endOfDirectoryName = filePath.lastIndexOf("/");
            int endOfFileName = filePath.indexOf(".txt");

            // check if placement is correct
            if (endOfDirectoryName == -1 || endOfFileName == -1) {
                throw new FilePathInvalidException();
            } else {
                try {
                    directoryPath = filePath.substring(0, endOfDirectoryName);
                    txtFileName = filePath.substring(endOfDirectoryName + 2, endOfFileName).trim();
                } catch (StringIndexOutOfBoundsException exception) {
                    throw new FilePathInvalidException();
                }
            }

            //Creating a File object
            File file = new File(directoryPath);
            //Creating the directory
            boolean isFileCreated = file.mkdir();
            if(isFileCreated){
                System.out.println("Directory created successfully " + TootieSymbols.HAPPY_EMOTICON);
                filePath = filePath + "/" + txtFileName + ".txt";
            }else{
                System.out.println("Sorry, could not create specified directory");
            }
        } catch (FilePathInvalidException e) {
            System.out.println("Error when making directory!");
        }

        newFile = new File(filePath);

        // make the file
        try {
            if (newFile.createNewFile()){
                System.out.println("File created: " + newFile.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("IO error when making file!");
        }

        System.out.println("Wow! New file at: " + filePath);

        return newFile.getAbsolutePath();
    }

    // Checks if the file with the given file path exists
    public static File getFileFromFilePath(String filePath) {
        File allTasksFile = new File(filePath);
        System.out.println("full path: " + allTasksFile.getAbsolutePath());
        Printers.printDivider();
        return allTasksFile;
    }

    // writes a double new line to the file to create one blank line of space
    public static void writeDoubleNewlineToFile(String filePath) throws IOException {
        appendsStringToFile(NEWLINE, filePath);
        appendsStringToFile(NEWLINE, filePath);
    }

    // appends the string to the given file specified by filePath
    public static void appendsStringToFile(String textToAppend, String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
        fw.write(textToAppend);
        fw.close();
    }
}
