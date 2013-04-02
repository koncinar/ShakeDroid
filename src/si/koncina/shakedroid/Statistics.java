package si.koncina.shakedroid;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rok
 * Date: 1.4.2013
 * Time: 9:53
 */
public class Statistics {
    public static double[] ease(double[] values, int amplitude) {
        if (amplitude <= 0) {
            return values.clone();
        }
        StatUtils.mean(values);
        DescriptiveStatistics ds = new DescriptiveStatistics(2 * amplitude + 1);
        double[] easedValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            double value = values[i];
            ds.addValue(value);
            easedValues[i] = ds.getMean();
        }
        return easedValues;
    }

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

    public static List<Extreme> findExtremes(double[] values, int easing) {
        List<Extreme> extremes = new ArrayList<Extreme>();
        int shift = easing > 1 ? easing + 1 : 1;
        for (int i = shift; i < values.length - shift; i++) {
            if (rising(values, i - shift, i + 1) && falling(values, i, i + shift + 1)) {
                extremes.add(new Extreme(i, values[i], true));
            } else if (falling(values, i - shift, i + 1) && rising(values, i, i + shift + 1)) {
                extremes.add(new Extreme(i, values[i], false));
            }
        }
        return extremes;
    }

    private static boolean rising(double values[], int from, int to) {
        for (int i = from; i < to - 1; i++) {
            if (values[i] >= values[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static boolean falling(double values[], int from, int to) {
        for (int i = from; i < to - 1; i++) {
            if (values[i] <= values[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
