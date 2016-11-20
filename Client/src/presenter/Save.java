package presenter;
/**
 * This class apply the method of saveMaze witch save the maze into a file / zip.
 */
public class Save extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Save(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		String[] tempArr = command.split(" ");
		if (tempArr.length < 1 ){
			presenter.getView().display("Invalid Input");
		} else if(tempArr[0].equals("zip")){ // if the command is: save zip mazeName
			presenter.getModel().saveToZip();
		}else{ // if the command is: save mazeName
			String nameMaze = tempArr[0];
			presenter.getModel().saveMaze(nameMaze);
		}
	}
}
