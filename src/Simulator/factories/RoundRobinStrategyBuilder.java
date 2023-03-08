package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////
	
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		int aux =1;
		if(data.has("timeslot"))
			aux = data.getInt("timeslot");
		
		return new RoundRobinStrategy(aux);
	}

}
