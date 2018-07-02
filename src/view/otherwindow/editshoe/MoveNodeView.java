package view.otherwindow.editshoe;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Motor;
import model.Sensor;
import model.Shoe.Side;
import view.otherwindow.ErrorWindow;
import view.mainwindow.shoe.ShoeView;


/**
 * Window used to choose new coordinates for a motor or a sensor.
 * @author Loïc David
 * @see Motor
 * @see Sensor
 */
public class MoveNodeView extends Stage {
    
    private final Circle node;
    private final Side side;

    /**
     * Create a new instance of MoveNodeView.
     * @param node Sensor or Motor to move.
     * @param side Side of the shoe (left or right).
     * @see MoveNodeView
     * @see Sensor
     * @see Motor
     */
    public MoveNodeView(Circle node, Side side) {
        this.node = node;
        this.side = side;
        initialize();
    }

    /**
     * Initialize the MoveNodeView.<br>
     * Create the components and implement interactions.
     */
    private void initialize() {
        
        // Boxes.
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setPrefWidth(250);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // Components.
        Label xLabel = new Label("X:");
        Label yLabel = new Label("Y:");
        TextField xTextField = new TextField();
        xTextField.setAlignment(Pos.CENTER_RIGHT);
        TextField yTextField = new TextField();
        yTextField.setAlignment(Pos.CENTER_RIGHT);
        Button acceptButton = new Button("Accept");
        Button cancelButton = new Button("Cancel");
        
        // Connect components to methods.
        // Move the sensor/motor.
        acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    double x = ShoeView.shoeToViewX(Double.parseDouble(xTextField.getText()), side);
                    double y = ShoeView.shoeToViewY(Double.parseDouble(yTextField.getText()), side);
                    if (x < 0 || y < 0 || x > 262 || y > 586) {
                        throw new Exception();
                    }
                    else {
                        node.setCenterX(x);
                        node.setCenterY(y);
                    }
                    hide();
                } catch (Exception e) {
                    (new ErrorWindow("Invalide values!")).show();
                }
            }
        });
        // Close the window.
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hide();
            }
        });
        
        // Add components to boxes.
        gridPane.add(xLabel, 0, 0);
        gridPane.add(yLabel, 0, 1);
        gridPane.add(xTextField, 1, 0);
        gridPane.add(yTextField, 1, 1);
        hBox.getChildren().addAll(cancelButton, acceptButton);
        vBox.getChildren().addAll(gridPane, hBox);

        // Initialize the window.
        Scene scene = new Scene(vBox);
        setScene(scene);
        setResizable(false);
        sizeToScene();
        setTitle("Move");
        
    }

}