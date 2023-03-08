package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private static final int _JRADIUS = 10;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private RoadMap _map;
	private Image _car, _rain, _cloud, _storm, _sun,_wind, _cont_0,_cont_1,_cont_2,_cont_3,_cont_4,_cont_5;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////
	
	public MapByRoadComponent(Controller ctrl) {
		ctrl.addObserver(this);
		this.setPreferredSize(new Dimension(300,200));
		initGUI();
	}
	
	/////////////////////////////// METODOS  //////////////////////////////////////

	private void initGUI() {
		_car = loadImage("car.png");
		_rain = loadImage("rain.png");
		_cloud = loadImage("cloud.png");
		_storm = loadImage("storm.png");
		_sun = loadImage("sun.png");
		_wind = loadImage("wind.png");
		_cont_0 = loadImage("cont_0.png");
		_cont_1 = loadImage("cont_1.png");
		_cont_2 = loadImage("cont_2.png");
		_cont_3 = loadImage("cont_3.png");
		_cont_4 = loadImage("cont_4.png");
		_cont_5 = loadImage("cont_5.png");
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().size() == 0) {
	
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
		drawJunctions(g);
	}
	
	
	
	private void drawJunctions(Graphics g) {
	

		for(int i =0; i < _map.getRoads().size();i++) {

		
			int x1 = 50;
			int y1 = (i+1)*50;
			int x2 = getWidth()-100;
			int y2 = (i+1)*50;
			Road r= _map.getRoads().get(i);
		
	
			drawCircles(g,_JUNCTION_COLOR,x1,y1,r.getSrc().getId());

			Color junctionColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				junctionColor = _GREEN_LIGHT_COLOR;
			}
			drawCircles(g,junctionColor,x2,y2,r.getDest().getId());
		}
			
	}
	
	private void drawCircles(Graphics g, Color c, int x, int y, String id_carretera) {
		g.setColor(c);
		g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(id_carretera, x-5, y-10);
	}


	private void drawRoads(Graphics g) {

		for(int i =0; i < _map.getRoads().size();i++) {
			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int y1 = (i+1)*50;
			int x2 = getWidth()-100;
			int y2 = (i+1)*50;

			Color roadColor = Color.BLACK;

			
			drawLine(g, x1, y1, x2, y2, 15, 5, roadColor);
			g.drawString(_map.getRoads().get(i).toString(), 10, y1+2);
			drawWeather(g,_map.getRoads().get(i).getWeather(),x2+10,y1-20);
			drawContamination(g,_map.getRoads().get(i).getTotalCO2(),_map.getRoads().get(i).getCO2Limit(),x2+x1,y1-20);
		}
	}

	private void drawWeather(Graphics g, Weather w, int x, int y) {
		
		switch(w){
		case SUNNY:
			g.drawImage(_sun,x,y,32,32,this);
			break;
		case STORM:
			g.drawImage(_storm,x,y,32,32,this);
			break;
		case CLOUDY:
			g.drawImage(_cloud,x,y,32,32,this);
			break;
		case RAINY:
			g.drawImage(_rain,x,y,32,32,this);
			break;
		case WINDY:
			g.drawImage(_wind,x,y,32,32,this);
			break;
		}
		
	}

	private void drawContamination(Graphics g,int cont_total, int cont_lim, int x, int y) {
		int c = (int) Math.floor(Math.min((double) cont_total/(1.0 + (double)cont_lim), 1.0)/0.19);
	
		
		switch(c){
		case 0:
			g.drawImage(_cont_0,x,y,32,32,this);
			break;
		case 1:
			g.drawImage(_cont_1,x,y,32,32,this);
			break;
		case 2:
			g.drawImage(_cont_2,x,y,32,32,this);
			break;
		case 3:
			g.drawImage(_cont_3,x,y,32,32,this);
			break;
		case 4:
			g.drawImage(_cont_4,x,y,32,32,this);
			break;
		case 5:
			g.drawImage(_cont_5,x,y,32,32,this);
			break;
		}
	}

	private void drawLine(Graphics g,int x1, int y1,int x2, int y2, int w, int h, Color lineColor) {
		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		
	}
	
	

	private void drawVehicles(Graphics g) {

		List<Road> carreteras = new ArrayList<Road>();
		carreteras = _map.getRoads();
		for(int i =0; i < _map.getVehicles().size();i++) {
			Vehicle v = _map.getVehicles().get(i);
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				Road r = v.getRoad();
				
				int x1 = 50;
				int y1 = (carreteras.indexOf(r)+1)*50;
				
				int x2 = getWidth()-100;
				int y2 = (carreteras.indexOf(r)+1)*50;
				
				double roadLength = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				double alpha = Math.atan(((double) Math.abs(x1 - x2)) / ((double) Math.abs(y1 - y2)));
				double relLoc = roadLength * ((double) v.getLocation()) / ((double) r.getLength());
				double x = Math.sin(alpha) * relLoc;
				double y = Math.cos(alpha) * relLoc;
				 
				int xDir = x1 < x2 ? 1 : -1;
				int yDir = y1 < y2 ? 1 : -1;

				int vX = x1 + xDir * ((int) x);
				int vY = y1 + yDir * ((int) y);

			
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
				g.drawImage(_car, vX, vY - 10, 16, 16, this);
				g.drawString(v.getId(), vX, vY - 6);
			}
		}
		
	}


	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("src/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	
	
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
