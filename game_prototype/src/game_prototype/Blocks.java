package game_prototype;

public class Blocks {
	
	private int width;
	private int height;
	private int xSize;
	private int ySize;
	private int yPos;
	private int blockHeight;
	private int f;
	
	private boolean[][] blocks;

	public Blocks(int w, int h, int x, int y) {
		width = w;
		height = h;
		xSize = x;
		ySize = y;
		yPos = y/8;
		f = 6;
		
		blocks = new boolean[h][w];
		setBlocks();
	}
	
	private void setBlocks(){
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				blocks[i][j] = true;
			}
		}
	}
	
	public boolean checkHit(int ballX, int ballY) {
		
		if(ballY < yPos || ballY > yPos + blockHeight * height)
		{
			return false;
		}
		
		int xLoc = Math.floorDiv(ballX, xSize/width);
		int yLoc = Math.floorDiv(ballY-yPos, ySize/(height*f));
		
		if(blocks[yLoc][xLoc])
		{
			blocks[yLoc][xLoc] = false;
			return true;
		}
		
		return false;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean[][]	getBlocksPattern() {
		return blocks;
	}
	
	public int[] getBlocks() {
		int args[] = {width, height, yPos, f};
		return args;
	}
}
