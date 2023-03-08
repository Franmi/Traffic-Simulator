package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	////////////////// ATRIBUTOS //////////////////////////////////////////
	
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	
	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	public NewVehicleEvent(int time, String a, int b, int c, List<String> d) {
		super(time);
		System.out.println("ME HE CREADO    VEHICLEONEVENT");
		this.id = a;
		this.maxSpeed = b;
		this.contClass = c;
		this.itinerary = d;	
	}

	///////////////////// METODO //////////////////////////////////////////
	
	void execute(RoadMap map) {
		
		List<Junction> cruces = new ArrayList<Junction>();
		
		for(int i = 0; i < this.itinerary.size(); i++) 
			cruces.add(map.getJunction(this.itinerary.get(i)));
		
		Vehicle vehiculo = new Vehicle(this.id, this.maxSpeed, this.contClass, cruces);
		map.addVehicle(vehiculo);
		vehiculo.moveToNextRoad();
	}

	@Override
	public String toString() {
		return "New Vehicle '" +id +"'";
	}

	@Override
	public int gettime() {
		// TODO Auto-generated method stub
		return this._time;
	}
}
