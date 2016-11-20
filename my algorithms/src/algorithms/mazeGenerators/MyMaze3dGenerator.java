package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public class MyMaze3dGenerator extends CommontMaze3dGenerator  {
	
	@Override
	public Maze3d generate(int x, int y, int z) throws IndexOutOfBoundsException {
		if (x<0 || y<0 || z<0)
			throw new IndexOutOfBoundsException("Arguments are not in range.");
		
		//variables
		Random rand = new Random();

		// build maze and initialize with only walls
		Maze3d maze3d = new Maze3d(x,y,z);
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				for(int k=0;k<z;k++){
					maze3d.setValue1(i, j, k);
				}
			}
		}
		//choose randomly start position
		Position st = new Position(rand.nextInt(maze3d.getLengthX()),rand.nextInt(maze3d.getLengthY()),rand.nextInt(maze3d.getLengthZ()));
		maze3d.setStartPosition(st);
		
        // iterate through direct neighbors of node
        ArrayList<Position> neighbors = new ArrayList<Position>();
        neighbors=addNeighbors(maze3d, neighbors, st);
       
         Position last=null;
        while(!neighbors.isEmpty()){
        	//remove from neighbors list a current node at random
        	Position cu = neighbors.remove((int)(Math.random()*neighbors.size()));
        	Position nx= cu.findOppositePosition(cu);
        	try{
        		// if both node and its next are walls
        		if((maze3d.getValue(cu.getX(), cu.getY(), cu.getZ())==1)&& (maze3d.getValue(nx.getX(), nx.getY(), nx.getZ())==1)){
        			//open path instead the walls	
        			maze3d.setValue0(cu);
    				maze3d.setValue0(nx);
    				
    				//store last node in order to mark it later
    				last=nx;
    				//iterate through direct neighbors of node, same as earlier
    		        neighbors=addNeighbors(maze3d, neighbors, nx);
    		       
        		}
        	}
        	catch (Exception e) {
				continue;
        	}
        }
        
        maze3d.setGoalPosition(last);
        return maze3d;
	}
	
	//add to the arrayList the neighbors of a specific position
	public ArrayList<Position> addNeighbors(Maze3d maze,ArrayList<Position> neighbors ,Position p)
	{
		 for(int i=-1;i<=1;i++){
	        	for(int j=-1;j<=1;j++){
	        		for(int k=-1;k<=1;k++){
	        			//check if its not the current position or diagonals, which is not neighbors
	        			if((i==0&&j==0&&k==0) || (i!=0&&j!=0&&k!=0) || (i!=0&&j!=0) || (i!=0&&k!=0) || (j!=0&&k!=0))
	        				continue;
	        			try{
	        				//check if its already a path
	        				if (maze.getValue(p.getX()+i, p.getY()+j, p.getZ()+k)==0)
	        					continue;
	        			}
	        			//ignore ArrayIndexOutOfBounds
	        			catch(Exception e){
	        				continue;
	        			}
	        			try{
	        				if(!neighbors.contains(p))//need to check!
	        					//add eligible points to frontier
	        					neighbors.add(new Position(p.getX()+i, p.getY()+j, p.getZ()+k, p));
	        			}
	        			catch(Exception e){
	        				continue;
	        			}	
	        		}
	        	}
		 }
		 return neighbors;															
	}
}