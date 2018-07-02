package view.otherwindow.editshoe;

import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Motor;
import model.Sensor;
import model.Shoe;
import view.mainwindow.shoe.MotorView;
import view.mainwindow.shoe.SensorView;
import view.mainwindow.shoe.ShoeView;


/**
 * View of a shoe with specific interactions to edit it.
 * @author Loïc David
 */
public class EditShoeView  extends Pane {
    
    private final Shoe model;
    private final DoubleProperty xCursor = new SimpleDoubleProperty(0);
    private final DoubleProperty yCursor = new SimpleDoubleProperty(0);
    private final ArrayList<MotorView> motors = new ArrayList<>();
    private final ArrayList<SensorView> sensors = new ArrayList<>();
    private final ArrayList<Label> motorLabels = new ArrayList<>();
    private final ArrayList<Label> sensorLabels = new ArrayList<>();
    private EventHandler<MouseEvent> cursorPosHandler;
    private EventHandler<MouseEvent> cursorInHandler;
    private EventHandler<MouseEvent> cursorOutHandler;
    private final ArrayList<Double> lastRightClick = new ArrayList<>();
    
    /**
     * Create a new instance of EditShoeView.
     * @param model The model of the shoe to edit.
     * @see EditShoeView
     */
    public EditShoeView(Shoe model) {
        this.model = model;
        initialize();
    }
    
    /**
     * Initialize the EditShoeView.<br>
     * Create all the components and implement the interactions.
     */
    public void initialize() {
        
        // Border.
        setStyle("-fx-border-width: 0 0 1 0;-fx-border-color: black");
        setMaxSize(262, 586);
        
        // Shoe image.
        String path = new File(".//.//ressources//" + model.getSide().toString() + "Shoe.png").toURI().toString();
        Image shoeImage = new Image(path);
        ImageView shoeImageView = new ImageView(shoeImage);
        getChildren().add(shoeImageView);
        
        // Axis.
        Line xAxis = new Line(0, ShoeView.shoeToViewY(0, model.getSide()), 262, ShoeView.shoeToViewY(0, model.getSide()));
        xAxis.setStrokeWidth(2);
        Line yAxis = new Line(ShoeView.shoeToViewX(0, model.getSide()), 0, ShoeView.shoeToViewX(0, model.getSide()), 586);
        yAxis.setStrokeWidth(2);
        getChildren().addAll(xAxis, yAxis);
        
        // Grid.
        for (int i = -21; i < 8; i++) {
            Line line = new Line(0, ShoeView.shoeToViewY(i * 10, model.getSide()), 262, ShoeView.shoeToViewY(i * 10, model.getSide()));
            getChildren().add(line);
        }
        int start;
        int end;
        if (model.getSide().equals(Shoe.Side.LEFT)) {
            start = -9;
            end = 4;
        }
        else {
            start = -3;
            end = 10;
        }
        for (int i = start; i < end; i++) {
            Line line = new Line(ShoeView.shoeToViewX(i * 10, model.getSide()), 0, ShoeView.shoeToViewX(i * 10, model.getSide()), 586);
            getChildren().add(line);
        }
        
        // Motors.
        int i = 1;
        for (Motor motor : model.getMotors()) {
            addMotor(motor, i);
            i++;
        }
        
        // Sensors.
        i = 1;
        for (Sensor sensor : model.getSensors()) {
            addSensor(sensor, i);
            i++;
        }
        
        // Context menu.
        ContextMenu contextMenu = new ContextMenu();
        MenuItem sensorMenuItem = new MenuItem("New Sensor");
        MenuItem motorMenuItem = new MenuItem("New Motor");
        contextMenu.getItems().addAll(sensorMenuItem, motorMenuItem);
        // Add a sensor at the location of the right click.
        sensorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addSensor(new Sensor(ShoeView.viewToShoeX(lastRightClick.get(0), model.getSide()), ShoeView.viewToShoeY(lastRightClick.get(1), model.getSide()), model.getSide()), sensorLabels.size() + 1);
            }
        });
        // Add a motor at the location of the right click.
        motorMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addMotor(new Motor(ShoeView.viewToShoeX(lastRightClick.get(0), model.getSide()), ShoeView.viewToShoeY(lastRightClick.get(1), model.getSide()), model.getSide()), motorLabels.size() + 1);
            }
        });
        lastRightClick.add(0.);
        lastRightClick.add(0.);
        setOnContextMenuRequested(e -> {lastRightClick.set(0, Math.floor(2 * e.getX()) / 2);
                                        lastRightClick.set(1, Math.floor(2 * e.getY()) / 2);
                                        contextMenu.show(this, e.getScreenX(), e.getScreenY());
                                        e.consume();});
        // Close the context menu whit a left click.
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    contextMenu.hide();
                }
            }  
        });

        // Cursor position.
        cursorPosHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xCursor.setValue(ShoeView.viewToShoeX(event.getX(), model.getSide()));
                yCursor.setValue(ShoeView.viewToShoeY(event.getY(), model.getSide()));
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
     * Add a motor.
     * @param motor Model of the motor.
     * @param index Indiex of the motor.
     */
    private void addMotor(Motor motor, int index) {
        
        // Motor.
        MotorView motorView = new MotorView(motor);

        // Motor label.
        Label label = new Label("" + index);
        label.setStyle("-fx-font-weight: bold");
        label.setMouseTransparent(true);
        label.setVisible(false);
        label.layoutXProperty().bind(motorView.centerXProperty().add(7));
        label.layoutYProperty().bind(motorView.centerYProperty().add(7));

        // Context menu.
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem moveMenuItem = new MenuItem("Move");
        contextMenu.getItems().addAll(deleteMenuItem, moveMenuItem);
        // Delete the motor.
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                motors.remove(motorView);
                int i = motorLabels.indexOf(label);
                motorLabels.remove(label);
                getChildren().remove(motorView);
                getChildren().remove(label);
                if (i < motorLabels.size()) {
                    for (int j = i; j < motorLabels.size(); j++) {
                        motorLabels.get(j).setText("" + (j + 1));
                    }
                }
            }
        });
        // Open a window to choose a new position for the motor.
        moveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                (new MoveNodeView(motorView, model.getSide())).show();
            }
        });

        // Show the label only when the mouse is on the motor.
        motorView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setVisible(true);
            }
        });
        motorView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setVisible(false);
            }
        });
        motorView.setOnContextMenuRequested(e -> {contextMenu.show(motorView, e.getScreenX(), e.getScreenY());
                                                    e.consume();});
        // To move the motor with drag and drop.
        motorView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    motorView.setCenterX(event.getX());
                    motorView.setCenterY(event.getY());
                }
            }
        });

        // Add to Pane and List.
        motors.add(motorView);
        motorLabels.add(label);
        getChildren().addAll(motorView, label);
    }
    
    /**
     * Add a sensor.
     * @param sensor Model of the sensor.
     * @param index Index of the sensor.
     */
    private void addSensor(Sensor sensor, int index) {
        
        // Sensor.
        SensorView sensorView = new SensorView(sensor);

        // Sensor label.
        Label label = new Label("" + index);
        label.setStyle("-fx-font-weight: bold");
        label.setMouseTransparent(true);
        label.setVisible(false);
        label.layoutXProperty().bind(sensorView.centerXProperty().add(5));
        label.layoutYProperty().bind(sensorView.centerYProperty().add(5));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem moveMenuItem = new MenuItem("Move");
        contextMenu.getItems().addAll(deleteMenuItem, moveMenuItem);
        // Delete the sensor.
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sensors.remove(sensorView);
                int i = sensorLabels.indexOf(label);
                sensorLabels.remove(label);
                getChildren().remove(sensorView);
                getChildren().remove(label);
                if (i < sensorLabels.size()) {
                    for (int j = i; j < sensorLabels.size(); j++) {
                        sensorLabels.get(j).setText("" + (j + 1));
                    }
                }
            }
        });
        // Open a window to choose a new position for the sensor.
        moveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new MoveNodeView(sensorView, model.getSide());
            }
        });

        // Show the label only when the mouse is on the sensor.
        sensorView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setVisible(true);
            }
        });
        sensorView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setVisible(false);
            }
        });
        sensorView.setOnContextMenuRequested(e -> {contextMenu.show(sensorView, e.getScreenX(), e.getScreenY());
                                                    e.consume();});
        // To move the sensor with drag and drop.
        sensorView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    sensorView.setCenterX(event.getX());
                    sensorView.setCenterY(event.getY());
                }
            }
        });

        // Add to Pane and List
        sensors.add(sensorView);
        sensorLabels.add(label);
        getChildren().addAll(sensorView, label);
    }
    
    /**
     * Clear all the components witout saving the modifications.
     */
    private void reset() {
        getChildren().clear();
        motors.clear();
        motorLabels.clear();
        sensors.clear();
        sensorLabels.clear();
        removeEventHandler(MouseEvent.MOUSE_MOVED, cursorPosHandler);
        removeEventHandler(MouseEvent.MOUSE_ENTERED, cursorInHandler);
        removeEventHandler(MouseEvent.MOUSE_EXITED, cursorOutHandler);
    }
    
    /**
     * Reset then initialize.<br>
     * All the modifications will be removed.
     */
    public void update() {
        reset();
        initialize();
    }
    
    /**
     * Contain the X coordinate of the cursor in the shoe coordinates system.
     * @return The xCursorProperty of the EditShoeView.
     * @see EditShoeView
     */
    public DoubleProperty xCursorProperty() {
        return xCursor;
    }
    
    /**
     * Contain the Y coordinate of the cursor in the shoe coordinates system.
     * @return The yCursorProperty of the EditShoeView.
     * @see EditShoeView
     */
    public DoubleProperty yCursorProperty() {
        return yCursor;
    }
    
    /**
     * Getter for the model of the edited shoe.
     * @return The model of the edited shoe.
     */
    public Shoe getModel() {
        return model;
    }

    /**
     * Getter for the list of the motors.
     * @return An ArrayList of the MotorView.
     * @see MotorView
     */
    public ArrayList<MotorView> getMotors() {
        return motors;
    }

    /**
     * Getter for the list of the sensors.
     * @return An ArrayList of the SensorView.
     * @see SensorView
     */
    public ArrayList<SensorView> getSensors() {
        return sensors;
    }
    
}