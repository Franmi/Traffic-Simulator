package simulator.model;

public class NewJunctionEvent extends Event {
	
	////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	////////////////// CONSTRUCTORA ///////////////////////////////////////
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy b, DequeuingStrategy c, int x, int y) {
		super(time);
		System.out.println("ME HE CREADO    JUNCITIONEVENT");
		this.id =id;
		this.lsStrategy = b;
		this.dqStrategy = c;
		this.xCoor = x;
		this.yCoor = y;
		
	}

	///////////////////////////////////////// METODO ///////////////////////////////////////////////////////
	
	void execute(RoadMap map) {
	
		Junction cruce = new Junction(this.id, this.lsStrategy, this.dqStrategy, this.xCoor, this.yCoor);
		
		map.addJunction(cruce);
		
	}
	
	@Override
	public String toString() {
		return "New Junction '" +id +"'";
	}
	@Override
	public int gettime() {
		// TODO Auto-generated method stub
		return this._time;
	}
}
