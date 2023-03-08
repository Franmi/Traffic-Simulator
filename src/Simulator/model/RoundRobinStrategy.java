package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	
	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private int timeSlot = 0;
	
	////////////////// CONSTRUCTORA  ///////////////////////////////////////
	
	public RoundRobinStrategy(int tSlot) {
		this.timeSlot = tSlot;
		System.out.println("ME HE CREADO    ROUNDROBIN");
	}
	
	////////////////// METODO ///////////////////////////////////////
	
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen,int lastSwitchingTime, int currTime) {
		
		if(roads.size()==0) return -1;
		
		if(currGreen == -1) return 0;
		
		if(currTime-lastSwitchingTime < this.timeSlot ) return currGreen;
	  
		return (currGreen+1)%roads.size();
	}
	
}
