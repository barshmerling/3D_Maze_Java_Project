
package guiView;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * This class extends from Canvas and having basic parameters which define a maze game 
 * @author BAR
 *@param maze3d 
 *@param currentPos
 *@param solution
 */
public class Maze3dDisplayer extends Canvas {

	protected Maze3d maze3d;
	protected Position currentPos;
	protected Solution<Position> solution;

	/**
	 * C'tor
	 * @param parent
	 * @param style
	 */
	public Maze3dDisplayer(Composite parent, int style) {
		super(parent, style);
		this.currentPos = new Position(0,0,0);
	}

	/**
	 * getters & setters
	 * @return
	 */
	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
	}

	public Position getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(Position currentPos) {
		this.currentPos = currentPos;
	}

	public Solution<Position> getSolution() {
		return solution;
	}

	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
		
	}
}
	