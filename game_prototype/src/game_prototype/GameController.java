package game_prototype;

import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameController extends JPanel{
	
	private int xSize;
	private int ySize;
	
	private GameFrame gameF;
	private Paddle paddle;
	private Ball ball;
	private Blocks blocks;
	private PaddleListener pListener;
	
	private Graphics2D g2;
	private Timer myTimer;	
	
	private String pink = "#f50288";
	private String yellow = "#f0de07";
	private String navy = "#10162e";
	private String greygreen = "#8ccdb9";
	
	public static void main(String[] args) throws IOException
	{
		GameController c = new GameController(800, 600);
		new StartFrame(c);
	}
	
	public GameController(int x, int y) {
		xSize = x;
		ySize = y;
	}
	
	public void startGame()
	{
		paddle = new Paddle(xSize, ySize);
		ball = new Ball(xSize+ySize/200);
		blocks = new Blocks(4, 20, xSize, ySize);
		gameF = new GameFrame(xSize,ySize,this);
		pListener = new PaddleListener(paddle);
		
		myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new ScheduleTask(), 1500, 500);
	}
	
	// GameState
	
	private void checkGameState() {
		checkGameOver();
		checkPaddleHit();
		checkBlocksHit();
		checkEdgeHit();
	}
	
	private void checkGameOver() {
		if(ball.getYPos() + ball.getRadius() >= ySize) {
			// game over
			// drawGameOver();
			myTimer.cancel();
			myTimer.purge();
		}
	}
	
	private void checkPaddleHit() {
		if(paddle.checkHit(ball.getXPos(), ball.getYPos())) {
			// change ball direction
		}
	}
	
	private void checkBlocksHit() {
		if(blocks.checkHit(ball.getXPos(), ball.getYPos())) {
			// change ball direction
		}
	}
	
	private void checkEdgeHit() {
		if(ball.getXPos() < ball.getRadius() || ball.getXPos() > xSize-ball.getRadius() || ball.getYPos() < ball.getRadius()){
			// change ball direction
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
