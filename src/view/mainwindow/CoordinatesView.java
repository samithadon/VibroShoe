package view.mainwindow;

import view.mainwindow.shoe.ShoeView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import model.CoP;
import model.Shoe;


/**
 * Display the positions of the cursor and the CoP in the coordinates system of
 * the shoe.
 * @author Loïc David
 * @see CoP
 * @see Shoe
 */
public class CoordinatesView extends HBox {
    
    private final ShoeView shoeView;

    /**
     * Create a new instance of CoordinatesView.
     * @param shoeView The ShoeView linked to this CoordinatesView.
     * @see CoordinatesView
     */
    public CoordinatesView(ShoeView shoeView) {
        this.shoeView = shoeView;
        initialize();
    }

    /**
     * Create the components of the CoordinatesView and bind them with the CoP's
     * and cursor's positions.
     */
    private void initialize() {
        
        // Create the view.
        setStyle("-fx-border-width: 0 0 1 0;-fx-border-color: black");
        
        Label copLabel = new Label("CoP: (None, None)");
        Region fillRegion = new Region();
        HBox.setHgrow(fillRegion, Priority.ALWAYS);
        Label cursorLabel = new Label("Cursor: (0, 0)");
        
        getChildren().addAll(copLabel, fillRegion, cursorLabel);
        
        // Bind cursorLabel to cursor position.
        shoeView.xCursorProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                cursorLabel.setText("Cursor: (" + newValue.doubleValue() + ", " + shoeView.yCursorProperty().getValue() + ")");
            }
        });
        shoeView.yCursorProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                cursorLabel.setText("Cursor: (" + shoeView.xCursorProperty().getValue() + ", " + newValue.doubleValue() + ")");
            }
        });
        
        // Bind copLabel to CoP position.
        shoeView.getModel().getCop().xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() > 500) {
                    copLabel.setText("CoP: (None, None)");
                }
                else {
                    copLabel.setText("CoP: (" + newValue.doubleValue() + ", " + shoeView.getModel().getCop().yProperty().getValue() + ")");
                }
            }
        });
        shoeView.getModel().getCop().yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() > 500) {
                    copLabel.setText("CoP: (None, None)");
                }
                else {
                    copLabel.setText("CoP: (" + shoeView.getModel().getCop().xProperty().getValue() + ", " + newValue.doubleValue() + ")");
                }
            }
        });
        
    }

}