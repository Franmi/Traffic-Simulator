package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	
	///////////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private TrafficSimulator _sim;
	private Factory<Event> _eventsFactory;
	

	//////////////////////// CONSTRUCTORA ////////////////////////////////////
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("Invalid arguments of the /Controller/: " + sim + " " + eventsFactory);
		}
		this._sim = sim;
		this._eventsFactory = eventsFactory;
	}
	
	
	//////////////////////// METODOS ////////////////////////////////////////
	
	public void loadEvents(InputStream in) {

		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray events = jo.getJSONArray("events");
		
		if (!jo.has("events")) {
			throw new IllegalArgumentException("Invalid input format");
		}
		for (int i = 0; i < events.length(); i++) {
			System.out.println("1\n");
			_sim.addEvent(_eventsFactory.createInstance(events.getJSONObject(i)));	
		}
	}


	public void run(int n) {
		
		for(int i = 0; i < n-1; i++) {
			_sim.advance(); 
		}
		this._sim.advance(); 
	}

	
	
	public void run(int n, OutputStream out) {
		if (out == null) 
			out = new OutputStream() {public void write(int b) throws IOException {}};
			
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println(" \"states\": [");
		
		for(int i = 0; i < n-1; i++) {
			_sim.advance(); 
			p.print(_sim.report()); 
			p.println(",");
		}
		
		this._sim.advance(); 
		p.print(this._sim.report());
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		this._sim.reset();
	}
	
	
	public void addObserver(TrafficSimObserver o) {
		_sim.addObserver(o);
	}
	public void removeObserver(TrafficSimObserver o) {
		_sim.removeObserver(o);
	}
	public void addEvent(Event e) {
		_sim.addEvent(e);
	}
	

}
