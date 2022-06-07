package fr.nerz.game.map;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
	
	private Player player;
	private GamePanel panel;
	private TestEnemy ennemie;

	public MyTimerTask( Player player,	GamePanel panel,TestEnemy ennemie ) {
		super();
		this.player = player;
		this.panel = panel;
		this.ennemie = ennemie;
		
	}
	@Override
	public void run() { // methode qui sera appelé tte les 17 ms 
		// TODO Auto-generated method stub
		this.ennemie.set(panel.getCameraX());
		this.player.set();
		
		
		if (this.panel.getWalls().get(this.panel.getWalls().size()-1).getX() <TheGame.screenWidth+100 ) { // si le X de le dernier mur de la liste de mur du panel et <800 (donc si il arrive a l'écran)
			this.panel.offset+=TheGame.screenWidth;
			this.panel.makeWalls(this.panel.offset,true);
			System.out.println("nmbre de murs : " + this.panel.getWalls().size());
			System.out.println(this.panel.getWalls().get(this.panel.getWalls().size()-1).getX());
			
		}
		
		for (Wall wall: this.panel.getWalls()) {
			wall.set(panel.getCameraX());
		}
		
//		for (Mine mine: this.panel.getMines()) {
//			mine.set(panel.getCameraX());
//		}
		
		for(int i =0; i<this.panel.getWalls().size(); i++) {                        // sert a supprimer les murs trop a gauche
			if (this.panel.getWalls().get(i).getX() <0) {//TheGame.screenWidth+100
				this.panel.getWalls().remove(i);
			}
		}
		for(int i =0; i<this.panel.getMines().size(); i++) {                        // sert a supprimer les murs trop a gauche
			if (this.panel.getMines().get(i).getX() <-0) { //TheGame.screenWidth+100
				this.panel.getMines().remove(i);
			}
		}
		
		
		this.panel.repaint(); //repaint automatiquement appel la fct paint

	}

}
