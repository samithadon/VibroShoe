package view.mainwindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import view.mainwindow.shoe.ShoeView;


/**
 * Contain checkBoxes to choose what we want to display on the ShoeView.
 * @author Loïc David
 * @see ShoeView
 */
public class ShoeSettingsView extends GridPane {
    
    CheckBox axisCheckBox;
    CheckBox gridCheckBox;
    CheckBox sensorCheckBox;
    CheckBox motorCheckBox;
    
    /**
     * Create a new instance of ShoeSettingsView.
     */
    public ShoeSettingsView() {
        initialize();
    }

    /**
     * Initialize the ShoeSettingsView.
     */
    private void initialize() {
        
        // Set style.
        setPadding(new Insets(5, 0, 5, 0));
        setAlignment(Pos.CENTER);
        setHgap(100);
        setVgap(10);
        
        // Create components.
        axisCheckBox = new CheckBox("Axis");
        axisCheckBox.setSelected(true);
        gridCheckBox = new CheckBox("Grid");
        gridCheckBox.setSelected(true);
        sensorCheckBox = new CheckBox("Sensors");
        sensorCheckBox.setSelected(true);
        motorCheckBox = new CheckBox("Motors");
        motorCheckBox.setSelected(true);
        
        // Add components to the grid.
        add(axisCheckBox, 0, 0);
        add(gridCheckBox, 0, 1);
        add(sensorCheckBox, 1, 0);
        add(motorCheckBox, 1, 1);
        
    }

    /**
     * Getter for the axisCheckBox.
     * @return The axisCheckBox
     */
    public CheckBox getAxisCheckBox() {
        return axisCheckBox;
    }

    /**
     * Getter for the gridCheckBox.
     * @return The gridCheckBox
     */
    public CheckBox getGridCheckBox() {
        return gridCheckBox;
    }

    /**
     * Getter for the sensorCheckBox.
     * @return The sensorCheckBox
     */
    public CheckBox getSensorCheckBox() {
        return sensorCheckBox;
    }

    /**
     * Getter for the motorCheckBox.
     * @return The motorCheckBox
     */
    public CheckBox getMotorCheckBox() {
        return motorCheckBox;
    }

}