package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	
	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	protected String _type;

	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	public Builder(String type) {
		
		if (type == null)
			throw new IllegalArgumentException("Invalid type: " + type);
		else
			_type = type;
	}
	
	////////////////////////////////////////  METODOS  ///////////////////////////////////////////

	public T createInstance(JSONObject info) {

		T b = null; // crea un objeto T ya que es un generico y no sabe si es un vehiculo una carretera o un cruce

		if (_type != null && _type.equals(info.getString("type"))) {  // Si el _type no es nulo y coincide con el type 
																	  //llama a createTheInstance para crear ese objeto
			System.out.println("3\n");
			b = createTheInstance(info.has("data") ? info.getJSONObject("data") : null);
		}

		return b;
	}

	protected abstract T createTheInstance(JSONObject data);
}
