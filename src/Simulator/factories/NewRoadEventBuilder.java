package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {
	
	////////////////////////////////////////// ATRIBUTOS /////////////////////////////////////////////

	protected int time;
	protected String id;
	protected String origen;
	protected String destino;
	protected int longitud;
	protected int contLimite;
	protected int velocidad_maxima;
	protected Weather condicion_ambiental;

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public NewRoadEventBuilder(String type) {
		super(type);
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////
	
	protected Event createTheInstance(JSONObject data) {
		
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.origen = data.getString("src");
		this.destino = data.getString("dest");
		this.longitud = data.getInt("length");
		this.contLimite = data.getInt("co2limit");
		this.velocidad_maxima = data.getInt("maxspeed");
		this.condicion_ambiental = Weather.valueOf(data.getString("weather").toUpperCase());

		return createTheRoad();
	}
	
	abstract Event createTheRoad();

}
