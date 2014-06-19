package game_prototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CommunicationThread implements Runnable {

	private Socket clientSocket;
	private BufferedReader input;
	private NetworkListener networkListener;
	private Thread thread;

	public CommunicationThread(Socket clientSocket, NetworkListener networkListener) {

		this.clientSocket = clientSocket;
		this.networkListener = networkListener;

		try {

			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
			networkListener.onError();
		}
	}

	public void run() {
		try {
			while (!thread.isInterrupted()) {
				String read = input.readLine();

				if (read != null) {
					networkListener.onReceive(read);
				} else {
					networkListener.onError();
					Thread.currentThread().interrupt();
				}
			}
		} catch (IOException e) {
			thread.interrupt();
			e.printStackTrace();
			networkListener.onError();
		}
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void interrupt() {
		thread.interrupt();
	}

}