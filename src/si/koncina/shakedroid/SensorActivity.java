package si.koncina.shakedroid;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that listens for changes of the sensor, saves all read values and
 * informs ShakeDroid class about current value.<br/>
 * User: Rok Koncina
 */
class SensorActivity extends Activity implements SensorEventListener {
    private List<float[]> allValues;
    private ShakeDroid shakeDroid;

    public SensorActivity(ShakeDroid shakeDroid) {
        this.shakeDroid = shakeDroid;
        allValues = new ArrayList<float[]>();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values.clone();
        allValues.add(values);
        shakeDroid.setCurrentValue(values);
    }

    /**
     * Clears currently saved values.
     */
    public void clearValues() {
        allValues.clear();
    }

    /**
     * Returns currently saved values.
     * @return all currently saved values
     */
    public List<float[]> getAllValues() {
        return new ArrayList<float[]>(allValues);
    }
}
