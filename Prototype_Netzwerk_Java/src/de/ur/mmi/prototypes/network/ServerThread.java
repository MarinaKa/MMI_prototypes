package de.ur.mmi.prototypes.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

	private ServerSocket serverSocket;

	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(Main.SERVERPORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted()) {

			try {

				socket = serverSocket.accept();

				CommunicationThread commThread = new CommunicationThread(socket);
				new Thread(commThread).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}