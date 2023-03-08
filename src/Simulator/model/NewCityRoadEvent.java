package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{
	
	////////////////// CONSTRUCTORA //////////////////////////////////////////

	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc,int maxSpeed , int co2Limit, int length, Weather weather) {
		super(time, id, srcJun, destJunc,maxSpeed , co2Limit, length, weather);
		System.out.println("ME HE CREADO    CITYROADEVENT");
	}

	////////////////// METODOS //////////////////////////////////////////
	
	protected Road createRoadObject() {
		return new CityRoad(this.id, this.org, this.dest, this.velocidad_maxima, this.contLimite,this.longitud ,this.condicion_ambiental);
	
	}

	
	@Override
	public String toString() {
		return "New CityRoad '" +id +"'";
	}

	@Override
	public int gettime() {
		// TODO Auto-generated method stub
		return this._time;
	}
}

