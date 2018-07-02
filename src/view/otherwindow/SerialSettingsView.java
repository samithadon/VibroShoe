package view.otherwindow;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuController;


/**
 * Window used to choose the settings of the serial links.
 * @author Loïc David
 */
public class SerialSettingsView extends Stage {
    
    private final MenuController menuController;
    
    /**
     * Create a new instance of SerialSettingsView.
     * @param menuController The MenuController of the software.
     * @see SerialSettingsView
     */
    public SerialSettingsView(MenuController menuController) {
        this.menuController = menuController;
        initialize();
    }

    /**
     * Initialize the SerialSettingsView.
     */
    private void initialize() {
        
        // Boxes.
        // Left shoe.
        GridPane leftGrid = new GridPane();
        leftGrid.setHgap(10);
        leftGrid.setVgap(10);
        VBox leftVBox = new VBox(20);
        leftVBox.setPadding(new Insets(10));
        // Right shoe.
        GridPane rightGrid = new GridPane();
        rightGrid.setHgap(10);
        rightGrid.setVgap(10);
        VBox rightVBox = new VBox(20);
        rightVBox.setPadding(new Insets(10));
        // Other boxes.
        HBox mainHBox = new HBox(10);
        VBox mainVBox = new VBox(20);
        mainVBox.setPadding(new Insets(10));
        HBox bottomHBox = new HBox(5);
        bottomHBox.setAlignment(Pos.CENTER_RIGHT);
        
        
        // Components.
        // Left shoe.
        TextField leftPortTextField = new TextField();
        leftPortTextField.setMinWidth(150);
        leftPortTextField.setMaxWidth(150);
        ChoiceBox<Integer> leftRateChoiceBox = new ChoiceBox<>();
        leftRateChoiceBox.getItems().addAll(75, 110, 150, 300, 600, 1200, 1800, 2400, 4800, 7200,
                                            9600, 14400, 19200, 38400, 56000, 57600, 115200, 128000);
        leftRateChoiceBox.setMinWidth(150);
        leftRateChoiceBox.setMaxWidth(150);
        ChoiceBox<Integer> leftBitsChoiceBox = new ChoiceBox<>();
        leftBitsChoiceBox.getItems().addAll(5, 6, 7, 8);
        leftBitsChoiceBox.setMinWidth(150);
        leftBitsChoiceBox.setMaxWidth(150);
        ChoiceBox<Double> leftStopChoiceBox = new ChoiceBox<>();
        leftStopChoiceBox.getItems().addAll(1., 1.5, 2.);
        leftStopChoiceBox.setMinWidth(150);
        leftStopChoiceBox.setMaxWidth(150);
        ChoiceBox<String> leftParityChoiceBox = new ChoiceBox<>();
        leftParityChoiceBox.getItems().addAll("None", "Odd", "Even", "Mark", "Space");
        leftParityChoiceBox.setMinWidth(150);
        leftParityChoiceBox.setMaxWidth(150);
        Label leftPortLabel = new Label("Port:");
        GridPane.setHalignment(leftPortLabel, HPos.RIGHT);
        Label leftRateLabel = new Label("Bits per second:");
        GridPane.setHalignment(leftRateLabel, HPos.RIGHT);
        Label leftBitsLabel = new Label("Data bits:");
        GridPane.setHalignment(leftBitsLabel, HPos.RIGHT);
        Label leftStopLabel = new Label("Stop bits:");
        GridPane.setHalignment(leftStopLabel, HPos.RIGHT);
        Label leftParityLabel = new Label("Parity:");
        GridPane.setHalignment(leftParityLabel, HPos.RIGHT);
        Label leftShoeLabel = new Label("Left shoe:");
        leftShoeLabel.setStyle("-fx-font-weight: bold");
        // Right shoe.
        TextField rightPortTextField = new TextField();
        rightPortTextField.setMinWidth(150);
        rightPortTextField.setMaxWidth(150);
        ChoiceBox<Integer> rightRateChoiceBox = new ChoiceBox<>();
        rightRateChoiceBox.getItems().addAll(75, 110, 150, 300, 600, 1200, 1800, 2400, 4800, 7200,
                                            9600, 14400, 19200, 38400, 56000, 57600, 115200, 128000);
        rightRateChoiceBox.setMinWidth(150);
        rightRateChoiceBox.setMaxWidth(150);
        ChoiceBox<Integer> rightBitsChoiceBox = new ChoiceBox<>();
        rightBitsChoiceBox.getItems().addAll(5, 6, 7, 8);
        rightBitsChoiceBox.setMinWidth(150);
        rightBitsChoiceBox.setMaxWidth(150);
        ChoiceBox<Double> rightStopChoiceBox = new ChoiceBox<>();
        rightStopChoiceBox.getItems().addAll(1., 1.5, 2.);
        rightStopChoiceBox.setMinWidth(150);
        rightStopChoiceBox.setMaxWidth(150);
        ChoiceBox<String> rightParityChoiceBox = new ChoiceBox<>();
        rightParityChoiceBox.getItems().addAll("None", "Odd", "Even", "Mark", "Space");
        rightParityChoiceBox.setMinWidth(150);
        rightParityChoiceBox.setMaxWidth(150);
        Label rightPortLabel = new Label("Port:");
        GridPane.setHalignment(rightPortLabel, HPos.RIGHT);
        Label rightRateLabel = new Label("Bits per second:");
        GridPane.setHalignment(rightRateLabel, HPos.RIGHT);
        Label rightBitsLabel = new Label("Data bits:");
        GridPane.setHalignment(rightBitsLabel, HPos.RIGHT);
        Label rightStopLabel = new Label("Stop bits:");
        GridPane.setHalignment(rightStopLabel, HPos.RIGHT);
        Label rightParityLabel = new Label("Parity:");
        GridPane.setHalignment(rightParityLabel, HPos.RIGHT);
        Label rightShoeLabel = new Label("Right shoe:");
        rightShoeLabel.setStyle("-fx-font-weight: bold");
        // Other components.
        Button acceptButton = new Button("Accept");
        Button cancelButton = new Button("Cancel");
        
        // Add components to boxes.
        // Left shoe.
        leftGrid.add(leftPortLabel, 0, 0);
        leftGrid.add(leftRateLabel, 0, 1);
        leftGrid.add(leftBitsLabel, 0, 2);
        leftGrid.add(leftStopLabel, 0, 3);
        leftGrid.add(leftParityLabel, 0, 4);
        leftGrid.add(leftPortTextField, 1, 0);
        leftGrid.add(leftRateChoiceBox, 1, 1);
        leftGrid.add(leftBitsChoiceBox, 1, 2);
        leftGrid.add(leftStopChoiceBox, 1, 3);
        leftGrid.add(leftParityChoiceBox, 1, 4);
        leftVBox.getChildren().addAll(leftShoeLabel, leftGrid);
        // Right shoe.
        rightGrid.add(rightPortLabel, 0, 0);
        rightGrid.add(rightRateLabel, 0, 1);
        rightGrid.add(rightBitsLabel, 0, 2);
        rightGrid.add(rightStopLabel, 0, 3);
        rightGrid.add(rightParityLabel, 0, 4);
        rightGrid.add(rightPortTextField, 1, 0);
        rightGrid.add(rightRateChoiceBox, 1, 1);
        rightGrid.add(rightBitsChoiceBox, 1, 2);
        rightGrid.add(rightStopChoiceBox, 1, 3);
        rightGrid.add(rightParityChoiceBox, 1, 4);
        rightVBox.getChildren().addAll(rightShoeLabel, rightGrid);
        // Others.
        bottomHBox.getChildren().addAll(cancelButton, acceptButton);
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        mainVBox.getChildren().addAll(mainHBox, bottomHBox);
        
        // Load the current settings.
        // Left shoe.
        leftPortTextField.setText(menuController.getLeftShoe().getSerialReader().getPort());
        leftRateChoiceBox.setValue(menuController.getLeftShoe().getSerialReader().getDataRate());
        leftBitsChoiceBox.setValue(menuController.getLeftShoe().getSerialReader().getDataBits());
        leftStopChoiceBox.setValue(stopToDouble(menuController.getLeftShoe().getSerialReader().getStopBits()));
        leftParityChoiceBox.setValue(intToParity(menuController.getLeftShoe().getSerialReader().getParity()));
        // Right shoe.
        rightPortTextField.setText(menuController.getRightShoe().getSerialReader().getPort());
        rightRateChoiceBox.setValue(menuController.getRightShoe().getSerialReader().getDataRate());
        rightBitsChoiceBox.setValue(menuController.getRightShoe().getSerialReader().getDataBits());
        rightStopChoiceBox.setValue(stopToDouble(menuController.getRightShoe().getSerialReader().getStopBits()));
        rightParityChoiceBox.setValue(intToParity(menuController.getRightShoe().getSerialReader().getParity()));
        
        // Connect components to methods.
        // Save the new settings and update the SerialReaders.
        acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                menuController.updateSerialSettings(menuController.getLeftShoe(),
                                                    leftPortTextField.getText(),
                                                    leftRateChoiceBox.getValue(),
                                                    leftBitsChoiceBox.getValue(),
                                                    doubleToStop(leftStopChoiceBox.getValue()),
                                                    parityToInt(leftParityChoiceBox.getValue()));
                menuController.updateSerialSettings(menuController.getRightShoe(),
                                                    rightPortTextField.getText(),
                                                    rightRateChoiceBox.getValue(),
                                                    rightBitsChoiceBox.getValue(),
                                                    doubleToStop(rightStopChoiceBox.getValue()),
                                                    parityToInt(rightParityChoiceBox.getValue()));
                hide();
            }
        });
        // Close the window.
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hide();
            }
            
        });

        // Initialize window.
        Scene scene = new Scene(mainVBox);
        setScene(scene);
        setTitle("Serial settings");
        setResizable(false);
        sizeToScene();
        
    }
    
    /**
     * Convert the int value used by the RXTX library to the equivalent parity.
     * @param i The int value of the parity.
     * @return The parity.
     */
    private String intToParity(int i) {
        switch (i) {
            case 0: return "None";
            case 1: return "Odd";
            case 2: return "Even";
            case 3: return "Mark";
            case 4: return "Space";
            default: return "Error";
        }
    }
    
    /**
     * Convert the parity to the equivalent int value used by the RXTX library.
     * @param p The parity.
     * @return The int value of the parity.
     */
    private int parityToInt(String p) {
        switch (p) {
            case "None": return 0;
            case "Odd": return 1;
            case "Even": return 2;
            case "Mark": return 3;
            case "Space": return 4;
            default: return -1;
        }
    }
    
    /**
     * Convert the int value used by the RXTX library to the equivalent stop
     * bits.
     * @param s The int value of the stop bits.
     * @return The stop bits.
     */
    private double stopToDouble(int s) {
        switch (s) {
            case 1: return 1.;
            case 2: return 2.;
            case 3: return 1.5;
            default: return -1.;
        }
    }
    
     /**
     * Convert the stop bits to the equivalent int value used by the RXTX
     * library.
     * @param d The stop bits.
     * @return The int value of the stop bits.
     */
    private int doubleToStop(double d) {
        if (d == 1.) {return 1;}
        if (d == 2.) {return 2;}
        if (d == 1.5) {return 3;}
        return -1;
    }
    
}