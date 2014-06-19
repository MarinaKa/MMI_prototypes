package game_prototype;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpUtil {
	
	public static final int SERVERPORT = 8000;

	public static String getIpAdress () throws SocketException{
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> adresses = networkInterfaces
						.nextElement().getInetAddresses();
				while (adresses.hasMoreElements()) {
					InetAddress address = adresses.nextElement();
					if (address.getAddress().length == 4 && !address.isLoopbackAddress()) {
						return address.getHostAddress();
					}
				}
			}
			throw new SocketException("No IP-Adress found.");
	}
}
