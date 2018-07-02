package view.mainwindow.shoe;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;


/**
 * A label with an arrow which can be placed in a ShoeView.<br>
 * The label can be drag and drop in the shoeView, the arrow will still point
 * the same coordinates.
 * @author Loïc David
 * @see ShoeView
 */
public class LabelView extends Pane {
    
    private final Label label;
    private final Line line = new Line(0, 0, 0, 0);
    private EventHandler<MouseEvent> pressHandler;
    private EventHandler<MouseEvent> moveHandler;
    private EventHandler<MouseEvent> releaseHandler;
    double x0;
    double y0;
    
    /**
     * Create a new instance of LabelView.
     * @param text Text to display in the label.
     * @param x X coordinate pointed by the arrow (in the ShoeView coordinate
     * system).
     * @param y Y coordinate pointed by the arrow (in the ShoeView coordinate
     * system).
     * @see LabelView
     * @see ShoeView
     */
    public LabelView(String text, double x, double y) {
        label = new Label(text);
        setLayoutX(x);
        setLayoutY(y);
        initialize();
    }
    
    /**
     * Initialize the LabelView.<br>
     * Define the style and the hitbox of the LabelView and implement the drag
     * and drop interaction.
     */
    private void initialize() {
        
        // Define the hitbox.
        setPickOnBounds(false);
        line.setMouseTransparent(true);
        // Set the label's style.
        label.setStyle("-fx-border-color: black;"
                + "-fx-background-color: white;"
                + "-fx-font-size: 10;");
        // Add components to the Pane.
        getChildren().addAll(line, label);
        
        // Initialize the position of the label and the arrow.
        // We need to use Platform.runLater because the size of the label is
        // null while it is not displayed.
        Platform.runLater(() -> {
            double x = 10;
            double y = 10;
            if (x > 262 - getLayoutX() - label.getWidth()) {
                x = 262 - getLayoutX() - label.getWidth();
            }
            if (y > 586 - getLayoutY() - label.getHeight()) {
                y = 586 - getLayoutY() - label.getHeight();
            }
            label.setLayoutX(x);
            label.setLayoutY(y);
            line.setEndX(x + label.getWidth() / 2);
            line.setEndY(y + label.getHeight() / 2);
        });
        
        // Drag and drop.
        // When pressed, we save the local position of the mouse and add
        // the handlers for the drag and the release.
        pressHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x0 = event.getX();
                y0 = event.getY();
                label.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveHandler);
                label.addEventHandler(MouseEvent.MOUSE_RELEASED, releaseHandler);
                event.consume();
            }
        };
        // When dragged, the label is moved and the end of the arrow has to
        // follow it.
        moveHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double newX = label.getLayoutX() + event.getX() - x0;
                double newY = label.getLayoutY() + event.getY() - y0;
                if (newX <= 262 - getLayoutX() - label.getWidth() && newX > -getLayoutX()) {
                    label.setLayoutX(newX);
                    line.setEndX(line.getEndX() + event.getX() - x0);
                }
                if (newY <= 586 - getLayoutY() - label.getHeight() && newY > -getLayoutY()) {
                    label.setLayoutY(newY);
                    line.setEndY(line.getEndY() + event.getY() - y0);
                }
                event.consume();
            }
        };
        // When released, we can remove the handler for the drag and the release.
        releaseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveHandler);
                label.removeEventHandler(MouseEvent.MOUSE_RELEASED, releaseHandler);
                event.consume();
            }
        };
        label.addEventHandler(MouseEvent.MOUSE_PRESSED, pressHandler);
        
    }

    /**
     * Getter for the label of the LabelView.
     * @return The label of the LabelView.
     * @see LabelView
     */
    public Label getLabel() {
        return label;
    }
    
}