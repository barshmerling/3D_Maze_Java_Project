package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;
/**
 * * this class get all the notifies from model & view and act accordingly.
 * @param view - indicate the desirable view
 * @param model - indicate the desirable model
 * @param hashStringCommand - mapping between String & Command.
 * @param properties - define the properties of the maze
 * @author BAR
 */ 
public class Presenter implements Observer{
	
	private Model model;
	private View view;
	private HashMap<String, Command> hashStringCommand;
	private Properties properties;
	
	/**
	 * C'tor
	 * @param view
	 * @param model
	 */
	public Presenter(View view, Model model){
		this.view = view;
		this.model = model;
		this.properties = new Properties();
		properties.defaultProperties();
		model.setProperties(properties);
		
		hashStringCommand = new HashMap<String, Command>();
		
		hashStringCommand.put("dir", new Dir(this));
		hashStringCommand.put("generate", new Generate(this));
		hashStringCommand.put("display", new Display(this));
		hashStringCommand.put("save", new Save(this));
		hashStringCommand.put("load", new Load(this));
		hashStringCommand.put("maze", new MazeSize(this));
		hashStringCommand.put("file", new FileSize(this));
		hashStringCommand.put("solve", new Solve(this));
		hashStringCommand.put("move", new Moves(this));
		hashStringCommand.put("exit", new Exit(this));
		
		view.setCommand(hashStringCommand);
		
	}
	
	/**
	 * getters & setters
	 */
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
		if (view != null)
			this.view.setProperties(properties);
		if (model != null)
			this.model.setProperties(properties);
	}
		
	/**
	 * this method invoke upon any change in the view and the model.
	 * when the observable notify the observer - its invoke the update method.
	 */
	@Override
	public void update(Observable observable, Object object) {
		if (observable == view) {
			// if the object is properties
			if (((object.getClass()).getName()).equals("presenter.Properties")) {
				Properties prop = (Properties) object;
				model.setProperties(prop);
			} else { // if it is a command
				Command command;
				String userCommand = (String) object;
				command = hashStringCommand.get(userCommand.split(" ")[0]);
				if (command != null) {
					if (userCommand.split(" ").length > 0) {
						command.doCommand(userCommand.substring(userCommand.indexOf(' ') + 1));
					} else {
						command.doCommand("");
					}
				} else {
					view.display("Invalid Input");
				}
			}
		} else if (observable == model) {
			String line = (String) object;
			String[] stringArray = line.split(" ");
			switch (stringArray[0]) {
			case "mazeIsReady":
				Maze3d maze = (Maze3d) model.getUserCommand(line);
				view.displayMaze(maze);
				view.displayPosition(maze.getStartPosition());
				break;
			case "displayCrossSectionBy":
				view.displayCrossSectionBy((int[][]) model.getUserCommand(line));
				break;
			case "saveMaze":
				view.display("Maze save succsesfully in file" + (String) model.getUserCommand(line));
				break;
			case "loadMaze":
				Maze3d maze3d = (Maze3d) model.getUserCommand(line);
				view.displayMaze(maze3d);
				view.displayPosition(maze3d.getStartPosition());
				break;
			case "mazeSize":
				view.display("Maze size in the memory is: " + (int) model.getUserCommand(line));
				break;
			case "fileSize":
				view.display("File size is: " + (int) model.getUserCommand(line));
				break;
			case "solutionIsReady":
				view.displaySolution(model.getMazeSolution((String) model.getUserCommand(line)));
				break;
			case "saveZip":
				view.display("Maze file is saved to: " + (String) model.getUserCommand(line));
				break;
			case "loadZip":
				view.display("Maze file is loaded from: " + (String) model.getUserCommand(line));
				break;
			case "move":
				view.displayMaze(model.getMaze3d((String) model.getUserCommand(line)));
				view.displayPosition(model.getPositionFromHash((String) model.getUserCommand(line)));
				break;
			case "exit":
				view.display("the program is shutting dowm, bye bye!");
				break;
			case "null":
				view.display("Maze " + (String) model.getUserCommand(line) + " is not exist");
				break;
			case "Invalid":
				switch (stringArray[1]) {
				case "parameters":
					view.display("Invalid parameters");
					break;
				case "index":
					view.display("Invalid index");
					break;
				case "file":
					view.display("The file" + (String) model.getUserCommand(line) + "is not exist");
					break;
				case "compress":
					view.display("Compressor" + (String) model.getUserCommand(line) + "is faild");
					break;
				case "maze":
					view.display("Error with" + (String) model.getUserCommand(line) + "maze");
					break;
				case "algorithm":
					view.display("Invalid algorithm");
					break;
				case "solution":
					view.display("Solution for:" + (String) model.getUserCommand(line) + "is not exist");
					break;

				default:
					view.display("Invalid command");
					break;
				}				
			}
		}
	}
	
	/**
	 * This method close the project orderly according to view that start this project
	 */
	public void exitView(){
		view.exit();
	}
}
