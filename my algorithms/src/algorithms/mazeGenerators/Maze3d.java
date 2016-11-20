package algorithms.mazeGenerators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class create a maze which includes a 3D int array, Position of start
 * and goal.
 * @author Moran
 *
 */
public class Maze3d implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int[][][] maze3d;
	private int lengthX;// floor
	private int lengthY;// row
	private int lengthZ;// Column
	private Position startPosition;
	private Position goalPosition;

	//Default C'tor
	public Maze3d(){
	}
	
	
	// C'tor
	public Maze3d(int lengthX, int lengthY, int lengthZ) {
		super();
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
		this.maze3d = new int[lengthX][lengthY][lengthZ];
	}

	// copy C'tor
	public Maze3d(Maze3d myMaze3d) {
		this.lengthX = myMaze3d.lengthX;
		this.lengthY = myMaze3d.lengthY;
		this.lengthZ = myMaze3d.lengthZ;
		this.startPosition = myMaze3d.startPosition;
		this.goalPosition = myMaze3d.goalPosition;
		this.maze3d = new int[myMaze3d.lengthX][myMaze3d.lengthY][myMaze3d.lengthZ];
	}

	// getters & setters
	public int[][][] getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(int[][][] maze3d) {
		this.maze3d = maze3d;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getLengthX() {
		return lengthX;
	}

	public void setLengthX(int lengthX) {
		this.lengthX = lengthX;
	}

	public int getLengthY() {
		return lengthY;
	}

	public void setLengthY(int lengthY) {
		this.lengthY = lengthY;
	}

	public int getLengthZ() {
		return lengthZ;
	}

	public void setLengthZ(int lengthZ) {
		this.lengthZ = lengthZ;
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
		maze3d[startPosition.getX()][startPosition.getY()][startPosition.getZ()] = 0;
	}

	public Position getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}

	// get Possible Moves
	public String[] getPossibleMoves(Position p) {
		ArrayList<String> listPossibleMoves = new ArrayList<String>();
		String[] PossibleMoves;
		// X

		// check if there is a possibility to move up
		if ((p.getX() + 1 < lengthX) && (this.maze3d[p.getX() + 1][p.getY()][p.getZ()] == 0))
			listPossibleMoves.add("Up");
		// check if there is a possibility to move right down
		if ((p.getX() - 1 >= 0) && (this.maze3d[p.getX() - 1][p.getY()][p.getZ()] == 0))
			listPossibleMoves.add("Down");
		// Y

		// check if there is a possibility to move right
		if ((p.getY() + 1 < this.lengthY) && (this.maze3d[p.getX()][p.getY() + 1][p.getZ()] == 0))
			listPossibleMoves.add("Right");
		// check if there is a possibility to move left
		if ((p.getY() - 1 >= 0) && (this.maze3d[p.getX()][p.getY() - 1][p.getZ()] == 0))
			listPossibleMoves.add("Left");
		// Z
		// check if there is a possibility to move forward
		if ((p.getZ() + 1 < this.lengthZ) && (this.maze3d[p.getX()][p.getY()][p.getZ() + 1] == 0))
			listPossibleMoves.add("Forward");
		// check if there is a possibility to move backward
		if ((p.getZ() - 1 >= 0) && (this.maze3d[p.getX()][p.getY()][p.getZ() - 1] == 0))
			listPossibleMoves.add("Backward");

		// convert ArrayList to string array
		PossibleMoves = new String[listPossibleMoves.size()];
		return listPossibleMoves.toArray(PossibleMoves);
	}

	// get Cross Section By X
	public int[][] getCrossSectionByX(int x) {
		int[][] maze2d;
		if ((x >= 0) && (x < this.lengthX)) {
			maze2d = new int[this.lengthY][this.lengthZ];
			for (int i = 0; i < this.lengthY; i++) {
				for (int j = 0; j < this.lengthZ; j++)
					maze2d[i][j] = this.maze3d[x][i][j];
			}
			return maze2d;
		} else
			throw new IndexOutOfBoundsException("Invalid Input");
	}

	// get Cross Section By Y
	public int[][] getCrossSectionByY(int y) {
		int[][] maze2d;
		if ((y >= 0) && (y < this.lengthY)) {
			maze2d = new int[this.lengthX][this.lengthZ];
			for (int i = 0; i < this.lengthX; i++) {
				for (int j = 0; j < this.lengthZ; j++)
					maze2d[i][j] = this.maze3d[i][y][j];
			}
			return maze2d;
		} else
			throw new IndexOutOfBoundsException("Invalid Input");
	}

	// get Cross Section By Z
	public int[][] getCrossSectionByZ(int z) {
		int[][] maze2d;
		if ((z >= 0) && (z < this.lengthZ)) {
			maze2d = new int[this.lengthX][this.lengthY];
			for (int i = 0; i < this.lengthX; i++) {
				for (int j = 0; j < this.lengthY; j++)
					maze2d[i][j] = this.maze3d[i][j][z];
			}
			return maze2d;
		} else
			throw new IndexOutOfBoundsException("Invalid Input");
	}

	// set 0 in specific position
	public void setValue0(Position p) {
		maze3d[p.getX()][p.getY()][p.getZ()] = 0;
	}

	// set 0 in specific position by get {x,y,z}
	public void setValue0(int x, int y, int z) {
		maze3d[x][y][z] = 0;
	}

	// set 1 in specific position
	public void setValue1(Position p) {
		maze3d[p.getX()][p.getY()][p.getZ()] = 1;
	}

	// set 1 in specific position by get {x,y,z}
	public void setValue1(int x, int y, int z) {
		maze3d[x][y][z] = 1;
	}

	public int getValue(int x, int y, int z) {
		return maze3d[x][y][z];

	}

	// set 1/0 in specific position
	public void setValue(int x, int y, int z, int val) {
		maze3d[x][y][z] = val;
	}

	// print final maze
	public void printMaze() {
		for (int i = 0; i < this.lengthX; i++) {
			for (int j = 0; j < this.lengthY; j++) {
				for (int k = 0; k < this.lengthZ; k++) {
					System.out.print(maze3d[i][j][k]);
				}
				System.out.println();
			}
			System.out.println("*******************************");
		}
	}

	/**
	 * compress the array to ByteArray
	 * 
	 * @return
	 */
	public byte[] toByteArray() throws IOException {
		// variables
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//A data output stream lets an application write primitive Java data types to an output stream in a portable way
		DataOutputStream data = new DataOutputStream(out);

		// writes maze sizes
		data.writeInt(this.lengthX);
		data.writeInt(this.lengthY);
		data.writeInt(this.lengthZ);

		// writes start position
		data.writeInt(this.startPosition.getX());
		data.writeInt(this.startPosition.getY());
		data.writeInt(this.startPosition.getZ());

		// writes goal position
		data.writeInt(this.goalPosition.getX());
		data.writeInt(this.goalPosition.getY());
		data.writeInt(this.goalPosition.getZ());

		// writes the maze
		for (int i = 0; i < this.lengthX; i++) {
			for (int j = 0; j < this.lengthY; j++) {
				for (int t = 0; t < this.lengthZ; t++) {
					data.write(maze3d[i][j][t]);
				}
			}
		}
		return out.toByteArray();
	}

	/**
	 * C'tor for decomprassor. gets an array of bytes and convert it to maze3d
	 * 
	 * @param byteArray
	 */
	public Maze3d(byte[] byteArray) throws IOException {
		// variables
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
		DataInputStream data = new DataInputStream(in);

		// decomprassor the size of the maze
		this.lengthX = ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 0, 4)).getInt();
		this.lengthY = ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 4, 8)).getInt();
		this.lengthZ = ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 8, 12)).getInt();

		// decomprassor the startPosition
		this.startPosition = new Position(ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 12, 16)).getInt(),
				ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 16, 20)).getInt(),
				ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 20, 24)).getInt());

		// decomprassor the goalPosition
		this.goalPosition = new Position(ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 24, 28)).getInt(),
				ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 28, 32)).getInt(),
				ByteBuffer.wrap(Arrays.copyOfRange(byteArray, 32, 36)).getInt());

		// decomprassor the maze
		this.maze3d = new int[this.lengthX][this.lengthY][this.lengthZ];
		int index = 36; // the index where we stop in the ByteArray
		for (int i = 0; i < this.lengthX; i++) {
			for (int j = 0; j < this.lengthY; j++) {
				for (int k = 0; k < this.lengthZ; k++) {
					this.maze3d[i][j][k] = (int) byteArray[index];
					index++;
				}
			}
		}
	}

	/**
	 * override equals function for equal between 2 mazes
	 */
	public boolean equals(Object obj) {
		Maze3d temp = (Maze3d) obj;
		if ((this.lengthX != temp.getLengthX()) || (this.lengthY != temp.getLengthY())
				|| (this.lengthZ != temp.getLengthZ()))
			return false;
		else {
			if (!(this.startPosition.equals(temp.getStartPosition()))
					|| !(this.goalPosition.equals(temp.getGoalPosition())))
				return false;
			else {
				for (int i = 0; i < this.lengthX; i++) {
					for (int j = 0; j < this.lengthY; j++) {
						for (int k = 0; k < this.lengthZ; k++) {
							if (this.maze3d[i][j][k] != temp.getMaze3d()[i][j][k])
								return false;
						}
					}
				}
			}
		}
		return true;
	}
}
