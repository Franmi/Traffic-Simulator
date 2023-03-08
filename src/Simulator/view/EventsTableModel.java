 package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////
	
	private String[] columnas = {"Time ", "Desc."};
	private List<Event> eventos;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////
	
	public EventsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		eventos = new ArrayList<Event>();
		this.fireTableDataChanged();
	}
	
	/////////////////////////////// METODOS (AbstractTableModel)  //////////////////////////////////////

	@Override
	public int getRowCount() {
		return eventos.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = eventos.get(rowIndex).gettime();
			break;
		case 1:
			s = eventos.get(rowIndex).toString();
			break;
		}

		return s;
		
	}
	public String getColumnName(int col) {
		return columnas[col];
	}
	
	/////////////////////////////// METODOS (TrafficSimObserver)  //////////////////////////////////////
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.eventos = events;
		this.fireTableDataChanged();

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.eventos = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		eventos=events;
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		eventos = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		eventos = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
}
