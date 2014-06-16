package game_prototype;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaddleListener implements KeyListener {

	public PaddleListener(Paddle paddle) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key Pressed: "+ e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}	

}
