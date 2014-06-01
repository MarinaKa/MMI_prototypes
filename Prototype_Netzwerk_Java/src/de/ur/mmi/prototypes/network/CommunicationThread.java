package de.ur.mmi.prototypes.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CommunicationThread implements Runnable {

	private Socket clientSocket;

	private BufferedReader input;

	public CommunicationThread(Socket clientSocket) {

		this.clientSocket = clientSocket;

		try {

			this.input = new BufferedReader(new InputStreamReader(
					this.clientSocket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while (!Thread.currentThread().isInterrupted()) {

			try {
				String read = input.readLine();

				if (read != null) {
					Main.receive(Thread.currentThread().getName() + " - "
							+ read);
				} else {
					Main.receive(Thread.currentThread().getName()
							+ " is dead :(");
					Thread.currentThread().interrupt();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}