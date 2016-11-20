package model;

import java.io.InputStream;
import java.io.OutputStream;

import controller.Controller;

/**
 * This class implements ClientHandler and holds controller.
 * @author Moran
 */
public abstract class AbstractClientHandler implements ClientHandler {

	protected Controller controller;

	@Override
	public abstract void handleClient(InputStream inFromClient, OutputStream outToClient);

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
}
