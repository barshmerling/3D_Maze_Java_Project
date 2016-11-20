package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMaze3dGenerator extends CommontMaze3dGenerator{

	@Override
	public Maze3d generate(int x, int y, int z)throws IndexOutOfBoundsException{
		if( x<0||y<0||z<0)
			throw new IndexOutOfBoundsException("Arguments are not in range.");
		//Variables
		Maze3d maze3d=new Maze3d(x, y, z);
		Random rand=new Random();
		
		//set all cells to 1 (wall)
		for(int i=0;i<x;i++)
			for(int j=0;j<y;j++)
				for(int k=0;k<z;k++)
					maze3d.setValue1(i, j, k);

		//choose randomly the length of the path (the max path is x*y*z+1)
		int LenPath=rand.nextInt(maze3d.getLengthX()*maze3d.getLengthY()*maze3d.getLengthZ()+1);
		
		//choose randomly position
		Position st=new Position(rand.nextInt(maze3d.getLengthX()),rand.nextInt(maze3d.getLengthY()),rand.nextInt(maze3d.getLengthZ()));
		maze3d.setStartPosition(st);
		
		//using copy C'tor
		Position p=new Position(st);
		
		//build random path
		while(LenPath!=0){
			int choosenDirection=new Random().nextInt(Direction.values().length);
			switch (Direction.values()[choosenDirection]){
			case UP:
				if(p.getX()+1<maze3d.getLengthX()){
					p.setX(p.getX()+1);
					maze3d.setValue0(p);
				}
				else continue;
				break;
			case DOWN:
				if(p.getX()-1>=0){
					p.setX(p.getX()-1);
					maze3d.setValue0(p);
				}
				else continue;
				break;
			case RIGHT:
				if(p.getY()+1<maze3d.getLengthY()){
					p.setY(p.getY()+1);
					maze3d.setValue0(p);
				}
				else continue;
				break;
			case LEFT:
				if(p.getY()-1>=0){
					p.setY(p.getY()-1);
					maze3d.setValue0(p);
				}
				else continue;
				break;
			case FORWARD:
				if(p.getZ()+1<maze3d.getLengthZ()){
					p.setZ(p.getZ()+1);
					maze3d.setValue0(p);
				}
				else continue;
				break;
			case BACKWARD:
				if(p.getZ()-1>=0){
					p.setZ(p.getZ()-1);
					maze3d.setValue0(p);
				}
				else continue;
				break;
				default:
					break;
			}
		LenPath--;	
		maze3d.setGoalPosition(p);
		}
		
		for(int i=0;i<x;i++)
			for(int j=0;j<y;j++)
				for(int k=0;k<z;k++){
					if(maze3d.getValue(i, j, k)==1)
						maze3d.setValue(i, j, k,rand.nextInt(2));
				}				
		return maze3d; 
	}
}
