package fr.nerz.game.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Player {
	
	public enum Keys {
		   Left, Right, Up, Down, Attack;
		}
	
	private GamePanel panel;
	
	final float GRAVITY =0.9f;
	
	private int x;
	private int y;
	
	private int xEpee;
	private int yEpee;
	
	private int width;
	private int height;
	
	private int epeeWidth;
	private int epeeHeight;
	
	private double xSpeed;
	private double ySpeed;
	
	private Rectangle hitBox;
	private Shape hitboxEpee;    // hitbox de départ de l'épée
	private Shape rotatedRect;   // hitbox réel
	
	int rotateAngle;
	
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;
	private boolean keyAttack;
	
	public Player(int x, int y, GamePanel panel) {
		
		this.panel = panel;
		this.x = x;
		this.y = y;
		
		this.width = 50;
		this.height = 100;
	
		this.epeeWidth = 10;
		this.epeeHeight = 60;
		
		this.hitBox = new Rectangle (x,y,this.width ,this.height);
		this.hitboxEpee = new Rectangle (this.xEpee, this.yEpee,0 ,0 );
		this.rotatedRect  = new Rectangle (this.xEpee, this.yEpee,0 ,0 );
	}
	
	public void set() { // methode to update the player 

		this.attack(); // Check if attack
		this.jump();	// check if jump
		this.setSpeed(); // set the speed
		this.collision(); // check for collisions
	
		this.panel.setCameraX(xSpeed); // move the camera insteed of the player X axis
		this.y+= this.ySpeed;
		
		this.hitBox.x = this.x;
		this.hitBox.y = this.y;
	//	System.out.println("x de hitbox : " + this.hitBox.x);
		
		this.xEpee =this.x + this.width -this.epeeWidth/2;
		this.yEpee= this.y-this.epeeHeight;
		
		// mort by chute
		if (this.y > TheGame.screenHeight) {
			panel.reset();
		}
	}
	
	public double getxSpeed() {
		return xSpeed;
	}

	public void setKey(Keys key, boolean state) {
		switch (key) {
		case Left:
			this.keyLeft = state;
			break;
		case Right:
			this.keyRight = state;
			break;
		case Up:
			this.keyUp = state;
			break;
		case Down:
			this.keyDown = state;
			break;
		case Attack:
			this.keyAttack = state;
			break;
		}
	}
	
	public void setSpeed() {
		if (this.keyLeft && this.keyRight || !this.keyLeft && !this.keyRight) {
			this.xSpeed*=0.5; // la vitesse diminue progressivement
		}
		
		else if (this.keyLeft && !this.keyRight ) {
			this.xSpeed -=2;
		}
		
		else if (this.keyRight && !this.keyLeft ) {
			this.xSpeed +=2;
		}
		if (this.xSpeed < 0.75 && this.xSpeed >-0.75) {
			this.xSpeed=0;
		}
		
		if (this.xSpeed > 7) {
			this.xSpeed = 7;
		}
		if ( this.xSpeed < -7) {
			this.xSpeed = -7;
		}
		
		this.ySpeed +=GRAVITY; //gravity   TODO ADD CONST FOR GRAVITY
		if (this.ySpeed > 12) {
			this.ySpeed =12;
		}
	}
	
	public void collision() {
		/*HORIZONTAL COLLISION **/
		hitBox.x+= xSpeed;                    
		for (Wall wall: this.panel.getWalls()) {
			if (this.hitBox.intersects(wall.getHitBox())){
				hitBox.x-=xSpeed;
				while (!wall.getHitBox().intersects(this.hitBox)) {
					this.hitBox.x += Math.signum(this.xSpeed); // POUR QUE CA MARCHE DS DIRECTION NEGATIVE (SINON ++ MARche)   MATh.signum = 1 si xspeed > 0 math.signum = -1 si xspeed <0
				}
				
				hitBox.x -=Math.signum(this.xSpeed);
				this.panel.setCameraX(hitBox.x-this.x);
				this.xSpeed = 0;
				this.hitBox.x = this.x;
			}
		}
		for (Mine mine: this.panel.getMines()) {
			if (this.hitBox.intersects(mine.getHitBox())) {
				//this.panel.reset();        // pour faire une mine qui tue
				this.ySpeed = -30;           // pour faire un jumper 
			}
		}
		
		/* VERTICAL COLLISION **/
		hitBox.y+= ySpeed;
		for (Wall wall: this.panel.getWalls()) {
			if (this.hitBox.intersects(wall.getHitBox())){
				hitBox.y-=ySpeed;
				while (!wall.getHitBox().intersects(this.hitBox)) {
					this.hitBox.y += Math.signum(this.ySpeed); // POUR QUE CA MARCHE DS DIRECTION NEGATIVE (SINON ++ MARche)   MATh.signum = 1 si xspeed > 0 math.signum = -1 si xspeed <0
				}
				
				hitBox.y -=Math.signum(this.ySpeed);
				this.ySpeed = 0;
				this.y =this.hitBox.y;
			}
		}
	}

	public void attack() {
		
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(this.rotateAngle), this.xEpee, this.yEpee+this.epeeHeight+this.height/3); // Rotate according to angle and center
		if (this.keyAttack && this.rotateAngle <90) {
			
			
			((Rectangle) this.hitboxEpee).setBounds (this.xEpee,this.yEpee,this.epeeWidth ,this.epeeHeight);
			this.rotateAngle += 10;
			System.out.println(this.rotateAngle);
			this.rotatedRect = at.createTransformedShape(this.hitboxEpee);
			
			
		}
		else if (!this.keyAttack )
		{
			((Rectangle) this.hitboxEpee).setBounds(this.xEpee, this.yEpee,0 ,0 );
			this.rotatedRect = at.createTransformedShape(this.hitboxEpee);
			this.rotateAngle=0;
		}
		if (this.rotateAngle>=90) {
			((Rectangle) this.hitboxEpee).setBounds(this.xEpee, this.yEpee,0 ,0 );
			this.rotatedRect = at.createTransformedShape(this.hitboxEpee);

		}
		
	}
	
	public void jump() {
		
		if (this.keyUp) {    // JUMPING
			//CHECKING IF TOUCHING GROUND
			hitBox.y++;
			for (Wall wall: this.panel.getWalls()) {
				if (wall.getHitBox().intersects(this.hitBox)) {
					this.ySpeed = -16;
				}
			}
			hitBox.y--;
		}
		
	}
	
	public void draw (Graphics2D gtd) {
		gtd.setColor(Color.RED);
		gtd.fill(this.rotatedRect);
		gtd.setColor(Color.BLACK);
		gtd.fill(this.hitBox);
		
	}
	
	public int getX () {
		return this.x;
	}
	
	public int getY () {
		return this.y;
	}
	
	public void setX (int a) {
		this.x = a;
	}
	
	public void setY (int a) {
		this.y = a;
	}
	
	public void setSpeedX(int a) {
		this.xSpeed =a;
	}
	public void setSpeedY(int a) {
		this.ySpeed =a;
	}
	void setPlayerConst(int x, int y , double xspeed,double yspeed) {
		this.x =x;
		this.y=y;
		this.xSpeed = xspeed;
		this.ySpeed = yspeed;
	}

}
