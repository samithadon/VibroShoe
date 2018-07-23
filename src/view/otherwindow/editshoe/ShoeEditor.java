package view.otherwindow.editshoe;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuController;
import model.Motor;
import model.Sensor;
import view.mainwindow.shoe.MotorView;
import view.mainwindow.shoe.SensorView;
import view.mainwindow.shoe.ShoeView;
import view.otherwindow.ErrorWindow;


/**
 * Window used to edit a shoe.<br>
 * With this window, motors and sensors can be moved, added or removed.
 * @author Loïc David.
 * @see Motor
 * @see Sensor
 */
public class ShoeEditor extends Stage {
    
    private final MenuController menuController;
    private final ShoeView shoeView;
    
    /**
     * Create a new instance of ShoeEditor.
     * @param shoeView The view of the shoe to edit.
     * @param menuController The MenuController of the software.
     * @see ShoeEditor
     */
    public ShoeEditor(ShoeView shoeView, MenuController menuController) {
        this.menuController = menuController;
        this.shoeView = shoeView;
        initialize();
    }

    /**
     * Initialize the ShoeEditor.<br>
     * Create components and implement interactions.
     */
    private void initialize() {
        
        // Components.
        EditShoeView editShoeView = new EditShoeView(shoeView.getModel());
        EditCoordinatesView coordinatesView = new EditCoordinatesView(editShoeView);
        Button resetButton = new Button("Reset");
        Button cancelButton = new Button("Cancel");
        Button acceptButton = new Button("Accept");
        
        // Connect components to methods.
        // Reset the window.
        resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editShoeView.update();
            }
        });
        // Close the window.
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hide();
            }
        });
        // Save the modification and update the shoe's model and view.
        acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ArrayList<Double> xSensors = new ArrayList<>();
                    ArrayList<Double> ySensors = new ArrayList<>();
                    ArrayList<Double> xMotors = new ArrayList<>();
                    ArrayList<Double> yMotors = new ArrayList<>();
                    for (SensorView sensorView : editShoeView.getSensors()){
                        xSensors.add(ShoeView.viewToShoeX(sensorView.centerXProperty().getValue(), shoeView.getModel().getSide()));
                        ySensors.add(ShoeView.viewToShoeY(sensorView.centerYProperty().getValue(), shoeView.getModel().getSide()));
                    }
                    for (MotorView motorView : editShoeView.getMotors()){
                        xMotors.add(ShoeView.viewToShoeX(motorView.centerXProperty().getValue(), shoeView.getModel().getSide()));
                        yMotors.add(ShoeView.viewToShoeY(motorView.centerYProperty().getValue(), shoeView.getModel().getSide()));
                    }
                    menuController.stopReading();
                    menuController.updateShoeSettings(shoeView.getModel().getSide(), xSensors, ySensors, xMotors, yMotors, editShoeView.getSensorGroups());
                } catch (IOException | NumberFormatException ex) {
                    (new ErrorWindow("Error updating shoe settings!")).show();
                }
                hide();
            }
        });
        
        // Boxes.
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(15, 10, 10, 10));
        hBox.setStyle("-fx-border-width: 1 0 0 0;-fx-border-color: black"); 
        VBox vBox = new VBox();
        
        // Add components to boxes.
        hBox.getChildren().addAll(resetButton, cancelButton, acceptButton);
        vBox.getChildren().addAll(editShoeView, coordinatesView, hBox);
        
        // Initialize window.
        Scene scene = new Scene(vBox);
        setScene(scene);
        setResizable(false);
        sizeToScene();
        setTitle("Settings " + shoeView.getModel().getSide().toString() + " shoe");
        
    }
    
}