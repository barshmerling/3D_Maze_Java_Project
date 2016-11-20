package guiView;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;
import algorithms.search.State;

/**
 * this class view the maze3d in the display window
 * 
 * @author BAR
 */
public class Maze3dGuiDisplayer extends Maze3dDisplayer {

	protected char axis;
	protected int index;
	protected Image imgBackround, imgPlayer, imgWin, imgTrace, imgGoal, imgWalls;
	protected int pos1, pos2;
	protected int goal1, goal2, goal3;
	protected Boolean bool;
	//protected Button hint;

	/**
	 * C'tor
	 * @param parent
	 * @param style
	 * @param parent
	 */
	public Maze3dGuiDisplayer(Composite parent, int style, char axis) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.axis = axis;
		//sets all the Images path 
		imgBackround = new Image(getDisplay(), "images/backround.jpg");
		imgPlayer = new Image(getDisplay(), "images/mickeyPlayer.png");
		imgWin = new Image(getDisplay(), "images/win2.jpg");	
		imgTrace = new Image(getDisplay(), "images/trace.png");
		imgGoal = new Image(getDisplay(), "images/MinnieGoal.png");
		imgWalls = new Image(getDisplay(), "images/walls.png");
		solution = null;
		// set the Canvas background
		setBackgroundImage(imgBackround);
		
		// adding some draw-object to display
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent event) {
				ArrayList<State<Position>> arrayListSolution = null;
				if(solution != null){
					bool = true;
					arrayListSolution = solution.getSolution();
				}else{
					bool = false; 
				}
				int width = getSize().x;
				int height = getSize().y;
				int[][] maze2d;
				
				if(maze3d != null && currentPos != null){
					switch(axis){
					case 'x':
						index = currentPos.getX();
						maze2d = maze3d.getCrossSectionByX(index);
						pos1 = currentPos.getY();
						pos2 = currentPos.getZ();
						goal1 = maze3d.getGoalPosition().getY();
						goal2 = maze3d.getGoalPosition().getZ();
						goal3 = maze3d.getGoalPosition().getX();
						break;
						
					case 'y':
						index = currentPos.getY();
						maze2d = maze3d.getCrossSectionByY(index);
						pos1 = currentPos.getX();
						pos2 = currentPos.getZ();
						goal1 = maze3d.getGoalPosition().getX();
						goal2 = maze3d.getGoalPosition().getZ();
						goal3 = maze3d.getGoalPosition().getY();
						break;
						
					case 'z':
						index = currentPos.getZ();
						maze2d = maze3d.getCrossSectionByZ(index);
						pos1 = currentPos.getX();
						pos2 = currentPos.getY();
						goal1 = maze3d.getGoalPosition().getX();
						goal2 = maze3d.getGoalPosition().getY();
						goal3 = maze3d.getGoalPosition().getZ();
						break;
						
					default:
						index = currentPos.getX();
						maze2d = maze3d.getCrossSectionByX(index);
						pos1 = currentPos.getY();
						pos2 = currentPos.getZ();
						goal1 = maze3d.getGoalPosition().getY();
						goal2 = maze3d.getGoalPosition().getZ();
						goal3 = maze3d.getGoalPosition().getX();
						break; 
					}
					State<Position> startPos = new State<Position>(maze3d.getStartPosition());
					int h = height / maze2d.length;
					int w = width / maze2d[0].length;
					if (arrayListSolution != null) {
						if ((bool = true)) {
							startPos = arrayListSolution.get(arrayListSolution.size() - 1);
						}
					}
					for (int i = 0; i < maze2d.length; i++) {
						for (int j = 0; j < maze2d[i].length; j++) {
							int x = i * h;
							int y = j * w;
							// drawing the walls 
							if (maze2d[i][j] != 0) {
								//drawImage(Image image, int srcX, int srcY, int srcWidth, int srcHeight, int destX, int destY, int destWidth, int destHeight)
								event.gc.drawImage(imgWalls, 0, 0, imgWalls.getBounds().width, imgWalls.getBounds().height, y, x, w, h);
							}
							// drawing the player on the current position 
							if (i == pos1 && j == pos2) {
								event.gc.drawImage(imgPlayer, 0, 0, imgPlayer.getBounds().width, imgPlayer.getBounds().height, y, x, w, h);
							}
							// drawing the target in the current goal position
							if (i == goal1 && j == goal2 && index == goal3) {
								event.gc.drawImage(imgGoal, 0, 0, imgGoal.getBounds().width, imgGoal.getBounds().height,y ,x, w, h);
							} 
							// if there is solution for the maze - drawing the whole solution by traces
							if (bool == true) {
								// arrayListSolution.size();
								State<Position> gPos = new State<Position>(
										arrayListSolution.get(arrayListSolution.size() - 1));
								switch (axis) {
								case 'x':
									gPos = new State<Position>(new Position(index, i, j));
									if (gPos.getState().getX() == index) {
										if (arrayListSolution.contains(gPos)
												&& !(gPos.getState().equals(maze3d.getGoalPosition())) && !(gPos
														.equals(arrayListSolution.get(arrayListSolution.size() - 1)))) {
											event.gc.drawImage(imgTrace, 0, 0, imgTrace.getBounds().width,
													imgTrace.getBounds().height, y, x, w, h);
										}
									}
									break;
								case 'y':
									gPos = new State<Position>(new Position(i, index, j));
									if (gPos.getState().getY() == index) {
										if (arrayListSolution.contains(gPos)
												&& !(gPos.getState().equals(maze3d.getGoalPosition()) && !(gPos.equals(
														arrayListSolution.get(arrayListSolution.size() - 1))))) {
											event.gc.drawImage(imgTrace, 0, 0, imgTrace.getBounds().width,
													imgTrace.getBounds().height, y, x, w, h);
										}
									}
									break;
								case 'z':
									gPos = new State<Position>(new Position(i, j, index));
									if (gPos.getState().getZ() == index) {
										if (arrayListSolution.contains(gPos)
												&& !(gPos.getState().equals(maze3d.getGoalPosition()) && !(gPos.equals(
														arrayListSolution.get(arrayListSolution.size() - 1))))) {
											event.gc.drawImage(imgTrace, 0, 0, imgTrace.getBounds().width,
													imgTrace.getBounds().height, y, x, w, h);
										}
									}
									break;
								}
							}
							// draw the win-image of the game
							if (currentPos.equals(maze3d.getGoalPosition())) {
								event.gc.drawImage(imgWin, 0, 0, imgWin.getBounds().width, imgWin.getBounds().height, 0, 0, getSize().x, getSize().y);
								// arrayListSolution = null;
							}
						}
					}
				}
			}
		});
	}
}