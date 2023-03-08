package  simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class RoadMap {
	

	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private List<Junction> lista_cruces;
	private List<Road> lista_carreteras;
	private List<Vehicle> lista_vehiculos;
	private Map<String, Junction> mapa_cruces;
	private Map<String, Road> mapa_carreteras;
	private Map<String, Vehicle> mapa_vehiculos;
	
    ////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	RoadMap(){
		this.lista_cruces = new ArrayList<Junction>();
		this.lista_carreteras = new ArrayList<Road>();
		this.lista_vehiculos = new ArrayList<Vehicle>();
		this.mapa_cruces = new HashMap<String, Junction>();
		this.mapa_carreteras = new HashMap<String, Road>();
		this.mapa_vehiculos = new HashMap<String, Vehicle>();
	}
	
	//////////////////////////////////////////// METODOS  //////////////////////////////////////////////////////////////
	
	void addJunction(Junction j) {
		
		if(lista_cruces.contains(j)) 
			throw new IllegalArgumentException("The junction: [ "+j+ " ] already exist in lista_cruces   /RoadMap/");
		
		lista_cruces.add(j);
		mapa_cruces.put(j._id, j);
		
	 }

	void addRoad(Road r) {
		if(lista_carreteras.contains(r) || !mapa_cruces.containsKey(r.getSrc()._id) || !mapa_cruces.containsKey(r.getDest()._id) ) 
			throw new IllegalArgumentException("The road: [ "+r+" ] already exist in lista_carreteras or their junctions dont exist   /RoadMap/");
		
		lista_carreteras.add(r);
		mapa_carreteras.put(r._id, r);
	}
	
	
	void addVehicle(Vehicle v) {
		
		boolean aux = true;
		for(int i = 0; i < (v.getItinerary().size()-1); i++) {
			if(mapa_carreteras.containsKey(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1))._id));
				aux = false;
		}
		
		if(lista_vehiculos.contains(v) && !aux) 
			throw new IllegalArgumentException("The vehicle: [ "+v+ " ] already exist in lista_vehicles or their roads already exist   /RoadMap/");
		
		lista_vehiculos.add(v);
		mapa_vehiculos.put(v._id, v);
	}
	
	public Junction getJunction(String id) {
		return mapa_cruces.get(id);
	}
	
	public Road getRoad(String id) {
		return mapa_carreteras.get(id);	
	}
	
	public Vehicle getVehicle(String id) {
		return mapa_vehiculos.get(id);		
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(this.lista_cruces);
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(this.lista_carreteras);
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(this.lista_vehiculos);
	}
	
	void reset() {
		
		if(!lista_cruces.isEmpty()) lista_cruces.clear();
		if(!lista_carreteras.isEmpty()) lista_carreteras.clear();
		if(!lista_vehiculos.isEmpty()) lista_vehiculos.clear();
		if(!mapa_cruces.isEmpty()) mapa_cruces.clear();
		if(!mapa_carreteras.isEmpty()) mapa_carreteras.clear();
		if(!mapa_vehiculos.isEmpty()) mapa_vehiculos.clear();

	}
	
	public JSONObject report() {
		
		JSONObject mc = new JSONObject();
		JSONArray cruces = new JSONArray();
		JSONArray carreteras = new JSONArray();
		JSONArray vehiculos = new JSONArray();
		
		for(int i = 0; i < lista_cruces.size(); i++)
			cruces.put(lista_cruces.get(i).report());
		for(int i = 0; i < lista_carreteras.size(); i++)
			carreteras.put(lista_carreteras.get(i).report());
		for(int i = 0; i < lista_vehiculos.size(); i++)
			vehiculos.put(lista_vehiculos.get(i).report());
		
		mc.put("junctions", cruces);
		mc.put("roads", carreteras);
		mc.put("vehicles", vehiculos);
		

		return mc;
	}

}