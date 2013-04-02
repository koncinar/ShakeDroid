package si.koncina.shakedroid;

/**
 * This class represents an extreme on graph. An extreme is a point on graph where all nearby points are either higher
 * or all are lower.<br/>
 * If all nearby points are higher, then the extreme point is called 'local minimum'.<br/>
 * If all nearby points are lower, then the extreme point is called 'local maximum'.<br/>
 * User: Rok Koncina
 */
public class Extreme {
    private int index;
    private double value;
    private boolean maximum;

    /**
     * @param index the x coordinate
     * @param value the y coordinate
     * @param maximum is the value maximum or minimum
     */
    public Extreme(int index, double value, boolean maximum) {
        this.index = index;
        this.value = value;
        this.maximum = maximum;
    }

    public int getIndex() {
        return index;
    }

    public double getValue() {
        return value;
    }

    public boolean isMaximum() {
        return maximum;
    }
}
