package game_prototype;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class PaddleListener extends KeyAdapter {
	
	private GameController c;
	private Paddle p;

	public PaddleListener(GameController c, Paddle p) {
		this.p = p;
		this.c = c;
	}

	@Override
    public void keyPressed(KeyEvent e) {
    	
    	int code = e.getKeyCode();

    	switch(code) {
    	case 37: // links
    		p.changePaddlePosition(-10);
    		break;
    	case 39: // rechts
    		p.changePaddlePosition(10);
    		break;
    	case 27: // esc
    		c.GameOver();
    		break;
    	case 32:
    		c.startPause();
    	}
    }
}
