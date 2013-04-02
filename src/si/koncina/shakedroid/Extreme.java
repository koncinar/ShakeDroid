package si.koncina.shakedroid;

/**
 * Created with IntelliJ IDEA.
 * User: rok
 * Date: 2.4.2013
 * Time: 19:22
 */
public class Extreme {
    private int index;
    private double value;
    private boolean maximum;

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
