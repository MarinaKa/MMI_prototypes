package de.ur.mmi.sensorGame;

public interface ClientConnectionErrorListener {

	public static enum Point {
		connect, send
	}

	public void onClientError(Point point);

}
