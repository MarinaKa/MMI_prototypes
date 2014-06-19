package game_prototype;

import java.io.File;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrcodeGenerator {

	public static File createQrcode(String ip, int port) throws WriterException, IOException {
		File file = new File(System.getProperty("java.io.tmpdir"), "SensorGameQrcode.gif");
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		bitMatrix = writer.encode(ip + ":" + port, BarcodeFormat.QR_CODE, 300, 300);
		MatrixToImageWriter.writeToPath(bitMatrix, "gif", file.toPath());
		return file;
	}

}
