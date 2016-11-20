package controller;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import algorithms.mazeGenerators.Maze3d;
import model.ClientHandler;
import model.Model;
import model.MyServer;
import view.View;

/**
 * The Controller get a command from the view and send it to the model. The
 * model returns his calculation to the controller. The controller send the
 * calculation to the view.
 * @author Moran
 */
public class Controller {
	private View view;
	private Model model;
	private MyServer myServer;
	private ClientHandler clientHandler;
	private PropertiesServer propertiesServer;
	
	/**
	 * C'tor
	 * @param clientHandler
	 * @param propertiesServer
	 */
	public Controller(ClientHandler clientHandler, PropertiesServer propertiesServer) {
		setClientHandler(clientHandler);
		this.propertiesServer = propertiesServer;
		this.myServer = new MyServer(5400, clientHandler, 3, this);
		
		try {
			XMLDecoder dXml = new XMLDecoder(new BufferedInputStream(new FileInputStream("PropertiesServer.xml")));
			propertiesServer = (PropertiesServer) dXml.readObject();
			dXml.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getters & setters
	 * @return
	 */
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public MyServer getMyServer() {
		return myServer;
	}

	public void setMyServer(MyServer myServer) {
		this.myServer = myServer;
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}
	
	/**
	 * This method set the ClientHandler and set the Controller of the clientHandler 
	 * @param clientHandler
	 */
	public void setClientHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
		clientHandler.setController(this);
	}

	public PropertiesServer getPropertiesServer() {
		return propertiesServer;
	}

	public void setPropertiesServer(PropertiesServer propertiesServer) {
		this.propertiesServer = propertiesServer;
	}
	
	/**
	 * This method start the project
	 */
	public void start(){
		myServer.start();
	}
	
	/**
	 * This method close the project
	 */
	public void close(){
		try {
			model.close();
			myServer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method display a string message
	 * @param message
	 */
	public void display(String message){
		view.printMessage(message);
	}
	
	/**
	 * 
	 * @param command
	 */
	public boolean update(String command){
		String[] tempArr = command.split(" ");
		if (tempArr[0].equals("generate")) {
			int x = Integer.parseInt(tempArr[2]);
			int y = Integer.parseInt(tempArr[3]);
			int z = Integer.parseInt(tempArr[4]);
			model.generate(tempArr[1], x, y, z); 
			return true;
		}
		if (tempArr[0].equals("solve")) {
			model.solve(tempArr[1], tempArr[2]);
			return true;
		}
		return false; 
	}

	/**
	 * This method return a maze3d according to the received mazeName
	 * @param mazeName
	 * @return Maze3d
	 */
	public Maze3d getMaze3d(String mazeName) {
		Maze3d maze3d = model.getMaze3d(mazeName);
		return maze3d;
	}
	
	/**
	 * This method return the solution of the maze according to the received mazeName
	 * @param mazeName
	 * @return String solution
	 */
	public String getSolution(String mazeName){
		String solution= model.getSolution(mazeName);
		return solution;
	}
	
}
