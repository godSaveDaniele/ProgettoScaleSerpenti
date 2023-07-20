package scaleserpenti.model;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import scaleserpenti.simulation.Simulatore;

public class Griglia implements Serializable{ 
	
	
	private static final long serialVersionUID = 1L;
	private final int numRighe;
	private final int numColonne;
	private final Casella [] griglia;

	private final int numPartecipanti;

	private final StatoGriglia stato;
	private Simulatore simulatore;
	
	
	
	
	public Griglia (Builder builder) {
		numRighe=builder.numRighe;
		numColonne=builder.numColonne;
		numPartecipanti=builder.numPartecipanti;
		if ( builder.doppioSei) {
			if (builder.lancioSoloDado) stato=StatoGriglia.LANCIO_SOLO_DADO_DOPPIO_SEI;
			else stato=StatoGriglia.DUE_DADI_DOPPIO_SEI;
		}else {
			if (builder.dadoSingolo) stato=StatoGriglia.DADO_SINGOLO;
			else if (builder.lancioSoloDado) stato=StatoGriglia.LANCIO_SOLO_DADO;
			else stato=StatoGriglia.DUE_DADI;
		}
		this.griglia= new Casella [numRighe*numColonne];
		for (int i=0; i<numRighe*numColonne; i++) {
			griglia[i]=new Casella(i, this);
		}
		setScale();
		setSerpenti();
		if (builder.casellePesca) setCasellePescaUnaCarta();
		if (builder.casellePremio) setCasellePremio();
		if (builder.caselleSosta) setCaselleSosta();
		
		
	}
	
	public Casella avanza (Casella c, int part, Simulatore sim) throws InterruptedException{
		return stato.avanza (c, this, part, sim );
	}
	
	
	
	public static class Builder  {
		private int numRighe;
		private int numColonne;
		private boolean dadoSingolo=false;
		private boolean lancioSoloDado=false;
		private boolean doppioSei=false;
		private boolean caselleSosta=false;
		private boolean casellePremio=false;
		private boolean casellePesca=false;
		private int numPartecipanti;
		
	
		
		public void setNumPartecipanti (int n) {
			this.numPartecipanti=n;
		}
		
		public void setDimensione (int numRighe, int numColonne) {
			this.numRighe=numRighe;
			this.numColonne=numColonne;
		}
		
	
			
		public void setDadoSingolo(boolean value) {
			dadoSingolo=value;
		}
		
		public void setLancioSoloDado(boolean value) {
			lancioSoloDado=value;
		}
		
		public void setDoppioSei(boolean value) {
			doppioSei=value;
		}
		
		public void setCasellePremio(boolean value) {
			casellePremio=value;
		}
		public void setCaselleSosta(boolean value) {
			caselleSosta=value;
		}
		
		public void setCasellePesca(boolean value) {
			casellePesca=value;
		}
		
		public Griglia build () {
			if (dadoSingolo && lancioSoloDado || dadoSingolo && doppioSei || numPartecipanti<=0 ||  numRighe<=0 || numColonne<=0) {
				throw new IllegalStateException();
			}
			return new Griglia(this);
		}
	
	}
	
	public int getNumRighe() {
		return numRighe;
	}
	
	public int getNumColonne() {
		return numColonne;
	}


	public Casella getCasella (int i) {
		return griglia[i];
	}
	
	public void setCaselleSosta () { //6%
		int numCaselleSosta= (numRighe*numColonne)*6/100;
		while (numCaselleSosta>0) {
			int posizione = new Random().nextInt(numRighe*numColonne-2)+1;
			if (griglia[posizione].getTipo()==TipoCasella.VUOTA) {
				TipoCasella tipo;
				if (numCaselleSosta%2==0) {
					tipo=TipoCasella.LOCANDA;
				}else tipo=TipoCasella.PANCHINA;
				griglia[posizione]= new Casella(posizione, tipo, this);
				numCaselleSosta--;
			}
		}
	}
	
	public void setCasellePremio() { //6%
		int numCasellePremio= (numRighe*numColonne)*6/100;
		while (numCasellePremio>0) {
			int posizione = new Random().nextInt(numRighe*numColonne-2)+1;
			if (griglia[posizione].getTipo()==TipoCasella.VUOTA) {
				TipoCasella tipo;
				if (numCasellePremio%2==0) {
					tipo=TipoCasella.DADI;
				}else tipo=TipoCasella.MOLLA;
				griglia[posizione]= new Casella(posizione, tipo, this);
				numCasellePremio--;
			}
		}
	}
	
	public void setCasellePescaUnaCarta() { //6%
		int numCasellePesca= (numRighe*numColonne)* 6/100;
		while (numCasellePesca>0) {
			int posizione = new Random().nextInt(numRighe*numColonne-2)+1;
			if (griglia[posizione].getTipo()==TipoCasella.VUOTA) {
				griglia[posizione]= new Casella(posizione, TipoCasella.PESCA, this);
				numCasellePesca--;
			}
		}
	}
	
	public void setScale() {
		for (int i=0; i<numRighe-2; i++) {
			int j=new Random ().nextInt(numColonne-1);
				int inizioScala = trovaIndiceAssoluto (i,j);
				int fineScala= trovaIndiceAssoluto (i+ Math.max(new Random().nextInt(numRighe-i),2), j+1);
				try {
					aggiungiScala (inizioScala,fineScala);
				}catch (IllegalStateException ioe) {}
		}
	}
	
	private void aggiungiScala (int i, int j) {
		if (i!=0 && griglia[i].getTipo()==TipoCasella.VUOTA && griglia[j].getTipo()==TipoCasella.VUOTA) {   //Se la riga di i è inferiore alla riga di j
			griglia[i]=new Casella (i, TipoCasella.INIZIO_SCALA, this);
			griglia[i].setFineScala(j);
			griglia[j]=new Casella (j, TipoCasella.FINE_SCALA, this);
		}else throw new IllegalStateException();
	}
	
	public void setSerpenti() {
		for (int i=0; i<numRighe-1; i++) {
			int j= Math.max( new Random().nextInt(numColonne),2);
			int codaSerpente= trovaIndiceAssoluto(i,j);
			int linguaSerpente =trovaIndiceAssoluto (i+1, Math.max(0, j - new Random().nextInt(3)));

			try {
				aggiungiSerpente (codaSerpente, linguaSerpente);
			}catch (IllegalStateException ioe) {}
		}
	}
	
	
	
	
	private void aggiungiSerpente (int i, int j) {
		if ( griglia[i].getTipo()==TipoCasella.VUOTA && griglia[j].getTipo()==TipoCasella.VUOTA && j!=numRighe*numColonne-1) {   //Se la riga di i è inferiore alla riga di j
			griglia[i]=new Casella (i, TipoCasella.CODA_SERPENTE, this);
			griglia[j]=new Casella (j, TipoCasella.LINGUA_SERPENTE, this);
			griglia[j].setCodaSerpente(i);
		}else throw new IllegalStateException();
	}
	private int trovaIndiceAssoluto(int riga, int colonna) {
		if (riga%2==0) {
			return riga*numColonne+colonna;
		}else {
			return riga*numColonne+ numColonne-colonna-1;
		}
	}
	public void avvia() {
		Simulatore sim= new  Simulatore(this);
		this.simulatore=sim;
		sim.start();
		
	}
	public Simulatore getSimulatore(){
		return simulatore;
	}

	public int getNumPartecipanti() {
		return numPartecipanti;
	}
	
	public boolean salva () {
		try {
			FileOutputStream fos= new FileOutputStream ("griglia.dat");
			ObjectOutputStream oos= new ObjectOutputStream (fos);
			oos.writeObject(this);
			return true;
		}catch (IOException ioe) {return false;}
	}
}
