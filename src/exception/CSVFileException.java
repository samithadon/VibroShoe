package exception;


/**
 * Exception used when an error occures while loading the csv files.
 * This exception can occure for many different reasons. The main reasons are:<br>
 * - File not found.<br>
 * - Wrong data format.<br>
 * - The data of one file are not compatible with the other one.
 * @author Loïc David
 */
public class CSVFileException extends Exception {

    /**
     * Return a string representation of CSVFileException.
     * This representation is a message that can be displayed to the user.
     * @return The message for this exception.
     * @see CSVFileException
     */
    @Override
    public String toString() {
        return "Error loading CSV files!!";
    }

}