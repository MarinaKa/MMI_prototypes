package game_prototype;

import java.awt.Frame;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public GameFrame(GameController c) {		
		setTitle("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(c);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}
}
