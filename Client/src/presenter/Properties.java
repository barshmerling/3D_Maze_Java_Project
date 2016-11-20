package presenter;

import java.io.Serializable;
/**
 * this class implements 'Serializable' and define properties of a maze game 
 * @author BAR
 */
public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;
	private int x,y,z;
	private int numOfThreads;
	private String algo;
	private String typeOfMaze;
	private String mazeName;
	private String chooseView;
	private char axis;
	
	/**
	 * C'tor
	 */
	public Properties(){
		super();
	}
	/**
	 * this function define default properties
	 */
	public void defaultProperties(){
		this.x = 3;
		this.y = 3;
		this.z = 3;
		this.numOfThreads = 5;
		this.algo = "AirDistance";
		this.typeOfMaze = "MyMaze3dGenerator";
		this.mazeName = "DefaultMaze";
		this.chooseView="Gui";
		this.axis='x';
	}
	
	/**
	 * setters & getters
	 */
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
	public int getNumOfThreads() {
		return numOfThreads;
	}
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	public String getAlgo() {
		return algo;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	public String getTypeOfMaze() {
		return typeOfMaze;
	}
	public void setTypeOfMaze(String typeOfMaze) {
		this.typeOfMaze = typeOfMaze;
	}
	public String getMazeName() {
		return mazeName;
	}
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}
	public String getChooseView() {
		return chooseView;
	}
	public void setChooseView(String chooseView) {
		this.chooseView = chooseView;
	}
	public char getAxis() {
		return axis;
	}
	public void setAxis(char axis) {
		this.axis = axis;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

