package controller;

import java.io.Serializable;
/**
 * This class implements Serializable and define the Properties of the Server
 * @author Moran
 *
 */
public class PropertiesServer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int port;
	private int numOfClients;
	
	/**
	 * C'tor
	 */
	public PropertiesServer() {
		super();
	}

	/**
	 * getters & setters
	 * @return
	 */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
	
	public void defaultPropertiesServer(){
		this.port = 5400;
		this.numOfClients = 10;
	}
	
	
	
	
}
