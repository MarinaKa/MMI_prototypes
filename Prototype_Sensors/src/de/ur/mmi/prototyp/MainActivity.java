package de.ur.mmi.prototyp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			fragment.initSensor((SensorManager) getSystemService(Context.SENSOR_SERVICE));
			getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
		}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements SensorEventListener {

		private TextView status;

		private SensorManager mSensorManager;
		private Sensor mAccelerometer;
		private Sensor mMagnetometer;

		private float[] mLastAccelerometer = new float[3];
		private float[] mLastMagnetometer = new float[3];
		private boolean mLastAccelerometerSet = false;
		private boolean mLastMagnetometerSet = false;

		private float[] mR = new float[9];
		private float[] mOrientation = new float[3];

		public PlaceholderFragment() {

		}

		public void initSensor(SensorManager sensorManager) {
			mSensorManager = sensorManager;
			mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			status = (TextView) rootView.findViewById(R.id.status);

			return rootView;
		}

		public void onResume() {
			super.onResume();
			mLastAccelerometerSet = false;
			mLastMagnetometerSet = false;
			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
			mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
		}

		@Override
		public void onPause() {
			super.onPause();
			mSensorManager.unregisterListener(this);
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

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
					status.setText("links");
				} else if (angle < -0.2) {
					status.setText("rechts");
				} else {
					status.setText("");
				}
			}
		}
	}

}
