package view.mainwindow.shoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.CoP;


/**
 * View of the CoP (Centre of Pressure).<br>
 * This view is a representation of the CoP's model.
 * @author Loïc David
 * @see CoP
 */
public class CoPView extends Circle {
    
    private final CoP model;

    /**
     * Create a new instance of CoPView.
     * @param model The model of the CoP.
     * @see CoPView
     */
    public CoPView(CoP model) {
        super(ShoeView.shoeToViewX(model.xProperty().getValue(), model.getSide()), ShoeView.shoeToViewY(model.yProperty().getValue(), model.getSide()), 4, Color.color(0, 0, 1));
        this.model = model;
        initialize();
    }
    
    /**
     * Initialize the CoPView.<br>
     * Define the style of the view and bind the view's properties to the
     * model's ones.
     */
    private void initialize() {
        
        // Set style
        setStroke(Color.BLACK);
        
        // Bind position to model.
        model.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() == 1000) {
                    setVisible(false);
                }
                else {
                    setVisible(true);
                    setCenterX(ShoeView.shoeToViewX(newValue.doubleValue(), model.getSide()));
                }
            }
        });
        model.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() == 1000) {
                    setVisible(false);
                }
                else {
                    setVisible(true);
                setCenterY(ShoeView.shoeToViewY(newValue.doubleValue(), model.getSide()));
                }
            }
        });
    }
    
}