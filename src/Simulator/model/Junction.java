package simulator.model;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;

public class Junction extends SimulatedObject {
	
	
	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private List<Road> lista_carreteras;
	private Map<Junction, Road> mapa_carreteras;
	private Map<Road, List<Vehicle>> carretera_cola;
	private List<List<Vehicle>> lista_colas;
	private int semaforo_verde;
	private int cambio_semaforo;
	private LightSwitchingStrategy estrategia_semaforo;
	private DequeuingStrategy estrategia_cola;
	private int x;
	private int y;
	
	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
  Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor){
	  
	  super(id);
	  if(lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0) 
		  throw new IllegalArgumentException("Invalid arguments of the /Junction/: "+this);
	  
	  this.estrategia_cola = dqStrategy;
	  this.estrategia_semaforo = lsStrategy;
	  this.x = xCoor;
	  this.y = yCoor;
	  this.lista_carreteras = new ArrayList<Road>();
	  this.lista_colas = new ArrayList<List<Vehicle>>();
	  this.mapa_carreteras = new HashMap<Junction, Road>();
	  this.carretera_cola = new HashMap<Road, List<Vehicle>>();
	  this.semaforo_verde = -1;
	  this.cambio_semaforo = 0;

  }
  
	 
  	////////////////// METODOS ///////////////////////////////////////
  
  public int getX() {
		return x;
  }
  public int getY() {
		return y;
  }

  
  public void addIncomingRoad(Road r) {
	 
	 if(r.getDest() != this) 
		 throw new IllegalArgumentException("The road: [ "+r+" ] is not an in coming road    /Junction/");
	 
	 this.lista_carreteras.add(r);
	 this.lista_colas.add(new LinkedList<Vehicle>());
	 carretera_cola.put(r, lista_colas.get(lista_colas.size()-1));

  }
 

  public void addOutGoingRoad(Road r) {
	
	if(this.mapa_carreteras.containsKey(r.getDest()) || r.getSrc() != this) 
		 throw new IllegalArgumentException("The road: [ "+r+" ] is not an out going road or there is other road to this junction   /Junction/");
	
	this.mapa_carreteras.put(r.getDest(), r);

  }

  void enter(Vehicle v) {

	lista_colas.get(lista_carreteras.indexOf(v.getRoad())).add(v);

  }
  
  public Road roadTo(Junction cruce) {

	 return mapa_carreteras.get(cruce);
	 
  }
  
  public void advance(int time) {

  /*1*/ List<Vehicle> aux = new ArrayList<Vehicle>();
			  
		if(this.semaforo_verde  != -1)
   	    if(!this.lista_colas.get(this.semaforo_verde).isEmpty()){
		
	    aux = (this.estrategia_cola.dequeue(this.lista_colas.get(this.semaforo_verde)));
	
	    for(int j = 0; j < aux.size(); j++) {
	    	aux.get(j).moveToNextRoad();
	    	this.lista_colas.get(this.semaforo_verde).remove(j);

	    }			 
   	    }
			  
		

  /*2*/ int var = this.estrategia_semaforo.chooseNextGreen(lista_carreteras, lista_colas, semaforo_verde, cambio_semaforo, time);
		 if(var != semaforo_verde) {
		  this.semaforo_verde = var;
		  this.cambio_semaforo = time;
		 }
  }
  
  public JSONObject report() {

		 JSONObject cruce = new JSONObject() ;
		
		 cruce.put("id", this._id);
		 if(semaforo_verde == -1) 
			 cruce.put("green", "none");
		 else
			 cruce.put("green", this.lista_carreteras.get(this.semaforo_verde));
		 
		 JSONArray array = new JSONArray();
		 
		 
		 for(int i=0; i< this.lista_carreteras.size();i++) {
			 JSONObject objeto = new JSONObject();
			 objeto.put("road", this.lista_carreteras.get(i));
			 JSONArray array1 = new JSONArray();
			 
			 for(int j = 0; j < lista_colas.get(i).size(); j++)
				 array1.put(this.lista_colas.get(i).get(j));
			
			objeto.put("vehicles", array1);
			array.put(objeto);
		 }
		
		 cruce.put("queues", array);
		 
		
		 
		 return cruce;
		 
		 


	 }

  
  //Funcion inventada
  
public String getGreen() {
	if(semaforo_verde == -1) 
		return "NONE";
	 else
		return this.lista_carreteras.get(this.semaforo_verde).getId();
}

public String getQueues() {
	String b= " ";
	for(int i = 0; i < this.lista_colas.size();i++) {
		String a = this.lista_carreteras.get(i) + ":" + this.lista_colas.get(i).toString() +"";
		b+=a;
	}
	return b;
}



  ///////////////// creados automaticamente

public int getGreenLightIndex() {
	return semaforo_verde;
}


public List<Road> getInRoads() {
	// TODO Auto-generated method stub
	return lista_carreteras;
}
	
}
