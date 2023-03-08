package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	////////////////// ATRIBUTOS //////////////////////////////////////////

	protected String id;
	protected String origen;
	protected String destino;
	protected int longitud;
	protected int contLimite;
	protected int velocidad_maxima;
	protected Weather condicion_ambiental;
	protected Junction org;
	protected Junction dest;
	
	////////////////// CONSTRUCTORA //////////////////////////////////////////

	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int maxSpeed, int co2Limit, int length, Weather weather) {
		super(time);
		System.out.println("ME HE CREADO    ROADONEVENT");
		this.id = id;
		this.origen = srcJun;
		this.destino = destJunc;
		this.longitud = length;
		this.contLimite = co2Limit;
		this.velocidad_maxima = maxSpeed;
		this.condicion_ambiental = weather;
	}
	
	////////////////// METODOS //////////////////////////////////////////

	void execute(RoadMap mapa) {
		
		org = mapa.getJunction(this.origen);
		dest = mapa.getJunction(destino);
		mapa.addRoad(createRoadObject());
	}
	
	abstract protected Road createRoadObject();
}
