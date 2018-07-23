package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import view.mainwindow.shoe.ShoeView;


/**
 * Model of a shoe.<br>
 * Contain all the Sensors and Motors, the CoP, the side of the Shoe, a
 * SerialReader and a CSVReader.
 * @author Loïc David.
 * @see Sensor
 * @see Motor
 * @see CoP
 * @see SerialReader
 * @see CSVReader
 * @see ShoeView
 */
public class Shoe {
    
    private final ArrayList<Sensor> sensors = new ArrayList<>();
    private final ArrayList<Motor> motors = new ArrayList<>();
    private final CoP cop;
    private final SerialReader serialReader;
    private final CSVReader csvReader;
    private final Side side;
    private final IntegerProperty updated = new SimpleIntegerProperty(0);
    
    /**
     * Create a new instance of Shoe.
     * @param side Shoe side (left or right).
     * @throws IOException TODO
     * @throws NumberFormatException TODO
     * @see Shoe
     */
    public Shoe(Side side) throws NumberFormatException, IOException {
        this.side = side;
        cop = new CoP(side);
        initialize();
        csvReader = new CSVReader(this);
        serialReader = new SerialReader(this);
    }
    
    /**
     * This enumeration defines the different sides possible for a shoe (left or
     * right).
     */
    public static enum Side {
        /**
         * Right side.
         */
        RIGHT {
            @Override
            public String toString() {
                return "right";
            }
        },
        /**
         * Left side.
         */
        LEFT {
            @Override
            public String toString() {
                return "left";
            }
        };
    }
    
    /**
     * Initialize the shoe by initializing the sensors and the motors.
     * @throws IOException TODO
     * @throws NumberFormatException TODO
     */
    private void initialize() throws NumberFormatException, IOException {
        initializeSensors();
        initializeMotors();
    }
    
    /**
     * Clear all the sensors and motors.
     */
    private void reset() {
        sensors.clear();
        motors.clear();
    }
    
    /**
     * Update the sensors and the motors.
     * @throws IOException TODO
     * @throws NumberFormatException TODO
     */
    public void update() throws NumberFormatException, IOException {
        reset();
        initialize();
        updated.setValue(updated.getValue() + 1);
    }
    
    /**
     * Read the position of the sensors in the settings file
     * (ressources/sensors[left or right].txt).
     * @throws IOException TODO
     * @throws NumberFormatException TODO
     */
    private void initializeSensors() throws NumberFormatException, IOException {
        InputStream flux = new FileInputStream("././ressources/sensors" + side.toString() + ".txt");
        InputStreamReader input = new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(input);
        String line;
        while ((line = buff.readLine()) != null){
            if (line.length() > 0) {
                String[] coordinates = line.split(" ");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                int grp = Integer.parseInt(coordinates[2]);
                sensors.add(new Sensor(x, y, side, grp));
            }
        }
        buff.close();
        input.close();
        flux.close();
    }
    
    /**
     * Read the position of the motors in the settings file
     * (ressources/motors[left or right].txt).
     * @throws IOException TODO
     * @throws NumberFormatException TODO
     */
    private void initializeMotors() throws IOException, NumberFormatException {
        InputStream flux = new FileInputStream("././ressources/motors" + side.toString() + ".txt");
        BufferedReader buff;
        InputStreamReader input = new InputStreamReader(flux);
        buff = new BufferedReader(input);
        String line;
        while ((line = buff.readLine()) != null){
            if (line.length() > 0) {
                String[] coordinates = line.split(" ");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                motors.add(new Motor(x, y, side));
            }
        }
        buff.close();
        input.close();
        flux.close();
    }

    /**
     * Getter for the sensors.
     * @return An ArrayList of the sensors.
     */
    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    /**
     * Getter for the motors.
     * @return An ArrayList of the motors.
     */
    public ArrayList<Motor> getMotors() {
        return motors;
    }

    /**
     * Getter for the CoP.
     * @return The CoP.
     */
    public CoP getCop() {
        return cop;
    }

    /**
     * Getter for the side.
     * @return The side.
     */
    public Side getSide() {
        return side;
    }

    /**
     * Getter for the SerialReader.
     * @return The SerialReader.
     */
    public SerialReader getSerialReader() {
        return serialReader;
    }

    /**
     * Getter for the CSVReader.
     * @return The CSVReader.
     */
    public CSVReader getCsvReader() {
        return csvReader;
    }
    
    /**
     * The value change when the shoe is updated.
     * @return The updatedProperty of the Shoe.
     */
    public IntegerProperty updatedProperty() {
        return updated;
    }
    
}