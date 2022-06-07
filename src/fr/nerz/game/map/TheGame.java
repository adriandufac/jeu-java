package fr.nerz.game.map;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class TheGame {

	static int screenWidth = 700;
	static int screenHeight = 700;
	public static void main(String[] args) {
		
		
		MainFrame frame = new MainFrame();
		
		frame.setSize(screenWidth,screenHeight);
		
		/* Pour centrer la fenetre au milieu de l'écran*/
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // récupére la dimension de l'écran
		frame.setLocation((int) (screenSize.getWidth()/2 - frame.getSize().getWidth()/2),
						  (int) (screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
		
		frame.setResizable(false); // on veut pas qu'on puisse redimenssionner la fenetre
		frame.setVisible(true);
		frame.setTitle("My Game");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
