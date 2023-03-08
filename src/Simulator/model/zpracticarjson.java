package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class zpracticarjson {

	public static int hijo = 0;
	public static int padre = 1;
	public static int abuelo = 2;
	public static List<String> familia = new ArrayList<String>();
	
	
	public static void main(String[] args) {
		
		familia.add("hijo");
		familia.add("padre");
		familia.add("abuelo");
		
		System.out.println(familia);

		JSONObject jo1 = new JSONObject();
		jo1.put("hijo vale:", hijo);
		jo1.put("padre vale:", padre);
		jo1.put("abuelo vale:", abuelo);
		
		JSONArray ja = new JSONArray();
		for(int i=0; i<familia.size();i++) {
			ja.put(familia.get(i));
		}
		jo1.put("La familia entera es:", ja);
		
		System.out.println(jo1);
		System.out.println(jo1.toString(2));
		System.out.println();
	}
}
