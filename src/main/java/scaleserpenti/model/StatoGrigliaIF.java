package scaleserpenti.model;

import java.io.Serializable;

import scaleserpenti.simulation.Simulatore;

public interface StatoGrigliaIF extends Serializable {
		
	public Casella avanza (scaleserpenti.model.Casella casella, Griglia griglia, int part, Simulatore sim) throws InterruptedException;
	

}
