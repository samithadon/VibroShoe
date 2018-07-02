package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Shoe.Side;
import view.mainwindow.shoe.SensorView;


/**
 * Model of a sensor.<br>
 * Contain the position of the sensor in the coordinates system of the Shoe, the
 * side of the Shoe this sensor belongs to, the value and the pressure value of
 * the sensor.
 * @author Loïc David.
 * @see Shoe
 * @see SensorView
 */
public class Sensor {
    
    private final double x;
    private final double y;
    private final Side side;
    private final DoubleProperty value = new SimpleDoubleProperty(0);
    private final DoubleProperty pressure = new SimpleDoubleProperty(0);

    /**
     * Create a new instance of Sensor.
     * @param x X coordinate of the motor in the coordinates system of the Shoe.
     * @param y Y coordinate of the motor in the coordinates system of the Shoe.
     * @param side Shoe side (left or right).
     * @see Sensor
     * @see Shoe
     */
    public Sensor(double x, double y, Side side) {
        this.x = x;
        this.y = y;
        this.side = side;
    }
    
    /**
     * Contain the value of the sensor read by the harware.
     * @return The valueProperty of the sensor.
     */
    public DoubleProperty valueProperty() {
        return value;
    }
    
    /**
     * Contain the pressure value of the sensor (in mmHg).
     * @return The pressureProperty of the sensor.
     */
    public DoubleProperty pressureProperty() {
        return pressure;
    }

    /**
     * Getter for the X coordinate of the sensor in the coordinates system of the
     * shoe.
     * @return the X coordinate of the sensor.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the Y coordinate of the sensor in the coordinates system of the
     * shoe.
     * @return the Y coordinate of the sensor.
     */
    public double getY() {
        return y;
    }
    
    /**
     * Getter for the side of the Shoe this sensor belongs to.
     * @return The side of the Shoe this sensor belongs to.
     * @see Shoe
     */
    public Side getSide() {
        return side;
    }
    
}