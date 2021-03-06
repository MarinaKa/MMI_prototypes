package game_prototype;

public class Paddle {
	
	private int xSizeFrame;
	private int xSizePaddle;
	private int ySizePaddle;
	private int xPos;
	private int yPos;
	
	public Paddle(int xSize, int ySize)
	{
		xSizeFrame = xSize;
		xSizePaddle = xSize/10;
		ySizePaddle = ySize/100;
		xPos = (xSize-xSizePaddle)/2;
		yPos = ySize - ySize/6;
	}
	
	public void changePaddlePosition(float change)
	{
		if(change < 0 && xPos > 0)
		{
			xPos -= xSizeFrame/50;
		}
		
		else if(change > 0 && xPos < xSizeFrame - xSizePaddle)
		{
			xPos += xSizeFrame/50;
		}
	}
	
	public int[] getPaddle()
	{
		int[] args = {xPos, yPos, xSizePaddle, ySizePaddle};
		return args;
	}
	
	public boolean checkHit(int ballX, int ballY, int r)
	{
		if(ballY+2*r < yPos || ballY+2*r > yPos + ySizePaddle) {
			return false;
		}
		
		if(ballX >= xPos && ballX <= xPos + xSizePaddle)
		{
			return true;
		}
		
		return false;
	}
}
