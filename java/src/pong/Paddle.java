package pong;

public class Paddle {

	public static final int WIDTH = 13; //how wide the paddle is
	public static final int HEIGHT = 70; //how tall the paddle is

	private int speed = 10;
	private int xPos, yPos;
	
	//constructor 
	public Paddle(int xPos, int yPos) {
		//prevent from creating 'bad' paddles outside of the top or bottom of window
		if (yPos > Game.WINDOW_HEIGHT - 110 || yPos < 0){
			this.yPos = Game.WINDOW_HEIGHT / 2;//just create one in middle of height, wouldnt do this for x because dont know if p1 or p2
		}else{
			this.xPos = xPos;
			this.yPos = yPos;
		}
	}

	public void setPosition(int newYPosition) {
		this.yPos = newYPosition;
	}
	
	public void moveUp(){
		yPos = Math.max(yPos - speed, 0);
	}

	public void moveDown(){
		yPos = Math.min(yPos + speed, Game.WINDOW_HEIGHT - 110);
	}
	
	//getter
	public int getXPos() {
		return xPos;
	}
	
	//getter
	public int getYPos() {
		return yPos;
	}
}
