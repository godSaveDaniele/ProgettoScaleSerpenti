package scaleserpenti.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import scaleserpenti.simulation.Simulatore;

public class PannelloLogger extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	Simulatore subject;
	

	
	public PannelloLogger(){
		this.setBorder(new LineBorder(Color.BLACK));
		this.update();
	}
	
	public void setSubject(Simulatore sim) {
		subject=sim;
	}
	
	public void update() {
		repaint();
		revalidate();
	}

	
	
	@Override
	public void paintComponent (Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		String daStampare= subject.getDaStampare();
		g.setColor(Color.BLACK);
		String[] righe = daStampare.split("\n");
		int y=30;

		for (String riga : righe) {
		    g.drawString(riga, 30, y);
		    y+=20;
		}
	}
}
