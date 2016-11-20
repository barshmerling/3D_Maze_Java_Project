package guiView;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * This class extends BasicWindow and define the maze3d window for the game.
 * @author BAR
 */
public class Maze3dWindow extends BasicWindow {
	
	protected Maze3d maze3d;
	protected Position currentPos;
	protected Solution<Position> solution;
	protected SelectionListener generateListener, solveListener, exitListener, saveListener, loadListener, propertiesListener;
	protected Menu toolBarMenu, fileMenu, optionsMenu;
	protected MenuItem fileSaveItem, fileLoadItem, fileExitItem, filePropertiesItem, optionSolveItem, optionStartItem;
	protected MenuItem fileMenuHeader, optionsMenuHeader;
	protected DisposeListener disposeExit;
	protected KeyListener keyboardListner;
	protected ArrayList<Maze3dDisplayer> maze3dDisplayerList;
	protected Button playButton;
	protected String fileName; 
	
	/**
	 * C'tor
	 * @param title
	 * @param width
	 * @param height
	 */
	public Maze3dWindow(String title, int width, int height) {
		super(title, width, height);
		this.maze3dDisplayerList = new ArrayList<Maze3dDisplayer>();
		playButton = new Button(shell, 0);
		updateDisplayerList();
	}
	/**
	 * getters & setters
	 */
	public String getFilename() {
		return fileName;
	}
	public void setFileName(String file) {
		this.fileName = file;
	}
	public Maze3d getMaze3d() {
		return maze3d;
	}
	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
		updateDisplayerList();
	}
	public Position getCurrentPos() {
		return currentPos;
	}
	public void setCurrentPos(Position currentPos) {
		this.currentPos = currentPos;
		updateDisplayerList();
	}
	public Solution<Position> getSolution() {
		return solution;
	}
	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
		updateDisplayerList();
	}
	public SelectionListener getGenerateListener() {
		return generateListener;
	}
	public void setGenerateListener(SelectionListener generateListener) {
		this.generateListener = generateListener;
	}
	public SelectionListener getSolveListener() {
		return solveListener;
	}
	public void setSolveListener(SelectionListener solveListener) {
		this.solveListener = solveListener;
	}
	public SelectionListener getExitListener() {
		return exitListener;
	}
	public void setExitListener(SelectionListener exitListener) {
		this.exitListener = exitListener;
	}
	public SelectionListener getSaveListener() {
		return saveListener;
	}
	public void setSaveListener(SelectionListener saveListener) {
		this.saveListener = saveListener;
	}
	public SelectionListener getLoadListener() {
		return loadListener;
	}
	public void setLoadListener(SelectionListener loadListener) {
		this.loadListener = loadListener;
	}
	public SelectionListener getPropertiesListener() {
		return propertiesListener;
	}
	public void setPropertiesListener(SelectionListener propertiesListener) {
		this.propertiesListener = propertiesListener;
	}
	public Menu getToolBarMenu() {
		return toolBarMenu;
	}
	public void setToolBarMenu(Menu toolBarMenu) {
		this.toolBarMenu = toolBarMenu;
	}
	public Menu getFileMenu() {
		return fileMenu;
	}
	public void setFileMenu(Menu fileMenu) {
		this.fileMenu = fileMenu;
	}
	public Menu getOptionsMenu() {
		return optionsMenu;
	}
	public void setOptionsMenu(Menu optionsMenu) {
		this.optionsMenu = optionsMenu;
	}
	public MenuItem getFileSaveItem() {
		return fileSaveItem;
	}
	public void setFileSaveItem(MenuItem fileSaveItem) {
		this.fileSaveItem = fileSaveItem;
	}
	public MenuItem getFileLoadItem() {
		return fileLoadItem;
	}
	public void setFileLoadItem(MenuItem fileLoadItem) {
		this.fileLoadItem = fileLoadItem;
	}
	public MenuItem getFileExitItem() {
		return fileExitItem;
	}
	public void setFileExitItem(MenuItem fileExitItem) {
		this.fileExitItem = fileExitItem;
	}
	public MenuItem getFilePropertiesItem() {
		return filePropertiesItem;
	}
	public void setFilePropertiesItem(MenuItem filePropertiesItem) {
		this.filePropertiesItem = filePropertiesItem;
	}
	public MenuItem getOptionSolveItem() {
		return optionSolveItem;
	}
	public void setOptionSolveItem(MenuItem optionSolveItem) {
		this.optionSolveItem = optionSolveItem;
	}
	public MenuItem getOptionStartItem() {
		return optionStartItem;
	}
	public void setOptionStartItem(MenuItem optionStartItem) {
		this.optionStartItem = optionStartItem;
	}
	public MenuItem getFileMenuHeader() {
		return fileMenuHeader;
	}
	public void setFileMenuHeader(MenuItem fileMenuHeader) {
		this.fileMenuHeader = fileMenuHeader;
	}
	public MenuItem getOptionsMenuHeader() {
		return optionsMenuHeader;
	}
	public void setOptionsMenuHeader(MenuItem optionsMenuHeader) {
		this.optionsMenuHeader = optionsMenuHeader;
	}
	public DisposeListener getDisposeExit() {
		return disposeExit;
	}
	public void setDisposeExit(DisposeListener disposeExit) {
		this.disposeExit = disposeExit;
		shell.addDisposeListener(disposeExit);
	}
	public KeyListener getKeyboardListner() {
		return keyboardListner;
	}
	public void setKeyboardListner(KeyListener keyboardListner) {
		this.keyboardListner = keyboardListner;
	}
	public ArrayList<Maze3dDisplayer> getMaze3dDisplayerList() {
		return maze3dDisplayerList;
	}
	public void setMaze3dDisplayerList(ArrayList<Maze3dDisplayer> maze3dDisplayerList) {
		this.maze3dDisplayerList = maze3dDisplayerList;
	}
	public Button getPlayButton() {
		return playButton;
	}
	public void setPlayButton(Button playButton) {
		this.playButton = playButton;
	}
	
	/**
	 * this method insert widgets to the maze window 
	 */
	@Override
	public void initWidgets() {
		
		//setting the menus
		shell.setLayout(new GridLayout(2,false));
		toolBarMenu = new Menu(shell,SWT.BAR); 
		shell.setMenuBar(toolBarMenu);

		fileMenuHeader = new MenuItem(toolBarMenu, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		filePropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		filePropertiesItem.setText("&Properties");

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("&Load");

		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");

		optionsMenu = new Menu(shell, SWT.DROP_DOWN);
		optionsMenuHeader = new MenuItem(toolBarMenu, SWT.CASCADE);
		optionsMenuHeader.setText("&Options");
		optionsMenuHeader.setMenu(optionsMenu);
		
		optionStartItem = new MenuItem(optionsMenu, SWT.PUSH);
		optionStartItem.setText("&Start");
		
		optionSolveItem = new MenuItem(optionsMenu, SWT.PUSH);
		optionSolveItem.setText("&Solve");
		
		//setting the listeners for each menuItem
		
		//exit listener
		fileExitItem.addSelectionListener(exitListener);
		//save listener
		fileSaveItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fileDialog = new FileDialog(shell,SWT.OPEN);
				fileDialog.setText("Save As");
				fileDialog.setFilterPath("desktop");
				String[] txt = {"*.txt", "*.doc"};
				fileDialog.setFilterExtensions(txt);
				setFileName(fileDialog.open());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		fileSaveItem.addSelectionListener(saveListener);
		//load listener
		fileLoadItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fileDialog = new FileDialog(shell,SWT.OPEN);
				fileDialog.setText("Open");
				fileDialog.setFilterPath("desktop");
				String[] txt = {"*.txt", "*.doc"};
				fileDialog.setFilterExtensions(txt);
				setFileName(fileDialog.open());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		fileLoadItem.addSelectionListener(loadListener);
		//Start listener
		optionStartItem.addSelectionListener(generateListener);
		
		Maze3dDisplayer maze3d = new Maze3dGuiDisplayer(shell, SWT.DOUBLE_BUFFERED, 'x');
		maze3d.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		maze3dDisplayerList.add(maze3d);
		
		//solve listener
		optionSolveItem.addSelectionListener(solveListener);
		//play button listener
		playButton.addKeyListener(keyboardListner);
		// properties listener
		filePropertiesItem.addSelectionListener(propertiesListener);		
	}
	/**
	 * this method update the data in the DisplayerList and redraw
	 */
	public void updateDisplayerList() {
		for (Maze3dDisplayer widget : maze3dDisplayerList) {
			if (maze3d != null) {
				widget.setMaze3d(maze3d);
			}
			if (currentPos != null) {
				widget.setCurrentPos(currentPos);
			}
			if (solution != null) {
				widget.setSolution(solution);
			}
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					widget.redraw();
				}
			});		
		}
	}
	/**
	 * this method moving the player by position
	 * this method get position to move in the C'tor 
	 */
	public void movePlayer(int x, int y, int z){
		if (x>=0 && x<maze3d.getLengthX() && y>=0 && y<maze3d.getLengthY() && z>=0 && z<maze3d.getLengthZ()){
			if (maze3d.getValue(x, y, z)==0){
				currentPos.setX(x);
				currentPos.setY(y);
				currentPos.setZ(z);
				updateDisplayerList();
			}
		}
	}
	/**
	 * This method display any message of Error
	 */
	public void displayError(String error){
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
				messageBox.setMessage(error);
				messageBox.open();		
			}
		});
	}
}
