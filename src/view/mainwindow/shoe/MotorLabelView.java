package view.mainwindow.shoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Motor;


/**
 * LabelView with specific functions for MotorView.
 * @author Loïc David
 * @see MotorView
 */
public class MotorLabelView extends LabelView {
    
    private final int index;
    private final Motor motor;

    /**
     * Create a new instance of MotorLabelView.
     * @param i Index of the motor.
     * @param motor Model of the motor.
     * @see MotorLabelView
     */
    public MotorLabelView(int i, Motor motor) {
        super("", ShoeView.shoeToViewX(motor.getX(), motor.getSide()), ShoeView.shoeToViewY(motor.getY(), motor.getSide()));
        index = i;
        this.motor = motor;
        initialize();
    }

    /**
     * Initialize the MotorLabelView.<br>
     * Bind the label with the value of the motor.
     */
    private void initialize() {
        
        // Initialize the label's text.
        getLabel().setText("Motor" + index + "\nvalue: 0.0");
        
        // Bind the text to the motor's value.
        motor.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                getLabel().setText("Motor" + index + "\nvalue: " + Math.floor(1000 * newValue.doubleValue()) / 10);
            }
        });
        
        // The LabelMotorView is not displayed by default.
        // It is displayed temporary when the mouse is on its MotorView or
        // continiously while its MotorView is selected.
        setVisible(false);
        
    }
     
}