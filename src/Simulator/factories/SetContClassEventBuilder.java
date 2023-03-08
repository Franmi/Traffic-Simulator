package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Integer>> x = new ArrayList<Pair<String, Integer>>();
		JSONArray ja = data.getJSONArray("info");
		for (int i = 0; i < ja.length(); i++) {
			JSONObject aux = ja.getJSONObject(i);
			
			x.add(new Pair<String, Integer>(aux.getString("vehicle"), aux.getInt("class")));
		}
		return new NewSetContClassEvent(data.getInt("time"), x);
	}
}
