package presenter;

import java.io.File;
/**
 * This class extends 'AbsCommand' and implements the 'doCommand' method.
 * @author Moran
 */
public class Dir extends AbstractCommand {

	/**
	 * C'tor
	 * @param presenter
	 */
	public Dir(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String command) {
		if (command == null) {
			presenter.getView().display("command not exist!");
		}else{
			try{
				File file = new File(command);
				if (file.list().length == 0) {
					presenter.getView().display("The file is not exist");
				} else { 
					presenter.getView().displayStringArray(file.list());
				}
			} catch (NullPointerException e) {
				presenter.getView().display("Invalid path");
			}	
		}
	}
}
