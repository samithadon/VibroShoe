package view.otherwindow;

import exception.CSVFileException;
import java.io.File;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.MenuController;


/**
 * Window used to select the CSV files to read.
 * @author Loïc David
 */
public class CSVSelector extends Stage {
    
    private final MenuController menuController;
    
    /**
     * Create a new instance of CSVSelector.
     * @param menuController The MenuController of the software.
     * @see CSVSelector
     */
    public CSVSelector(MenuController menuController) {
        this.menuController = menuController;
        initialize();
    }

    /**
     * Initialize the CSVSelector.<br>
     * Create components and implement interactions.
     */
    private void initialize() {
        
        // Create box.
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(20, 10, 10, 10));
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox(5);
        hBox3.setAlignment(Pos.CENTER_RIGHT);
        hBox3.setPadding(new Insets(0, 0, 0, 50));
        
        // Create components.
        Label leftLabel = new Label("CSV file for left shoe:");
        TextField leftTextField = new TextField();
        leftTextField.setMinWidth(300);
        leftTextField.setMaxWidth(300);
        Label rightLabel = new Label("CSV file for right shoe:");
        TextField rightTextField = new TextField();
        rightTextField.setMinWidth(300);
        rightTextField.setMaxWidth(300);
        Button leftButton = new Button("...");
        Button rightButton = new Button("...");
        Button cancelButton = new Button("Cancel");
        Button acceptButton = new Button("Accept");
        
        // Connect components to methods.
        // Close the window.
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hide();
            }
        });
        // Start the CSV reading.
        acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    menuController.readCSV(leftTextField.getText(), rightTextField.getText());
                } catch (CSVFileException ex) {
                    (new ErrorWindow(ex.toString())).show();
                }
                hide();
            }
        });
        // Open a file chooser for the left shoe's CSV file.
        leftButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select left CSV file");
                fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("CSV Files", "*.csv"),
                        new ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                leftTextField.setText(selectedFile.toPath().toString());
            }
        });
        // Open a file chooser for the right shoe's CSV file.
        rightButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select right CSV file");
                fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("CSV Files", "*.csv"),
                        new ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(new Stage());
                rightTextField.setText(selectedFile.toPath().toString());
            }
        });
        
        // Add components to box.
        hBox1.getChildren().addAll(leftTextField, leftButton);
        hBox2.getChildren().addAll(rightTextField, rightButton);
        leftVBox.getChildren().addAll(leftLabel, hBox1);
        rightVBox.getChildren().addAll(rightLabel, hBox2);
        hBox3.getChildren().addAll(cancelButton, acceptButton);
        vBox.getChildren().addAll(leftVBox, rightVBox, hBox3);
        
        // Initialize window.
        Scene scene = new Scene(vBox);
        setTitle("Select CSV files");
        setScene(scene);
        setResizable(false);
        sizeToScene();
    }
    
}