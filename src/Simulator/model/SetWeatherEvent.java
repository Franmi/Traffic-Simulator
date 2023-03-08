package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	////////////////// ATRIBUTOS //////////////////////////////////////////
	
	private List<Pair<String, Weather>> ws;
	
	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> x) {
		super(time);
		System.out.println("ME HE CREADO    SETWEATHEREVENT");
		if(x == null) 
			throw new IllegalArgumentException("Invalid arguments of /SetWeatherEvent/: "+x);
		
		this.ws =x;
	}

	////////////////// METODO ///////////////////////////////////////
	
	void execute(RoadMap map) {
	
		for(int i =0; i < ws.size(); i++) {
			if(map.getRoad(ws.get(i).getFirst())== null) {
				throw new IllegalArgumentException("The road : [ "+ws.get(i).getFirst()+ " ] doesnt exist in map  /SetWeatherEvent/");
			}
			map.getRoad(ws.get(i).getFirst()).setWeather(ws.get(i).getSecond()); 
		}
		
	}

	public String getValues() {
		String a = "[";
		
		for(int i =0; i < this.ws.size();i++) {
			a += "("+this.ws.get(i).getFirst() + ", "+ws.get(i).getSecond().toString()+")";
			if(i<ws.size()-1)
				a+=",";
		}
		a+="]";
		return a;
	}
	
	@Override
	public String toString() {
		return "Change Weather "+getValues();
	}
	@Override
	public int gettime() {
		// TODO Auto-generated method stub
		return this._time;
	}

}
