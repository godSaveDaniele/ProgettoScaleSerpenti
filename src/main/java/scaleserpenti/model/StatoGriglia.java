package scaleserpenti.model;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import scaleserpenti.simulation.Simulatore;

public enum StatoGriglia implements StatoGrigliaIF {
	
	DUE_DADI{
		public Casella avanza(Casella vecchiaCasella, Griglia griglia, int part, Simulatore sim) throws InterruptedException {
			int dado1= new Random().nextInt(6)+1;
			int dado2= new Random().nextInt(6)+1;
			int avanzamento= dado1+dado2;
			sim.setUltimoAvanzamento(avanzamento);
			sim.addDaStampare("Lancio del primo dado: " +dado1);
			TimeUnit.SECONDS.sleep(1);
			sim.addDaStampare("Lancio del secondo dado: " +dado2 );
			TimeUnit.SECONDS.sleep(1);
			sim.addDaStampare("Avanzamento totale: "+avanzamento);
			TimeUnit.SECONDS.sleep(2);
			int max=griglia.getNumColonne()* griglia.getNumRighe() -1;
			int newPos= vecchiaCasella.getNumCasella()+avanzamento;
			if (newPos>max) {
				int gap=newPos-max;
				newPos= max-gap;
			}
			Casella nuovaCasella=griglia.getCasella(newPos);
			vecchiaCasella.rimuoviPedina(part);
			nuovaCasella.aggiungiPedina(part);
			return nuovaCasella;
		}
		
		
	},
	DADO_SINGOLO{
		public Casella avanza(Casella vecchiaCasella, Griglia griglia, int part, Simulatore sim) throws InterruptedException {
			int avanzamento= new Random().nextInt(6)+1;
			sim.setUltimoAvanzamento(avanzamento);
			sim.addDaStampare("OPZIONE Lancio dado singolo ATTIVATA");
			TimeUnit.SECONDS.sleep(1);
			sim.addDaStampare("Lancio di un dado: " + avanzamento);
			TimeUnit.SECONDS.sleep(2);
			int max=griglia.getNumColonne()* griglia.getNumRighe() -1;
			int newPos= vecchiaCasella.getNumCasella()+avanzamento;
			if (newPos>max) {
				int gap=newPos-max;
				newPos= max-gap;
			}
			Casella nuovaCasella= griglia.getCasella(newPos);
			vecchiaCasella.rimuoviPedina(part);
			nuovaCasella.aggiungiPedina(part);
			return nuovaCasella;
		}
		
	},
	LANCIO_SOLO_DADO{
		public Casella avanza(Casella vecchiaCasella, Griglia griglia, int part, Simulatore sim) throws InterruptedException{
			int max=griglia.getNumColonne()* griglia.getNumRighe() -1;
			int dado1= new Random().nextInt(6)+1;
			int dado2= new Random().nextInt(6)+1;
			int avanzamento;
			if (vecchiaCasella.getNumCasella() <=max-1 && vecchiaCasella.getNumCasella()>=max-6){
				 avanzamento = dado1;
				 sim.addDaStampare("OPZIONE Lancio di un solo dado ATTIVATA /n Pedina in una delle ultime 6 caselle");
				 TimeUnit.SECONDS.sleep(1);
				 sim.addDaStampare("Lancio del dado: "+ avanzamento);
				 TimeUnit.SECONDS.sleep(2);
			}else {
				avanzamento=dado1+dado2;
				sim.addDaStampare("Lancio del primo dado: " +dado1);
				TimeUnit.SECONDS.sleep(1);
				sim.addDaStampare("Lancio del secondo dado: " +dado2 );
				TimeUnit.SECONDS.sleep(1);
				sim.addDaStampare("Avanzamento totale: "+avanzamento);
				TimeUnit.SECONDS.sleep(2);
			}
			sim.setUltimoAvanzamento(avanzamento);
			int newPos= vecchiaCasella.getNumCasella()+avanzamento;
			if (newPos>max) {
				int gap=newPos-max;
				newPos= max-gap;
			}
			Casella nuovaCasella=griglia.getCasella(newPos);
			vecchiaCasella.rimuoviPedina(part);
			nuovaCasella.aggiungiPedina(part);
			return nuovaCasella;
		}
	},
	DUE_DADI_DOPPIO_SEI{
		public Casella avanza (Casella vecchiaCasella, Griglia griglia,int part, Simulatore sim) throws InterruptedException{
			//LANCIO 2 DADI 
			int dado1= new Random().nextInt(6)+1;
			int dado2= new Random().nextInt(6)+1;
			int avanzamento= dado1+dado2;
			sim.setUltimoAvanzamento(avanzamento);
			sim.addDaStampare("Lancio del primo dado: " +dado1);
			TimeUnit.SECONDS.sleep(1);
			sim.addDaStampare("Lancio sel secondo dado: " +dado2 );
			TimeUnit.SECONDS.sleep(1);
			sim.addDaStampare("Avanzamento totale: "+avanzamento);
			TimeUnit.SECONDS.sleep(2);
			
			int max=griglia.getNumColonne()* griglia.getNumRighe() -1;
			int newPos= vecchiaCasella.getNumCasella()+avanzamento;
			if (newPos>max) {
				int gap=newPos-max;
				newPos= max-gap;
			}
			Casella casella1=griglia.getCasella(newPos);
			vecchiaCasella.rimuoviPedina(part);
			casella1.aggiungiPedina(part);
			
			
			TimeUnit.SECONDS.sleep(3);


			Casella casella2 = casella1.esegui(part, sim, avanzamento);
			casella1.rimuoviPedina(part);
			casella2.aggiungiPedina (part);
			TimeUnit.SECONDS.sleep(3);
			
			if (avanzamento==12) {
				sim.addDaStampare( "OPZIONE Doppio Sei ATTIVATA");
				sim.addDaStampare ("La pedina "+sim.getColor(part)+" può lanciare nuovamente i dadi");
				return DUE_DADI_DOPPIO_SEI.avanza(casella2, griglia, part, sim);
			}else {
				return casella2;
			}
			
			
		}
	},
	LANCIO_SOLO_DADO_DOPPIO_SEI{
		public Casella avanza (Casella vecchiaCasella, Griglia griglia,int part, Simulatore sim) throws InterruptedException {
			
			int max=griglia.getNumColonne()* griglia.getNumRighe() -1;
			int dado1= new Random().nextInt(6)+1;
			int dado2= new Random().nextInt(6)+1;
			int avanzamento;
			if (vecchiaCasella.getNumCasella() <=max-1 && vecchiaCasella.getNumCasella()>=max-6){
				avanzamento = dado1;
				sim.addDaStampare("OPZIONE Lancio di un solo dado ATTIVATA");
				sim.addDaStampare ("Pedina in una delle ultime 6 caselle");
				TimeUnit.SECONDS.sleep(1);
				sim.addDaStampare("Lancio del dado: "+ avanzamento);
				TimeUnit.SECONDS.sleep(2);
			}else {
				avanzamento=dado1+dado2;
				sim.addDaStampare("Lancio del primo dado: " +dado1);
				TimeUnit.SECONDS.sleep(1);
				sim.addDaStampare("Lancio sel secondo dado: " +dado2 );
				TimeUnit.SECONDS.sleep(1);
				sim.addDaStampare("Avanzamento totale: "+avanzamento);
				TimeUnit.SECONDS.sleep(2);
			}
			sim.setUltimoAvanzamento(avanzamento);
			int newPos= vecchiaCasella.getNumCasella()+avanzamento;
			if (newPos>max) {
				int gap=newPos-max;
				newPos= max-gap;
			}
			Casella casella1=griglia.getCasella(newPos);
			vecchiaCasella.rimuoviPedina(part);
			casella1.aggiungiPedina(part);
			
			
			
			
			if (avanzamento==12) {
				Casella casella2=casella1.esegui( part, sim, avanzamento);
				casella1.rimuoviPedina(part);
				casella2.aggiungiPedina(part);
				sim.addDaStampare( "OPZIONE Doppio Sei ATTIVATA");
				sim.addDaStampare ("La pedina "+sim.getColor(part)+" può lanciare nuovamente i dadi");
				return LANCIO_SOLO_DADO_DOPPIO_SEI.avanza(casella2, griglia, part, sim);
			}else {
				return casella1;
			}
		}
		
	};
	
}
