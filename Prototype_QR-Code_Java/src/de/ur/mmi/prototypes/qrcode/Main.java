package de.ur.mmi.prototypes.qrcode;

import java.io.File;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Main {

	public static void main(String[] args) {
		String ip = "123.123.123.123";
		int port = 8000;
		
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = writer.encode(ip + ":" + port, BarcodeFormat.QR_CODE, 300, 300);
			MatrixToImageWriter.writeToPath(bitMatrix, "gif", new File("C:\\temp\\output.gif").toPath());
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
