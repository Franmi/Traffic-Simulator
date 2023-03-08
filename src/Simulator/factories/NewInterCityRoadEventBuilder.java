package simulator.factories;

import simulator.model.Event;

import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	Event createTheRoad() {
		return new NewInterCityRoadEvent(this.time, this.id, this.origen, this.destino, this.velocidad_maxima,
				this.contLimite,this.longitud ,this.condicion_ambiental);
	}


}
