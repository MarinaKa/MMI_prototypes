package game_prototype;

public class Ball {
	
	private int xPos;
	private int yPos;
	private int radius;
	private int hitCount;
	private int xDir;
	private int yDir;
	
	public Ball(int r, int startX, int startY){
		radius = r;
		xPos = startX;
		yPos = startY;
		
		hitCount = 0;
		xDir = 0;
		yDir = 5;
	}
	
	public void move() {
		xPos += xDir;
		yPos += yDir;
	}
	
	public void changeDirection(boolean hit, String side)
	{
		if(hit) {
			if (hitCount == 7 || xDir == 0) {
				hitCount = 0;
				setAngle();
			}
			else hitCount++;
		}
		
		if(side.equals("horizontal")) {
			yDir *= -1;
		}

		if(side.equals("vertical")) {
			xDir *= -1;
		}
	}
	
	private void setAngle() {
		int xFlag = getFlag(xDir);
		int yFlag = getFlag(yDir);
		xDir = (int) Math.ceil(Math.random() * 4 + 5) * xFlag/2;
		yDir = (int) Math.ceil(Math.random() * 4 + 5) * yFlag/2;
	}
	
	private int getFlag(int n) {
		if(n<0) return -1;
		else return 1;
	}
	
	public int getXPos()
	{
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int[] getBall() {
		int[] args = {xPos, yPos, radius};
		return args;
	}

}
