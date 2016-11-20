package presenter;
/**
 * This class apply the method of generateMaze which create a maze
 * according to Prim algorithm.
 */
public class Generate extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Generate(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		presenter.getModel().generate(command);
	}

}
