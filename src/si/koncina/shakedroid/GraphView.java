package si.koncina.shakedroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import static si.koncina.shakedroid.Statistics.ease;
import static si.koncina.shakedroid.Statistics.findExtremes;

/**
 * This view draws graphs. It receives a List of arrays of values and
 * than displays one graph per each value of the array.<br/>
 * User: Rok Koncina
 */
public class GraphView extends View {
    private static final int AMPLITUDE = 40;
    private static final int[] COLORS = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.GRAY};

    private RealMatrix valuesMatrix;
    private int easing;
    private boolean showExtremes;

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
     *
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
        drawGraph(canvas, p, ease(values, easing));
    }

    private void drawGraph(Canvas canvas, Paint p, double[] points) {
        int h = getHeight();
        int w = getWidth();
        int pointsSize = points.length;
        for (int i = 0; i < pointsSize - 1; i++) {
            double point1 = points[i];
            double point2 = points[i + 1];
            float x1 = i * w / pointsSize;
            float y1 = (float) ((point1 + (AMPLITUDE / 2)) * h / AMPLITUDE);
            float x2 = (i + 1) * w / pointsSize;
            float y2 = (float) ((point2 + (AMPLITUDE / 2)) * h / AMPLITUDE);
            canvas.drawLine(x1, y1, x2, y2, p);
        }
        if (showExtremes) {
            for (Extreme extreme : findExtremes(points, easing)) {
                canvas.drawCircle(extreme.getIndex() * w / pointsSize, (float) ((extreme.getValue() + (AMPLITUDE / 2)) * h / AMPLITUDE), 2, p);
            }
        }
    }

    /**
     * Sets the easing of the graph. Allowed numbers are 0, 1, 2, 3 and 4. 0 means no easing, 4 means maximum easing.
     * Every other number will set easing to 0.
     * @param easing the intensity of easing
     */
    public void setEasing(int easing) {
        switch (easing) {
            case 0:
                this.easing = 0;
                break;
            case 1:
                this.easing = 1;
                break;
            case 2:
                this.easing = 2;
                break;
            case 3:
                this.easing = 5;
                break;
            case 4:
                this.easing = 10;
                break;
            default:
                this.easing = 0;
        }
        invalidate();
    }

    /**
     * Should extremes be visible on graph or not. If this is set to false, extremes are not calculated.
     * @param showExtremes should extremes be visible
     */
    public void setShowExtremes(boolean showExtremes) {
        this.showExtremes = showExtremes;
        invalidate();
    }
}
