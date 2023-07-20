package scaleserpenti.model;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import scaleserpenti.simulation.Simulatore;
import scaleserpenti.view.Observer;




public class Casella implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private static HashMap<TipoCasella, String> mappa= new HashMap<>();
	private int numCasella;
	private TipoCasella tipo;
	
	private int fineScala;  //Questo campo ha un valore significativo solo se la casella è di tipo INIZIO_SCALA
	private int codaSerpente;   ////Questo campo ha un valore significativo solo se la casella è di tipo LINGUA_SERPENTE
	
	private Griglia griglia;
	
	private LinkedList<Integer> pedine=new LinkedList<>();
	
	public LinkedList <Observer> osservatori=new LinkedList<>();
	
	
	static {
		mappa.put(TipoCasella.DADI, "dadi.jpg");
		mappa.put(TipoCasella.LOCANDA, "hotel.jpg");
		mappa.put(TipoCasella.MOLLA, "molla.jpg");
		mappa.put(TipoCasella.PESCA, "pesca_carta.jpeg");
		mappa.put(TipoCasella.PANCHINA, "panchina.jpeg");
		mappa.put(TipoCasella.VUOTA, "bianco.jpeg");
		mappa.put(TipoCasella.INIZIO_SCALA, "bianco.jpeg");
		mappa.put(TipoCasella.FINE_SCALA, "bianco.jpeg");
		mappa.put(TipoCasella.CODA_SERPENTE, "bianco.jpeg");
		mappa.put(TipoCasella.LINGUA_SERPENTE, "bianco.jpeg");
		
	}
	
	public Casella(int numCasella, Griglia griglia) {
		tipo= TipoCasella.VUOTA;
		this.numCasella=numCasella;
		this.griglia=griglia;
	}
	
	public Casella (int numCasella, TipoCasella tipo, Griglia griglia) {
		this.griglia=griglia;
		this.tipo=tipo;
		this.numCasella=numCasella;
	}
	//può essere invocato solo se la casella è di tipo INIZIO_SCALA
	public void setFineScala (int numFineScala) {
		fineScala= numFineScala;
	}
	
	//può essere invocato solo se la casella è di tipo LINGUA_SERPENTE
	public void setCodaSerpente (int numCodaSerpente) {
		codaSerpente=numCodaSerpente;
	}
	
	//può essere invocato solo se la casella è di tipo INIZIO_SCALA
	public int getFineScala () {
		return fineScala;
	}
	
	//può essere invocato solo se la casella è di tipo LINGUA_SERPENTE
	public int getCodaSerpente () {
		return codaSerpente;
	}
	
	public Casella esegui(int part, Simulatore sim, int avanzamento)throws InterruptedException {
		return tipo.esegui(this ,griglia , part , sim, avanzamento);
	}
	
	public void setTipo (TipoCasella tipo) {
		this.tipo= tipo;
	}
	
	public TipoCasella getTipo() {
		return tipo;
	}
	
	public void aggiungiPedina (int part) {
		pedine.add(part);
		notifyObserver ();
	}
	
	public void rimuoviPedina ( int part) {
		Iterator<Integer> it =pedine.iterator();
		while (it.hasNext()) {
			if (it.next()==part)it.remove();
		}
		notifyObserver();
	}
	
	public void notifyObserver () {
		for (Observer o: osservatori) {
			o.update();
		}
	}
	
	public void aggiugiObserver (Observer o) {
		if ( ! osservatori.contains(o)) {
			osservatori.add(o);
		}
	}
	
	public void rimuoviObserver (Observer o) {
		osservatori.remove(o);
	}
	
	public int getNumCasella() {
		return numCasella;
	}
	
	public LinkedList<Integer> getListaPartecipanti() {
		return pedine;
	}
	
	
	public Image getImage() throws IOException {
		File file = new File(mappa.get(tipo));
		BufferedImage image = ImageIO.read(file);
		return image;
	}
 	
	
	
	
	
}
