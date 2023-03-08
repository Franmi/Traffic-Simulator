package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Weather>> x = new ArrayList<Pair<String, Weather>>();
		JSONArray ja = data.getJSONArray("info");
		for (int i = 0; i < ja.length(); i++) {
			JSONObject aux = ja.getJSONObject(i);
			x.add(new Pair<String, Weather>(aux.getString("road"), Weather.valueOf(aux.getString("weather").toUpperCase())));
			
		}
		return new SetWeatherEvent(data.getInt("time"), x);
	}

}
