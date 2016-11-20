package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
/**
 * This class define an abstract class for a different views.
 * This class extends Observable and implements View.
 * @param in - a BufferedReader.
 * @param out - a PrintWriter.
 * @param hashStringCommand - mapping between String and Command.
 * @param hashCommandObject - mapping between Command and Object.
 * @author Moran
 */
public abstract class AbstractView extends Observable implements View {

	protected BufferedReader in;
	protected PrintWriter out;
	protected HashMap<String, Command> hashStringCommand;
	protected HashMap<Command, Object> hashCommandObject;
	
	/**
	 * C'tor
	 * @param in
	 * @param out
	 */
	public AbstractView(BufferedReader in, PrintWriter out) {
		super();
		this.in = in;
		this.out = out;
		this.hashStringCommand = new HashMap<String, Command>();
		this.hashCommandObject = new HashMap<Command, Object>();
	}
	
	/**
	 * Abstract Methods
	 */
	public abstract void start();
	public abstract void display(String message);
	public abstract void displayStringArray(String[] arr);
	public abstract void displayCrossSectionBy(int[][] maze2d);
	public abstract void displaySolution(Solution<Position> solution);
	public abstract void setCommand(HashMap<String, Command> commandMap);
	public abstract void displayMaze(Maze3d maze);
	public abstract void displayPosition(Position position);
	public abstract void setProperties(Properties prop);
	public abstract void exit();
}
