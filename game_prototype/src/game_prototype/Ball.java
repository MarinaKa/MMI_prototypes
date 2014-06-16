package game_prototype;

public class Ball {

	private int xPos;
	private int yPos;
	private int radius;
	
	public Ball(int r){
		radius = r;
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
}
