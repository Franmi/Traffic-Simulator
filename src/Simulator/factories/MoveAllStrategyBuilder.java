package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy>{
	
	////////////////////////////////////////// CONSTRUCTORA /////////////////////////////////////////////

	public MoveAllStrategyBuilder() {
		super("most_all_dqs");

	}

	////////////////////////////////////////// METODOS /////////////////////////////////////////////

	protected DequeuingStrategy createTheInstance(JSONObject data) {
		
		return new MoveAllStrategy();
	}

}
