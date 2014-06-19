package game_prototype;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

	private ServerSocket serverSocket;
	private NetworkListener networkListener;
	
	public ServerThread(NetworkListener networkListener) {
		this.networkListener = networkListener;
	}

	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(IpUtil.SERVERPORT);
		} catch (IOException e) {
			networkListener.onError();
		}
		while (!Thread.currentThread().isInterrupted()) {

			try {

				socket = serverSocket.accept();

				CommunicationThread commThread = new CommunicationThread(socket, networkListener);
				new Thread(commThread).start();
				networkListener.onConnect();

			} catch (IOException e) {
				networkListener.onError();
			}
		}
	}
}