package de.ur.mmi.prototypes.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Main {

	public static final int SERVERPORT = 8000;

	public static void main(String[] args) {
		System.out.println("Starting App...");

		Thread serverThread = new Thread(new ServerThread());
		serverThread.start();

		System.out.println("Waiting for connection...");
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> adresses = networkInterfaces
						.nextElement().getInetAddresses();
				while (adresses.hasMoreElements()) {
					InetAddress address = adresses.nextElement();
					if (address.getAddress().length == 4 && !address.isLoopbackAddress()) {
						System.out.println("IP-Address: "
								+ address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("Port: " + SERVERPORT);
	}

	public static void receive(String read) {
		System.out.println("Received: " + read);
	}

}
