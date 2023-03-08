package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;//
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;


public class ControlPanel extends JPanel implements ActionListener, TrafficSimObserver{

	/////////////////////////////// ATRIBUTOS //////////////////////////////////////
	
	private Controller ctrl;
	private RoadMap mapa;
	private List<Event> eventos;
	private int tiempo;
	
	private boolean _stopped;
	private boolean error;
	private JFileChooser archivos; 
	private JSpinner ticks;
	private JButton btnExit, btnFicheros , btnCO2 , btnWeather , btnEjecucion, btnParada;

	
	///////////////////////////// CONSTRUCTORA //////////////////////////////////////

	public ControlPanel(Controller _ctrl) {
		ctrl = _ctrl;
		ctrl.addObserver(this);
		archivos = new JFileChooser();
		_stopped = false;
		tiempo=0;
		initGUI();
		eventos = new ArrayList<Event>();
	}
	
	///////////////////////////////// METODOS ///////////////////////////////////////

	private void initGUI() {
		
		
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

		 // 1º Boton FICHEROS
		Box box0 = new Box(BoxLayout.X_AXIS);      
		btnFicheros = CrearBoton(Imagenes.FICHEROS,"Ficheros",box0);  		
		this.add(Box.createRigidArea(new Dimension(10,10)));
		box0.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(box0);
		
		
		this.add(Box.createRigidArea(new Dimension(10,10)));

		// 2º Boton CO2 y 3º Boton WEATHER
		Box box1 = new Box(BoxLayout.X_AXIS);	                  
		btnCO2 = CrearBoton(Imagenes.CO2,"CO2Class",box1);
		btnWeather = CrearBoton(Imagenes.WEATHER,"WeatherClass",box1);
		box1.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(box1);
		
		this.add(Box.createRigidArea(new Dimension(10,10)));
		
		//4º Boton RUN y 5º Boton STOP
		Box box2 = new Box(BoxLayout.X_AXIS);                      
		btnEjecucion = CrearBoton(Imagenes.EJECUCION,"Run",box2);
		btnParada = CrearBoton(Imagenes.PARADA,"Stop",box2);				
		box2.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(box2);
		
		this.add(Box.createRigidArea(new Dimension(5,5)));

  /**/  this.add(new JLabel("Ticks: "));
		
  /**/	ticks = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		ticks.setMaximumSize(new Dimension(80, 40));
		ticks.setMinimumSize(new Dimension(80, 40));
		ticks.setPreferredSize(new Dimension(80, 40));
		this.add(ticks);
		
		this.add(Box.createHorizontalGlue());
		
		// 6º Boton EXIT
		Box box3 = new Box(BoxLayout.X_AXIS);
		btnExit = CrearBoton(Imagenes.EXIT,"Exit",box3);  	        	
		box3.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(box3);


		if(eventos.isEmpty()) enableToolBarIni(false);
		else enableToolBarIni(true);
	}


	public JButton CrearBoton(Imagenes _imagen, String ActionListener, Box caja)  {
		JButton boton = new JButton();
		  
		if(new File(_imagen.imagen().toString()).exists())
		boton.setIcon(_imagen.imagen());
		else boton.setText(ActionListener);
		
		boton.setActionCommand(ActionListener);
		boton.addActionListener(this);
		caja.add(boton);
		return boton;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
/**/	if(e.getActionCommand().equals("Ficheros")) {
			int result = archivos.showOpenDialog(this);
			String str = String.valueOf(archivos.getSelectedFile());
			
			try {
				InputStream in = new FileInputStream(new File(str));
				if(result == JFileChooser.APPROVE_OPTION) {
				ctrl.reset();
				ctrl.loadEvents(in);
				error = false;
				enableToolBarIni(true);
				}
		 
			} catch (Exception e1) {
				  JOptionPane.showMessageDialog(this, "You didn´t choose any file ", "Warning",
					        JOptionPane.WARNING_MESSAGE);
			}
			
		
		
		}
/**/	else if(e.getActionCommand().equals("Run") ) {

			_stopped = false;
			enableToolBar(false);
			this.run_sim((int) ticks.getValue());
	
		}
/**/	else if(e.getActionCommand().equals("Stop")) {
			stop();
			enableToolBar(true);
		}
/**/	else if(e.getActionCommand().equals("CO2Class")) {
	
			try {
				
			ChangeCO2ClassDialog dialogo1 = new ChangeCO2ClassDialog();
			dialogo1.inicializar(mapa);
	
			if(dialogo1.showDialogo("Change CO2 Class") == 0) {
				
				List<Pair<String, Integer>> x = new ArrayList<Pair<String, Integer>>();
				int timetotal = tiempo + dialogo1.getTicks();
				x.add(new Pair<String, Integer>(dialogo1.getVehiculo(), dialogo1.getCO2()));

				ctrl.addEvent(new NewSetContClassEvent(timetotal, x));
			}
			}
			catch(Exception exc) {
				
				   JOptionPane.showMessageDialog(null, "You can't do that, the program hasn't started yet",
						      "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
/**/	else if(e.getActionCommand().equals("WeatherClass")) {
	
			try {
				
			ChangeWeatherDialog dialogo2 = new ChangeWeatherDialog();
			dialogo2.inicializar(mapa);
			
			if(dialogo2.showDialogo("Change Road Weather") == 0 ) {
				
			List<Pair<String, Weather>> x = new ArrayList<Pair<String, Weather>>();
			int timetotal= tiempo+ dialogo2.getTicks();
		
			x.add(new Pair<String, Weather>(dialogo2.getRoads(), dialogo2.getWeather()));
			ctrl.addEvent(new SetWeatherEvent(timetotal, x));
			}
			}
			catch(Exception exc) {
				
				   JOptionPane.showMessageDialog(null, "You can't do that, the program hasn't started yet",
						      "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("Exit")) {
			int resp = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Alerta!", JOptionPane.YES_NO_OPTION);
			
	  		if(resp == JOptionPane.YES_NO_OPTION) System.exit(0); 
		}
			
	}
	
	
	private void run_sim(int n)  {
		try {
		Thread.sleep(100);
		}
		catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if(n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			}
			catch(Exception e){
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					run_sim(n-1);
				}
			});
		}
		else {
			enableToolBar(true);
			if(!error)
			_stopped = true;
			else enableToolBarIni(false);
		}
		
	}
	
	
	private void stop() {
		_stopped = true;
	}
	
	private void enableToolBar(Boolean estado) {
		btnExit.setEnabled(estado);
		btnFicheros.setEnabled(estado);
		btnCO2.setEnabled(estado);
		btnWeather.setEnabled(estado);
		btnEjecucion.setEnabled(estado);
	}

	private void enableToolBarIni(Boolean estado) {

		btnCO2.setEnabled(estado);
		btnWeather.setEnabled(estado);
		btnEjecucion.setEnabled(estado);
		btnParada.setEnabled(estado);
	}

	
	///////////////////////////////// METODOS (TrafficSimObserver) ///////////////////////////////////////

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		mapa = map;
		tiempo = time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		mapa = map;
		tiempo = time;
	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		mapa = map;
		eventos = events;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		eventos = events;
		mapa = map;
		tiempo = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		mapa = map;
		tiempo = time;
		eventos = events;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		 ctrl.reset();
		 error = true;
		 stop();
	}
}