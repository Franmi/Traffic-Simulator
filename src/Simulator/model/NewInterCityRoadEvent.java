package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

	////////////////// CONSTRUCTORA //////////////////////////////////////////

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc,int maxSpeed , int co2Limit, int length, Weather weather) {
		super(time, id, srcJun, destJunc, maxSpeed, co2Limit, length, weather); 
		System.out.println("ME HE CREADO    INTERCITYEVENT");
	}

	////////////////// METODOS //////////////////////////////////////////

	protected Road createRoadObject() {
		return new InterCityRoad(this.id, this.org, this.dest, this.velocidad_maxima, this.contLimite,this.longitud ,this.condicion_ambiental);
	}
	
	@Override
	public String toString() {
		return "New InterCityRoad '" +id +"'";
	}
	@Override
	public int gettime() {
		// TODO Auto-generated method stub
		return this._time;
	}
}
