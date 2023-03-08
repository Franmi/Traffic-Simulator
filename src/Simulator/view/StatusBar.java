package simulator.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////

	private JLabel texto, texto1;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public StatusBar(Controller _ctrl) {
		_ctrl.addObserver(this);
		initGUI();
	}
	
	/////////////////////////////// METODOS  //////////////////////////////////////

	private void initGUI() {

		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

/**/	Box box0 = new Box(BoxLayout.X_AXIS);  
		box0.add(new JLabel(" Time: "));
		texto = new JLabel("0");
		box0.add(texto);
		this.add(box0);
		this.add(Box.createRigidArea(new Dimension(100,20)));
		
/**/	Box box1 = new Box(BoxLayout.X_AXIS);  
		texto1 = new JLabel("  WELCOME!");
		box1.add(texto1);
		this.add(box1);
		
	}

	
	
	/////////////////////////////// METODOS (TrafficSimObserver) //////////////////////////////////////

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		texto.setText(""+time);
		texto1.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		texto1.setText("Event added "+e.toString());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		texto.setText(""+0);
		texto1.setText("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		 JOptionPane.showMessageDialog(null, err,"Error!", JOptionPane.ERROR_MESSAGE);
	
	}
}
