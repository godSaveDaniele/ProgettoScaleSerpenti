package scaleserpenti.test;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import scaleserpenti.panels.PannelloConfigurazione;

public class Test {
	
	public static void main (String[] args) {
		JFrame f= new JFrame();
		JPanel cardPanel= new JPanel();
		CardLayout cardLayout= new CardLayout();
 		cardPanel.setLayout(cardLayout);
		cardPanel.add(new PannelloConfigurazione(cardPanel, cardLayout));
		f.add(cardPanel);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

}
