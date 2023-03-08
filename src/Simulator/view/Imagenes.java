package simulator.view;
import javax.swing.ImageIcon;

public enum Imagenes {
	FICHEROS("ficheros") {
		@Override
		public ImageIcon imagen() { 
			// TODO Auto-generated method stub
			ImageIcon ico = new ImageIcon ("src/icons/open.png");
			return ico;
		}
	} ,
	CO2("co2") {
		@Override
		public ImageIcon imagen() {
			// TODO Auto-generated method stub
			ImageIcon ico = new ImageIcon ("src/icons/co2class.png");
			return ico;
		}
	} ,
	WEATHER("weather") {
		@Override
		public ImageIcon imagen() {
			ImageIcon ico = new ImageIcon ("src/icons/weather.png");
			return ico;
		}
	}, 
	EJECUCION("ejecucion") {
		@Override
		public ImageIcon imagen() {
			ImageIcon ico = new ImageIcon ("src/icons/run.png");
			return ico;
		}
	},
	PARADA("parada") {
		@Override
		public ImageIcon imagen() {
			ImageIcon ico = new ImageIcon ("src/icons/stop.png");
			return ico;
		}
	},
	EXIT("exit") {
		@Override
		public ImageIcon imagen() {
			ImageIcon ico = new ImageIcon ("src/icons/exit.png");
			return ico;
		}
	};
	

	private String name;
	
	// constructor 
	private Imagenes(String x) { 
		name = x;
	};
	
	//public ImageIcon imagen();
	public abstract ImageIcon imagen();

}