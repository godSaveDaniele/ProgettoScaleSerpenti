package scaleserpenti.panels;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import scaleserpenti.model.Casella;
import scaleserpenti.model.Griglia;
import scaleserpenti.model.TipoCasella;
import scaleserpenti.view.BoxCasella;
import scaleserpenti.view.PannelloLogger;



public class PannelloGrafico extends JPanel{
	

	private JPanel griglia;
	private int latoCasella;
	private int n;
	private int m;
	
	
	public PannelloGrafico (Griglia subject, JPanel cardPanel, CardLayout cardLayout) {
		this.latoCasella=Math.min(590/subject.getNumRighe(), 590/subject.getNumColonne());
		this.n= subject.getNumRighe();
		this.m= subject.getNumColonne();
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		
		
		this.griglia= new JPanel (new GridLayout (subject.getNumRighe(), subject.getNumColonne()));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		for (int i=n-1; i>=0; i--){
			if (i%2==0) {
				for (int j=0; j<m; j++) {
					Casella casella=subject.getCasella(i*m+j);
					BoxCasella box= new BoxCasella(casella,latoCasella);
					casella.aggiugiObserver(box);
					griglia.add (box);
				}
			}else {
				for (int j=m-1; j>=0; j--) {
					Casella casella= subject.getCasella(i*m+j);
					BoxCasella box= new BoxCasella(casella, latoCasella);
					casella.aggiugiObserver(box);
					griglia.add (box);
				}
			}
		}
		layeredPane.add(griglia, Integer.valueOf(0));
		griglia.setBounds (0,0, latoCasella*subject.getNumColonne(), latoCasella*subject.getNumRighe());
		
		//AGGIUNGI SCALE
		for (int i=0; i<n*m; i++) {
			if (subject.getCasella(i).getTipo()==TipoCasella.INIZIO_SCALA) {
				JPanel scala= new JPanel() {
					@Override
					protected void paintComponent(Graphics g) {
						File file = new File("scala2.png");
						BufferedImage imm = null;
						try {
							imm = ImageIO.read(file);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
						//ImageIcon im= new ImageIcon(PannelloGrafico.class.getResource("scala2.png"));
						//Image imm= im.getImage();
						g.drawImage(imm, 0,0, this.getWidth(), this.getHeight(), null);
					}
				};
				layeredPane.add (scala, Integer.valueOf(1));
				int j= subject.getCasella(i).getFineScala();
				
				int deltaX= Math.abs(colonna(i)-colonna (j));
				int deltaY= Math.abs(riga(i)- riga(j));
				scala.setBounds( colonna(i)*latoCasella + latoCasella/2, riga (j)*latoCasella - latoCasella/2, deltaX*latoCasella, deltaY*latoCasella);
			}
		}
		
		
		//AGGIUNGI SERPENTI 
		for (int i=0; i<n*m; i++) {
			if (subject.getCasella(i).getTipo()==TipoCasella.LINGUA_SERPENTE) {
				JPanel serpente= new JPanel() {
					@Override
					protected void paintComponent(Graphics g) {
						File file = new File("serpente2.png");
						BufferedImage imm = null;
						try {
							imm = ImageIO.read(file);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
						//ImageIcon im= new ImageIcon(PannelloGrafico.class.getResource("serpente2.png"));
						//Image imm= im.getImage();
						g.drawImage(imm, 0,0, this.getWidth(), this.getHeight(), null);
					}
				};
				layeredPane.add (serpente, Integer.valueOf(2));
				int j= subject.getCasella(i).getCodaSerpente();
				
				int deltaX= Math.abs(colonna(i)-colonna (j));
				int deltaY= Math.abs(riga(i)- riga(j));
				serpente.setBounds( colonna(i)*latoCasella + latoCasella/2, riga (i)*latoCasella - latoCasella/2, deltaX*latoCasella, deltaY*latoCasella);
			}
		}
		
		JButton buttonSalvaConfigurazione= new JButton (" Salva configurazione");
		buttonSalvaConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				subject.salva();
				cardLayout.previous(cardPanel);
				cardPanel.remove(1);
			}
		});
		layeredPane.add(buttonSalvaConfigurazione, Integer.valueOf(3));
		buttonSalvaConfigurazione.setBounds (latoCasella *(m+1), 150, 200, 50);
		
		
		PannelloLogger logger= new PannelloLogger();
		JButton buttonAvvia= new JButton ("Avvia simulazione");
		buttonAvvia.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				subject.avvia();
				logger.setSubject(subject.getSimulatore());
				subject.getSimulatore().addObserver(logger);
				layeredPane.add(logger, Integer.valueOf(4));
				logger.setBounds(m*latoCasella +20, 150, 350, 450);
				layeredPane.remove(buttonSalvaConfigurazione);
				layeredPane.remove(buttonAvvia);
			}
		});
		layeredPane.add(buttonAvvia, Integer.valueOf(3));
		buttonAvvia.setBounds (latoCasella *(m+1), 50, 200, 50);
		
		this.add(layeredPane);
		
		layeredPane.setPreferredSize(new Dimension (1000,600));
		repaint();
		revalidate();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000,600);
	}
	
	
	private int colonna (int i) {
		int riga = i/m;
		int colonna;
		switch (riga%2) {
			case 0: colonna= i%m;break;
			default: colonna= m -1-(i%m);
		}
		return colonna;
	}
	
	private  int riga (int i) {
		int riga= i/m;
		return n-riga;
		
	}
	

}
