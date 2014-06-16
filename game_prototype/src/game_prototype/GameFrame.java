package game_prototype;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public GameFrame(int x, int y, GameController c) {		
		add(c);
		setTitle("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(x,y);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
	}
}
