package game_prototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.google.zxing.WriterException;


public class StartFrame implements ActionListener {

	private JFrame frame;
	private GameController c;
	private ServerThread serverThread;

	public ServerThread getServerThread() {
		return serverThread;
	}

	public StartFrame(GameController c) {
		this.c = c;
		frame = new JFrame("SensorGame");
		frame.setUndecorated(true);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.decode(c.navy));
		addComponentsToPane(frame);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		serverThread = new ServerThread(new NetworkListener(this, c));
		serverThread.start();
	}
	
	public JFrame getFrame() {
		return frame;
	}

	private void addComponentsToPane(Container pane) {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.decode(c.navy));

		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("game_prototype/ic_launcher.png"));
		JLabel header = new JLabel("SensorGame", icon, JLabel.LEFT);
		header.setForeground(Color.decode(c.pink));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(64.0f));

		JButton closeButton = new JButton("X");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StartFrame.this.frame.dispose();
				serverThread.interrupt();
			}
		});
		closeButton.setBackground(Color.decode(c.pink));
		closeButton.setForeground(Color.decode(c.navy));
		closeButton.setBorderPainted(false);

		JPanel closePanel = new JPanel(new FlowLayout());
		closePanel.setBackground(Color.decode(c.navy));

		closePanel.add(closeButton);
		headerPanel.add(header, BorderLayout.CENTER);
		headerPanel.add(closePanel, BorderLayout.LINE_END);
		pane.add(headerPanel, BorderLayout.PAGE_START);

		try {
			File qrCodeFile = QrcodeGenerator.createQrcode(IpUtil.getIpAdress(), IpUtil.SERVERPORT);
			ImageIcon qrCodeIcon = new ImageIcon(qrCodeFile.getAbsolutePath());
			JLabel qrCode = new JLabel(qrCodeIcon);
			pane.add(qrCode, BorderLayout.CENTER);
		} catch (WriterException | IOException e) {
			JLabel errorMessage = new JLabel("Fehler beim Erzeugen des QR-Codes. Bitte starte das Programm neu.");
			errorMessage.setForeground(Color.decode(c.pink));
			errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
			pane.add(errorMessage, BorderLayout.CENTER);
		}

		JButton startButton = new JButton("Spiel ohne Smartphonesteuerung starten.");
		startButton.setBackground(Color.decode(c.navy));
		startButton.setForeground(Color.decode(c.yellow));
		startButton.setBorderPainted(false);
		startButton.addActionListener(this);
		pane.add(startButton, BorderLayout.PAGE_END);
	}

	public void actionPerformed(ActionEvent e) {
		startGame();
	}
	
	public void startGame(){
		frame.dispose();
		c.startGame();
	}
}
