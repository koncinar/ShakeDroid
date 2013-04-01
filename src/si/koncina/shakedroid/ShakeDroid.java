package si.koncina.shakedroid;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

public class ShakeDroid extends Activity {
    private static final NumberFormat SENSOR_NUMBER_FORMAT = NumberFormat.getNumberInstance();

    static {
        SENSOR_NUMBER_FORMAT.setMinimumFractionDigits(4);
        SENSOR_NUMBER_FORMAT.setMaximumFractionDigits(4);
        SENSOR_NUMBER_FORMAT.setMinimumIntegerDigits(2);
    }

    private Button startButton;
    private Button stopButton;
    private GraphView graphView;
    private TextView infoTextView;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorActivity sensorActivity;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareViews();
        prepareSensors();
        prepareListeners();
    }

    private void prepareViews() {
        setContentView(R.layout.main);
        graphView = (GraphView) findViewById(R.id.graph_panel);
        startButton = (Button) findViewById(R.id.start_recording_button);
        stopButton = (Button) findViewById(R.id.stop_recording_button);
        infoTextView = (TextView) findViewById(R.id.text_panel);
    }

    private void prepareSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorActivity = new SensorActivity(this);
    }

    private void prepareListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorActivity.clearValues();
                startSensor();
                alternateStartStopButtons(false);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopReading();
            }
        });
    }

    /**
     * Stops reading values from sensor (removes Listener) and displays all currently read information on graph.
     */
    private void stopReading() {
        stopSensor();
        graphView.setValues(sensorActivity.getAllValues());
        sensorActivity.clearValues();
        alternateStartStopButtons(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopReading();
    }

    private void startSensor() {
        sensorManager.registerListener(sensorActivity, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void stopSensor() {
        sensorManager.unregisterListener(sensorActivity);
    }

    /**
     * Sets current state of the sensor to be displayed in the label.
     * @param currentValue current state of the sensor
     */
    public void setCurrentValue(float[] currentValue) {
        infoTextView.setText(toString(currentValue));
    }

    /**
     * Switches between Start and Stop buttons.  If Start button is visible then Stop button is not and vice versa.
     * @param showStartButton should Start button be visible
     */
    private void alternateStartStopButtons(boolean showStartButton) {
        startButton.setVisibility(showStartButton ? View.VISIBLE : View.GONE);
        stopButton.setVisibility(showStartButton ? View.GONE : View.VISIBLE);
    }

    /**
     * Retrieves an array of values, formats them according to SENSOR_NUMBER_FORMAT and puts them into a String - each
     * value in it's own line
     * @param values array of values to be converted into String
     * @return String with each value in it's own line
     */
    private String toString(float[] values) {
        StringBuilder sb = new StringBuilder();
        for (float value : values) {
            sb.append(SENSOR_NUMBER_FORMAT.format(value));
            sb.append("\n");
        }
        return sb.toString();
    }
}
