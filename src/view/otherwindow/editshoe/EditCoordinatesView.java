package view.otherwindow.editshoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import view.mainwindow.CoordinatesView;


/**
 * CoordinatesView for the ShoeEditor.<br>
 * Same than CoordinatesView but with only the cursor position.
 * @author Loïc David
 * @see CoordinatesView
 * @see ShoeEditor
 */
public class EditCoordinatesView extends Label {
    
    private final EditShoeView shoeView;

    /**
     * Create a new instance of EditCoordinatesView.
     * @param shoeView The ShoeView of the edited shoe.
     * @see EditCoordinatesView
     */
    public EditCoordinatesView(EditShoeView shoeView) {
        this.shoeView = shoeView;
        initialize();
    }

    /**
     * Initialize the EditCoordinatesView.<br>
     * Set its text and bind it to the cursor position.
     */
    private void initialize() {
        
        setText("Cursor: (0, 0)");
        
        // Bind cursorLabel to cursor position.
        shoeView.xCursorProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setText("Cursor: (" + newValue.doubleValue() + ", " + shoeView.yCursorProperty().getValue() + ")");
            }
        });
        shoeView.yCursorProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setText("Cursor: (" + shoeView.xCursorProperty().getValue() + ", " + newValue.doubleValue() + ")");
            }
        });

    }
    
}