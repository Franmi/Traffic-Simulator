package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////

	
	private String[] columnas = {"Id ", "Locations ","Itinerary ","CO2 Class", "Max. Speed ","Speed ", "Total CO2 ", "Distance "};
	private List<Vehicle> vehiculos;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public VehiclesTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		vehiculos = new ArrayList<Vehicle>();
		this.fireTableDataChanged();
	}
	
	/////////////////////////////// METODOS (AbstractTableModel) //////////////////////////////////////

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return vehiculos.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = vehiculos.get(rowIndex).getId();
			break;
		case 1:
			s= vehiculos.get(rowIndex).getVehicleStatus();
			break;
		case 2:
			s = vehiculos.get(rowIndex).getItinerary();
			break;
		case 3:
			s = vehiculos.get(rowIndex).getContClass();
			break;
		case 4:
			s = vehiculos.get(rowIndex).getVelocidad_maxima();
			break;
		case 5:
			s = vehiculos.get(rowIndex).getVelocidad_actual();
			break;
		case 6:
			s = vehiculos.get(rowIndex).getContaminacion_total();
			break;
		case 7:
			s = vehiculos.get(rowIndex).getDist_total();
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
		this.vehiculos = map.getVehicles();
	}
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.vehiculos = map.getVehicles();
		this.fireTableDataChanged();
	}
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.vehiculos = map.getVehicles();
		this.fireTableDataChanged();
	}
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub	
		this.vehiculos = map.getVehicles();
		this.fireTableDataChanged();
	}
	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.vehiculos = map.getVehicles();
		this.fireTableDataChanged();
	}
	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub	
	}
}
