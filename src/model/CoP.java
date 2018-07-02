package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Shoe.Side;
import view.mainwindow.shoe.CoPView;


/**
 * Model of the CoP (Centre of Pressure).<br>
 * Contain the position of the CoP in the coordinates system of the Shoe and the
 * side of the Shoe this CoP belongs to.
 * @author Loïc David
 * @see Shoe
 * @see CoPView
 */
public class CoP {
    
    private final DoubleProperty x = new SimpleDoubleProperty(0);
    private final DoubleProperty y = new SimpleDoubleProperty(0);
    private final Side side;
    
    /**
     * Create a new instance of CoP.
     * @param side Shoe side (left or right).
     * @see CoP
     */
    public CoP(Side side) {
        this.side = side;
    }
    
    /**
     * Contain the X coordinate of the CoP in the coordinates system of the shoe.
     * @return The xProperty of the CoP.
     * @see CoP
     */
    public DoubleProperty xProperty() {
        return x;
    }
    
    /**
     * Contain the y coordinate of the CoP in the coordinates system of the shoe.
     * @return yProperty of the CoP.
     * @see CoP
     */
    public DoubleProperty yProperty() {
        return y;
    }

    /**
     * Getter for the side of the Shoe this CoP belongs to.
     * @return The side of the Shoe this CoP belongs to.
     * @see Shoe
     */
    public Side getSide() {
        return side;
    }
    
}