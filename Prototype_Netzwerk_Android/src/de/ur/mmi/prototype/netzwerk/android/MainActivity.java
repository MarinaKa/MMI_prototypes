package de.ur.mmi.prototype.netzwerk.android;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	private static Logger log = Logger.getLogger(MainActivity.class.getName());

	// private static final String SERVER_IP = "10.0.2.2";// "192.168.178.27";
	// private static final int SERVER_PORT = 8000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		private static Button connect;
		private static Button send;
		private static EditText serverIp;
		private static EditText serverPort;

		private ClientThread clientThread;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			serverIp = (EditText) rootView.findViewById(R.id.ip);

			serverPort = (EditText) rootView.findViewById(R.id.port);

			connect = (Button) rootView.findViewById(R.id.connect_button);
			connect.setOnClickListener(this);

			send = (Button) rootView.findViewById(R.id.send_button);
			send.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			clientThread.disconnect();
		}

		@Override
		public void onClick(View button) {
			switch (button.getId()) {
			case R.id.send_button:
				clientThread.send("Button");
				Logger.getLogger(getClass().getName()).info("Sent Data");
				break;
			case R.id.connect_button:
				try {
					clientThread = new ClientThread(serverIp.getText()
							.toString(), Integer.valueOf(serverPort.getText()
							.toString()));
					new Thread(clientThread).start();
				} catch (NumberFormatException e) {
					log.log(Level.WARNING, "error: " + e.getMessage(), e);
				}
				break;
			}
		}
	}

}
