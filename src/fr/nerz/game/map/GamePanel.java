package fr.nerz.game.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import fr.nerz.game.map.Player.Keys;

public class GamePanel extends javax.swing.JPanel implements ActionListener{

	private Player player;
	private TestEnemy ennemie;
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Mine> mines = new ArrayList<Mine>();
	
	private Timer gameTimer;
	
	int offset;
	
	private int cameraX;
	
	public GamePanel() {
		
		player = new Player(100,500,this);
		ennemie = new TestEnemy(200,500,this);
		//makeMines(50);
		reset();
		
		gameTimer = new Timer();
		gameTimer.schedule(new MyTimerTask(player,this,ennemie),0,5); // 17 ms beetwen each frame = > environ 60 fps
		
	}
	
	public void reset() {
		player.setPlayerConst(200,150,0,0);
		ennemie.setEnnemieConst(200,150,0,0);
		this.cameraX = 150;
		this.walls.clear();

		this.offset = -150;
		makeWalls(this.offset , true); 
		//makeMines(offset);
	}
	
	public void makeWalls(int offset, Boolean bool) {
		int s =50; // size of wall
		int index;
		if (bool) {
			 index=0;
		}
		else {
			Random rand = new Random();
			 index = rand.nextInt(4); // random entre 0 et (1-1)
		}
		System.out.println(index);
		switch (index) { //TODO GENERATE BETTER METHOD TO ADD RANDOMLY GENERATED SEQUENCE OF WALLS
		case 0:
			for (int i=0; i<14; i ++) {
				walls.add(new Wall(offset + i*50,600,s,s));
			}
			walls.add(new Wall(offset + 600,550,s,s));
			walls.add(new Wall(offset + 600,500,s,s));
			walls.add(new Wall(offset + 600,450,s,s));
			walls.add(new Wall(offset + 600,550,s,s));
			walls.add(new Wall(offset + 600,500,s,s));
			walls.add(new Wall(offset + 600,450,s,s));
			walls.add(new Wall(offset + 100,550,s,s));
			walls.add(new Wall(offset + 100,500,s,s));
			walls.add(new Wall(offset + 100,450,s,s));
			walls.add(new Wall(offset + 100,550,s,s));
			walls.add(new Wall(offset + 100,500,s,s));
			walls.add(new Wall(offset + 100,450,s,s));
			//walls.add(new Wall(offset + 500,550,s,s));
			break;
		case 1:
			for (int i=0; i<14; i ++) {
				walls.add(new Wall(offset + i*50,600,s,s));
			}
			for (int i=0; i<12; i ++) {
				walls.add(new Wall(offset+s + i*50,550,s,s));
			}
			for (int i=0; i<10; i ++) {
				walls.add(new Wall(offset+2*s + i*50,500,s,s));
			}
			for (int i=0; i<8; i ++) {
				walls.add(new Wall(offset+3*s + i*50,450,s,s));
			}
			for (int i=0; i<6; i ++) {
				walls.add(new Wall(offset+4*s + i*50,400,s,s));
			}
			break;
		
		case 2:
			for (int i=0; i<8; i ++) {
				walls.add(new Wall(offset + i*50,600,s,s));
			}
			for (int i=11; i<14; i ++) {
				walls.add(new Wall(offset + i*50,600,s,s));
			}
			break;
			
		case 3:
			for(int i=0; i<3;i=i+1) {
				walls.add(new Wall(offset + 4*i*50,600-i*50,s,s));
				walls.add(new Wall(offset + 4*i*50+50,600-i*50,s,s));
			}
//			for(int i=3; i>0;i--) {
//				walls.add(new Wall(offset + 4*i*50,600-i*50,s,s));
//				walls.add(new Wall(offset + 4*i*50+50,600-i*50,s,s));
//			}
			
			
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public void makeMines(int offset) {
		
		mines.add(new Mine (350,550,50,50));
		mines.add(new Mine (400,550,50,50));
		mines.add(new Mine (450,550,50,50));
		mines.add(new Mine (500,550,50,50));
		mines.add(new Mine (550,550,50,50));
	}
	
	public void paint(Graphics g) { // MEthode automatiquement appelée a chaque fois que la fenetre dois etre rafraichie 
									// (quand on crée la fenetre par exemple)
		super.paint(g);  // 
		Graphics2D gtd = (Graphics2D) g; 
		
		for (Wall wall: walls) wall.draw(gtd);
		for (Mine mine: mines) mine.draw(gtd);
		player.draw(gtd);
		ennemie.draw(gtd);
	}
	
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'q') player.setKey(Keys.Left, true);
		if (e.getKeyChar() == 'd') player.setKey(Keys.Right, true);
		if (e.getKeyChar() == 's') player.setKey(Keys.Down, true);
		if (e.getKeyChar() == 'a') player.setKey(Keys.Attack, true);
		if (e.getKeyChar() == 'z' || e.getKeyCode()== KeyEvent.VK_SPACE ) player.setKey(Keys.Up, true);
	
		
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'q') player.setKey(Keys.Left, false);
		if (e.getKeyChar() == 'd') player.setKey(Keys.Right, false);
		if (e.getKeyChar() == 's') player.setKey(Keys.Down, false);
		if (e.getKeyChar() == 'a') player.setKey(Keys.Attack, false);
		if (e.getKeyChar() == 'z' || e.getKeyCode()== KeyEvent.VK_SPACE ) player.setKey(Keys.Up, false);
		
	}
	
	public ArrayList<Wall> getWalls(){
		return this.walls;
	}
	
	public ArrayList<Mine> getMines(){
		return this.mines;
	}
	
	public void setCameraX(double x) {
		this.cameraX -= x;
	}
	
	public int getCameraX() {
		return this.cameraX;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	

}
