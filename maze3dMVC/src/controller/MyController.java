package controller;

import java.util.HashMap;

import model.Model;
import view.View;

/**
 * This class extends 'AbsController' class. Holds an HashMap as a parameter.
 * 'MyController' gets a command from the view and send it to the model. The
 * model returns his calculation to the controller. The controller send the
 * calculation to the view.
 * 
 * @param commandMap-
 *            mapping between the String key to the Command
 * 
 * @author Bar
 */
public class MyController extends AbsController {

	private HashMap<String,Command> commandMap;
	
	/**
	 * C'tor
	 * @param view
	 * @param model
	 */
	public MyController(View view, Model model) {
		super(view, model);
		this.commandMap = new HashMap<String, Command>();
		this.commandMap.put("dir" ,new Dir(this));
		this.commandMap.put("generate" ,new Generate(this));
		this.commandMap.put("display" ,new Display(this));
		this.commandMap.put("save" ,new Save(this));
		this.commandMap.put("load" ,new Load(this));
		this.commandMap.put("maze" ,new MazeSize(this));
		this.commandMap.put("file" ,new FileSize(this));
		this.commandMap.put("solve" ,new Solve(this));
		this.commandMap.put("exit" ,new Exit(this));
		
		view.setCommand(commandMap);
	}
	
	/**
	 * This method creates a maze3d according to the model
	 * @param name
	 * @param y
	 * @param x
	 * @param z
	 */
	@Override
	public void generateMaze(String name, int x, int y, int z) {
		model.generateMaze(name, x, y, z);
	}

	/**
	 * This method gets cross section by x/y/z  according to model 
	 * @param name
	 * @param by
	 * @param index
	 */
	@Override
	public void displayCrossSection(String name, String by, int index) {
		model.displayCrossSection(name, by, index);
	}
	/**
	 * this method called the model to display the maze by name
	 */
	@Override
	public void displayMaze(String name) {
		model.displayMaze(name);
	}
	
	/**
	 * This method save a maze into a file according to model
	 * @param name
	 * @param fileName
	 */
	@Override
	public void saveMaze(String name, String fileName) {
		model.saveMaze(name, fileName);
	}
	
	/**
	 * This method load a maze from a file according to model
	 * @param name
	 * @param fileName
	 */
	@Override
	public void loadMaze(String name, String fileName) {
		model.loadMaze(name, fileName);
	}
	
	/**
	 * This method display the size of the maze according to model 
	 * @param name
	 */
	@Override
	public void mazeSize(String name) {
		model.mazeSize(name);

	}
	/**
	 * This method display the size of the maze in the file according to model
	 * @param name
	 */
	@Override
	public void fileSize(String name) {
		model.fileSize(name);

	}
	
	/**
	 * This method solve the maze by specific algorithm according to model
	 * @param name
	 * @param algorithm
	 */
	@Override
	public void solveMaze(String name, String algorithm) {
		model.solveMaze(name, algorithm);

	}
	
	/**
	 * This method display the solution of chosen algorithm according to model 
	 * @param name
	 */
	@Override
	public void displaySolution(String name) {
		model.displaySolution(name);
	}
	
	/**
	 * This method close the project orderly according to model and view
	 */
	@Override
	public void exit() {
		model.exit();
		view.exit();
	}

}
