package game_prototype;

import java.awt.Component;

public class Blocks {
	
	private int xNumBlocks;
	private int yNumBlocks;
	private int xSize;
	private int ySize;
	private int yStartPos;
	
	private boolean[][] blocks;

	public Blocks(int w, int h, int x, int y) {
		xNumBlocks = w;
		yNumBlocks = h;
		xSize = x;
		ySize = y/6;
		yStartPos = y/8;
		
		blocks = new boolean[yNumBlocks][xNumBlocks];
		setBlocks();
	}
	
	private void setBlocks(){
		for(int i=0; i<yNumBlocks; i++){
			for(int j=0; j<xNumBlocks; j++){
				blocks[i][j] = true;
			}
		}
	}
	
	public boolean checkHit(int ballX, int ballY) {
		
		if(ballY < yStartPos || ballY > yStartPos + ySize)
		{
			return false;
		}
		
		float blockxNumBlocks = xSize/xNumBlocks;
		float x = ballX/blockxNumBlocks;
		
		float blockyNumBlocks = ySize/yNumBlocks;
		float y = (ballY-yStartPos)/blockyNumBlocks;
		
		int xLoc = (int) Math.floor(x);
		int yLoc = (int) Math.floor(y);
		
		if(blocks[yLoc][xLoc])
		{
			blocks[yLoc][xLoc] = false;
			return true;
		}
		
		return false;
	}
	
	public boolean[][]	getBlocksPattern() {
		return blocks;
	}
	
	public int[] getBlocks() {
		int args[] = {xNumBlocks, yNumBlocks, yStartPos, ySize};
		return args;
	}
}
