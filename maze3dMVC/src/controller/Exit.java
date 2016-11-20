package controller;

/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * 
 * @author Bar
 */
public class Exit extends AbsCommand {

	/**
	 * C'tor	
	 * @param controller
	 */
	public Exit(Controller controller) {
		super(controller);
	}

	/**
	 * This method apply the exit method which close the project orderly.
	 */
	@Override
	public void doCommand(String command) {
		controller.exit();

	}

}
