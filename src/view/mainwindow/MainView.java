package view.mainwindow;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.MenuController;
import model.Shoe;
import model.TimeController;
import view.mainwindow.shoe.ShoeView;


/**
 * Contain all the components of the window.
 * @author Loïc David
 */
public class MainView extends VBox {
    
    private final MenuController menuController;
    private final TimeController timeController;
    private final Shoe shoeModelLeft;
    private final Shoe shoeModelRight;

    /**
     * Create a new instance of MainView.
     * @param menuController The MenuController of the software.
     * @param timeController The TimeController of the software.
     * @param shoeModelLeft The left Shoe of the software.
     * @param shoeModelRight The right Shoe of the software.
     * @see MainView
     */
    public MainView(MenuController menuController, TimeController timeController, Shoe shoeModelLeft, Shoe shoeModelRight) {
        this.menuController = menuController;
        this.timeController = timeController;
        this.shoeModelLeft = shoeModelLeft;
        this.shoeModelRight = shoeModelRight;
        initialize();
    }
    
    /**
     * Initialize the MainWindow.
     */
    private void initialize() {
           
        // Boxes.
        VBox shoeVBoxLeft = new VBox();
        shoeVBoxLeft.setStyle("-fx-border-width: 0 1 0 0;-fx-border-color: black");
        HBox leftLabelsHBox = new HBox();
        leftLabelsHBox.setStyle("-fx-border-width: 0 0 1 0;-fx-border-color: black");
        VBox shoeVBoxRight = new VBox();
        shoeVBoxRight.setStyle("-fx-border-width: 0 0 0 1;-fx-border-color: black");
        HBox rightLabelsHBox = new HBox();
        rightLabelsHBox.setStyle("-fx-border-width: 0 0 1 0;-fx-border-color: black");
        HBox shoesHBox = new HBox(10);
        
        // Components.
        // Right shoe.
        Label rightLabel = new Label("  Right shoe:");
        rightLabel.setStyle("-fx-font-weight: bold");
        ShoeSettingsView shoeSettingsViewRight = new ShoeSettingsView();
        ShoeView shoeViewRight = new ShoeView(shoeModelRight, shoeSettingsViewRight);
        CoordinatesView rightCoordinates = new CoordinatesView(shoeViewRight);
        // Left shoe.
        Label leftLabel = new Label("  Left shoe:");
        leftLabel.setStyle("-fx-font-weight: bold");
        ShoeSettingsView shoeSettingsViewLeft = new ShoeSettingsView();
        ShoeView shoeViewLeft = new ShoeView(shoeModelLeft, shoeSettingsViewLeft);
        CoordinatesView leftCoordinates = new CoordinatesView(shoeViewLeft);
        // Others.
        LegendView legendView = new LegendView(shoeModelLeft);
        MenuView menuView = new MenuView(menuController, shoeViewLeft, shoeViewRight);
        TimeView timeView = new TimeView(timeController);
        
        
        // Add components to boxes.
        shoeVBoxLeft.getChildren().addAll(leftLabel, shoeSettingsViewLeft, shoeViewLeft, leftCoordinates);
        shoeVBoxRight.getChildren().addAll(rightLabel, shoeSettingsViewRight, shoeViewRight, rightCoordinates);
        shoesHBox.getChildren().addAll(shoeVBoxLeft,legendView, shoeVBoxRight);
        getChildren().addAll(menuView, shoesHBox, timeView);
        
    }
    
}