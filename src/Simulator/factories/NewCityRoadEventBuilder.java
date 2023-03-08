package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {
	
	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public NewCityRoadEventBuilder(){
		super("new_city_road");
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	Event createTheRoad() {
		return new NewCityRoadEvent(this.time, this.id, this.origen, this.destino,this.velocidad_maxima ,
				this.contLimite,this.longitud ,this.condicion_ambiental);
	}

}
