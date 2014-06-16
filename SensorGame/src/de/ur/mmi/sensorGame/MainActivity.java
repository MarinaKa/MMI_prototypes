package de.ur.mmi.sensorGame;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button scanQRcode;
	private Button manual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		scanQRcode = (Button) findViewById(R.id.scanQRcode);
		scanQRcode.setOnClickListener(this);
		manual = (Button) findViewById(R.id.manual);
		manual.setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		switch (button.getId()) {
		case R.id.scanQRcode:
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
			break;
		case R.id.manual:
			Intent intent = new Intent(this, ManualActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (scanResult != null) {
			Intent intent = new Intent(this, ControllerActivity.class);
			intent.putExtra("target", scanResult.getContents());
			startActivity(intent);
		}
	}

}
