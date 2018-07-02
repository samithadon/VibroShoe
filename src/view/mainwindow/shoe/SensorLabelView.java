package view.mainwindow.shoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Sensor;


/**
 * LabelView with specific functions for SensorView.
 * @author Loïc David
 * @see SensorView
 */
public class SensorLabelView extends LabelView {
    
    private final int index;
    private final Sensor sensor;

    /**
     * Create a new instance of SensorLabelView.
     * @param i Index of the motor.
     * @param sensor Model of the sensor.
     */
    public SensorLabelView(int i, Sensor sensor) {
        super("", ShoeView.shoeToViewX(sensor.getX(), sensor.getSide()), ShoeView.shoeToViewY(sensor.getY(), sensor.getSide()));
        index = i;
        this.sensor = sensor;
        initialize();
    }

    /**
     * Initialize the SensorLabelView.<br>
     * Bind the label with the value of the sensor.
     */
    private void initialize() {
        
        // Initialize the label's text.
        getLabel().setText("Sensor" + index + "\nvalue: 0\n");
        
        // Bind the text to the sensor's pressure value.
        sensor.pressureProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                getLabel().setText("Motor" + index + "\nvalue: " + newValue.intValue());
            }
        });
        
        // The LabelSensorView is not displayed by default.
        // It is displayed temporary when the mouse is on its SensorView or
        // continiously while its SensorView is selected.
        setVisible(false);
    }
    
}