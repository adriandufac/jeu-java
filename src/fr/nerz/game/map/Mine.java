package fr.nerz.game.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Mine {
	
	private GamePanel panel;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle hitBox;
	int startX;
	
	public Mine (int x, int y , int width, int height) {
		
		this.x = x;
		this.startX=x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hitBox = new Rectangle (x,y,width ,height);
	}
	
	public int set(int cameraX) {
		this.x = this.startX + cameraX;
		this.hitBox.x=this.x;
		return this.x;
	}
	
	public Rectangle getHitBox(){
		return this.hitBox;
	}
	
public void draw (Graphics2D gtd) {
		
		gtd.setColor(Color.RED);
		
		gtd.drawRect(x, y, width, height);
		gtd.setColor(Color.RED);
		gtd.fillRect(x, y, this.width, this.height);		
	}

public int getX() {
	return x;
}

public int getY() {
	return y;
}

}
