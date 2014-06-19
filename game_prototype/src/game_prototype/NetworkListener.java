package game_prototype;

import javax.swing.JOptionPane;

public class NetworkListener {

	private StartFrame startFrame;
	private GameController gameController;

	public NetworkListener(StartFrame startFrame, GameController gameController) {
		this.startFrame = startFrame;
		this.gameController = gameController;
	}

	public void onError() {
		if(gameController.getGameF()!= null){
			gameController.getGameF().dispose();
			if(!startFrame.getServerThread().isInterrupted()){
				startFrame.getServerThread().interrupt();
			}
			startFrame = new StartFrame(gameController);
		}
		JOptionPane.showMessageDialog(startFrame.getFrame(), "Netzwerkverbindung verloren");
	}

	public void onReceive(String direction) {
		if ("left".equals(direction)) {
			gameController.getPaddle().changePaddlePosition(-10);
		} else if ("right".equals(direction)) {
			gameController.getPaddle().changePaddlePosition(10);
		}
	}

	public void onConnect() {
		startFrame.startGame();
	}

}
