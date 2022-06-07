package fr.nerz.game.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class TestEnemy {
	
private GamePanel panel;
	
	final float GRAVITY =0.9f;
	
	private int startX;
	private int x;
	private int y;
	
	private int tmp = this.startX;
	
	private boolean GORIGHT =true;
	private boolean GOLEFT =false;
	
	private int width;
	private int height;
	
	private double xSpeed;
	private double ySpeed;
	
	private Rectangle hitBox;
	
	public TestEnemy(int x, int y, GamePanel panel) {
			
		this.panel = panel;
		this.startX=x;
		this.x = x;
		this.y = y;

		
		this.width = 50;
		this.height = 50;
	

		
		this.hitBox = new Rectangle (x,y,this.width ,this.height);
	}
	
	public void set(int cameraX) { // methode to update the player 

		
		this.deplacement();
		this.collision(); // check for collisions
		//this.xSpeed=5;q
		 //int tmp =this.startX;
		tmp+=this.xSpeed;
		tmp-=this.panel.getPlayer().getxSpeed();


		
	    
	    System.out.println(tmp);
	    this.x=tmp;
	    this.x+= this.xSpeed;
		
		this.y+= this.ySpeed;
		this.hitBox.x = this.x;
		this.hitBox.y = this.y;
		//this.collision(); // check for collisions
	//	System.out.println("x de hitbox : " + this.hitBox.x);

		
	}
	public void collision() {
		/*HORIZONTAL COLLISION **/
		boolean turnleft;
		hitBox.x+= xSpeed;                    
		for (Wall wall: this.panel.getWalls()) {
			if (this.hitBox.intersects(wall.getHitBox())){//(this.hitBox.intersects(wall.getHitBox()))(this.hitBox.intersectsLine(wall.getX(),wall.getY(),wall.getX(),wall.getY()+wall.getHeight()))
				hitBox.x-= xSpeed;

				 
				if (this.GORIGHT) {
					turnleft = true;
				}
				else {
					turnleft = false;
				}
				
				while (!wall.getHitBox().intersects(this.hitBox)) {
					if (turnleft) {
						this.GORIGHT = false;
						this.GOLEFT = true;
					}
					else {
						this.GORIGHT = true;
						this.GOLEFT = false;
					}
					
					this.hitBox.x += Math.signum(this.xSpeed); // POUR QUE CA MARCHE DS DIRECTION NEGATIVE (SINON ++ MARche)   MATh.signum = 1 si xspeed > 0 math.signum = -1 si xspeed <0
					
				}
				//System.out.println("AAAAAAAAAAAAAAAAAAAA");
				hitBox.x -=Math.signum(this.xSpeed);
				this.xSpeed = 0;
				this.x =this.hitBox.x;
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
	
	public void deplacement () {
		this.ySpeed +=GRAVITY; //gravity   TODO ADD CONST FOR GRAVITY
		if (this.ySpeed > 12) {
			this.ySpeed =12;
		}
		//System.out.println(this.xSpeed);
		
		if (this.ySpeed < 2 && GORIGHT) {
		//	System.out.println("qsdqsdqsdq");
			this.xSpeed=3;
		}
		if (this.ySpeed < 2 && GOLEFT) {
			this.xSpeed=-3;
		}
		
	}
	
	void setEnnemieConst(int x, int y , double xspeed,double yspeed) {
		this.x =x;
		this.y=y;
		this.xSpeed = xspeed;
		this.ySpeed = yspeed;
	}
	
	public void draw (Graphics2D gtd) {
		gtd.setColor(Color.BLUE);
		gtd.fill(this.hitBox);
		
	}

}
