package presenter;
/**
 * This class apply the exit method which close the project orderly.
 */
public class Exit extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Exit(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		presenter.getModel().exit();
		presenter.getView().exit();
	}

}
