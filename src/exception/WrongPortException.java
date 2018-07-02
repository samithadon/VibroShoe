package exception;

import model.Shoe.Side;


/**
 * Exception used when an error occures while connecting to the serial port.
 * This exception is used when the port we try to connect to cannot be
 * found.
 * @author Loïc David
 */
public class WrongPortException extends Exception {
    
    private final String port;
    private final Side side;
    
    /**
     * Create a new instance of WrongPortException.
     * @param port Serial port we try to connect to.
     * @param side Shoe side (left or right).
     * @see WrongPortException
     */
    public WrongPortException(String port, Side side) {
        this.port = port;
        this.side = side;
    }
    
    /**
     * Return a string representation of WrongPortException.
     * This representation is a message that can be displayed to the user.
     * @return The message for this exception.
     * @see WrongPortException
     */
    @Override
    public String toString() {
        return "Port " + port + " (" + side.toString() + " shoe) cannot be found!";
    }

}