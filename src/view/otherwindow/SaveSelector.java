package view.otherwindow;

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
import javafx.stage.Stage;
import model.MenuController;


/**
 * Window used to select the ouput files to save the data from serial.
 * @author Loïc David
 */
public class SaveSelector extends Stage {
    
    private final MenuController menuController;
    
    /**
     * Create a new instance of SaveSelector.
     * @param menuController The MenuController of the software.
     */
    public SaveSelector(MenuController menuController) {
        this.menuController = menuController;
        initialize();
    }
    
    /**
     * Initialize the SaveSelector.<br>
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
        Label leftLabel = new Label("save file for left shoe (.csv):");
        TextField leftTextField = new TextField();
        leftTextField.setMinWidth(300);
        leftTextField.setMaxWidth(300);
        Label rightLabel = new Label("save file for right shoe (.csv):");
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
        // Save the files.
        acceptButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                menuController.saveData(leftTextField.getText(), rightTextField.getText());
                hide();
            }
        });
        // Open a file chooser for the left shoe's CSV output file.
        leftButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select left save file");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showSaveDialog(new Stage());
                leftTextField.setText(selectedFile.toPath().toString());
            }
        });
        // Open a file chooser for the right shoe's CSV output file.
        rightButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select right save file");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showSaveDialog(new Stage());
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
        setTitle("Select save files");
        setScene(scene);
        
    }
    
}