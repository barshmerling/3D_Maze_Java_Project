package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import controller.Controller;

/**
 * This is an abstract class ,implements View.
 * @param BufferedReader in- for 
 * @param PrintWriter out - for 
 * @param serverSocket - represent a socket for talking to the clients 
 * @param controller - for define a specific controller
 * @author Moran
 *
 */
public abstract class AbstractViewServer implements View {

	protected BufferedReader in;
	protected PrintWriter out;
	protected ServerSocket serverSocket;
	protected Controller controller;
	
	/**
	 * C'tor
	 * @param controller
	 */
	
	public AbstractViewServer(Controller controller) {
		super();
		this.controller = controller;
	}

	
	public  abstract void start();
	public abstract void close();
	public abstract void printMessage(String message);
}
