package duke.fileHandlers;

import duke.Duke;
import duke.tootieFunctions.TextUi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class fileFunctions {
    public fileFunctions() {
    }

    public static final String NEWLINE = System.lineSeparator();

    // check if a file exists
    public static void checkFileExists(File tootieSettingsFile) throws FileNotFoundException {
        if (!tootieSettingsFile.exists()){
            throw new FileNotFoundException();
        }
    }

    // create a new file at the specified file path
    public static String autoCreateNewFile(String filePath) {
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

    // Checks if the file with the given file path exists
    public static File getFileFromFilePath(String filePath) {
        File allTasksFile = new File(filePath);
        System.out.println("full path: " + allTasksFile.getAbsolutePath());
        TextUi.printDivider();
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
