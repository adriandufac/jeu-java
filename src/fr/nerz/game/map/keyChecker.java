package fr.nerz.game.map;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class keyChecker extends KeyAdapter {
	
	private GamePanel panel;
	
	public  keyChecker (GamePanel panel) {
		this.panel = panel;
	}
	
	@Override 
	public void keyPressed (KeyEvent e) {
		panel.keyPressed(e);
	}
	@Override 
	public void keyReleased (KeyEvent e) {
		panel.keyReleased(e);
	}
	
	

}
