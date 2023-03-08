package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	
	////////////////// ATRIBUTOS ///////////////////////////////////////
	private List<TrafficSimObserver> observers;
	private RoadMap mapa_carreteras;
	private List<Event> lista_eventos;
	private int tiempo_simulacion;
	
	
	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	public TrafficSimulator() {
		this.tiempo_simulacion = 0;
		lista_eventos = new SortedArrayList<Event>();
		mapa_carreteras = new RoadMap();
		observers = new ArrayList<TrafficSimObserver>();
	}
	
	////////////////// METODOS  ///////////////////////////////////////
	
	public void addEvent(Event e) {
		try {
			lista_eventos.add(e);

			for(TrafficSimObserver o: this.observers) {
				o.onEventAdded(mapa_carreteras, lista_eventos, e, tiempo_simulacion);
	  	}
		}
		catch(Exception ex) {
			for(TrafficSimObserver o: this.observers) {
				o.onError(ex.getMessage());
			}
		}
	}
	
	 
	public void advance() {

	  	try {
	  		
  /*1*/ this.tiempo_simulacion++;

        for(TrafficSimObserver o: this.observers) {
  			o.onAdvanceStart(mapa_carreteras, lista_eventos, tiempo_simulacion);
  		}
  			
  /*2*/ if(!lista_eventos.isEmpty()) {
	 		while(!lista_eventos.isEmpty() && lista_eventos.get(0).getTime() == this.tiempo_simulacion ) {
	 			lista_eventos.get(0).execute(mapa_carreteras);
	 			lista_eventos.remove(0);	
	 		}
		}
  		
  /*3*/ for(int i = 0; i < mapa_carreteras.getJunctions().size(); i++) {
			mapa_carreteras.getJunctions().get(i).advance(this.tiempo_simulacion);
		 }

  /*4*/ for(int i = 0; i < mapa_carreteras.getRoads().size(); i++) {
			mapa_carreteras.getRoads().get(i).advance(this.tiempo_simulacion);
		}
  
		 for(TrafficSimObserver o: this.observers) {
			o.onAdvanceEnd(mapa_carreteras, lista_eventos, tiempo_simulacion);
		}
		 
  	}
  	catch(Exception e){
  	  for(TrafficSimObserver o: this.observers) {
			o.onError(e.getMessage());
		}
  	}
  	
	}

	
	public void reset() {
		mapa_carreteras.reset();
		if(!lista_eventos.isEmpty()) lista_eventos.clear();
		this.tiempo_simulacion = 0;
		
		  for(TrafficSimObserver o: this.observers) {
	  			o.onReset(mapa_carreteras, lista_eventos, tiempo_simulacion);
	  		}
	}

	public JSONObject report() {
		
		JSONObject simulator = new JSONObject();
		simulator.put("time", this.tiempo_simulacion);
		simulator.put("state", this.mapa_carreteras.report());
		return simulator;
	}
	
	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
		
		for(TrafficSimObserver x: this.observers) {
			x.onRegister(mapa_carreteras, lista_eventos, tiempo_simulacion);
	  	}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}
	
	
}