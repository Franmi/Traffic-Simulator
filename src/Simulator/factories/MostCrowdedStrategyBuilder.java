package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		int aux =1;
		if(data.has("timeslot"))
			aux = data.getInt("timeslot");
		
		return new MostCrowdedStrategy(aux);
	}

}
