package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	////////////////////////////////////////// ATRIBUTOS /////////////////////////////////////////////

	private Factory<LightSwitchingStrategy> lss;
	private Factory<DequeuingStrategy> dqs;
	
	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy>lssFactory, Factory<DequeuingStrategy>dqsFactory) {
		super("new_junction");
		this.lss = lssFactory;
		this.dqs = dqsFactory;
	}
	
	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	protected Event createTheInstance(JSONObject data) {

		return new NewJunctionEvent(data.getInt("time"),
				data.getString("id"),
				this.lss.createInstance(data.getJSONObject("ls_strategy")), 
				this.dqs.createInstance(data.getJSONObject("dq_strategy")),
				data.getJSONArray("coor").getInt(0), 
				data.getJSONArray("coor").getInt(1));
	}

	
}
