package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
/**
 * this class implements all the methods of the 'View' interface.
 * @author BAR
 *
 */
public class Maze3dView extends AbstractView {

	private ExecutorService threadPool;
	
	/**
	 * C'tor
	 * @param in
	 * @param out
	 */
	public Maze3dView(BufferedReader in, PrintWriter out) {
		super(in, out);
		this.threadPool = Executors.newCachedThreadPool();
	}
	
	/**
	 * starts running CLI
	 */
	@Override
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					out.println("client side");
					out.println("please enter a command");
					out.flush();
					String command = in.readLine();
					while (!(command.equals("exit"))) {
						setChanged();
						notifyObservers(command);
						try {
							threadPool.awaitTermination(2, TimeUnit.SECONDS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						out.println("please enter a new command");
						out.flush();
						command = in.readLine();
					}
					setChanged();
					notifyObservers(command);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * this method display a general message
	 * @param message
	 */
	@Override
	public void display(String message) {
		if (message != null) {
			out.println(message);
			out.flush();
		} else {
			out.println("no message!");
			out.flush();
		}
	}

	/**
	 * this method display a string array
	 * @param arr
	 */
	@Override
	public void displayStringArray(String[] arr) {
		if (arr != null) {
			for (String s : arr) {
				out.println(s);
			}
			out.flush();
		} else {
			out.println("the array is null");
			out.flush();
		}
	}

	/**
	 * this method display crossSection by x/y/z
	 * @param maze2d
	 */
	@Override
	public void displayCrossSectionBy(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[i].length; j++) {
				out.print(maze2d[i][j]);
			}
			out.println();
			out.flush();
		}
	}

	/**
	 * this method display the solution for maze3d
	 * @param solution
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		solution.print();
	}

	/**
	 * this method update the 'commandMap' hash map 
	 * @param commandMap
	 */
	@Override
	public void setCommand(HashMap<String, Command> commandMap) {
		this.hashStringCommand=commandMap;
	}

	/**
	 * this method display the maze
	 * @param maze
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		maze.printMaze();
	}

	/**
	 * this method display a position
	 * @param position
	 */
	@Override
	public void displayPosition(Position position) {
		out.println(position);
		out.flush();
	}

	/**
	 * this method update the User Interface 
	 * @param prop
	 */
	@Override
	public void setProperties(Properties prop) {
		if (!(prop.getChooseView().equals("Command line"))) {
			setChanged();
			notifyObservers("replaceUserInterface");
		}
	}

	/**
	 * this method close the project orderly
	 */
	@Override
	public void exit() {
		out.println("Successfully closed");
		out.flush();
	}
}
