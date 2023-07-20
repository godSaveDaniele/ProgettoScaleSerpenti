package scaleserpenti.view;

import javax.swing.*;
import javax.swing.border.LineBorder;

import scaleserpenti.model.Casella;

import java.io.IOException;
import java.util.LinkedList;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class BoxCasella extends JPanel implements Observer{
	
	
	private static final long serialVersionUID = 1L;
	private Casella subject;
	private int latoCasella;
	private LinkedList<Integer> listaPartecipanti;
	private  FeaturePedina [] featurePedine;
	
	public BoxCasella (Casella subject, int latoCasella) {
		this.latoCasella=latoCasella;
		riempiFeaturePedine();
		this.subject=subject;
		
		this.setBorder(new LineBorder(Color.BLACK));
		update();
	}
	@Override
	public Dimension getPreferredSize(){
		
		return new Dimension (latoCasella, latoCasella);
	}
	
	public void  update() {
		listaPartecipanti=subject.getListaPartecipanti();
		repaint();
		revalidate();
	}
	

	@Override
	protected void  paintComponent (Graphics g) {
		Graphics2D graphics= (Graphics2D)g;
		Image immagine= null;
		try {
			immagine = subject.getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if ( immagine!=null)
			graphics.drawImage (immagine, 0, 0,latoCasella, latoCasella, null);
		
	
		String num= Integer.toString(subject.getNumCasella()+1);
		g.drawString(num, 0+5, latoCasella -5);
			
		for (int i: listaPartecipanti) {
			Ellipse2D.Double cerchio= new Ellipse2D.Double( featurePedine[i].x, featurePedine[i].y,latoCasella/4, latoCasella/4);
			graphics.setColor(featurePedine[i].color);
			graphics.fill(cerchio);
			graphics.setColor(Color.BLACK);
			graphics.draw(cerchio);
		}
	}
	
	private void riempiFeaturePedine() {
		featurePedine= new FeaturePedina[4];
		featurePedine[0]=new FeaturePedina(10, 10, Color.green);
		featurePedine[1]=new FeaturePedina(20 + latoCasella/4, 10, Color.yellow);
		featurePedine[2]=new FeaturePedina(10, latoCasella/4 + 20, Color.red);
		featurePedine[3]=new FeaturePedina(latoCasella/4+20, latoCasella/4+20, Color.blue);
	}
	
	private static class FeaturePedina implements Serializable {
		double x;
		double y;
		Color color;
		public FeaturePedina ( double x, double y, Color c) {
			this.x=x;
			this.y=y;
			this.color=c;
		}
	}
}
