package model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Astar;
import algorithms.search.Bfs;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * This class extends AbsModel class and implements all the methods of MODEL
 * interface
 * 
 * @author Moran
 */
public class MyModel extends AbsModel {
	
	private StringBuilder stringBuilder;
	
	/**
	 * C'tor
	 */
	public MyModel() {
		this.stringBuilder=new StringBuilder();
	}
	
	/**
	 * This method generate Maze3d
	 * @param name
	 * @param x
	 * @param y
	 * @param z
	 */
	@Override
	public void generateMaze(String name, int x, int y, int z) {
		threadPool.execute(new Runnable(){

			@Override
			public void run() {
				Maze3d maze3d=new MyMaze3dGenerator().generate(x, y, z);
				hashMaze.put(name, maze3d);
				controller.displayMessage("maze " + name + " is ready");
			}
		});
	}
	
	/**
	 * This method display a maze3d
	 * @param name
	 */
	@Override
	public void displayMaze(String name) {
		Maze3d maze3d = hashMaze.get(name);
		if (maze3d == null) {
			controller.displayMessage("Maze " + name + " isn't exist!");
		} else {
			try {
				controller.displayByteArray(maze3d.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method gets cross section by x/y/z
	 * @param name
	 * @param by
	 * @param index
	 */
	@Override
	public void displayCrossSection(String name, String by, int index) {
		Maze3d maze3d = hashMaze.get(name);
		int[][] maze2d=null;
		if (maze3d==null){
			controller.displayMessage("Maze " + name + " is not exist");
			return;
		}
		try{
			switch (by){
			case "x":
			case "X": 
				maze2d = maze3d.getCrossSectionByX(index);
				break;
			case "y":
			case "Y":
				maze2d = maze3d.getCrossSectionByY(index);
				break;
			case "z":
			case "Z":
				maze2d = maze3d.getCrossSectionByZ(index);
				break;
			default: this.controller.displayMessage("Invalid input!");
			}
		}catch (IndexOutOfBoundsException e){
			controller.displayMessage("Invalid Index!");
			return;
		}
		String maze2dPrint = "";
		for (int i=0;i<maze2d.length;i++){
			for(int j=0;j<maze2d[i].length;j++){
				maze2dPrint = maze2dPrint + String.valueOf(maze2d[i][j]+ " ");
			}
			maze2dPrint = maze2dPrint + "\n";
		}
		controller.displayMessage(maze2dPrint);
	}

	/**
	 * This method save a maze into a file.
	 * @param name
	 * @param fileName
	 */
	@Override
	public void saveMaze(String name, String fileName) {
		Maze3d maze3d = hashMaze.get(name);
		if (maze3d == null)
			controller.displayMessage("Maze " + name + " is not exsist");
		else {
			try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
				out.write(maze3d.toByteArray());
				out.close();
				controller.displayMessage("Maze " + name + " is saved successfully in the file " + fileName);
			} catch (FileNotFoundException e) {
				controller.displayMessage("The " + fileName + " is not found");
			} catch (IOException e) {
				controller.displayMessage("Cannot collapse the " + name);
			}
		}
	}

	/**
	 * This method load a maze from a file.??
	 * @param name
	 * @param fileName
	 */
	@Override
	public void loadMaze(String name, String fileName) {
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte[] bArr = new byte[50000];
			//Reads some number of bytes from the input stream and stores them into the buffer array bArr
			int numByte = in.read(bArr);
			in.read(bArr);
			in.close();
			byte[] newbArr = new byte[numByte];
			for (int i = 0; i < newbArr.length; i++) {
				newbArr[i] = bArr[i];
			}
			Maze3d maze3d = new Maze3d(bArr);
			hashMaze.put(name, maze3d);
			controller.displayMessage("Maze " + name + " is loaded from " + fileName + " file");
			in.close();
		} catch (FileNotFoundException e) {
			controller.displayMessage("The file " + fileName + " is not found");
		} catch (IOException e) {
			controller.displayMessage("Error with the new maze");
		}
	}

	/**
	 * This method display the size of the maze in the memory
	 * @param name
	 */
	@Override
	public void mazeSize(String name) {
		Maze3d maze3d = hashMaze.get(name);
		if(maze3d == null){
			controller.displayMessage("Maze " + name + " is not exist");
		}else{
			int sizeX = maze3d.getLengthX()*4;
			int sizeY = maze3d.getLengthY()*4;
			int sizeZ = maze3d.getLengthZ()*4;
			int size = sizeX + sizeY + sizeZ + 36;
			controller.displayMessage("The size of " + name + " maze in the memory is: " + size);
		}
	}

	/**
	 * This method display the size of a maze in the file
	 * @param name
	 */
	@Override
	public void fileSize(String name) {
		Maze3d maze3d = hashMaze.get(name);
		if(maze3d == null){
			controller.displayMessage("Maze " + name + " is not exist");
		}else{
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			MyCompressorOutputStream compressorOut = new MyCompressorOutputStream(byteArrayOut);
			try {
				compressorOut.write(maze3d.toByteArray());
				controller.displayMessage("The size of " + name + " maze in the file is: " + byteArrayOut.size());
			} catch (IOException e) {
				controller.displayMessage("Could not write the " + name + " maze to a file");
			}
		}
	}

	/**
	 * This method solve the maze by a chossen algorithm
	 */
	@Override
	public void solveMaze(String name, String algorithm) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				Maze3d maze3d = hashMaze.get(name);
				if (maze3d == null) {
					controller.displayMessage("Maze " + name + " is not exist");
				} else {
					Searcher<Position> myAlgorithm;
					Searchable<Position> mazeSearchable = new MazeSearch(maze3d);
					switch (algorithm) {
					case "BFS":
						myAlgorithm = new Bfs<Position>();
						break;
					case "AirDistance":
						myAlgorithm = new Astar<Position>(new MazeAirDistance());
						break;
					case "ManhattanDistance":
						myAlgorithm = new Astar<Position>(new MazeManhattanDistance());
						break;
					default:
						controller.displayMessage("Invalid algorithm");
						return;
					}
					hashSolution.put(name, myAlgorithm.search(mazeSearchable));
					controller.displayMessage("Solution for " + name + " is ready");
				}
			}
		});
	}

	/**
	 * This method display the solution of chosen algorithm
	 * @param name
	 */
	@Override
	public void displaySolution(String name) {
		// get solution from hashMap by key (name).
		Solution<Position> mySolution = hashSolution.get(name);
		if (mySolution == null)
			controller.displayMessage("Solution for " + name + " isn not exist");
		else {
			// temp arrayList to get the solution.
			ArrayList<State<Position>> arraySolution = mySolution.getSolution();
			// concat all solution steps.
			for (int i = 0; i < arraySolution.size(); i++)
				this.stringBuilder.append(arraySolution.get(i));
			// print solution
			controller.displayMessage("The solution for maze " + name + " is:\n" + stringBuilder.toString());
		}
	}

	/**
	 * This method close the project orderly
	 */
	@Override
	public void exit() {
		controller.displayMessage("The system is shutting down");
		threadPool.shutdown();
		try {
			while (!(threadPool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	}
}