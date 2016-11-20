package algorithms.mazeGenerators;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * This class represent a position in 3D array
 * @author Moran
 *
 */
public class Position implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int z;
	private Position parent;

	// C'tor
	public Position(int x, int y, int z) {
		super();
		// if(x<0 || y<0 || z<0)
		// throw new IndexOutOfBoundsException("Arguments are not in range.");
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Position() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.parent = null;
	}

	// C'tor
	public Position(int x, int y, int z, Position parent) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.parent = parent;
	}

	// copy C'tor
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Position getParent() {
		return parent;
	}

	public void setParent(Position parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "," + z + "}";
	}

	// compareTo for compare the int: x\y\z
	public int compareTo(int num1, int num2) {
		if (num1 > num2)
			return 1;
		else if (num1 < num2)
			return -1;
		else
			return 0;
	}

	// return the next position,same direction from the parent
	public Position findOppositePosition(Position pos) {
		if (compareTo(pos.x, pos.parent.x) != 0)
			return new Position(pos.x + compareTo(pos.x, pos.parent.x), pos.y, pos.z, pos);
		if (compareTo(pos.y, pos.parent.y) != 0)
			return new Position(pos.x, pos.y + compareTo(pos.y, pos.parent.y), pos.z, pos);
		if (compareTo(pos.z, pos.parent.z) != 0)
			return new Position(pos.x, pos.y, pos.z + compareTo(pos.z, pos.parent.z), pos);
		return null;

	}

	@Override
	public boolean equals(Object obj) {
		Position p = (Position) obj;
		if (this.x == p.getX() && this.y == p.getY() && this.z == p.getZ())
			return true;
		return false;
	}
	
	//
	// public boolean equals(Position p){
	// if(this.x==p.getX()&&this.y==p.getY()&&this.z==p.getZ())
	// return true;
	// return false;
	// }

}
