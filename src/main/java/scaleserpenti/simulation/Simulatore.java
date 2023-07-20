package scaleserpenti.simulation;

import scaleserpenti.model.Casella;
import scaleserpenti.model.Griglia;
import scaleserpenti.view.Observer;

import java.util.concurrent.TimeUnit;

public class Simulatore extends Thread {
	private Griglia griglia;
	private Casella[] caselleCorrenti;
	private int[] turniFermi;
	private String daStampare="";
	private Observer logger;
	private String [] colore= new String [] {"VERDE", "GIALLA", "ROSSA", "BLU"}; 
	private int ultimoAvanzamento=0;
	
	public Simulatore (Griglia griglia) {
		this.griglia=griglia;
		caselleCorrenti= new Casella [griglia.getNumPartecipanti()];
		for (int i=0; i<caselleCorrenti.length; i++) {
			caselleCorrenti[i]=griglia.getCasella(0);
			caselleCorrenti[i].aggiungiPedina(i);
		}
		turniFermi = new int [griglia.getNumPartecipanti()];
	}
	
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(2);
			setDaStampare("INIZIO");
			while (! isInterrupted()) {
				TimeUnit.SECONDS.sleep(3);
				for (int i=0; i< caselleCorrenti.length; i++) {
					if (!isInterrupted()) {
						setDaStampare ("TURNO PEDINA "+ colore[i]);
						TimeUnit.SECONDS.sleep(3);
						if (turniFermi[i]>0) {
							turniFermi[i]--;
							setDaStampare ("Pedina "+colore[i]+ "resta ferma");
						}
						else {
							Casella nuovaCasella = griglia.avanza (caselleCorrenti[i],i, this);
							addDaStampare ("Pedina "+colore[i]+ " spostata sulla casella "+ (nuovaCasella.getNumCasella()+1));
							Casella nuovaCasella2= nuovaCasella.esegui(i, this,ultimoAvanzamento);
							if (nuovaCasella!=nuovaCasella2)
								addDaStampare ("Pedina "+colore[i]+ " spostata sulla casella "+ (nuovaCasella2.getNumCasella()+1));
							caselleCorrenti[i]=nuovaCasella2;
							int max= griglia.getNumColonne()*griglia.getNumRighe()-1;
							if (caselleCorrenti[i].getNumCasella()==max) {
								setDaStampare ("LA PEDINA " +colore[i]+ " HA VINTO!!!!!!");
								for (int j=0; j<caselleCorrenti.length; j++) {
									caselleCorrenti[j].rimuoviPedina(j);
								}
								interrupt();
							}
						}
					}
					TimeUnit.SECONDS.sleep(3);
				}
			}
		}catch (InterruptedException ie) {}
	}
	
	public void staiFermo(int part, int numTurni) {
		turniFermi[part]+=numTurni;
	}
	public String getDaStampare() {
		return daStampare;
	}
	
	public void setDaStampare(String s) {
		daStampare=s;
		notifyLogger();
	}
	public void addDaStampare (String s) {
		daStampare+= "\n";
		daStampare+=  s;
		notifyLogger();
	}
	public String getColor(int part) {
		return colore[part];
	}
	public void addObserver(Observer logger) {
		this.logger=logger;
	}
	
	public void  notifyLogger() {
		logger.update();
	}
	public void setUltimoAvanzamento(int x) {
		this.ultimoAvanzamento=x;
	}

}
