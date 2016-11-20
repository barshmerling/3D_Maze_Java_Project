package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

/**
 * this class extends commonView class and implements all the methods of VIEW
 * interface
 * 
 * @param Cli
 * @author Moran & Bar
 *
 */
public class MyView extends CommonView {

	private CLI cli;

	public MyView(BufferedReader bufferedReader, PrintWriter printWriter) {
		this.in = bufferedReader;
		this.out = printWriter;
	}

	/**
	 * starts running CLI
	 */
	@Override
	public void start() {
		cli.start();
	}

	/**
	 * this method prints a String Array
	 */
	@Override
	public void printArr(String[] str) {
		if (str != null) {
			for (int i = 0; i < str.length; i++) {
				out.println(str[i] + " ");
			}
			out.flush();
		} else {
			out.println("this is an empty Array");
			out.flush();
		}
	}

	/**
	 * this method gets a massage and prints it
	 */
	@Override
	public void printMessage(String message) {
		if (message != null) {
			out.println(message);
			out.flush();
		} else {
			out.println("no message to print");
			out.flush();
		}
	}

	/**
	 * this method creates a maze3d by getting a byte array and prints it
	 * 
	 * @throws IOException
	 */
	@Override
	public void printByteArray(byte[] byteArr) {
		try {
			Maze3d maze = new Maze3d(byteArr);
			out.println("Start position:" + maze.getStartPosition());
			out.println("Goal position:" + maze.getGoalPosition());
			maze.printMaze();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method updates the CLI
	 */
	@Override
	public void setCommand(HashMap<String, Command> commandMap) {
		cli = new CLI(in, out, commandMap);
	}

	/**
	 * this method notify exit
	 */
	@Override
	public void exit() {
		out.println("goodBye!");
	}

}
