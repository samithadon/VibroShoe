package exception;

import model.Shoe.Side;


/**
 * Exception used when an error occures while connecting to the serial port.
 * This exception is used when the port we try to connect to is already
 * used.
 * @author Loïc David
 */
public class UsedPortException extends Exception {
    
    private final String port;
    private final Side side;

    /**
     * Create a new instance of UsedPortException.
     * @param port Serial port we try to connect to.
     * @param side Shoe side (left or right).
     * @see UsedPortException
     */
    public UsedPortException(String port, Side side) {
        this.port = port;
        this.side = side;
    }
    
    /**
     * Return a string representation of UsedPortException.
     * This representation is a message that can be displayed to the user.
     * @return The message for this exception.
     * @see UsedPortException
     */
    @Override
    public String toString() {
        return "Port " + port + " (" + side.toString() + " shoe) is already used!\n";
    }
    
    
}