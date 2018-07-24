package model;

import exception.CSVFileException;
import exception.OtherConnectPortException;
import exception.UsedPortException;
import exception.WrongPortException;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * This class manages the time of the reading.<br>
 * For the serial data, its role is only to start and stop the recording and to
 * store the time.<br>
 * For the CSV data, it is used to control the time
 * (move in time, pause, play...).
 * @author Loïc David
 */
public class TimeController {
    
    private final Shoe rightShoe;
    private final Shoe leftShoe;
    private double step;
    private final BooleanProperty sleep = new SimpleBooleanProperty(true);
    private final BooleanProperty serial = new SimpleBooleanProperty(false);
    private final BooleanProperty readCSV = new SimpleBooleanProperty(false);
    private final BooleanProperty play = new SimpleBooleanProperty(false);
    private final IntegerProperty indexTotalTime = new SimpleIntegerProperty();
    private final DoubleProperty time = new SimpleDoubleProperty(0);
    private final IntegerProperty indexTime = new SimpleIntegerProperty(0);
    
    /**
     * Create a new instance of TimeController.
     * @param leftShoe Model of the left shoe.
     * @param rightShoe Model of the right shoe.
     * @see TimeController
     */
    public TimeController(Shoe leftShoe, Shoe rightShoe) {
        this.rightShoe = rightShoe;
        this.leftShoe = leftShoe;
    }
    
    /**
     * Stop the current reading (serial or CSV).
     */
    public void stopReading() {
        time.unbind();
        serial.setValue(false);
        readCSV.setValue(false);
        play.setValue(false);
        time.setValue(0);
        indexTime.setValue(0);
        sleep.setValue(true);
    }
    
    /**
     * Initialize the serial reading.
     */
    public void initializeSerial() {
        // Stop the previous reading (CSV or serial).
        time.unbind();
        sleep.setValue(false);
        serial.setValue(false);
        readCSV.setValue(false);
        play.setValue(false);
        // Start the new one.
        time.setValue(0);
        time.bind(rightShoe.getSerialReader().timeProperty());
        time.bind(leftShoe.getSerialReader().timeProperty());
        serial.setValue(true);
    }

    /**
     * Initialize the CSV reading.
     * @throws CSVFileException Issue to read the CSV files.
     */
    public void initializeCSVRead() throws CSVFileException {
        // Stop the previous reading (CSV or serial).
        time.unbind();
        sleep.setValue(false);
        serial.setValue(false);
        readCSV.setValue(false);
        try {
            Thread.sleep((long)(1500 * step));
        } catch (InterruptedException ex) {
            throw new CSVFileException();
        }
        play.setValue(false);
        // Start the new one.
        loadIndexTotalTime();
        loadStep();
        indexTime.setValue(0);
        time.setValue(0);
        update();
        readCSV.setValue(true);
        (new Timer()).start();
    }
    
    /**
     * Thread used to count the time (in JavaFX, sleep can't be used because it
     * freezes the graphic interface, so another thread is necessary to count
     * the time).<br>
     * This timer is used only whit the CSV reading when the user want to read
     * the data without clicking on the button "next". In serial reading mod,
     * the data are displayed when they are read, so the timer is useless.
     */
    private class Timer extends Thread {
        /**
         * Program executed when the thread is started.<br>
         * It waits the step then go to the next data.
         */
        @Override
        public void run() {
            while (readCSV.getValue()) {
                try {
                    Thread.sleep((long)(1000 * step));
                } catch (InterruptedException ex) {}
                if (play.getValue()) {
                    Platform.runLater(() -> next());
                }
            }
        }
    }
    
    /**
     * Go to the previous data (CSV only).
     */
    public void previous() {
        if (indexTime.getValue() != 0) {
            changeIndexTime(-1);
            update();
        }
    }
    
    /**
     * Go to the next data (CSV only).
     */
    public void next() {
        if (indexTime.getValue() != indexTotalTime.getValue()) {
            changeIndexTime(1);
            update();
        }
    }
    
    /**
     * Switch between play and pause (CSV only).
     */
    public void playPause() {
        if (play.getValue()) {
            play.setValue(false);
        }
        else {
            play.setValue(true);
        }
    }
    
    /**
     * Start and stop the recording of serial data.
     * @throws WrongPortException The port cannot be found.
     * @throws UsedPortException The port is already used.
     * @throws OtherConnectPortException All other issues.
     */
    public void recordStop() throws WrongPortException, UsedPortException, OtherConnectPortException {
        if (play.getValue()) {
            play.setValue(false);
            rightShoe.getSerialReader().stopRead();
            leftShoe.getSerialReader().stopRead();
            rightShoe.getSerialReader().closePort();
            leftShoe.getSerialReader().closePort();
        }
        else {
            rightShoe.getSerialReader().connect();
            leftShoe.getSerialReader().connect();
            play.setValue(true);
            rightShoe.getSerialReader().startRead();
            leftShoe.getSerialReader().startRead();
        }
    }
    
    /**
     * Load the CSV data of the current time from the CSVReader and send them
     * to the classes which need them.
     * @see CSVReader
     */
    public void update() {
        time.setValue(rightShoe.getCsvReader().getTime(indexTime.getValue()));
        for (int i = 0; i < rightShoe.getCsvReader().getNbMotors(); i++) {
            rightShoe.getMotors().get(i).valueProperty().setValue(rightShoe.getCsvReader().getMotorValue(indexTime.getValue(), i + 1));
        }
        for (int i = 0; i < leftShoe.getCsvReader().getNbMotors(); i++) {
            leftShoe.getMotors().get(i).valueProperty().setValue(leftShoe.getCsvReader().getMotorValue(indexTime.getValue(), i + 1));
        }
        for (int i = 0; i < rightShoe.getCsvReader().getNbSensors(); i++) {
            rightShoe.getSensors().get(i).valueProperty().setValue(rightShoe.getCsvReader().getSensorValue(indexTime.getValue(), i + 1));
        }
        for (int i = 0; i < leftShoe.getCsvReader().getNbSensors(); i++) {
            leftShoe.getSensors().get(i).valueProperty().setValue(leftShoe.getCsvReader().getSensorValue(indexTime.getValue(), i + 1));
        }
        for (int i = 0; i < rightShoe.getCsvReader().getNbSensors(); i++) {
            rightShoe.getSensors().get(i).pressureProperty().setValue(rightShoe.getCsvReader().getSensorPressure(indexTime.getValue(), i + 1));
        }
        for (int i = 0; i < leftShoe.getCsvReader().getNbSensors(); i++) {
            leftShoe.getSensors().get(i).pressureProperty().setValue(leftShoe.getCsvReader().getSensorPressure(indexTime.getValue(), i + 1));
        }
        rightShoe.getCop().xProperty().setValue(rightShoe.getCsvReader().getXcop(indexTime.getValue()));
        leftShoe.getCop().xProperty().setValue(leftShoe.getCsvReader().getXcop(indexTime.getValue()));
        rightShoe.getCop().yProperty().setValue(rightShoe.getCsvReader().getYcop(indexTime.getValue()));
        leftShoe.getCop().yProperty().setValue(leftShoe.getCsvReader().getYcop(indexTime.getValue()));
    }
    
    /**
     * Load the time step from the CSV files.
     * @throws CSVFileException If the step of a file is different from
     * the other one or if the CSV files haven't been loaded properly.
     */
    public void loadStep() throws CSVFileException {
        try {
            double step1 = rightShoe.getCsvReader().getTime(1) - rightShoe.getCsvReader().getTime(0);
            double step2 = leftShoe.getCsvReader().getTime(1) - leftShoe.getCsvReader().getTime(0);
            if (step1 != step2) {
                throw new CSVFileException();
            }
            else {
                step = step1;
            }
        } catch (Exception e) {
            throw new CSVFileException();
        }
    }
    
    /**
     * Load the index of the last data of the CSV files.<br>
     * This index is equivalent to the total time in number of data.
     * @throws CSVFileException If the index of a file is different
     * from the other one or if the CSV files haven't been loaded properly.
     */
    public void loadIndexTotalTime() throws CSVFileException {
        try {
            int time1 = rightShoe.getCsvReader().getDataSize() - 1;
            int time2 = leftShoe.getCsvReader().getDataSize() - 1;
            if (time1 != time2) {
                throw new CSVFileException();
            }
            else {
                indexTotalTime.setValue(time1);
            }
        } catch (Exception e) {
            throw new CSVFileException();
        }
    }
    
    /**
     * Increment the indexTime of dt.
     * @param dt Value to add to the indexTime.
     */
    private void changeIndexTime(int dt) {
        indexTime.setValue(indexTime.getValue() + dt);
    }
    
    /**
     * Contain the time (in second).
     * @return The timeProperty of the TimeController.
     * @see TimeController
     */
    public DoubleProperty timeProperty() {
        return time;
    }
    
    /**
     * Contain the indexTime (time in number of data).
     * @return The indexTimeProperty of the TimeController.
     * @see TimeController
     */
    public IntegerProperty indexTimeProperty() {
        return indexTime;
    }
    
    /**
     * Contain the last indexTime (time in number of data of the last data).
     * @return The indexTotalTimeProperty of the TimeController.
     * @see TimeController
     */
    public IntegerProperty indexTotalTimeProperty() {
        return indexTotalTime;
    }
    
    /**
     * Contain true if the current action is a CSV reading, else false.
     * @return The ReadCSVProperty of the TimeController.
     * @see TimeController
     */
    public BooleanProperty readCSVProperty() {
        return readCSV;
    }
    
    /**
     * Contain true if the current action is a serial reading, else false.
     * @return The serialProperty of the TimeController.
     * @see TimeController
     */
    public BooleanProperty serialProperty() {
        return serial;
    }
    
    /**
     * Contain true if the TimeController is in play (CSV) or record (serial)
     * mod, else false.
     * @return The playProperty of the TimeController.
     * @see TimeController
     */
    public BooleanProperty playProperty() {
        return play;
    }
    
    /**
     * Contain true if there is not any reading at the moment, else false.
     * @return The sleepProperty of the TimeController.
     * @see TimeController
     */
    public BooleanProperty sleepProperty() {
        return sleep;
    }
    
    /**
     * Setter for the playProperty.
     * @param bool The new value of the playProperty.
     */
    public void setPlay(boolean bool) {
        play.setValue(bool);
    }
    
}