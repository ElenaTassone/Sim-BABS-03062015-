package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.event.EventType;

public class Event implements Comparable<Event>{
	
	private LocalDateTime time ;
	private EventType type ;
	private Trip trip ;

	public enum EventType { 
		
		PRESA,
		RITORNO;
	}
		
	
	
	public Event(LocalDateTime time, EventType type, Trip trip) {
		super();
		this.time = time;
		this.type = type;
		this.trip = trip ;
	}




	public LocalDateTime getTime() {
		return time;
	}




	public void setTime(LocalDateTime time) {
		this.time = time;
	}




	public EventType getType() {
		return type;
	}




	public void setType(EventType type) {
		this.type = type;
	}




	@Override
	public int compareTo(Event altro) {
		
		return this.time.compareTo(altro.getTime()) ;
	}




	public Trip getTrip() {
		return trip;
	}




	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	
}
