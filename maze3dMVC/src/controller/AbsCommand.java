package controller;

/**
 * This class implements 'Command' interface. Holds Controller as a parameter.
 * 
 * @author Moran
 */
public abstract class AbsCommand implements Command {

	protected Controller controller;

	public AbsCommand(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public abstract void doCommand(String command);

}
