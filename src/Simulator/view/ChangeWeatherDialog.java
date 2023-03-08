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
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog implements ActionListener{

	/////////////////////////////// ATRIBUTOS /////////////////////////////////////////


	private int opcion_final = -1;
	private static final int OK = 0;
	private static final int CANCEL = 1;
	
	private List<Road> carreteras;
	
	private JSpinner ticks;
	private JComboBox caja1, caja2;
	private int tiempo;
	
	/////////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public ChangeWeatherDialog() {
		super(new JFrame(),"Enter data",true);
	}

	/////////////////////////////// METODOS //////////////////////////////////////

	public void inicializar(RoadMap mapa) {
		carreteras = mapa.getRoads();
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
		Dimension tamanoPantalla = mipantalla.getScreenSize(); 
		int alturaPantalla = tamanoPantalla.height;
		int anchoPantalla = tamanoPantalla.width;
		this.setLocation(anchoPantalla/4, alturaPantalla/4);
		
		this.setLayout(new BorderLayout());
		
		/////// PANEL NORTE
		
		JPanel primerpanel = new JPanel();
		primerpanel.setLayout(new BoxLayout(primerpanel,BoxLayout.Y_AXIS));
		
		primerpanel.add(new JLabel("<html>Schedule an event to change the weather of a road after a given "
				+ "number of simulation ticks from now.</html>"));
		

		/////// PANEL CENTRO
		
		JPanel segundopanel = new JPanel();
		segundopanel.setLayout(new BoxLayout(segundopanel,BoxLayout.X_AXIS));
		
/**/	segundopanel.add(new JLabel(" Road: "));
		
/**/	caja1 = new JComboBox();
		for(int i=0; carreteras != null && i<carreteras.size() ;i++) 
			caja1.addItem(carreteras.get(i).getId());
		segundopanel.add(caja1);
		
/**/	segundopanel.add(new JLabel("Weather: "));
		
/**/	caja2 = new JComboBox();
		String[] weather = {"SUNNY","CLOUDY","RAINY","WINDY","STORM"};
		for(int i =0; i < weather.length; i++) caja2.addItem(weather[i]);
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
	
	
	public String getRoads() {
		return caja1.getSelectedItem().toString();		
	}
	public Weather getWeather() {
		return Weather.valueOf(caja2.getSelectedItem().toString());
	}
	public int getTicks() {
		return Integer.parseInt(ticks.getValue().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("OK")) {
			opcion_final = OK;
			dispose();
		}
		else if(e.getActionCommand().equals("Cancel")) {
			opcion_final = CANCEL;
			this.dispose();
		}
	}
}
