package de.ur.mmi.sensorGame;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Toast;

public class ControllerActivity extends Activity implements ClientConnectionErrorListener {

	private static Logger log = Logger.getLogger(Client.class.getName());

	private String ip;
	private int port;
	private Client client;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		handler = new UiHandler(this);

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
	};
}
