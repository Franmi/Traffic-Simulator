package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {

	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private List<Pair<String,Integer>> cs;
	
	////////////////// CONSTRUCTORA ////////////////////////////////////
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> a) {
		super(time);
		System.out.println("ME HE CREADO    NewSetContClassEvent");
		if( a == null) 
			throw new IllegalArgumentException("Invalid arguments of /SetContClassEvent/: "+a);
		
		this.cs = a;
	}


	////////////////// METODO //////////////////////////////////////////
	
	void execute(RoadMap map) {

		for(int i =0; i < this.cs.size(); i++) {
			if(map.getVehicle(this.cs.get(i).getFirst())== null) {
				throw new IllegalArgumentException("The vehicle: [ "+this.cs.get(i).getFirst()+" ] doesnt exist in map /SetContClassEvent/");
			}
			map.getVehicle(cs.get(i).getFirst()).setContaminationClass(cs.get(i).getSecond()); 
		}
		
	}

	public String getValues() {
		String a = "[";
		
		for(int i =0; i < this.cs.size();i++) {
			a += "("+this.cs.get(i).getFirst() + ", "+cs.get(i).getSecond().toString()+")";
			if(i<cs.size()-1)
				a+=",";
			
		}
	
		a+="]";
		return a;
	}
	
	@Override
	public String toString() {
		return "Change CO2 Class: "+ getValues() ;
	}
	

	@Override
	public int gettime() {
		// TODO Auto-generated method stub
		return this._time;
	}
}
