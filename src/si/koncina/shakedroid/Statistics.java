package si.koncina.shakedroid;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: rok
 * Date: 1.4.2013
 * Time: 9:53
 * To change this template use File | Settings | File Templates.
 */
public class Statistics {
    public static double[] ease(double[] values, int amplitude) {
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

    public double[][] toDouble(float[][] values) {
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
}
