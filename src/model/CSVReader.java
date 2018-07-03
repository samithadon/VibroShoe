package model;

import exception.CSVFileException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Class used to read the CSV files.<br>
 * This class reads the data from the csv file and stores them.
 * Several methods are implemented to get the stored data.
 * @author Loïc David
 */
public class CSVReader {
    
    private InputStream flux;
    private final ArrayList<ArrayList<Double>> data = new ArrayList<>();
    private final Shoe shoe;
    
    /**
     * Create a new instance of CSVReader.
     * @param shoe the shoe this CSVReader belongs to.
     * @see CSVReader
     */
    public CSVReader(Shoe shoe) {
        this.shoe = shoe;
    }

    /**
     * Read the CSV file and store the data.
     * @param filePath CSV file to read.
     * @throws CSVFileException If there is any problem to load the
     * file or to collect the data.
     */
    public void loadFile(String filePath) throws CSVFileException {
        resetData();
        try {
            flux = new FileInputStream(filePath);
            BufferedReader buff;
            try (InputStreamReader input = new InputStreamReader(flux)) {
                buff = new BufferedReader(input);
                String line;
                buff.readLine();
                while ((line = buff.readLine()) != null){
                    if (line.length() > 0) {
                        String[] elements = line.split(";");
                        ArrayList<Double> lineArray = new ArrayList<>();
                        for (String s : elements) {
                            lineArray.add(Double.parseDouble(s));
                        }
                        data.add(lineArray);
                    }
                }
            }
            buff.close();
            flux.close();
        } catch (IOException | NumberFormatException ex) {
            throw new CSVFileException();
        }
    }
    
    /**
     * Clear the stored data.
     */
    private void resetData() {
        flux = null;
        data.clear();
    }
    
    /**
     * Getter for the time of the ith data.
     * @param i Index of the data.
     * @return The time the ith data.
     */
    public double getTime(int i) {
        return data.get(i).get(0);
    }
    
    /**
     * Getter for the ith value of the jth sensor.
     * @param i Index of the data.
     * @param j Index of the sensor.
     * @return The ith value of the jth sensor.
     * @see Sensor
     */
    public double getSensorValue(int i, int j) {
        return data.get(i).get(j);
    }
    
    /**
     * Getter for the ith pressure value of the jth sensor.
     * @param i Index of the data.
     * @param j Index of the sensor.
     * @return The ith pressure value of the jth sensor.
     * @see Sensor
     */
    public double getSensorPressure(int i, int j) {
        return data.get(i).get(j + shoe.getSensors().size());
    }
    
    /**
     * Getter for the ith value of the jth motor.
     * @param i Index of the data.
     * @param j Index of the sensor.
     * @return The ith value of the jth motor.
     * @see Motor
     */
    public double getMotorValue(int i, int j) {
        return data.get(i).get(j + 2 * shoe.getSensors().size());
    }
    
    /**
     * Getter for the ith X coordinate of the CoP in the coordinate system of
     * the shoe.
     * @param i Index of the data.
     * @return The ith X coordinate of the CoP.
     * @see CoP
     */
    public double getXcop(int i) {
        return data.get(i).get(2 * shoe.getSensors().size() + shoe.getMotors().size() + 1);
    }
    
    /**
     * Getter for the ith Y coordinate of the CoP in the coordinate system of
     * the shoe..
     * @param i Index of the data.
     * @return The ith Y coordinate of the CoP.
     * @see CoP
     */
    public double getYcop(int i) {
        return data.get(i).get(2 * shoe.getSensors().size() + shoe.getMotors().size() + 2);
    }
    
    /**
     * Getter for the number of sensors.
     * @return The number of sensors.
     * @see Sensor
     */
    public int getNbSensors() {
        return shoe.getSensors().size();
    }
    
    /**
     * Getter for the number of motors.
     * @return The number of motors.
     * @see Sensor
     */
    public int getNbMotors() {
        return shoe.getMotors().size();
    }
    
    /**
     * Getter for the size of the data.
     * @return The size of the data.
     */
    public int getDataSize() {
        return data.size();
    }
   
}