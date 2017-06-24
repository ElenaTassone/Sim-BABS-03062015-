package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.*;

import it.polito.tdp.babs.db.BabsDAO;

public class Model {
	
	private Map <Integer, Station> stazioniMap ;
	private Map<Integer, Trip> tripMap;
	private BabsDAO dao ;
	private List<Trip> tripGiorno;
	Simulator s ;
	
	public Model(){
		this.dao = new BabsDAO () ;
		this.stazioniMap = dao.getAllStations();
		this.tripMap = dao.getAllTrips() ;
		this.tripGiorno = new ArrayList<Trip> ();
		}
		
	public List<Station> getTrip(LocalDate data) {
		
//		LocalDate data1 = LocalDate.of(data.getYear(), data.getMonth(), data.getDayOfMonth()) ;
		
		List<Integer> idP = dao.getPartenze(data);
		List<Integer> idA = dao.getArrivi (data) ;
		
		if(idP.size() ==0 && idA.size() ==0 )
			return null ;
		if(idP.size()!=0){	
			for(Integer i : idP){
				Trip t = tripMap.get(i);
				stazioniMap.get(t.getStartStationID()).addPartenza();
				if(!this.tripGiorno.contains(t))
					this.tripGiorno.add(t);
			}
		}
		
		if(idA.size()!=0){
			for(Integer i : idA){
				Trip t = tripMap.get(i);
				stazioniMap.get(t.getEndStationID()).addArrivo();
				if(!this.tripGiorno.contains(t))
					this.tripGiorno.add(t);
			}
		}
		List<Station> result = new ArrayList<Station> (stazioniMap.values()) ;
		Collections.sort(result, new Comparator<Station> () {
			public int compare(Station s1, Station s2 ){
				double dif = s1.getLat()-s2.getLat() ;
				if(dif<0)
					return +1 ;
				if(dif>0)
					return -1 ;
				return  0;				
			}
		});
		
		return result;
	}

	public Simulator getSimulator(LocalDate data, double k) {
		this.getTrip(data);
		s = new Simulator(data, k) ;
		s = s.run(this.tripGiorno, this.stazioniMap);
		return s;
	}

	
}
