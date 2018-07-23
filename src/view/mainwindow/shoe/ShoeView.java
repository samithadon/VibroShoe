package view.mainwindow.shoe;

import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.CoP;
import model.Motor;
import model.Sensor;
import model.Shoe;
import model.Shoe.Side;
import view.mainwindow.ShoeSettingsView;


/**
 * View of a shoe.<br>
 * This view is a representation of a shoe model, it contains all the SensorView
 * and MotorsView, the CoPView, an image of the shoe, a grid and the axis.
 * @author Loïc David
 * @see Shoe
 * @see SensorView
 * @see MotorView
 * @see CoPView
 */
public class ShoeView extends Pane {
    
    private final Shoe model;
    private final ShoeSettingsView setting;
    private final DoubleProperty xCursor = new SimpleDoubleProperty(0);
    private final DoubleProperty yCursor = new SimpleDoubleProperty(0);
    private final ArrayList<MotorView> motors = new ArrayList<>();
    private final ArrayList<SensorView> sensors = new ArrayList<>();
    private EventHandler<MouseEvent> cursorPosHandler;
    private EventHandler<MouseEvent> cursorInHandler;
    private EventHandler<MouseEvent> cursorOutHandler;
    
    /**
     * Create a new instance of ShoeView.
     * @param model The model of the shoe.
     * @param setting The ShoeSettingView linked to this ShoeView.
     * @see ShoeView
     */
    public ShoeView(Shoe model, ShoeSettingsView setting) {
        this.model = model;
        this.setting = setting;
        initialize();
        model.updatedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                update();
            }
        });
    }
    
    /**
     * Initialize the ShoeView.<br>
     * Create all the components of the ShoeView and implements some
     * interactions.
     */
    private void initialize() {
        
        // Border.
        setStyle("-fx-border-width: 1 0 1 0;-fx-border-color: black");
        setMaxSize(262, 586);
        
        // Shoe image.
        String path = new File(".//.//ressources//" + model.getSide().toString() + "Shoe.png").toURI().toString();
        Image shoeImage = new Image(path);
        ImageView shoeImageView = new ImageView(shoeImage);
        getChildren().add(shoeImageView);
        
        // Axis.
        // The lines are created and bind to their checkBox in the ShoeSettingsView.
        Line xAxis = new Line(0, shoeToViewY(0, model.getSide()), 262, shoeToViewY(0, model.getSide()));
        xAxis.setStrokeWidth(2);
        xAxis.visibleProperty().bind(setting.getAxisCheckBox().selectedProperty());
        Line yAxis = new Line(shoeToViewX(0, model.getSide()), 0, shoeToViewX(0, model.getSide()), 586);
        yAxis.setStrokeWidth(2);
        yAxis.visibleProperty().bind(setting.getAxisCheckBox().selectedProperty());
        getChildren().addAll(xAxis, yAxis);
        
        // Grid.
        // The lines are created and bind to their checkBox in the ShoeSettingsView.
        for (int i = -21; i < 8; i++) {
            Line line = new Line(0, shoeToViewY(i * 10, model.getSide()), 262, shoeToViewY(i * 10, model.getSide()));
            line.visibleProperty().bind(setting.getGridCheckBox().selectedProperty());
            getChildren().add(line);
        }
        int start;
        int end;
        if (model.getSide().equals(Side.LEFT)) {
            start = -9;
            end = 4;
        }
        else {
            start = -3;
            end = 10;
        }
        for (int i = start; i < end; i++) {
            Line line = new Line(shoeToViewX(i * 10, model.getSide()), 0, shoeToViewX(i * 10, model.getSide()), 586);
            line.visibleProperty().bind(setting.getGridCheckBox().selectedProperty());
            getChildren().add(line);
        }
        
        // Motors.
        // The motorViews are created and bind to their checkBox in the ShoeSettingsView.
        for (Motor motor : model.getMotors()) {
            MotorView motorView = new MotorView(motor);
            motors.add(motorView);
            motorView.visibleProperty().bind(setting.getMotorCheckBox().selectedProperty());
            getChildren().add(motorView);
        }
        
        // Sensors.
        // The sensorViews are created and bind to their checkBox in the ShoeSettingsView.
        for (Sensor sensor : model.getSensors()) {
            SensorView sensorView = new SensorView(sensor);
            sensors.add(sensorView);
            sensorView.visibleProperty().bind(setting.getSensorCheckBox().selectedProperty());
            getChildren().add(sensorView);
        }
        
        // CoP.
        CoP cop = model.getCop();
        CoPView copView = new CoPView(cop);
        getChildren().add(copView);
        
        // Motors' labels.
        // Can't be created when creating the motorViews, because they whould be
        // under all the components created after them.
        int i = 0;
        for (Motor motor : model.getMotors()) {
            MotorLabelView motorLabel = new MotorLabelView(i + 1, motor);
            motors.get(i).setLabelView(motorLabel);
            getChildren().add(motorLabel);
            i++;
        }
        
        // Sensors' labels.
        // Can't be created when creating the sensorViews, because they whould be
        // under all the components created after them.
        i = 0;
        for (Sensor sensor : model.getSensors()) {
            SensorLabelView sensorLabel = new SensorLabelView(i + 1, sensor);
            sensors.get(i).setLabelView(sensorLabel);
            getChildren().add(sensorLabel);
            i++;
        }
        
        // Cursor position.
        cursorPosHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xCursor.setValue(viewToShoeX(event.getX(), model.getSide()));
                yCursor.setValue(viewToShoeY(event.getY(), model.getSide()));
            }
        };
        addEventHandler(MouseEvent.MOUSE_MOVED, cursorPosHandler);
        addEventHandler(MouseEvent.MOUSE_DRAGGED, cursorPosHandler);
        
        // Cursor type.
        cursorInHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR);
            }
        };
        addEventHandler(MouseEvent.MOUSE_ENTERED, cursorInHandler);
        cursorOutHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT);
            }
        };
        addEventHandler(MouseEvent.MOUSE_EXITED, cursorOutHandler);
        
    }
    
    /**
     * Clear all the components of the ShoeView.
     */
    private void reset() {
        getChildren().clear();
        motors.clear();
        sensors.clear();
        removeEventHandler(MouseEvent.MOUSE_MOVED, cursorPosHandler);
        removeEventHandler(MouseEvent.MOUSE_ENTERED, cursorInHandler);
        removeEventHandler(MouseEvent.MOUSE_EXITED, cursorOutHandler);
    }
    
    /**
     * Reset and initialize the ShoeView.
     */
    private void update() {
        reset();
        initialize();
    }
    
    /**
     * Translation from the shoe coordinates system to the view coordinates
     * system for X.
     * @param x X coordinate in the shoe.
     * @param side Side of the shoe (left or right).
     * @return X coordinate in the view.
     */
    public static double shoeToViewX(double x, Side side) {
        double result;
        if (side.equals(Side.RIGHT)) {
            result = x + 36;
        }
        else {
            result = x + 95;
        }
        return 2 * result;
    }
    
    /**
     * Translation from the view coordinates system to the shoe coordinates
     * system for X.
     * @param x X coordinate in the view.
     * @param side Side of the shoe (left or right).
     * @return X coordinate in the shoe.
     */
    public static double viewToShoeX(double x, Side side) {
        double result = x / 2;
        if (side.equals(Side.RIGHT)) {
            result -= 36;
        }
        else {
            result -= 95;
        }
        return result;
    }
    
    /**
     * Translation from the shoe coordinates system to the view coordinates
     * system for Y.
     * @param y Y coordinate in the shoe.
     * @param side Side of the shoe (left or right).
     * @return Y coordinate in the view.
     */
    public static double shoeToViewY(double y, Side side) {
        double result;
        if (side.equals(Side.RIGHT)) {
            result = -1 * y + 76;
        }
        else {
            result = -1 * y + 76;
        }
        return 2 * result;
    }
    
    /**
     * Translation from the view coordinates system to the shoe coordinates
     * system for Y.
     * @param y Y coordinate in the view.
     * @param side Side of the shoe (left or right).
     * @return Y coordinate in the shoe.
     */
    public static double viewToShoeY(double y, Side side) {
        double result;
        if (side.equals(Side.RIGHT)) {
            result = 76 - y / 2;
        }
        else {
            result = 76 - y / 2;
        }
        return result;
    }
    
    /**
     * Contain the X coordinate of the cursor in the shoe coordinates system.
     * @return The xCursorProperty of the ShoeView.
     * @see ShoeView
     */
    public DoubleProperty xCursorProperty() {
        return xCursor;
    }
    
    /**
     * Contain the Y coordinate of the cursor in the shoe coordinates system.
     * @return The yCursorProperty of the ShoeView.
     * @see ShoeView
     */
    public DoubleProperty yCursorProperty() {
        return yCursor;
    }
    
    /**
     * Getter for the model of the ShoeView.
     * @return The model of the ShoeView.
     * @see ShoeView
     */
    public Shoe getModel() {
        return model;
    }
    
}