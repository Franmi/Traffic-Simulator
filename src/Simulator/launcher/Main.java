package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;


import simulator.view.MainWindow;


public class Main {

	///////////////////////// ATRIBUTOS ///////////////////////////////////////
	
	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer _timeLimit = null; 				 //numero de pasos
	private static String _inFile = null;					//fichero del que se leen eventos
	private static String _outFile = null;					//fichero de salida
	private static Factory<Event> _eventsFactory = null;    //factor�a de eventos

	private static String mode;
	

	///////////////////////// METODOS ///////////////////////////////////////


	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
	
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}
			
		} catch (ParseException e) {
			
			JOptionPane.showMessageDialog(null, e, "Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Number of ticks").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Mode of execution").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
		
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		
	
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
		
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException {
		if(line.getOptionValue("t") == null ) {
			_timeLimit = _timeLimitDefaultValue;
		}
		else
			_timeLimit = Integer.parseInt(line.getOptionValue("t"));
		
		if(_timeLimit < 0)
			_timeLimit = _timeLimitDefaultValue;

	}
	
	//////////////////7
	
	public static void parseModeOption(CommandLine line) throws ParseException {
		if(line.getOptionValue("m")== null) {
			mode = "gui";
		}
		else if (line.getOptionValue("m").toLowerCase().equals("gui") ||line.getOptionValue("m").toLowerCase().equals("console"))
			mode = line.getOptionValue("m").toLowerCase();
		else 
			mode = "gui";
		
	
	}
	//////////////////7

	
	private static void initFactories() {
		// se crean las estrategias de cambio de sem�foro
		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		// se crean las estrategias de extracci�n de la cola
		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		// se crea la lista de builders
		List<Builder<Event>> eventBuilders = new ArrayList<>();
		eventBuilders.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
		eventBuilders.add(new NewCityRoadEventBuilder());
		eventBuilders.add(new NewInterCityRoadEventBuilder());
		eventBuilders.add(new NewVehicleEventBuilder());
		eventBuilders.add(new SetWeatherEventBuilder());
		eventBuilders.add(new SetContClassEventBuilder());
		_eventsFactory = new BuilderBasedFactory<>(eventBuilders);
		
		System.out.println("0\n");
	}

	private static void startBatchMode() throws IOException {
		if (_inFile == null) {
			throw new IOException("An events file is missing");
		}
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out = _outFile == null ?
		System.out : new FileOutputStream(new File(_outFile));
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		ctrl.loadEvents(in);
		ctrl.run(_timeLimit, out);
		in.close();
		System.out.println("Done!");
	}

	public static void startGUIMode() throws IOException {
		
		
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindow(ctrl);
			}
		});
		
		if(_inFile != null) {
			InputStream in = new FileInputStream(new File(_inFile));
			ctrl.loadEvents(in);
		}
		
		
	}
	private static void start(String[] args) throws IOException {
		initFactories();  
		parseArgs(args); 
		
		if(mode.equals("gui"))  startGUIMode();	
		else  startBatchMode();	
				
	}
	

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	
	///////////////////////// MAIN ///////////////////////////////////////

	public static void main(String[] args) {
		try {
			start(args);	
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, e, "Error!", JOptionPane.ERROR_MESSAGE);
		}

	}

}
 
