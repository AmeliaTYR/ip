package duke.storage;

import duke.exceptions.FilePathInvalidException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static duke.constants.TootieNormalMsgs.COULD_NOT_CREATE_DIRECTORY_MSG;
import static duke.constants.TootieNormalMsgs.DIRECTORY_CREATED_SUCCESSFULLY_MSG;
import static duke.constants.TootieNormalMsgs.FILE_ALREADY_EXISTS_MSG;
import static duke.constants.TootieNormalMsgs.FILE_AUTO_CREATED_MSG;
import static duke.constants.TootieNormalMsgs.FILE_CREATED_PATH_MSG;
import static duke.constants.TootieNormalMsgs.FILE_NOT_FOUND_MSG;
import static duke.constants.TootieNormalMsgs.FILE_PATH_TO_DIRECTORY_INVALID_MSG;
import static duke.constants.TootieNormalMsgs.IO_ERROR_WHEN_MAKING_FILE_MSG;
import static duke.constants.TootieNormalMsgs.NEW_FILE_CREATED_MSG_FORMAT;

import static duke.parsers.Checks.pathReplaceIllegalCharacters;

/**
 * Functions to modify and create files to write to
 */
public class FileFunctions {

    public static final String NEWLINE = System.lineSeparator();

    /**
     * check if a file exists
     *
     * @param file a given file to check
     * @throws FileNotFoundException the file to check was not found
     */
    public static void checkFileExists(File file) throws FileNotFoundException {
        if (!file.exists()){
            throw new FileNotFoundException();
        }
    }

    /**
     * Create a new file at the specified file path.
     *
     * @param filePath specified file path
     * @return absolute path of the new path
     */
    public static String autoCreateNewFile(String filePath) {
        File newFile = new File(filePath);
        System.out.println(String.format(FILE_AUTO_CREATED_MSG, newFile.getAbsolutePath()));

        filePath = pathReplaceIllegalCharacters(filePath);

        try {
            checkFileExists(newFile);
        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND_MSG);
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
                    txtFileName = filePath.substring(endOfDirectoryName + 1, endOfFileName).trim();
                } catch (StringIndexOutOfBoundsException exception) {
                    throw new FilePathInvalidException();
                }
            }

            //Creating a File object
            File file = new File(directoryPath);
            //Creating the directory
            boolean isFileCreated = file.mkdir();
            if(isFileCreated){
                System.out.println(DIRECTORY_CREATED_SUCCESSFULLY_MSG);
                filePath = directoryPath + "/" + txtFileName + ".txt";
            }else{
                System.out.println(COULD_NOT_CREATE_DIRECTORY_MSG);
            }
        } catch (FilePathInvalidException e) {
            System.out.println(FILE_PATH_TO_DIRECTORY_INVALID_MSG);
        }

        newFile = new File(filePath);

        // make the file
        try {
            if (newFile.createNewFile()){
                System.out.println(String.format(FILE_CREATED_PATH_MSG, newFile.getAbsolutePath()));
            } else {
                System.out.println(FILE_ALREADY_EXISTS_MSG);
            }
        } catch (IOException e) {
            System.out.println(IO_ERROR_WHEN_MAKING_FILE_MSG);
        }

        System.out.println(String.format(NEW_FILE_CREATED_MSG_FORMAT, newFile.getAbsolutePath()));

        return newFile.getAbsolutePath();
    }

    /**
     * Checks if the file with the given file path exists
     *
     * @param filePath given file path
     * @return file object retrieved from the given file path
     */
    public static File getFileFromFilePath(String filePath) {
        File allTasksFile = new File(filePath);
        return allTasksFile;
    }

    /**
     * writes a double new line to the file to create one blank line of space
     *
     * @param filePath given file path
     * @throws IOException unable to write to file
     */
    public static void writeDoubleNewlineToFile(String filePath) throws IOException {
        appendsStringToFile(NEWLINE, filePath);
        appendsStringToFile(NEWLINE, filePath);
    }

    /**
     * appends the string to the given file specified by filePath
     *
     * @param textToAppend string to be appended to the file
     * @param filePath filepath to the file
     * @throws IOException unable to write to file
     */
    public static void appendsStringToFile(String textToAppend, String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
        fw.write(textToAppend);
        fw.close();
    }
}
