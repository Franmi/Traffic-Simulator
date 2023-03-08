package simulator.model;

import org.json.JSONObject;

public abstract class SimulatedObject {

	////////////////// ATRIBUTOS //////////////////////////////////////////

	protected String _id;

	////////////////// CONSTRUCTORA ///////////////////////////////////////

	SimulatedObject(String id) {
		_id = id;
	}

	////////////////// METODOS //////////////////////////////////////////

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract void advance(int time);

	abstract public JSONObject report();
}
