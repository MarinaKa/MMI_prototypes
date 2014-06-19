package game_prototype;

import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameController extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private int xSize;
	private int ySize;
	
	private GameFrame gameF = null;

	private Paddle paddle;
	private Ball ball;
	private Blocks blocks;
	
	private Graphics2D g2;
	private Timer myTimer;	
	
	public String pink = "#f50288";
	public String yellow = "#f0de07";
	public String navy = "#10162e";
	public String greygreen = "#8ccdb9";
	
	private boolean paused;
	
	public static void main(String[] args) throws IOException
	{
		GameController c = new GameController();
		new StartFrame(c);

	}
	
	public GameController() {

	}
	
	public void startGame()
	{
		gameF = new GameFrame(this);
		
		xSize = gameF.getWidth() - gameF.getInsets().right - gameF.getInsets().left;
		ySize = gameF.getHeight() - gameF.getInsets().top - gameF.getInsets().bottom;
			
		paddle = new Paddle(xSize, ySize);
		ball = new Ball((xSize+ySize)/200, xSize/2, ySize/2);
		blocks = new Blocks(30, 5, xSize, ySize);
		
		gameF.addKeyListener(new PaddleListener(this, paddle));
		
		paused = true;
		startPause();
	}
	 
	public Paddle getPaddle() {
		return paddle;
	}
	public GameFrame getGameF() {
		return gameF;
	}

	public void startPause() {
		
		if(paused) {
			myTimer = new Timer();
			myTimer.scheduleAtFixedRate(new ScheduleTask(), 0, 50);
			paused = false;
		}
		else {
			myTimer.cancel();
			paused = true;
		}
	}
	// GameState
	
	private void checkGameState() {
		ball.move();
		checkPaddleHit();
		checkBlocksHit();
		checkEdgeHit();
	}
	
	public void GameOver() {
		// drawGameOver();
		myTimer.cancel();
		myTimer.purge();
	}
	
	private void checkPaddleHit() {
		if(paddle.checkHit(ball.getXPos(), ball.getYPos(), ball.getRadius())) {
			ball.changeDirection(true, "horizontal");
		}
	}
	
	private void checkBlocksHit() {
		if(blocks.checkHit(ball.getXPos(), ball.getYPos())) {
			ball.changeDirection(false, "horizontal");
		}
	}
	
	private void checkEdgeHit() {
		if(ball.getXPos() <= 0 || ball.getXPos() > xSize-2*ball.getRadius()-15){
			ball.changeDirection(false, "vertical");
		}
		
		if(ball.getYPos() <= 0) { 
			ball.changeDirection(false, "horizontal");
		}
		
		else if(ball.getYPos() + 2*ball.getRadius() >= ySize)
		{
			GameOver();
		}
	}
	
	// draw GameState
	
	public void paint(Graphics g) {
		super.paint(g);
		g2 = (Graphics2D) g;
		
		drawBackground();
		drawBlocks();
		drawPaddle();
		drawBall();
	}
	
	private void drawBackground() {
		g2.setBackground(Color.decode(navy));
		g2.clearRect(0, 0, xSize, ySize);
	}
	
	private void drawBlocks() {
		
		g2.setColor(Color.decode(greygreen));
		
		int[] args = blocks.getBlocks(); // width, height, yPos, f
		int blockWidth = xSize/args[0];
		int blockHeight = ySize/(args[3]*args[1]);
		int yPos = args[2];
		
		boolean[][] blocksPattern = blocks.getBlocksPattern();
		
		for(int i=0; i<args[1]; i++) {
			for(int j=0; j<args[0]; j++) {
				if(blocksPattern[i][j]) {
					g2.fillRect(j*blockWidth, yPos + i*blockHeight, blockWidth, blockHeight);
				}
			}
		}
	}
	
	private void drawPaddle() {
		g2.setColor(Color.decode(pink));
		int[] args = paddle.getPaddle();
		g2.fillRect(args[0], args[1], args[2], args[3]);
	}
	
	private void drawBall() {
		g2.setColor(Color.decode(yellow));
		int[] args = ball.getBall();

		Ellipse2D.Double circle = new Ellipse2D.Double(args[0],args[1],args[2]*2,args[2]*2);
		g2.fill(circle);
	}
	
	// Timer
	
	class ScheduleTask extends TimerTask
	{
		public void run()
		{
			checkGameState();
			repaint();
		}
	}
}
