package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	////////////////// ATRIBUTOS /////////////////////////////////////////
	
	private int timeSlot = 0;
	
	////////////////// CONSTRUCTORA //////////////////////////////////////
	
	public  MostCrowdedStrategy(int tSlot) {
		this.timeSlot = tSlot;
		System.out.println("ME HE CREADO    MOSTCROWDEDSTRATEGY");
	}
	
	//////////////////// METODOS /////////////////////////////////////////
	
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen,	int lastSwitchingTime, int currTime) {
		
		if(roads.size() == 0) return -1; 
		
		if(currTime-lastSwitchingTime < this.timeSlot ) return currGreen; 
			
		return calcularTamanioCola(roads,qs,currGreen); 
		
	}


	private int calcularTamanioCola(List<Road> carreteras, List<List<Vehicle>> vehiculos, int currGreen) {
		
		int aux = -1, posicion = 0;
		
		if (currGreen == -1) {
			 for(int i = 0; i < vehiculos.size(); i++) {
			 	if(vehiculos.get(i).size() > aux) {
			 		aux = vehiculos.get(i).size();
			 		posicion = i;
			 	}	
			 }
		}
		else {
			int i = (currGreen + 1) % carreteras.size();
			
			posicion = currGreen;//
			
			while(i != currGreen) {
			 	if(vehiculos.get(i).size() > aux) {
			 		aux = vehiculos.get(i).size();
			 		posicion = i;
			 	}
			 	i = (i + 1) % carreteras.size();
			}
		}
		
		return posicion;
	}
}
