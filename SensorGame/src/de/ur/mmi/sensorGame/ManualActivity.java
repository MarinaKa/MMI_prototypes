package de.ur.mmi.sensorGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ManualActivity extends Activity implements OnClickListener {

	private EditText ipManual;
	private EditText portManual;
	private Button connectManual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		ipManual = (EditText) findViewById(R.id.ip_manual);
		portManual = (EditText) findViewById(R.id.port_manual);
		connectManual = (Button) findViewById(R.id.connect_manual);
		connectManual.setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		switch (button.getId()) {
		case R.id.connect_manual:
			Intent intent = new Intent(this, ControllerActivity.class);
			intent.putExtra("target", ipManual.getText() + ":" + portManual.getText());
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}
