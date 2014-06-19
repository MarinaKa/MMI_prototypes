package game_prototype;

public class NetworkListener {
	
	private StartFrame startFrame;
	private GameController gameController;
	
	public NetworkListener(StartFrame startFrame, GameController gameController) {
		this.startFrame = startFrame;
		this.gameController = gameController;
	}
	
	public void onError(){
		
	}
	
	public void onReceive(String direction){
		
	}

	public void onConnect() {
		startFrame.startGame();
	}
	
}
