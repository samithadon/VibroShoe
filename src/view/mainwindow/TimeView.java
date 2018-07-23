package view.mainwindow;

import exception.OtherConnectPortException;
import exception.UsedPortException;
import exception.WrongPortException;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.TimeController;
import view.otherwindow.ErrorWindow;


/**
 * Contain all the tools to control the time.
 * @author Loïc David
 */
public class TimeView extends HBox {
    
    private final TimeController timeController;
    private Button previousButton;
    private Button nextButton;
    private Button playPauseButton;
    private Slider slider;
    private Label timeLabel;
    private final ImageView playImageView = new ImageView(new Image(new File(".//.//ressources//play.png").toURI().toString()));
    private final ImageView pauseImageView = new ImageView(new Image(new File(".//.//ressources//pause.png").toURI().toString()));
    private final ImageView recordImageView = new ImageView(new Image(new File(".//.//ressources//record.png").toURI().toString()));
    private final ImageView stopImageView = new ImageView(new Image(new File(".//.//ressources//stop.png").toURI().toString()));
    private EventHandler<MouseEvent> playPause;
    private EventHandler<MouseEvent> recordStop;
    private EventHandler<MouseEvent> pressSlider;
    private EventHandler<MouseEvent> moveSlider;
    private EventHandler<MouseEvent> releaseSlider;
    private ChangeListener<Boolean> playPauseListener;
    private ChangeListener<Boolean> recordStopListener;
    
    /**
     * Create a new instance of TimeView.
     * @param timeController The TimeController of the software.
     * @see TimeView
     */
    public TimeView(TimeController timeController) {
        this.timeController = timeController;
        initialize();
    }

    /**
     * Initialize the TimeView.<br>
     * Create the components and connect them to their functions.
     * The slider is not bind to the time value here, it is bind when the CSV
     * reading starts.
     */
    private void initialize() {
        
        // Create/initialize components.
        // previousButton.
        previousButton = new Button();
        String path = new File(".//.//ressources//previous.png").toURI().toString();
        previousButton.setGraphic(new ImageView(new Image(path)));
        previousButton.setMinSize(30, 30);
        previousButton.setMaxSize(30, 30);
        previousButton.setDisable(true);
        // nextButton.
        nextButton = new Button();
        path = new File(".//.//ressources//next.png").toURI().toString();
        nextButton.setGraphic(new ImageView(new Image(path)));
        nextButton.setMinSize(30, 30);
        nextButton.setMaxSize(30, 30);
        nextButton.setDisable(true);
        // playPauseButton.
        playPauseButton = new Button();
        playPauseButton.setGraphic(playImageView);
        playPauseButton.setMinSize(30, 30);
        playPauseButton.setMaxSize(30, 30);
        playPauseButton.setDisable(true);
        // slider.
        slider = new Slider(0, 100, 10);
        slider.setPrefSize(510, 30);
        slider.setDisable(true);
        // timeLabel.
        timeLabel = new Label("00:00");
        timeLabel.setPrefSize(54, 30);
        
        
        // Add components to TimeView.
        getChildren().addAll(previousButton, playPauseButton, nextButton, slider, timeLabel);
        
        
        // Connect components to methods.
        // previousButton.
        // Go to the previous data (CSV only).
        previousButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeController.setPlay(false);
                timeController.previous();
            }
        });
        // nextButton.
        // Go to the next data (CSV only).
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeController.setPlay(false);
                timeController.next();
            }
        });
        // playPauseButton.
        // Switch between play and pause (CSV only).
        playPause = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeController.playPause();
            }
        };
        // Start or stop the record of serial data.
        recordStop = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    timeController.recordStop();
                } catch (WrongPortException ex) {
                    (new ErrorWindow(ex.toString())).show();
                } catch (UsedPortException ex) {
                    (new ErrorWindow(ex.toString())).show();
                } catch (OtherConnectPortException ex) {
                    (new ErrorWindow(ex.toString())).show();
                }
            }
        };
        // Change the image of the button in function of the mod.
        playPauseListener = new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    playPauseButton.setGraphic(pauseImageView);
                }
                else {
                    playPauseButton.setGraphic(playImageView);
                }
            }
        };
        // Change the image of the button in function of the mod.
        recordStopListener = new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    playPauseButton.setGraphic(stopImageView);
                }
                else {
                    playPauseButton.setGraphic(recordImageView);
                }
            }
        };
        // Slider
        // When the slider is pressed, the time is updated and the handlers for
        // the drag and the release are added.
        pressSlider = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeController.setPlay(false);
                timeController.update();
                slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveSlider);
                slider.addEventHandler(MouseEvent.MOUSE_RELEASED, releaseSlider);
            }
        };
        // When the slider is dragged, the time is updated.
        moveSlider = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timeController.update();
            }
        };
        // When the slider is released, the drag and release handlers are removed.
        releaseSlider = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                slider.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveSlider);
                slider.removeEventHandler(MouseEvent.MOUSE_RELEASED, releaseSlider);
            }
        };
        slider.addEventHandler(MouseEvent.MOUSE_PRESSED, pressSlider);
        // timeLabel
        // The time label is bind to the timeProperty of the TimeController.
        timeController.timeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int min = newValue.intValue() / 60;
                int sec = newValue.intValue() - min * 60;
                String s = String.format("%02d:%02d", min, sec);
                timeLabel.setText(s);
            }
        });
        // Detect the start of CSV reading.
        timeController.readCSVProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    startCSVRead();
                }
            }
        });
        // Detect the start of serial reading.
        timeController.serialProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    startSerial();
                }
            }
        });
        // Detect the end of all reading.
        timeController.sleepProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    stopReading();
                }
            }
        });
    }
    
    /**
     * Bind the max and value properties of the slider to the properties of the
     * TimeController.
     * @see TimeController
     */
    private void bindSlider() {
        slider.maxProperty().bind(timeController.indexTotalTimeProperty());
        slider.valueProperty().bindBidirectional(timeController.indexTimeProperty());
    }
    
    /**
     * Start the CSV reading.
     */
    private void startCSVRead() {
        bindSlider();
        try {
            playPauseButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, recordStop);
            timeController.playProperty().removeListener(recordStopListener);
        } catch (Exception e) {}
        try {
            playPauseButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, playPause);
            timeController.playProperty().removeListener(playPauseListener);
        } catch (Exception e) {}
        playPauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, playPause);
        timeController.playProperty().addListener(playPauseListener);
        playPauseButton.setGraphic(playImageView);
        previousButton.setDisable(false);
        playPauseButton.setDisable(false);
        nextButton.setDisable(false);
        slider.setDisable(false);
    }
    
    /**
     * Start the serial reading.
     */
    private void startSerial() {
        try {
            playPauseButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, recordStop);
            timeController.playProperty().removeListener(recordStopListener);
        } catch (Exception e) {}
        try {
            playPauseButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, playPause);
            timeController.playProperty().removeListener(playPauseListener);
        } catch (Exception e) {}
        playPauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, recordStop);
        timeController.playProperty().addListener(recordStopListener);
        playPauseButton.setGraphic(recordImageView);
        previousButton.setDisable(true);
        playPauseButton.setDisable(false);
        nextButton.setDisable(true);
        slider.setDisable(true);
    }
    
    /**
     * Stop the current reading.
     */
    private void stopReading() {
        try {
            playPauseButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, recordStop);
            timeController.playProperty().removeListener(recordStopListener);
        } catch (Exception e) {}
        try {
            playPauseButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, playPause);
            timeController.playProperty().removeListener(playPauseListener);
        } catch (Exception e) {}
        previousButton.setDisable(true);
        playPauseButton.setDisable(true);
        nextButton.setDisable(true);
        slider.setDisable(true);
    }
    
}