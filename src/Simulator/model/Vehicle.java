package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;


public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {
	
	////////////////// ATRIBUTOS ///////////////////////////////////////


	private List<Junction> itinerario;
	private int velocidad_maxima;
	private int velocidad_actual;
	private VehicleStatus estado;
	private Road carretera;
	private int localizacion;
	private int grado_contaminacion;
	private int contaminacion_total;
	private int dist_total;
	
	
	///////////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	Vehicle (String id, int maxSpeed, int contClass, List<Junction> itinerary){
		super(id);
		if(maxSpeed < 0 || contClass < 0 || contClass > 10 || itinerary.size() < 2){
			throw new IllegalArgumentException("Invalid arguments of the /Vehicle/: "+this);
		}
		this.velocidad_maxima = maxSpeed;
		this.grado_contaminacion = contClass;
		this.contaminacion_total = 0;
		this.velocidad_actual = 0;
		this.localizacion = 0;
		this.dist_total = 0;
		this.itinerario = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.estado = VehicleStatus.PENDING;

	}
	
	/////////////////////////////////////////////// METODOS ///////////////////////////////////////////////////////

	public int getLocation(){
		return this.localizacion;
	}
	int getSpeed() {
		return this.velocidad_actual;
	}
	public int getContClass() {
		return this.grado_contaminacion;
	}
	public VehicleStatus getStatus() {
		return this.estado;
	}
	public List<Junction> getItinerary(){
		return this.itinerario;
	}
	public Road getRoad() {
		return this.carretera;
	}
	public int getVelocidad_maxima() {
		return velocidad_maxima;
	}

	public int getVelocidad_actual() {
		return velocidad_actual;
	}
	public int getContaminacion_total() {
		return contaminacion_total;
	}

	public int getDist_total() {
		return dist_total;
	}
	
	public  String getVehicleStatus() {
		String s= "";
		if(this.estado == VehicleStatus.PENDING)
			s = "PENDING";
		else if(this.estado == VehicleStatus.ARRIVED)
			s = "ARRIVED";
		else if(this.estado == VehicleStatus.WAITING)
			s = "WAITING: "+ this.getRoad().getDest();
		else 
		s = this.getRoad().toString() +":"+this.getLocation();

		return s;
	}
	
	void setSpeed(int s) {
		
		if(s < 0) 
			throw new IllegalArgumentException("Invalid speed: "+s+"  /Vehicle/");
		
		if(velocidad_maxima < s) velocidad_actual = velocidad_maxima;
		else 
			velocidad_actual = s;	
	}
	
	void setContaminationClass(int c) {
		
		if(c < 0 || c >10) 
			throw new IllegalArgumentException("Invalid contamination class: "+c+"  /Vehicle/");
		
		this.grado_contaminacion = c;
	}
	
	
	void advance(int time) {

		int loc_antigua = this.localizacion;
		
		if(this.estado == VehicleStatus.TRAVELING && this.velocidad_actual != 0) {
			
	/*a*/	if((this.localizacion + this.velocidad_actual) > this.carretera.getLenght())
				this.localizacion = this.carretera.getLenght();
			else 
				this.localizacion += this.velocidad_actual;
		
	/*b*/	int c = (this.localizacion - loc_antigua)*this.grado_contaminacion;
			this.contaminacion_total += c;
			this.carretera.addContamination(c);
	
		
	/*c*/	if(this.localizacion >= this.carretera.getLenght()) {

				this.estado = VehicleStatus.WAITING;
				this.velocidad_actual = 0;
				this.carretera.getDest().enter(this);
			}
		}

		dist_total += this.localizacion - loc_antigua;
		
	}
	
	
	
	void moveToNextRoad() {
		
		if(this.estado != VehicleStatus.PENDING && this.estado != VehicleStatus.WAITING)
			throw new IllegalArgumentException("Invalid status: "+this.estado+ " of the /Vehicle/: "+this);
		
		
		if(this.estado == VehicleStatus.PENDING) {
			this.estado = VehicleStatus.TRAVELING;
			this.carretera = this.itinerario.get(0).roadTo(this.itinerario.get(1));
			this.carretera.enter(this);
		}
		else if (itinerario.indexOf(carretera.getDest())+1 == this.itinerario.size()) {
			this.carretera.exit(this);
			this.estado = VehicleStatus.ARRIVED;
			this.velocidad_actual = 0;
		}
		else {
			this.carretera.exit(this);
			this.estado = VehicleStatus.TRAVELING;
			this.carretera = this.carretera.getDest().roadTo(itinerario.get(itinerario.indexOf(carretera.getDest())+1));
			this.carretera.enter(this);
	
		}
		this.localizacion = 0;
	}
	
	
	public int compareTo(Vehicle o) {
		if (this.localizacion < o.localizacion) 
			return 1;
		if (this.localizacion == o.localizacion) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	
	public JSONObject report() {
	
		JSONObject vehiculo = new JSONObject();

		vehiculo.put("id", this._id);
		vehiculo.put("speed", this.velocidad_actual);
		vehiculo.put("distance", this.dist_total);
		vehiculo.put("co2", this.contaminacion_total);
		vehiculo.put("class", this.grado_contaminacion);
		vehiculo.put("status", this.estado);
		
		if(this.estado != VehicleStatus.PENDING && this.estado != VehicleStatus.ARRIVED) {
			vehiculo.put("road", this.carretera);
			vehiculo.put("location", this.localizacion);
		}
		return vehiculo;
	}

	
}