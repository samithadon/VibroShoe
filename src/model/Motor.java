package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Shoe.Side;
import view.mainwindow.shoe.MotorView;


/**
 * Model of a motor.<br>
 * Contain the position of the motor in the coordinates system of the Shoe, the
 * side of the Shoe this motor belongs to and the value of the motor.
 * @author Loïc David
 * @see Shoe
 * @see MotorView
 */
public class Motor {
    
    private final double x;
    private final double y;
    private final Side side;
    private final DoubleProperty value = new SimpleDoubleProperty(0);

    /**
     * Create a new instance of Motor.
     * @param x X coordinate of the motor in the coordinates system of the Shoe.
     * @param y Y coordinate of the motor in the coordinates system of the Shoe.
     * @param side Shoe side (left or right).
     * @see Motor
     * @see Shoe
     */
    public Motor(double x, double y, Side side) {
        this.x = x;
        this.y = y;
        this.side = side;
    }

    /**
     * Contain the intensity of the vibrations of the motor (in percentage).
     * @return valueProperty of the motor.
     */
    public DoubleProperty valueProperty() {
        return value;
    }

    /**
     * Getter for the X coordinate of the motor in the coordinates system of the
     * Shoe.
     * @return the X coordinate of the motor.
     * @see Shoe
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the Y coordinate of the motor in the coordinates system of the
     * Shoe.
     * @return the Y coordinate of the motor.
     * @see Shoe
     */
    public double getY() {
        return y;
    }
    
    /**
     * Getter for the side of the Shoe this motor belongs to.
     * @return The side of the Shoe this motor belongs to.
     * @see Shoe
     */
    public Side getSide() {
        return side;
    }
    
}