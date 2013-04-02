package si.koncina.shakedroid;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with methods that help with statistics.<br/>
 * User: Rok Koncina
 */
public class Statistics {
    /**
     * This method eases the array of values by calculating the average of current value and nearby values.
     * The nearby values are the ones that are inside interval <code>[i-amplitude, i+amplitude]</code>.
     *
     * @param values    array of values
     * @param amplitude defines which numbers are nearby
     * @return eased values
     */
    public static double[] ease(double[] values, int amplitude) {
        if (values == null || values.length == 0) {
            return new double[0];
        }
        if (amplitude <= 0) {
            return values.clone();
        }
        DescriptiveStatistics ds = new DescriptiveStatistics(2 * amplitude + 1);
        double[] easedValues = new double[values.length];
        for (int i = 0; i <= amplitude; i++) {
            ds.addValue(values[0]);
        }
        for (int i = 0; i < amplitude; i++) {
            ds.addValue(values[i]);
        }
        for (int i = amplitude; i < values.length; i++) {
            ds.addValue(values[i]);
            easedValues[i - amplitude] = ds.getMean();
        }
        for (int i = values.length; i < values.length + amplitude; i++) {
            ds.addValue(values[values.length - 1]);
            easedValues[i - amplitude] = ds.getMean();
        }
        return easedValues;
    }

    /**
     * This method converts a 2-dimensional array of floats into a (same) array of doubles.
     *
     * @param values 2-dimensional array of floats
     * @return 2-dimensional array of doubles
     */
    public static double[][] toDouble(float[][] values) {
        if (values == null || values.length == 0 || values[0].length == 0) {
            return new double[0][0];
        }
        int m = values.length;
        int n = values[0].length;
        double[][] doubles = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                doubles[i][j] = values[i][j];
            }
        }
        return doubles;
    }

    /**
     * This method searches for all extremes in the array of given values.<br/>
     * If nearby values are rising before current value and falling after current value, than the current value is
     * maximum. If it-s the other way around than the current value is minimum.<br/>
     * To determine which values are near the current value, shift is used: near are all the numbers in
     * interval <code>[i - shift, i + shift]</code>.
     *
     * @param values all values that potentially have extremes
     * @param shift  defines the 'nearby' size
     * @return List of extremes
     * @see Extreme
     */
    public static List<Extreme> findExtremes(double[] values, int shift) {
        List<Extreme> extremes = new ArrayList<Extreme>();
        int shift1 = shift > 1 ? shift + 1 : 1;
        for (int i = shift1; i < values.length - shift1; i++) {
            if (rising(values, i - shift1, i + 1) && falling(values, i, i + shift1 + 1)) {
                extremes.add(new Extreme(i, values[i], true));
            } else if (falling(values, i - shift1, i + 1) && rising(values, i, i + shift1 + 1)) {
                extremes.add(new Extreme(i, values[i], false));
            }
        }
        return extremes;
    }

    /**
     * This method checks if all values between <code>from</code> (inclusive) and <code>to</code> (exclusive) are rising. That means that every
     * value with greater index is greater (and not equal!).
     *
     * @param values array of all values
     * @param from   first index (should be the smallest value)
     * @param to     last index - the number with this index is not checked anymore
     * @return true if values are rising or false otherwise
     */
    private static boolean rising(double values[], int from, int to) {
        for (int i = from; i < to - 1; i++) {
            if (values[i] >= values[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks if all values between <code>from</code> (inclusive) and <code>to</code> (exclusive) are falling. That means that every
     * value with greater index is smaller (and not equal!).
     *
     * @param values array of all values
     * @param from   first index (should be the largest value)
     * @param to     last index - the number with this index is not checked anymore
     * @return true if values are falling or false otherwise
     */
    private static boolean falling(double values[], int from, int to) {
        for (int i = from; i < to - 1; i++) {
            if (values[i] <= values[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
