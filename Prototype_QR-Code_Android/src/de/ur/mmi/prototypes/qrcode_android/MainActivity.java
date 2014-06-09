package de.ur.mmi.prototypes.qrcode_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity implements OnClickListener {

	Button startScanning;
	TextView results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startScanning = (Button) findViewById(R.id.startScanning);
		startScanning.setOnClickListener(this);
		results = (TextView) findViewById(R.id.results);
	}

	@Override
	public void onClick(View arg0) {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (scanResult != null) {
			// handle scan result
			results.setText(scanResult.getContents());
		}
	}

}
