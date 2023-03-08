package simulator.model;


public class InterCityRoad extends Road {
	
	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////
	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////
	
	void reduceTotalContamination() {
		int x = 0;
		switch(this.condicion_ambiental) {
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case WINDY:
			x = 15;
			break;
		case STORM:
			x = 20;
			break;
		default:
			break;
		}
		
		this.contaminacion_total = (int) (((100.0-x)/100.0)*this.contaminacion_total);
		
	}

	
	void updateSpeedLimit() {
		
		if(this.contaminacion_total > this.alarma_contaminacion) 
			this.limite_velocidad = (int)(this.velocidad_maxima*0.5);
		else 
			this.limite_velocidad = this.velocidad_maxima;
	}

	
	int calculateVehicleSpeed(Vehicle v) {
	
		if(this.condicion_ambiental == Weather.STORM) return (int)(this.limite_velocidad*0.8);
		else return(this.limite_velocidad);
		
	}

	
	
	
	/////////////////// automaticamente
	public int getCO2Limit() {
		
		return this.alarma_contaminacion;
	}

	@Override
	public int getTotalCO2() {
		
		return this.contaminacion_total;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return this.getLenght();
	}
	
}
