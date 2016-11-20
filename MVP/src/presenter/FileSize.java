package presenter;
/**
 * This class apply the method of fileSize which display the size of the
 * maze in the file.
 */
public class FileSize extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public FileSize(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length != 2){
			presenter.getView().display("Invalid input");
		} else{
			String name = tempArr[1];
			presenter.getModel().fileSize(name);
		}
	}
}
