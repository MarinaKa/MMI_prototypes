package de.ur.mmi.sensorGame;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class ControllerActivity extends Activity implements ClientConnectionErrorListener, SensorListener {

	private static Logger log = Logger.getLogger(Client.class.getName());

	private String ip;
	private int port;
	private Client client;
	private Handler handler;
	private SensorController sensorController;
	private ImageView leftArrow;
	private ImageView rightArrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		leftArrow = (ImageView)findViewById(R.id.left_arrow);
		rightArrow = (ImageView)findViewById(R.id.right_arrow);
		
		handler = new UiHandler(this);
		sensorController = new SensorController(this, (SensorManager) getSystemService(Context.SENSOR_SERVICE));

		final String target = getIntent().getExtras().getString("target");
		final Pattern pattern = Pattern.compile("^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})$");
		final Matcher matcher = pattern.matcher(target);
		if (matcher.matches()) {
			ip = matcher.group(1);
			port = Integer.valueOf(matcher.group(2));
			connect();
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.toast_invalid_data), Toast.LENGTH_LONG);
			toast.show();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		client.disconnect();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		sensorController.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sensorController.onPause();
	}

	private void connect() {
		log.info("connecting ...");
		client = new Client(ip, port, this);
        new Thread(client).start();
	}

	@Override
	public void onClientError(Point point) {
		handler.sendEmptyMessage(point.ordinal());
	}
	
	private static class UiHandler extends Handler {
		
		private Activity activity;
		
		public UiHandler(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void handleMessage(Message msg) {
			String message;
			if (msg.what == Point.connect.ordinal()) {
				message = activity.getString(R.string.toast_connection_error);
			} else {
				message = activity.getString(R.string.toast_send_error);
			}
			Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG);
			toast.show();
			Intent intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
		}
	}

	@Override
	public void onLeft() {
		leftArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_active));
		rightArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
		client.send("left");
	}

	@Override
	public void onRight() {
		leftArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
		rightArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_active));
		client.send("right");
	}

	@Override
	public void onMiddle() {
		leftArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
		rightArrow.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
	};
}
