package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private Junction origen;
	private Junction destino;
	private int longitud;
	protected int velocidad_maxima;
	protected int limite_velocidad;
	protected int alarma_contaminacion;
	protected Weather condicion_ambiental;
	protected int contaminacion_total;
	private List<Vehicle> vehiculos;
	
	
	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		 if(maxSpeed < 0 || contLimit < 0 || length < 0 || srcJunc == null || destJunc == null || weather == null) 
			 throw new IllegalArgumentException("Invalid arguments of the /Road/: "+this);
		
		 this.origen = srcJunc; 
		 this.destino = destJunc;
		 this.velocidad_maxima = maxSpeed;
		 this.alarma_contaminacion = contLimit;
		 this.longitud = length;
		 this.condicion_ambiental = weather;
		 this.vehiculos = new ArrayList<Vehicle>();
		 this.limite_velocidad = 0;
		 this.contaminacion_total = 0;
		 this.destino.addIncomingRoad(this);
		 this.origen.addOutGoingRoad(this);

	}
	
	
	///////////////////////////////////////////// METODOS //////////////////////////////////////////////////////////////
	
	public int getLenght() {
		return this.longitud;
	}

	 public Junction getDest() {
		return this.destino;
	}
	
	 public Junction getSrc() {
		 return this.origen;
	 }
	
	 public Weather getWeather() {
		 return this.condicion_ambiental;
	 }
	 public int getVelocidad_max() {
		 return this.velocidad_maxima;
	 }
	 public int getLimite_vel() {
		 return this.limite_velocidad;
	 }

	 void enter(Vehicle v) {
		 if(v.getLocation() != 0 && v.getSpeed() != 0) 
			 throw new IllegalArgumentException("Invalid location or speed of the vehicle : [ "+v+ " ] on the road: [ "+this+" ]   /Road/");
		 
		 this.vehiculos.add(v);
	 }
	 
     void exit(Vehicle v) {
		 this.vehiculos.remove(v);
	 }
	 	 
	 void setWeather(Weather w) {
		if(w == null) 
			 throw new IllegalArgumentException("Invalid weather on the road : [ "+this+ " ]   /Road/");
		
		this.condicion_ambiental = w;
	 }
	 
	 void addContamination(int c) {
		
		 if(c < 0) {
			 throw new IllegalArgumentException("Invalid contamination : "+c+" on the road: [ "+this+" ]   /Road/");
		 }
		 this.contaminacion_total += c;
	 }

	 public void advance(int time) {

  /*1*/	reduceTotalContamination(); 
  /*2*/	updateSpeedLimit();         

  /*3*/	for(int i = 0; i < this.vehiculos.size(); i++) {

	   	   if( this.vehiculos.get(i).getStatus() == VehicleStatus.WAITING) 
			   this.vehiculos.get(i).setSpeed(0);
		   else  
			   this.vehiculos.get(i).setSpeed(this.calculateVehicleSpeed(this.vehiculos.get(i)));
	   	   
			 this.vehiculos.get(i).advance(time);
		 }                         
		 
		 this.vehiculos.sort(null); 
		 
	 }
	 
	 public JSONObject report() {

		 JSONObject carretera = new JSONObject() ;
		
		 carretera.put("id", this._id);
		 carretera.put("speedlimit", this.limite_velocidad);
		 carretera.put("weather", this.condicion_ambiental);
		 carretera.put("co2", this.contaminacion_total);
		 JSONArray ja = new JSONArray();
		 for(int i=0; i< this.vehiculos.size();i++) {
			 ja.put(this.vehiculos.get(i));
		 }
		 carretera.put("vehicles", ja);
		 
		 return carretera;
	 }
	 
	 abstract void reduceTotalContamination();
	 abstract void updateSpeedLimit();
	 abstract int calculateVehicleSpeed(Vehicle v);


	 
	 /////////////////////////////////// automaticamente 

	public abstract int getCO2Limit();


	public abstract int getTotalCO2();


	public abstract int getLength();
}
