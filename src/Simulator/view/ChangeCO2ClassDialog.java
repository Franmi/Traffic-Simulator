package simulator.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog implements ActionListener{

	/////////////////////////////// ATRIBUTOS /////////////////////////////////////////

	private int opcion_final = -1;
	private static final int OK = 0;
	private static final int CANCEL = 1;

	private List<Vehicle> vehiculos;
	
	private JSpinner ticks;
	private JComboBox caja2, caja1;

	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public ChangeCO2ClassDialog() {
		super(new JFrame(),"Enter data",true);
	
	}

	/////////////////////////////// METODOS //////////////////////////////////////

	public void inicializar(RoadMap mapa) {
		vehiculos = mapa.getVehicles();
	}
	
	
	public int showDialogo(String tipo) {
		setTitle(tipo);
		initGUI();
		setSize(460,150);
		setVisible(true);
		setResizable(false);
		return opcion_final;
	}
	
	private void initGUI() {

		/////// Localizacion en la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Dimension tamanoPantalla = mipantalla.getScreenSize(); // Hemos guardado el tamaño de mi pantalla
		int alturaPantalla = tamanoPantalla.height;
		int anchoPantalla = tamanoPantalla.width;
		this.setLocation(anchoPantalla/4, alturaPantalla/4);
		
		this.setLayout(new BorderLayout());
		
		
		/////// PANEL NORTE
		
		JPanel primerpanel = new JPanel();
		primerpanel.setLayout(new BoxLayout(primerpanel,BoxLayout.Y_AXIS));
		primerpanel.add(new JLabel("<html>Schedule an event to change the CO2 class of a vehicle after a given "
				+ "number of simulation ticks from now.</html>"));

		/////// PANEL CENTRO
	
		JPanel segundopanel = new JPanel();
		segundopanel.setLayout(new BoxLayout(segundopanel,BoxLayout.X_AXIS));
		
/**/	segundopanel.add(new JLabel(" Vehicle: "));
		
/**/	caja1 = new JComboBox();	
		
		for(int i=0; vehiculos != null && i<vehiculos.size() ;i++) 
			caja1.addItem(vehiculos.get(i).getId());
		segundopanel.add(caja1);
		
/**/	segundopanel.add(new JLabel("CO2 Class: "));
		
/**/	caja2 = new JComboBox();
		for(int i=0; i<=10;i++) 
			caja2.addItem(i);
		segundopanel.add(caja2);
		
/**/	segundopanel.add(new JLabel("Ticks: "));
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		ticks.setMaximumSize(new Dimension(80, 40));
		segundopanel.add(ticks);
	

		/////// PANEL SUR
		
		JPanel tercerpanel = new JPanel();
		CrearBoton("Cancel",tercerpanel);
		CrearBoton("OK",tercerpanel);
		
		
		this.getContentPane().add(primerpanel,BorderLayout.NORTH);
		this.getContentPane().add(segundopanel,BorderLayout.CENTER);
		this.getContentPane().add(tercerpanel,BorderLayout.SOUTH);
		

		
	}
	
	public void CrearBoton(String ActionListener, JPanel panel) {
		JButton boton = new JButton(ActionListener);
		boton.setActionCommand(ActionListener);
		boton.addActionListener(this);
		panel.add(boton);
	}
	
	
	public String getVehiculo() {
		return caja1.getSelectedItem().toString();
	}
	public int getCO2() {
		return Integer.parseInt(caja2.getSelectedItem().toString());
	}
	public int getTicks() {
		return Integer.parseInt(ticks.getValue().toString());
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("OK")) {
			opcion_final = OK;
			this.dispose();
		}
		if(e.getActionCommand().equals("Cancel")) {
			opcion_final = CANCEL;
			this.dispose();
		}
	}
}

