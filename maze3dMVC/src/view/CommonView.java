package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;
/**
 * This class define an abstract class for different views
 * @author Moran
 */
public abstract class CommonView implements View {
	 
	protected Controller controller;
	protected BufferedReader in; 
	protected PrintWriter out;
	
	/**
	 * abstract methods 
	 */
	public abstract void start();
	public abstract void printArr(String[] str);
	public abstract void printMessage(String massage);
	public abstract void printByteArray(byte[] byteArr);
	public abstract void setCommand(HashMap<String, Command> commandMap);
	public abstract void exit();
	
	/**
	 * getters and setters
	 */
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public BufferedReader getIn() {
		return in;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public PrintWriter getOut() {
		return out;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
}