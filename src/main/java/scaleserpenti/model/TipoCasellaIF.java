package scaleserpenti.model;

import java.io.Serializable;

import scaleserpenti.simulation.Simulatore;

public interface TipoCasellaIF extends Serializable{
	
	public Casella esegui (Casella casella, Griglia griglia, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException;
}
