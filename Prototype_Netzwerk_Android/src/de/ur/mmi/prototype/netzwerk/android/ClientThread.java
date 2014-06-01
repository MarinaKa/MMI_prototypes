package de.ur.mmi.prototype.netzwerk.android;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread implements Runnable {

	private static Logger log = Logger.getLogger(ClientThread.class.getName());
	private static Socket socket;

	private String serverIp;
	private int serverPort;

	public ClientThread(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
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
			socket = new Socket(serverAddr, serverPort);
			send("Hallo");

		} catch (Throwable e) {
			log.log(Level.WARNING, "error: " + e.getMessage(), e);
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
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(string);
		} catch (UnknownHostException e) {
			log.log(Level.WARNING, e.getMessage(), e);
		} catch (IOException e) {
			log.log(Level.WARNING, e.getMessage(), e);
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage(), e);
		}

	}
}
