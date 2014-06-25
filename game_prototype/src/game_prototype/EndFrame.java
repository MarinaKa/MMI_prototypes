package game_prototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndFrame implements ActionListener {
	private JFrame frame;
	private GameController c;
	private ServerThread serverThread;
	
	
	
	public EndFrame(GameController c) {
		this.c = c;
		frame = new JFrame("SensorGame");
		frame.setUndecorated(true);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.decode(c.navy));
		addComponentsToPane(frame);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	private void addComponentsToPane(Container pane) {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.decode(c.navy));

		JLabel header = new JLabel("Game Over", JLabel.LEFT);
		header.setForeground(Color.white);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(64.0f));
		
		JButton closeButton = new JButton("X");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EndFrame.this.frame.dispose();
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


		JButton startButton = new JButton("Spiel neu starten");
		startButton.setBackground(Color.decode(c.navy));
		startButton.setForeground(Color.decode(c.yellow));
		startButton.setBorderPainted(false);
		startButton.addActionListener(this);
		pane.add(startButton, BorderLayout.CENTER);
	}	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.dispose();
		c.startGame();
		
	}
}
