package view;
import controller.Controller;

/**
 * This class implement the methods from AbstractViewServer
 * @author Moran
 */
public class MyViewServer extends AbstractViewServer {

	/**
	 * C'tor
	 * @param controller
	 */
	public MyViewServer(Controller controller) {
		super(controller);
	}

	/**
	 * This method start the project
	 */
	@Override
	public void start() {
		controller.start();
	}

	/**
	 * This method close the project
	 */
	@Override
	public void close() {
		controller.close();

	}

	/**
	 * This method print a message
	 * @param message
	 */
	@Override
	public void printMessage(String message) {
		if(message != null)
			System.out.println(message);
		else
			System.out.println("empty message");
	}

}
