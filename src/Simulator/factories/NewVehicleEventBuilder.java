package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	protected Event createTheInstance(JSONObject data) {
	
		List<String> lista = new ArrayList<String>();
		JSONArray ja = data.getJSONArray("itinerary");
		for (int i = 0; i < ja.length(); i++) lista.add(ja.getString(i));
		
		return new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"), data.getInt("class"), lista);
	}

}
