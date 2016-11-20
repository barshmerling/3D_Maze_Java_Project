package guiView;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import guiProperties.MessageWindow;
import presenter.Command;
import presenter.Properties;

/**
 * This class allocate all the listeners from maze3dWindow and extends AbstractGuiView class.
 * @author Moran
 */
public class Maze3dGuiView extends AbstractGuiView {
	
	protected Maze3dWindow maze3dWindow;
	protected Properties properties;
	
	/**
	 * C'tor
	 * @param title
	 * @param width
	 * @param height
	 * @param in
	 * @param out
	 */
	public Maze3dGuiView(String title, int width, int height, BufferedReader in, PrintWriter out) {
		super(in, out);
		maze3dWindow = new Maze3dWindow(title, width, height);
		this.properties = new Properties();
		
		//set the generateListner
		maze3dWindow.setGenerateListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate 3d maze ");
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// set the solveListner 
		maze3dWindow.setSolveListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("solve ");				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// set the saveListner
		maze3dWindow.setSaveListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String file = maze3dWindow.getFilename();
				setChanged();
				notifyObservers("save " + file);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// set the loadListener
		maze3dWindow.setLoadListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String file = maze3dWindow.getFilename();
				if (file == null){
					display("No file is selected");
					return;
				}
				String[] splitFileString = (file.split("/"));
				setChanged();
				notifyObservers("load "+ file +" "+ splitFileString[splitFileString.length-1]);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//set the exitListener
		maze3dWindow.setExitListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze3dWindow.shell.dispose();	
			}	
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//set the disposeExit
		maze3dWindow.setDisposeExit(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				setChanged();
				notifyObservers("exit");
			}
		});
		
		// set the propertiesListener 
		maze3dWindow.setPropertiesListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						MessageWindow messageWindow = new MessageWindow("Properties Game" , 400, 562);
						messageWindow.run();
						Properties tempProp = new Properties();
						try{
							XMLDecoder xml = new XMLDecoder(new BufferedInputStream(new FileInputStream("Properties.xml")));
							tempProp = (Properties) xml.readObject();
							xml.close();
							setChanged();
							notifyObservers(tempProp);
						} catch (FileNotFoundException e){
							e.printStackTrace();
						}
					}	
				}).start();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		// set the keyListener - player movement
		maze3dWindow.setKeyboardListner(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent key){
				switch(key.keyCode){
					case SWT.PAGE_UP:
						setChanged();
						notifyObservers("move up");
						System.out.println("up");
						break;
					case SWT.PAGE_DOWN:
						setChanged();
						notifyObservers("move down");
						System.out.println("down");
						break;
					case SWT.ARROW_UP:
						setChanged();
						notifyObservers("move left");
						System.out.println("forward");
						break;
					case SWT.ARROW_DOWN:
						setChanged();
						notifyObservers("move right");
						System.out.println("backward");
						break;
					case SWT.ARROW_RIGHT:
						setChanged();
						notifyObservers("move forward");
						System.out.println("right");
						break;
					case SWT.ARROW_LEFT:
						setChanged();
						notifyObservers("move backward");
						System.out.println("left");
						break;
					default: 
						break;
				}
			}
		});
	}

	/**
	 * this method start the game by the GUI 
	 */
	@Override
	public void start() {
		maze3dWindow.run();
	}
	/**
	 * this method display a massage
	 */
	@Override
	public void display(String message) {
		maze3dWindow.displayError(message);
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		maze3dWindow.setSolution(solution);
	}
	@Override
	public void displayMaze(Maze3d maze) {
		maze3dWindow.setMaze3d(maze);
	}

	@Override
	public void setProperties(Properties prop) {
		if (properties.getChooseView() != null)
			if (!properties.getChooseView().equals(prop.getChooseView())) {
				setChanged();
				notifyObservers("replace userInterface");
			} else
				this.properties = prop;
	}

	@Override
	public void displayStringArray(String[] arr) {}

	@Override
	public void displayCrossSectionBy(int[][] maze2d) {}

	@Override
	public void setCommand(HashMap<String, Command> commandMap) {}
	
	@Override
	public void displayPosition(Position position) {
		maze3dWindow.setCurrentPos(position);
	}

	@Override
	public void exit() {}
}
