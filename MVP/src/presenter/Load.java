package presenter;
/**
 * This class apply the method of loadMaze which load a maze from a file.
 */
public class Load extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Load(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length < 1) {
			presenter.getView().display("Invalid Input");
		} else if (tempArr[0].equals("zip")) {
			presenter.getModel().loadFromZip();
		} else {
			String fileName = tempArr[0];
			String mazeName = tempArr[1];
			presenter.getModel().loadMaze(fileName, mazeName);
		}
	}
}
