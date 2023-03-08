package simulator.model;

public class CityRoad extends Road{

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////
	
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////
	
	void reduceTotalContamination() {
		
		int x = 0;
		switch(this.condicion_ambiental) {
		case WINDY:
			x = 10;
			break;
		case STORM:
			x = 10;
			break;
		default:
			x = 2;
			break;
		}
		if(this.contaminacion_total - x > 0)
			this.contaminacion_total -= x;
		else this.contaminacion_total = 0;
	}

	
	void updateSpeedLimit() {
		this.limite_velocidad = this.velocidad_maxima;
	}

	
	int calculateVehicleSpeed(Vehicle v) {

		return (int)(((11.0-v.getContClass())/11.0)*this.limite_velocidad);
	}

	
	////////////////////////////////// automaticamente
	@Override
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
