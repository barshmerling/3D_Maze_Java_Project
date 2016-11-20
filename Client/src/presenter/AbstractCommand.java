package presenter;

/**
 * This class implements 'Command' interface. Holds Presnter as a parameter.
 * @author Moran
 */
public abstract class AbstractCommand implements Command {

	protected Presenter presenter;
	
	/**
	 * C'tor
	 * @param presenter
	 */
	public AbstractCommand(Presenter presenter) {
		super();
		this.presenter = presenter;
	}
	
	/**
	 * An abstract doCommand for implements different kind of commands
	 */
	@Override
	public abstract void doCommand(String command);
	
	
}
