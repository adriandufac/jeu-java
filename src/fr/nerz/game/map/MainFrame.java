package fr.nerz.game.map;

import java.awt.Color;

public class MainFrame extends javax.swing.JFrame{

	
	public MainFrame() {
		
		/** we use panel to display */
		GamePanel panel = new GamePanel();
		panel.setLocation(0,0); // relative to the frame
		panel.setSize(this.getSize());
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setVisible(true);
		this.add(panel);
		
		addKeyListener (new keyChecker (panel));
		
		

		
	}
	
	
}
