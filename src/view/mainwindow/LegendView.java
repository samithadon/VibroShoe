package view.mainwindow;

import java.io.File;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import view.mainwindow.shoe.MotorView;
import view.mainwindow.shoe.SensorView;


/**
 * Legend of the sensors' and motors' colors. 
 * @author Loïc David
 * @see MotorView
 * @see SensorView
 */
public class LegendView extends GridPane {
    
    /**
     * Create a new instance of LegendView.
     * @see LegendView
     */
    public LegendView() {
        initialize();
    }

    /**
     * Initialize the LegendView.
     */
    private void initialize() {
        
        // Configure the grid.
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(7, 0, 0, 0));
        setAlignment(Pos.CENTER);
        ColumnConstraints col1 = new ColumnConstraints(50);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints(50);
        col2.setHalignment(HPos.CENTER);
        
        // Create components.
        Label motorLabel = new Label("Motor\n(%)");
        motorLabel.setTextAlignment(TextAlignment.CENTER);
        Label sensorLabel = new Label("Sensor\n(mmHg)");
        sensorLabel.setTextAlignment(TextAlignment.CENTER);
        String pathMotor = new File(".//.//ressources//legendmotor.png").toURI().toString();
        String pathSensor = new File(".//.//ressources//legendsensor.png").toURI().toString();
        ImageView motorLegend = new ImageView(new Image(pathMotor));
        ImageView sensorLegend = new ImageView(new Image(pathSensor));
        
        // Add components to the grid.
        getColumnConstraints().addAll(col1, col2);
        add(motorLabel, 0, 0);
        add(sensorLabel, 1, 0);
        add(motorLegend, 0, 1);
        add(sensorLegend, 1, 1);
        
    }
    
}