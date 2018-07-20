package model;

import exception.CSVFileException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javafx.application.Platform;
import view.mainwindow.MenuView;
import view.otherwindow.ErrorWindow;
import view.mainwindow.shoe.ShoeView;


/**
 * Class containing all the methods the menus need to interact with the model.<br>
 * It has a role of interface between the view and the model. Thus, the menus
 * and the model are as independent as possible.
 * @author Loïc David
 * @see MenuView
 */
public class MenuController {

    private final TimeController timeController;
    private final Shoe leftShoe;
    private final Shoe rightShoe;

    /**
     * Create a new instance of MenuController.
     * @param timeController The TimeController of the software.
     * @param leftShoe The left Shoe of the software.
     * @param rightShoe The right Shoe of the software.
     * @see MenuController
     */
    public MenuController(TimeController timeController, Shoe leftShoe, Shoe rightShoe) {
        this.timeController = timeController;
        this.leftShoe = leftShoe;
        this.rightShoe = rightShoe;
        loadDataType();
    }
    
    /**
     * Stop the current reading (CSV or serial).
     */
    public void stopReading() {
        rightShoe.getSerialReader().stopRead();
        leftShoe.getSerialReader().stopRead();
        rightShoe.getSerialReader().closePort();
        leftShoe.getSerialReader().closePort();
        timeController.stopReading();
    }
    
    /**
     * Start the CSV reading.<br>
     * Close the previous reading (CSV or serial) then initialize the
     * TimeController for the CSV reading.
     * @param leftFile CSV file for the left shoe.
     * @param rightFile CSV file for the right shoe.
     * @see TimeController
     */
    public void readCSV(String leftFile, String rightFile) {
        try {
            rightShoe.getSerialReader().stopRead();
            leftShoe.getSerialReader().stopRead();
            rightShoe.getSerialReader().closePort();
            leftShoe.getSerialReader().closePort();
            rightShoe.getCsvReader().loadFile(rightFile);
            leftShoe.getCsvReader().loadFile(leftFile);
            timeController.initializeCSVRead();
        } catch (CSVFileException e) {
            (new ErrorWindow(e.toString())).show();
        }
    }
    
    /**
     * Start the serial reading.<br>
     * Close the previous reading (CSV or serial) then
     * initialize the TimeController for the serial reading.
     * @see TimeController
     */
    public void readSerial() {
        rightShoe.getSerialReader().stopRead();
        leftShoe.getSerialReader().stopRead();
        rightShoe.getSerialReader().closePort();
        leftShoe.getSerialReader().closePort();
        timeController.initializeSerial();
    }
    
    /**
     * Save the last recorded data from the serial in the specified files.<br>
     * When serial data are read, they are automatically written in save files.
     * This method just copies these files into the files specified by the user.
     * @param leftPath CSV output file for the left shoe.
     * @param rightPath CSV output file for the right shoe.
     */
    public void saveData(String leftPath, String rightPath) {
        try {
            File leftSource = new File("././ressources/savedataleft.csv");
            File rightSource = new File("././ressources/savedataright.csv");
            File leftDest = new File(leftPath);
            File rightDest = new File(rightPath);
            Files.copy(leftSource.toPath(), leftDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(rightSource.toPath(), rightDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            (new ErrorWindow("Cannot save the data!")).show();
        }
    }
    
    /**
     * Close the software.
     */
    public void quit() {
        Platform.exit();
    }
    
    /**
     * Update the settings of the serial link for one Shoe.
     * @param shoe Model of the shoe to which belongs the updated SerialReader.
     * @param port Serial port.
     * @param rate Bits per second.
     * @param bits Data bits.
     * @param stop Stop bits.
     * @param parity Parity.
     * @see SerialReader
     */
    public void updateSerialSettings(Shoe shoe, String port, int rate, int bits, int stop, int parity) {
        try (FileWriter fileWriter = new FileWriter("././ressources/serial" + shoe.getSide().toString() + ".txt");
                BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(port + "\n" + rate + "\n" + bits + "\n" + stop + "\n" + parity + "\n");
        } catch (IOException ex) {
            (new ErrorWindow("The settings have not been saved!")).show();
        }
        shoe.getSerialReader().initializeParameters();
    }
    
    /**
     * Set the shoes data type by reading it from the save file.
     * (ressources/datatype.txt)
     */
    public void loadDataType() {
        InputStream flux;
        try {
            flux = new FileInputStream("././ressources/datatype.txt");
            BufferedReader buff;
            try (InputStreamReader inputStreamReader = new InputStreamReader(flux)) {
                buff = new BufferedReader(inputStreamReader);
                String line;
                line = buff.readLine();
                int type = Integer.parseInt(line);
                leftShoe.getSerialReader().setDataType(type);
                rightShoe.getSerialReader().setDataType(type);
            }
            buff.close();
            flux.close();
        } catch (IOException | NumberFormatException ex) {
            (new ErrorWindow("Error loading data type!")).show();
        }
    }
    
    /**
     * Update the save file containing the data type.
     * @param type 0 for "All data", 1 for "Only sensors".
     */
    private void saveDataType(int type) {
        try (FileWriter fileWriter = new FileWriter("././ressources/datatype.txt");
                BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(type + "\n");
        } catch (IOException ex) {
            (new ErrorWindow("The data type have not been saved!")).show();
        }
    }
    
    /**
     * Change the data type.
     * @param type 0 for "All data", 1 for "Only sensors".
     */
    public void changeDataType(int type) {
        saveDataType(type);
        loadDataType();
    }
    
    /**
     * Update the settings (sensors and motors) of a Shoe.
     * @param shoeView The View of the updated shoe.
     * @param xSensors List of the X coordinates of the sensors in the
     * coordinate system of the shoe.
     * @param ySensors List of the Y coordinates of the sensors in the
     * coordinate system of the shoe.
     * @param xMotors List of the X coordinates of the motors in the
     * coordinate system of the shoe.
     * @param yMotors List of the Y coordinates of the motors in the
     * coordinate system of the shoe.
     * @param sensorGroups List of the groups of the sensors.
     * @see Shoe
     * @see Sensor
     * @see Motor
     */
    public void updateShoeSettings(ShoeView shoeView, ArrayList<Double> xSensors, ArrayList<Double> ySensors, ArrayList<Double> xMotors, ArrayList<Double> yMotors, ArrayList<Integer> sensorGroups) {
        try { 
            FileWriter fileWriter = new FileWriter("././ressources/sensors" + shoeView.getModel().getSide().toString() + ".txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (int i = 0; i < xSensors.size(); i++) {
                    writer.append(xSensors.get(i).intValue() + " " + ySensors.get(i).intValue() + " " + sensorGroups.get(i) + "\n");
                }
            writer.close();
            fileWriter.close();
            fileWriter = new FileWriter("././ressources/motors" + shoeView.getModel().getSide().toString() + ".txt");
            writer = new BufferedWriter(fileWriter);
            for (int i = 0; i < xMotors.size(); i++) {
                    writer.append(xMotors.get(i).intValue() + " " + yMotors.get(i).intValue() + "\n");
                }
            writer.close();
            fileWriter.close();
            shoeView.getModel().update();
            shoeView.update();
        } catch (IOException ex) {
            (new ErrorWindow("The settings have not been saved!")).show();
        }
    }

    /**
     * Getter for the left shoe.
     * @return The left shoe.
     */
    public Shoe getLeftShoe() {
        return leftShoe;
    }

    /**
     * Getter for the right shoe.
     * @return The Right shoe.
     */
    public Shoe getRightShoe() {
        return rightShoe;
    }
    
}