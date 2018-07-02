package view.mainwindow.shoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Motor;


/**
 * View of a motor.<br>
 * This view is a representation of a motor's model.
 * @author Loïc David
 * @see Motor
 */
public class MotorView extends Circle {
    
    private final Motor model;
    private boolean labelVisible = false;

    /**
     * Create a new instance of MotorView.
     * @param model The model of the motor.
     * @see MotorView
     */
    public MotorView(Motor model) {
        super(ShoeView.shoeToViewX(model.getX(), model.getSide()), ShoeView.shoeToViewY(model.getY(), model.getSide()), 6, Color.color(0, 1, 0));
        this.model = model;
        initialize();
    }
    
    /**
     * Initialize the MotorView.<br>
     * Define the style of the MotorView and bind its color with the model's
     * value.
     */
    private void initialize() {
        
        // Set style.
        setStroke(Color.BLACK);
        
        // Bind color to model.
        model.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double red = 0;
                double green = 0;
                double v = newValue.doubleValue() * 3;
                if (v < 1) {
                    red = v;
                    green = 1;
                }
                else if (v < 2) {
                    red = 1;
                    green = 2 - v;
                }
                else {
                    red = 3 - v;
                    green = 0;
                }
                setFill(Color.color(red, green, 0));
            }
        });
        
    }
    
    /**
     * Set the label of this MotorView.<br>
     * Define all the interactions between the label and the MotorView.
     * @param labelView The label to set.
     * @see MotorView
     * @see MotorLabelView
     */
    public void setLabelView(MotorLabelView labelView) {
        // Display the label when the mouse enter.
        addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                labelView.setVisible(true);
            }
        });
        // Hide the label when the mouse exit, if the MotorView hasn't been selected.
        addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!labelVisible) {
                    labelView.setVisible(false);
                }
            }
        });
        // Select or unselect the MotorView on click.
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (labelVisible) {
                    setStrokeWidth(1);
                    labelVisible = false;
                }
                else {
                    labelVisible = true;
                    setStrokeWidth(2);
                }
            }
        });
        // The label has to follow the behaviours of the MotorView.
        visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (labelVisible) {
                        labelView.setVisible(true);
                    }
                }
                else {
                    labelView.setVisible(false);
                }
            }
        });
    }
  
}