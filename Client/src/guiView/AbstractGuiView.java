package guiView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
import view.AbstractView;

/**
 * This class is an abstract class for gui view, extends AbstractView.
 * @author BAR
 */
public abstract class AbstractGuiView extends AbstractView {
	
	/**
	 * C'tor
	 * @param in
	 * @param out
	 */
	public AbstractGuiView(BufferedReader in, PrintWriter out) {
		super(in, out);
	
	}
	/**
	 * all the methods from AbstractView stay Abstract
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
