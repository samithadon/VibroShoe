package view.mainwindow;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import model.Shoe;
import view.mainwindow.shoe.MotorView;
import view.mainwindow.shoe.SensorView;


/**
 * Legend of the sensors' and motors' colors. 
 * @author Loïc David
 * @see MotorView
 * @see SensorView
 */
public class LegendView extends GridPane {
    
    private final Shoe shoe;
    
    /**
     * Create a new instance of LegendView.
     * @param shoe One of the Shoe (used to know the data type).
     * @see LegendView
     */
    public LegendView(Shoe shoe) {
        this.shoe = shoe;
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
        Label dataTypeLabel = new Label();
        dataTypeLabel.setTextAlignment(TextAlignment.CENTER);
        if (shoe.getSerialReader().dataTypeProperty().getValue() == 0) {
            dataTypeLabel.setText("Serial data type:\nAll data");
        }
        else {
            dataTypeLabel.setText("Serial data type:\nOnly sensors");
        }
        Label motorLabel = new Label("Motor\n(%)");
        motorLabel.setTextAlignment(TextAlignment.CENTER);
        Label sensorLabel = new Label("Sensor\n(mmHg)");
        sensorLabel.setTextAlignment(TextAlignment.CENTER);
        String pathMotor = new File(".//.//ressources//legendmotor.png").toURI().toString();
        String pathSensor = new File(".//.//ressources//legendsensor.png").toURI().toString();
        ImageView motorLegend = new ImageView(new Image(pathMotor));
        ImageView sensorLegend = new ImageView(new Image(pathSensor));
        
        // Add interactions to components.
        shoe.getSerialReader().dataTypeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() == 0) {
                    dataTypeLabel.setText("Serial data type:\nAll data");
                }
                else {
                    dataTypeLabel.setText("Serial data type:\nOnly sensors");
                }
            }
        });
        
        // Add components to the grid.
        getColumnConstraints().addAll(col1, col2);
        add(dataTypeLabel, 0, 0, 2, 1);
        add(motorLabel, 0, 1);
        add(sensorLabel, 1, 1);
        add(motorLegend, 0, 2);
        add(sensorLegend, 1, 2);
        
    }
    
}