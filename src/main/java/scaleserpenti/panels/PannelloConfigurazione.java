package scaleserpenti.panels;

import javax.swing.*;

import scaleserpenti.model.Griglia;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class PannelloConfigurazione extends JPanel{
	
	JPanel cardPanel;
	CardLayout cardLayout;
	public PannelloConfigurazione ( JPanel cardPanel, CardLayout cardLayout){
		this.cardPanel=cardPanel;
		this.cardLayout=cardLayout;
		Griglia.Builder grigliaBuilder= new Griglia.Builder();
		
		this.setLayout( new BoxLayout (this, BoxLayout.Y_AXIS));
		JRadioButton button1= new JRadioButton("1");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grigliaBuilder.setNumPartecipanti(1);
			}
		});
		button1.doClick();
		JRadioButton button2= new JRadioButton("2");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grigliaBuilder.setNumPartecipanti(2);
			}
		});
		
		JRadioButton button3= new JRadioButton("3");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grigliaBuilder.setNumPartecipanti(3);
			}
		});
		JRadioButton button4= new JRadioButton("4");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grigliaBuilder.setNumPartecipanti(4);
			}
		});
		
		ButtonGroup group1= new ButtonGroup();
		group1.add(button1);
		group1.add(button2);
		group1.add(button3);
		group1.add(button4);
		
		
		
		
		
		JPanel riquadro0= new JPanel();
		riquadro0.setLayout( new BoxLayout (riquadro0, BoxLayout.Y_AXIS));
		riquadro0.add(new JLabel("Scegli il numero di partecipanti"));
		riquadro0.add(button1);
		riquadro0.add(button2);
		riquadro0.add(button3);
		riquadro0.add(button4);
		this.add(riquadro0);
		
		
		
	
		 
		JRadioButton button48= new JRadioButton("6 x 8");
		button48.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grigliaBuilder.setDimensione(6, 8);
			}
		});
		button48.doClick();
		JRadioButton button100= new JRadioButton("10 x 10");
		button100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grigliaBuilder.setDimensione(10, 10);
			}
		});
		
		
		ButtonGroup group2= new ButtonGroup();
		group2.add(button48);
		group2.add(button100);
		
		JPanel riquadro1= new JPanel();
		
		riquadro1.setLayout( new BoxLayout (riquadro1, BoxLayout.Y_AXIS));
		
		
		riquadro1.add(new JLabel("Scegli la dimensione della griglia:"));
		riquadro1.add(button48);
		riquadro1.add(button100);
		riquadro1.setPreferredSize(new Dimension (300, 100));
		this.add(riquadro1, BorderLayout.CENTER);
		
		JRadioButton buttonDadoSingolo= new JRadioButton("Dado singolo");
		JRadioButton buttonLancioSoloDado= new JRadioButton ("Lancio di un solo dado");
		JRadioButton buttonDoppioSei= new JRadioButton ("Doppio sei");
		

		
		buttonDadoSingolo.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                	grigliaBuilder.setDadoSingolo(false);
                		buttonLancioSoloDado.setEnabled(true);
                		buttonDoppioSei.setEnabled(true);
                }
                if (e.getStateChange()==ItemEvent.SELECTED) {
                	grigliaBuilder.setDadoSingolo(true);
    				buttonLancioSoloDado.setEnabled(false);
    				buttonLancioSoloDado.setSelected(false);
    				buttonDoppioSei.setEnabled(false);
    				buttonDoppioSei.setSelected (false);
                }
            }
        });
		
		buttonLancioSoloDado.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                	grigliaBuilder.setLancioSoloDado(false);

                }
                if (e.getStateChange()==ItemEvent.SELECTED) {
                	grigliaBuilder.setLancioSoloDado(true);
    				
                }
            }
		});
		
		buttonDoppioSei.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                	grigliaBuilder.setDoppioSei(false);

                }
                if (e.getStateChange()==ItemEvent.SELECTED) {
                	grigliaBuilder.setDoppioSei(true);
    				
                }
            }
		});
		
		JRadioButton buttonCaselleSoste= new JRadioButton ("Caselle di sosta");
		buttonCaselleSoste.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	grigliaBuilder.setCaselleSosta(true);
                }
                if(e.getStateChange()==ItemEvent.DESELECTED){
                	grigliaBuilder.setCaselleSosta(false);
                }
			}
		});
		
		
		JRadioButton buttonCasellePremio= new JRadioButton ("Caselle di premio");
		buttonCasellePremio.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	grigliaBuilder.setCasellePremio(true);
                }
                if(e.getStateChange()==ItemEvent.DESELECTED){
                	grigliaBuilder.setCasellePremio(false);
                }
			}
		});
		
		
		JRadioButton buttonCasellePescaUnaCarta= new JRadioButton ("Caselle pesca una carta");
		buttonCasellePescaUnaCarta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	grigliaBuilder.setCasellePesca(true);
                }
                if(e.getStateChange()==ItemEvent.DESELECTED){
                	grigliaBuilder.setCasellePesca(false);
                }
			}
		});
		
		JRadioButton buttonUlterioriCarte= new JRadioButton ("Inserisci carte divietoDiSosta");
		
		buttonUlterioriCarte.setEnabled(false);
		buttonCasellePescaUnaCarta.addItemListener(new ItemListener () {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.SELECTED) {
					buttonUlterioriCarte.setEnabled(true);
				}
				if (e.getStateChange()==ItemEvent.DESELECTED) {
					buttonUlterioriCarte.setEnabled(false);
					buttonUlterioriCarte.setSelected (false);
				}
			}
		});
		
		
		
		
		
		
		
		JPanel riquadro2= new JPanel();
		riquadro2.setLayout( new BoxLayout (riquadro2, BoxLayout.Y_AXIS));
		riquadro2.add(new JLabel("Scegli tra le seguenti funzionalità aggiuntive (più opzioni consentite):"));
		riquadro2.add(buttonDadoSingolo);
		riquadro2.add(buttonLancioSoloDado);
		riquadro2.add(buttonDoppioSei);
		riquadro2.add(buttonCaselleSoste);
		riquadro2.add(buttonCasellePremio);
		riquadro2.add(buttonCasellePescaUnaCarta);
		riquadro2.add(buttonUlterioriCarte);
		
		riquadro2.setPreferredSize(new Dimension (300,100));
		
		this.add(riquadro2, BorderLayout.CENTER);
		
		
		JButton buttonCreaGriglia= new JButton ("Configura il gioco");
		buttonCreaGriglia.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				visualizzaGriglia(grigliaBuilder);	
			}
		});
		
		this.add(buttonCreaGriglia);
		
		JButton buttonRipristina= new JButton ("Ripristina precedente configurazione");
		buttonRipristina.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				ripristina();
			}
		});
		
		this.add(buttonRipristina);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000,600);
	}
	
	public void visualizzaGriglia(Griglia.Builder grigliaBuilder) {
		Griglia g= grigliaBuilder.build();
		PannelloGrafico pannello= new PannelloGrafico (g, cardPanel, cardLayout);
		cardPanel.add(pannello);
		cardLayout.next(cardPanel);
	}
	public void ripristina () {
		try	{
			FileInputStream fis= new FileInputStream("griglia.dat");
			ObjectInputStream ois= new ObjectInputStream (fis);
			Griglia g= (Griglia) ois.readObject();
			PannelloGrafico pannello= new PannelloGrafico (g, cardPanel, cardLayout);
			cardPanel.add(pannello);
			cardLayout.next(cardPanel);
			ois.close();
		}catch (IOException ioe) { ioe.printStackTrace();}
		catch (ClassNotFoundException cnfe) {}
	}
	
	

}
