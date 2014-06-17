package de.ur.mmi.sensorGame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorController implements SensorEventListener {

	private SensorListener sensorListener;
	private SensorManager sensorManager;
	
	private Sensor mAccelerometer;
	private Sensor mMagnetometer;

	private float[] mLastAccelerometer = new float[3];
	private float[] mLastMagnetometer = new float[3];
	private boolean mLastAccelerometerSet = false;
	private boolean mLastMagnetometerSet = false;

	private float[] mR = new float[9];
	private float[] mOrientation = new float[3];
	
	public SensorController(SensorListener sensorListener, SensorManager sensorManager) {
		this.sensorListener = sensorListener;
		this.sensorManager = sensorManager;

		mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	public void onResume() {
		mLastAccelerometerSet = false;
		mLastMagnetometerSet = false;
		sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onPause() {
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// nothing to do
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == mAccelerometer) {
			System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
			mLastAccelerometerSet = true;
		} else if (event.sensor == mMagnetometer) {
			System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
			mLastMagnetometerSet = true;
		}
		if (mLastAccelerometerSet && mLastMagnetometerSet) {
			SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
			SensorManager.getOrientation(mR, mOrientation);
			float angle = mOrientation[1];
			if (angle > 0.2) {
				sensorListener.onLeft();
			} else if (angle < -0.2) {
				sensorListener.onRight();
			} else{
				sensorListener.onMiddle();
			}
		}
	}
	
}
