package game_prototype;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class StartFrame implements ActionListener{
	
	private JFrame frame;
	private GameController c;
	
	public StartFrame(GameController c)
	{
		frame = new JFrame("Setup");
		frame.setSize(500,300);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		addComponentsToPane(frame);		
		
		this.c = c;
	}
	
	private void addComponentsToPane(Container pane)
	{
		JButton startButton = new JButton("Start");
		startButton.addActionListener(this);
		startButton.setPreferredSize(new Dimension(150, 30));
		pane.add(startButton, BorderLayout.PAGE_START);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		frame.dispose();
		c.startGame();
	}
}
