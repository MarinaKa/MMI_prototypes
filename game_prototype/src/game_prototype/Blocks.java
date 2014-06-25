package game_prototype;

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
		
		if(ballY <= yStartPos || ballY >= yStartPos + ySize)
		{
			return false;
		}
		
		float blockXSize = xSize/xNumBlocks;
		float x = ballX/blockXSize;
		
		float blockYSize = ySize/yNumBlocks;
		float y = (ballY-yStartPos)/blockYSize-1;
		
		int xLoc = (int) Math.floor(x);
		int yLoc;
		
		if (y<1) yLoc = 0;
		else if (y>=1 && y<2) yLoc = 1;
		else if (y>=2 && y<3) yLoc = 2;
		else if (y>=3 && y<4) yLoc = 3;
		else yLoc = 4;
		
		if (xLoc < 0 || xLoc > xNumBlocks) {
			return false;
		}
		
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
