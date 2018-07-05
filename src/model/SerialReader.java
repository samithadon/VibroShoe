package model;

import exception.WrongPortException;
import exception.OtherConnectPortException;
import exception.UsedPortException;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import view.otherwindow.ErrorWindow;


/**
 * Class used to read the serial port.<br>
 * This class read the data from the serial port and send them to the different
 * class which need them.
 * @author Loïc David
 */
public final class SerialReader {
    
    private String port;
    private int dataRate;
    private int dataBits;
    private int stopBits;
    private int parity;
    private boolean record = false;
    private final Shoe shoeModel;
    private final DoubleProperty time = new SimpleDoubleProperty(0);
    private InputStream input;
    private CommPortIdentifier portIdentifier;
    private SerialPort serialPort;
    private final String header;
    private Reader reader;

    /**
     * Create a new instance of SerialReader.
     * @param shoeModel The model of the shoe the SerialReader belongs to.
     * @see SerialReader
     */
    public SerialReader (Shoe shoeModel) {
        this.shoeModel = shoeModel;
        String h = "time;";
        for (int i = 1; i < 17; i++) {
            h += "sensorValue" + i + ";";
        }
        for (int i = 1; i < 17; i++) {
            h += "pressureValue" + i + ";";
        }
        for (int i = 1; i < 9; i++) {
            h += "motorValue" + i + ";";
        }
        h += "Xcop;";
        h += "Ycop\n";
        header = h;
        initializeParameters();
    }
    
    /**
     * Read the parameters of the serial link in the setting file
     * (ressources/[left or right]shoe.txt).
     */
    public void initializeParameters() {
        InputStream flux;
        try {
            flux = new FileInputStream("././ressources/serial" + shoeModel.getSide().toString() + ".txt");
            BufferedReader buff;
            try (InputStreamReader inputStreamReader = new InputStreamReader(flux)) {
                buff = new BufferedReader(inputStreamReader);
                String line;
                port = buff.readLine();
                line = buff.readLine();
                dataRate = Integer.parseInt(line);
                line = buff.readLine();
                dataBits = Integer.parseInt(line);
                line = buff.readLine();
                stopBits = Integer.parseInt(line);
                line = buff.readLine();
                parity = Integer.parseInt(line);
            }
            buff.close();
            flux.close();
        } catch (IOException | NumberFormatException ex) {
            (new ErrorWindow("Error loading parameters (" + shoeModel.getSide().toString() + " shoe)!")).show();
        }
    }
    
    /**
     * Connect to the serial port.
     * @throws WrongPortException If the serial port cannot be found.
     * @throws UsedPortException If the serial port is already used.
     * @throws OtherConnectPortException If any other error occures.
     */
    public void connect() throws WrongPortException, UsedPortException, OtherConnectPortException {
        try {
            portIdentifier = CommPortIdentifier.getPortIdentifier(port);
            serialPort = (SerialPort)portIdentifier.open(this.getClass().getName(), 2000);
            serialPort.setSerialPortParams(dataRate, dataBits, stopBits, parity);
            input = serialPort.getInputStream();
        } catch (NoSuchPortException ex) {
            throw new WrongPortException(port, shoeModel.getSide());
        } catch (PortInUseException ex) {
            throw new UsedPortException(port, shoeModel.getSide());
        } catch (UnsupportedCommOperationException | IOException ex) {
            throw new OtherConnectPortException(port, shoeModel.getSide());
        }
    }
    
    /**
     * Start the reading of the data from the serial port.
     */
    public void startRead() {
        record = true;
        reader = new Reader();
        reader.start();
    }
    
    /**
     * Class which reads the data from the serial port, analyses them and sends
     * them.
     */
    private class Reader extends Thread {
        /**
         * Program executed when the thread is started.<br>
         * Its main role is to find the end of the lines and call the method
         * sendData which needs the line as a parameter.
         */
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int len;
            int lrIndex;
            String line = "";
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("././ressources/savedata" + shoeModel.getSide().toString() + ".csv"))) {
                    writer.write(header);
                    while ((len = input.read(buffer)) > -1 && record) {
                        String str = new String(buffer,0,len);
                        if ((lrIndex = str.indexOf("\n")) != -1) {
                            if (lrIndex == 0 && str.length() == 1){
                                sendData(line, writer);
                                line = "";
                            }
                            else {
                                String[] strSplit = str.split("\n");
                                line += strSplit[0];
                                sendData(line, writer);
                                try {
                                    line = strSplit[1];
                                } catch (IndexOutOfBoundsException e) {
                                    line = "";
                                }
                            }
                        }
                        else {
                            line += str;
                        }
                    }
                    writer.close();
                }
            } catch (IOException e) {}
        }
    }
    
    /**
     * Write the line in a temporary save file and split it to get the data.
     * Then, convert the data to send them.
     * @param str The line to analize and send.
     * @param writer The writer to write the line in the save file.
     */
    private void sendData(String str, BufferedWriter writer) {
        Platform.runLater(() -> { 
            try {
                String[] data = str.split(";");
                double t = Double.parseDouble(data[0]);
                    time.setValue(t);
                for (int i = 1; i < shoeModel.getSensors().size() + 1; i++) {
                    shoeModel.getSensors().get(i - 1).valueProperty().setValue(Double.parseDouble(data[i]));
                }
                for (int i = shoeModel.getSensors().size() + 1; i < 2 * shoeModel.getSensors().size() + 1; i++) {
                    shoeModel.getSensors().get(i - 17).pressureProperty().setValue(Double.parseDouble(data[i]));
                }
                for (int i = 2 * shoeModel.getSensors().size() + 1; i < 2 * shoeModel.getSensors().size() + shoeModel.getMotors().size() + 1; i++) {
                    shoeModel.getMotors().get(i - 33).valueProperty().setValue(Double.parseDouble(data[i]));
                }
                shoeModel.getCop().xProperty().setValue(Double.parseDouble(data[2 * shoeModel.getSensors().size() + shoeModel.getMotors().size() + 1]));
                shoeModel.getCop().yProperty().setValue(Double.parseDouble(data[2 * shoeModel.getSensors().size() + shoeModel.getMotors().size() + 2]));
                writer.append(str + "\n");
            } catch (Exception e) {
                // If there is a problem to analyse the line, we just do nothing and wait the next one.
            }
        });  
    }
    
    /**
     * Stop the reading of the data from the serial port.
     */
    public void stopRead() {
        record = false;
    }
    
    /**
     * Close the connection with the port.
     */
    public void closePort() {
        stopRead();
        try {
            input.close();
            serialPort.close();
        } catch (IOException | NullPointerException e) {}
    }
    
    /**
     * Contain the time read from the serial port.
     * @return The timeProperty of the SerialReader.
     * @see SerialReader
     */
    public DoubleProperty timeProperty() {
        return time;
    }

    /**
     * Getter for the serial port.
     * @return The serial port.
     */
    public String getPort() {
        return port;
    }

    /**
     * Getter for the number of bits per second.
     * @return The number of bits per second.
     */
    public int getDataRate() {
        return dataRate;
    }

    /**
     * Getter for the data bits.
     * @return The data bits.
     */
    public int getDataBits() {
        return dataBits;
    }

    /**
     * Getter for the stop bits.
     * @return The stop bits.
     */
    public int getStopBits() {
        return stopBits;
    }

    /**
     * Getter for the parity.
     * @return The parity.
     */
    public int getParity() {
        return parity;
    }
      
}