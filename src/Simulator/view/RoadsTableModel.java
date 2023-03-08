package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////

	private String[] columnas = {"Id ", "Length ","Weather ", "Max. Speed ","Speed Limit ", "Total CO2 ", "CO2 Limit "};
	private List<Road> carreteras;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public RoadsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		carreteras = new ArrayList<Road>();
		this.fireTableDataChanged();
	}
	
	/////////////////////////////// METODOS (AbstractTableModel) //////////////////////////////////////

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return carreteras.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = carreteras.get(rowIndex).getId();
			break;
		case 1:
			s = carreteras.get(rowIndex).getLength();
			break;
		case 2:
			s = carreteras.get(rowIndex).getWeather();
			break;
		case 3:
			s = carreteras.get(rowIndex).getVelocidad_max();
			break;
		case 4:
			s = carreteras.get(rowIndex).getLimite_vel();
			break;
		case 5:
			s = carreteras.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = carreteras.get(rowIndex).getCO2Limit();
			break;
		}
		return s;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}
	
	/////////////////////////////// METODOS (TrafficSimObserver) //////////////////////////////////////

	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		carreteras = map.getRoads();
	}
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		carreteras = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		carreteras = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		carreteras = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		carreteras = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
	
	}

}
