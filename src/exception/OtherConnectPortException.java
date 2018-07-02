package exception;

import model.Shoe.Side;


/**
 * Exception used when an error occures while connecting to the serial port.
 * This exception is used when the issue is different from UsedPortException
 * and WrongPortException.
 * @author Loïc David
 * @see UsedPortException
 * @see WrongPortException
 */
public class OtherConnectPortException extends Exception {
    
    private final String port;
    private final Side side;

    /**
     * Create a new instance of OtherConnectPortException.
     * @param port Serial port we try to connect to.
     * @param side Shoe side (left or right).
     * @see OtherConnectPortException
     */
    public OtherConnectPortException(String port, Side side) {
        this.port = port;
        this.side = side;
    }

    /**
     * Return a string representation of OtherConnectPortException.
     * This representation is a message that can be displayed to the user.
     * @return The message for this exception.
     * @see OtherConnectPortException
     */
    @Override
    public String toString() {
        return "Unknow error during connection to port " + port + " ("  + side.toString() + " shoe)!";
    }
    
}