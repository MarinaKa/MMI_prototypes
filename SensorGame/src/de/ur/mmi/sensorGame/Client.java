package de.ur.mmi.sensorGame;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

	private static final int TIMEOUT = 3000;
	private static Logger log = Logger.getLogger(Client.class.getName());
	private static Socket socket;

	private String serverIp;
	private int serverPort;

	private ClientConnectionErrorListener errorListener;

	public Client(String serverIp, int serverPort, ClientConnectionErrorListener errorListener) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.errorListener = errorListener;
	}

	@Override
	public void run() {
		connect(serverIp, serverPort);
	}

	private void connect(String serverIp, int serverPort) {
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIp);

			if (socket != null) {
				socket.close();
			}
			socket = new Socket();
			socket.connect(new InetSocketAddress(serverAddr, serverPort), TIMEOUT);
			log.info("connected!");
		} catch (Throwable e) {
			log.log(Level.WARNING, "error: " + e.getMessage(), e);
			errorListener.onClientError(ClientConnectionErrorListener.Point.connect);
		}
	}

	public synchronized void disconnect() {
		try {
			socket.close();
		} catch (Throwable e) {
			log.log(Level.WARNING, e.getMessage(), e);
		}
	}

	public synchronized void send(String string) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(string);
		} catch (IOException e) {
			log.log(Level.WARNING, e.getMessage(), e);
			errorListener.onClientError(ClientConnectionErrorListener.Point.send);
		}

	}
}
