package model;

import java.io.InputStream;
import java.io.OutputStream;

import controller.Controller;

/**
 * This interface define the methods for handle the clients
 * @author Moran
 *
 */
public interface ClientHandler {

	void handleClient(InputStream inFromClient, OutputStream outToClient);
	void setController(Controller controller);
}