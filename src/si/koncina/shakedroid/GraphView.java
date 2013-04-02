package si.koncina.shakedroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

import static si.koncina.shakedroid.Statistics.ease;
import static si.koncina.shakedroid.Statistics.toDouble;

/**
 * This view draws graphs. It receives a List of arrays of values and
 * than displays one graph per each value of the array.<br/>
 * User: Rok Koncina
 */
public class GraphView extends View {
    private static final int AMPLITUDE = 40;
    private static final int[] COLORS = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.GRAY};

    private RealMatrix valuesMatrix;

    public GraphView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.WHITE);
    }

    /**
     * Set values to be drawn on graph.
     * @param values to be drawn
     */
    public void setValues(double[][] values) {
        valuesMatrix = new Array2DRowRealMatrix(values);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.LTGRAY);
        int heightHalf = getHeight() / 2;
        canvas.drawLine(0, heightHalf, getWidth(), heightHalf, p);
        if (valuesMatrix != null && valuesMatrix.getRowDimension() > 0 && valuesMatrix.getColumnDimension() > 0) {
            for (int i = 0; i < valuesMatrix.getColumnDimension(); i++) {
                drawGraph(canvas, valuesMatrix.getColumn(i), i);
            }
        }
    }

    private void drawGraph(Canvas canvas, double[] values, int i) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (i >= COLORS.length) {
            p.setColor(Color.BLACK);
        } else {
            p.setColor(COLORS[i]);
        }
        drawGraph(canvas, p, ease(values, 10));
    }

    /**
     * This method converts 2-dimensional array of values into one-dimensional. It extracts all i-th values from each
     * array.<br/>
     * <br/>
     * For example:<br/>
     * <code>values = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}</code><br/>
     * <code>i = 1</code><br/>
     * <code>extract(values, i) = {2, 5, 8}</code>
     *
     *
     *
     * @param values List of arrays (aka. 2-dimensional array)
     * @param k index of the value to be extracted
     * @return List of values.
     */
    private double[] extract(double[][] values, int k) {
        double[] vector = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            vector[i] = values[i][k];
        }
        return vector;
    }

    private void drawGraph(Canvas canvas, Paint p, double[] points) {
        int h = getHeight();
        int w = getWidth();
        for (int i = 0, pointsSize = points.length; i < pointsSize - 1; i++) {
            double point1 = points[i];
            double point2 = points[i + 1];
            float x1 = i * w / pointsSize;
            float y1 = (float) ((point1 + (AMPLITUDE / 2)) * h / AMPLITUDE);
            float x2 = (i + 1) * w / pointsSize;
            float y2 = (float) ((point2 + (AMPLITUDE / 2)) * h / AMPLITUDE);
            canvas.drawLine(x1, y1, x2, y2, p);
        }
    }
}
