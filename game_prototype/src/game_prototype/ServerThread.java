package game_prototype;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread implements Runnable {

	private ServerSocket serverSocket;
	private NetworkListener networkListener;
	private Thread thread;
	private List<CommunicationThread> commThreads;
	private boolean closed = false;

	public ServerThread(NetworkListener networkListener) {
		this.networkListener = networkListener;
		commThreads = new ArrayList<CommunicationThread>();
	}

	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(IpUtil.SERVERPORT);
			while (!thread.isInterrupted()) {
				socket = serverSocket.accept();
				CommunicationThread commThread = new CommunicationThread(socket, networkListener);
				commThread.start();
				commThreads.add(commThread);
				networkListener.onConnect();
			}
		} catch (IOException e) {
			if (!closed) {
				e.printStackTrace();
				networkListener.onError();
			}
		}
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void interrupt() {
		thread.interrupt();
		try {
			closed = true;
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CommunicationThread commThread : commThreads) {
			commThread.interrupt();
		}
	}

	public boolean isInterrupted() {
		return thread.isInterrupted() && !thread.isAlive();
	}
}