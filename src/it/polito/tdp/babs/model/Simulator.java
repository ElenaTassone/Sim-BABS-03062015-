package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.babs.model.Event.EventType;

public class Simulator {
	
	private LocalDate data ;
	private double k ;
	private int ritorniMancati ;
	private int preseMancate ;
	
	private Queue<Event> queue ;
	

	public Simulator(LocalDate data, double k) {
		this.data = data;
		this.k = k/100 ;
		this.ritorniMancati = 0 ;
		this.preseMancate = 0 ;
		
		this.queue = new PriorityQueue<Event> () ;
	}


	public int getRitorniMancati() {
		return ritorniMancati;
	}


	public void addRitornoMancato() {
		this.ritorniMancati++;
	}


	public int getPreseMancate() {
		return preseMancate;
	}


	public void addPresaMancata() {
		this.preseMancate++;
	}


	public Simulator run(List<Trip> trips, Map<Integer, Station> staz) {
		
		for(Station s : staz.values()){
			s.setIniziali(k) ;
		}
		
		for(Trip t : trips){
			Event e = null;
			//if(t.getStartDate().getDayOfMonth()==this.data.getDayOfMonth())
				e = new Event(t.getStartDate(), EventType.PRESA, t);
//			else
//				e = new Event(t.getStartDate(), EventType.RITORNO, t);
			queue.add(e);
		}
		
		while (!queue.isEmpty()) {
			Event e = queue.poll();
			System.out.println(e);
			Trip trip = e.getTrip() ;
			
			switch (e.getType()) {
			
			case PRESA:
				
				Station partenza = staz.get(trip.getStartStationID()) ;
				// se ho abbastanza bici
				if(partenza.getBici()>0){
					// ne tolgo una -->aggiungo posto libero
					partenza.removeBici();
					// schedulo un ritorno
					Event nuovo = new Event(trip.getEndDate(), EventType.RITORNO, trip) ;
					queue.add(e) ;
				}
				// se non ho abbastanza bici
				else{
					this.preseMancate++;
				}
		
				break;
			case RITORNO:
				Station arrivo = staz.get(trip.getEndStationID());
				// se hoo abbastanza post liberi
				if(arrivo.getLiberi()>0){
					//poso la bici ---> tolgo posto ibero
					arrivo.addBici();
				}
				else{
				// se non ho posti liberi
					this.ritorniMancati++;
				}
				break;
			}
		}
		
		return this;
	}

	
	
	
}
