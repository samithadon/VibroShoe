package view.mainwindow;

import view.mainwindow.shoe.ShoeView;
import view.otherwindow.editshoe.ShoeEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.MenuController;
import view.otherwindow.CSVSelector;
import view.otherwindow.SaveSelector;
import view.otherwindow.SerialSettingsView;


/**
 * Menu of the software.
 * @author Loïc David
 */
public class MenuView extends MenuBar {
    
    private final MenuController menuController;
    private final ShoeView shoeViewLeft;
    private final ShoeView shoeViewRight;
    
    /**
     * Create a new instance of MenuView.
     * @param menuController The MenuController of the software.
     * @param shoeViewLeft The left ShoeView of the software.
     * @param shoeViewRight The right ShoeView of the software.
     */
    public MenuView(MenuController menuController, ShoeView shoeViewLeft, ShoeView shoeViewRight) {
        this.menuController = menuController;
        this.shoeViewLeft = shoeViewLeft;
        this.shoeViewRight = shoeViewRight;
        initialize();
    }

    /**
     * Initialize the MenuView.<br>
     * Create the menus and connect them to their functions.
     */
    private void initialize() {
        
        // File.
        
        // Create menus.
        Menu fileMenu = new Menu("File");
        MenuItem csvMenuItem = new MenuItem("Read a CSV file");
        MenuItem serialMenuItem = new MenuItem("Read data from serial");
        MenuItem saveMenuItem = new MenuItem("Save serial data");
        MenuItem quitMenuItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(csvMenuItem, serialMenuItem, saveMenuItem, quitMenuItem);
        
        // Connect menus to methods.
        // csvMenuItem.
        // Open a window to choose teh CSV files to read.
        csvMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                (new CSVSelector(menuController)).show();
            }
        });
        // serialMenuItem.
        // Start the serial reading.
        serialMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuController.readSerial();
            }
        });
        // saveMenuItem.
        // Open a window to choose where we want to save the serial data.
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                (new SaveSelector(menuController)).show();
            }
        });
        // quitMenuItem.
        // Quit the software.
        quitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuController.quit();
            }
        });
        
        
        // Settings.
        
        // Create menus.
        Menu settingsMenu = new Menu("Settings");
        MenuItem serialSettingsMenuItem = new MenuItem("Serial");
        MenuItem leftShoeMenuItem = new MenuItem("Left shoe");
        MenuItem rightShoeMenuItem = new MenuItem("Right shoe");
        settingsMenu.getItems().addAll(serialSettingsMenuItem, leftShoeMenuItem, rightShoeMenuItem);
        
        // Connect menus to methods.
        // serialSettingsMenuItem.
        // Open a window to choose the serial settings.
        serialSettingsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                (new SerialSettingsView(menuController)).show();
            }
        });
        // leftShoeMenuItem.
        // Open a window to configure the left shoe.
        leftShoeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                (new ShoeEditor(shoeViewLeft,  menuController)).show();
            }
        });
        // rightShoeMenuItem.
        // Open a window to configure the right shoe.
        rightShoeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                (new ShoeEditor(shoeViewRight,  menuController)).show();
            }
        });
        
        getMenus().addAll(fileMenu, settingsMenu);
        
    }  
    
}