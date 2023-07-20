package scaleserpenti.model;

import java.util.concurrent.TimeUnit;

import scaleserpenti.simulation.Simulatore;

public enum TipoCasella implements TipoCasellaIF {
	VUOTA {
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) {
			return casella;
		}
	},
	FINE_SCALA{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) {
			return casella;
		}
	},
	CODA_SERPENTE{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) {
			return casella;
		}
	},
	LINGUA_SERPENTE{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException{
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina finita sulla lingua di un serpente");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("DISCESA LUNGO IL SERPENTE");
			TimeUnit.SECONDS.sleep(2);
			Casella nuovaCasella= g.getCasella(casella.getCodaSerpente());
			casella.rimuoviPedina(part);
			nuovaCasella.aggiungiPedina(part);
			return nuovaCasella;
		}
	},
	INIZIO_SCALA{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException{
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina "+ sim.getColor(part)+" ai piedi di una scala");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("RISALITA DELLA SCALA");
			TimeUnit.SECONDS.sleep(2);
			Casella nuovaCasella= g.getCasella(casella.getFineScala());
			casella.rimuoviPedina(part);
			nuovaCasella.aggiungiPedina(part);
			return nuovaCasella;
		}
	},
	DADI{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException {
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina "+ sim.getColor(part)+" finita su una casella DADI");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare ("Pedina pronta per rilanciare i dadi");
			TimeUnit.SECONDS.sleep(2);
			Casella nuovaCasella= g.avanza(casella, part, sim);
			Casella nuovaNuovaCasella= nuovaCasella.esegui(part, sim, vecchioAvanzamento);
			return nuovaNuovaCasella;
		}
	},
	MOLLA{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException{
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina "+ sim.getColor(part)+" finita su una casella MOLLA");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare ("Spostamento del valore precedente");
			TimeUnit.SECONDS.sleep(2);
			int max=g.getNumColonne()* g.getNumRighe() -1;
			int newPos= casella.getNumCasella()+vecchioAvanzamento;
			if (newPos>max) {
				int gap=newPos-max;
				newPos= max-gap;
			}
			Casella nuovaCasella=g.getCasella(newPos);
			casella.rimuoviPedina(part);
			nuovaCasella.aggiungiPedina(part);
			Casella nuovaNuovaCasella= nuovaCasella.esegui(part, sim, vecchioAvanzamento);
			return nuovaNuovaCasella;
		}
		
	},
	PANCHINA{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException{
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina "+ sim.getColor(part)+" finita su una casella PANCHINA");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare ("Pedina ferma per un turno");
			TimeUnit.SECONDS.sleep(2);
			sim.staiFermo(part, 1);
			return casella;
		}
	},
	LOCANDA{
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException{
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina "+ sim.getColor(part)+" finita su una casella LOCANDA");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare ("Pedina ferma per tre turni");
			TimeUnit.SECONDS.sleep(2);
			sim.staiFermo(part, 3);
			return casella;
		}
	},
	PESCA{
		private  String [] carteDaGioco= {"PANCHINA", "LOCANDA", "DADI", "MOLLA"};
		private int i=0;
		public Casella esegui (Casella casella, Griglia g, int part, Simulatore sim, int vecchioAvanzamento) throws InterruptedException{
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare("Pedina "+ sim.getColor(part)+" finita su una casella PESCA UNA CARTA");
			TimeUnit.SECONDS.sleep(2);
			sim.addDaStampare ("PESCA DELLA CARTA IN CORSO...");
			TimeUnit.SECONDS.sleep(2);
			String carta= carteDaGioco [i];
			i=(i+1)%4;
			sim.addDaStampare ("Ottenuta una carta "+ carta);
			switch (carta){
				case "PANCHINA": return PANCHINA.esegui (casella, g, part, sim, vecchioAvanzamento);
				case "LOCANDA": return LOCANDA.esegui (casella, g, part, sim, vecchioAvanzamento);
				case "DADI": return DADI.esegui (casella, g, part, sim, vecchioAvanzamento);
				default: return MOLLA.esegui (casella, g, part, sim, vecchioAvanzamento);
			}
		}
	
	}
}
