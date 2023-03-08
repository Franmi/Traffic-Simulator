package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////

	private String[] columnas = {"Id ", "Green ","Queues "};
	private List<Junction> cruces;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public JunctionsTableModel(Controller _ctrl) {
		_ctrl.addObserver(this);
		cruces = new ArrayList<Junction>();
		this.fireTableDataChanged();
	}

	/////////////////////////////// METODOS (AbstractTableModel) //////////////////////////////////////

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return cruces.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = cruces.get(rowIndex).getId();
			break;
		case 1:
			s = cruces.get(rowIndex).getGreen();
			break;
		case 2:
			s = cruces.get(rowIndex).getQueues();
			break;
	
		}

		return s;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}
	
	/////////////////////////////// METODOS (TrafficSimObserver) //////////////////////////////////////

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.cruces = map.getJunctions();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.cruces = map.getJunctions();
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.cruces = map.getJunctions();
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.cruces = map.getJunctions();
		this.fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.cruces = map.getJunctions();
		this.fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
	}
}
