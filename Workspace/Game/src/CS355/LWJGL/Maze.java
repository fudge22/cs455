package CS355.LWJGL;

import java.util.ArrayList;

public class Maze {
	public ArrayList<Wall> Walls = new ArrayList<Wall>();
	
	public Maze() {
		//floor
		Walls.add(new Wall(new Point3D(300,0,300),new Point3D(300,0,-300),new Point3D(-300,0,-300),new Point3D(-300,0,300),new Color3D(.6, .5,.3)));
		
		//outer wall
		Walls.add(new Wall(new Point3D(300,0,300),new Point3D(300,0,-300),new Point3D(300,80,-300),new Point3D(300,80,300), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-300,0,-300),new Point3D(-300,0,300),new Point3D(-300,80,300),new Point3D(-300,80,-300),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(300,0,-300),new Point3D(-300,0,-300),new Point3D(-300,80,-300),new Point3D(300,80,-300),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(300,0,300),new Point3D(-300,0,300),new Point3D(-300,80,300),new Point3D(300,80,300),new Color3D(0, .4,0)));
		
		
		//vertical walls
		Walls.add(new Wall(new Point3D(260,0,300),new Point3D(260,0,-40),new Point3D(260,80,-40),new Point3D(260,80,300), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(260,0,-80),new Point3D(260,0,-140),new Point3D(260,80,-140),new Point3D(260,80,-80), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(260,0,-180),new Point3D(260,0,-260),new Point3D(260,80,-260),new Point3D(260,80,-180), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(220,0,180),new Point3D(220,0,-80),new Point3D(220,80,-80),new Point3D(220,80,180), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(220,0,-120),new Point3D(220,0,-220),new Point3D(220,80,-220),new Point3D(220,80,-120), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(180,0,180),new Point3D(180,0,140),new Point3D(180,80,140),new Point3D(180,80,180), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(180,0,100),new Point3D(180,0,-180),new Point3D(180,80,-180),new Point3D(180,80,100), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(140,0,260),new Point3D(140,0,220),new Point3D(140,80,220),new Point3D(140,80,260), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(140,0,140),new Point3D(140,0,100),new Point3D(140,80,100),new Point3D(140,80,140), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(140,0,60),new Point3D(140,0,-140),new Point3D(140,80,-140),new Point3D(140,80,60), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(100,0,100),new Point3D(100,0,-100),new Point3D(100,80,-100),new Point3D(100,80,100), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(60,0,180),new Point3D(60,0,140),new Point3D(60,80,140),new Point3D(60,80,180), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(60,0,20),new Point3D(60,0,-60),new Point3D(60,80,-60),new Point3D(60,80,20), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-60,0,140),new Point3D(-60,0,100),new Point3D(-60,80,100),new Point3D(-60,80,140), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-60,0,60),new Point3D(-60,0,-60),new Point3D(-60,80,-60),new Point3D(-60,80,60), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-100,0,300),new Point3D(-100,0,260),new Point3D(-100,80,260),new Point3D(-100,80,300), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-100,0,100),new Point3D(-100,0,-60),new Point3D(-100,80,-60),new Point3D(-100,80,100), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-100,0,-300),new Point3D(-100,0,-260),new Point3D(-100,80,-260),new Point3D(-100,80,-300), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-140,0,220),new Point3D(-140,0,180),new Point3D(-140,80,180),new Point3D(-140,80,220), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-140,0,140),new Point3D(-140,0,-140),new Point3D(-140,80,-140),new Point3D(-140,80,140), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-180,0,180),new Point3D(-180,0,-60),new Point3D(-180,80,-60),new Point3D(-180,80,180), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-180,0,-100),new Point3D(-180,0,-180),new Point3D(-180,80,-180),new Point3D(-180,80,-100), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-220,0,220),new Point3D(-220,0,180),new Point3D(-220,80,180),new Point3D(-220,80,220), new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-220,0,140),new Point3D(-220,0,-220),new Point3D(-220,80,-220),new Point3D(-220,80,140), new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-260,0,260),new Point3D(-260,0,-260),new Point3D(-260,80,-260),new Point3D(-260,80,260), new Color3D(0, .4,0)));
		
		//horizontal walls
		Walls.add(new Wall(new Point3D(-260,0,-260),new Point3D(-180,0,-260),new Point3D(-180,80,-260),new Point3D(-260,80,-260),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-140,0,-260),new Point3D(-100,0,-260),new Point3D(-100,80,-260),new Point3D(-140,80,-260),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-60,0,-260),new Point3D(260,0,-260),new Point3D(260,80,-260),new Point3D(-60,80,-260),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-220,0,-220),new Point3D(220,0,-220),new Point3D(220,80,-220),new Point3D(-220,80,-220),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-180,0,-180),new Point3D(0,0,-180),new Point3D(0,80,-180),new Point3D(-180,80,-180),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(40,0,-180),new Point3D(180,0,-180),new Point3D(180,80,-180),new Point3D(40,80,-180),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-140,0,-140),new Point3D(140,0,-140),new Point3D(140,80,-140),new Point3D(-140,80,-140),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(300,0,-140),new Point3D(260,0,-140),new Point3D(260,80,-140),new Point3D(300,80,-140),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-100,0,-100),new Point3D(100,0,-100),new Point3D(100,80,-100),new Point3D(-100,80,-100),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(260,0,-80),new Point3D(220,0,-80),new Point3D(220,80,-80),new Point3D(260,80,-80),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(260,0,-80),new Point3D(220,0,-80),new Point3D(220,80,-80),new Point3D(260,80,-80),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-60,0,-60),new Point3D(60,0,-60),new Point3D(60,80,-60),new Point3D(-60,80,-60),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(60,0,20),new Point3D(100,0,20),new Point3D(100,80,20),new Point3D(60,80,20),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-60,0,60),new Point3D(60,0,60),new Point3D(60,80,60),new Point3D(-60,80,60),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-60,0,100),new Point3D(100,0,100),new Point3D(100,80,100),new Point3D(-60,80,100),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-180,0,100),new Point3D(-140,0,100),new Point3D(-140,80,100),new Point3D(-180,80,100),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(140,0,100),new Point3D(180,0,100),new Point3D(180,80,100),new Point3D(140,80,100),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-20,0,140),new Point3D(140,0,140),new Point3D(140,80,140),new Point3D(-20,80,140),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-60,0,140),new Point3D(-140,0,140),new Point3D(-140,80,140),new Point3D(-60,80,140),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(180,0,140),new Point3D(220,0,140),new Point3D(220,80,140),new Point3D(180,80,140),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-260,0,140),new Point3D(-220,0,140),new Point3D(-220,80,140),new Point3D(-260,80,140),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-140,0,180),new Point3D(80,0,180),new Point3D(80,80,180),new Point3D(-140,80,180),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(120,0,180),new Point3D(180,0,180),new Point3D(180,80,180),new Point3D(120,80,180),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-220,0,220),new Point3D(220,0,220),new Point3D(220,80,220),new Point3D(-220,80,220),new Color3D(0, .4,0)));
		
		Walls.add(new Wall(new Point3D(-220,0,260),new Point3D(-100,0,260),new Point3D(-100,80,260),new Point3D(-220,80,260),new Color3D(0, .4,0)));
		Walls.add(new Wall(new Point3D(-60,0,260),new Point3D(220,0,260),new Point3D(220,80,260),new Point3D(-60,80,260),new Color3D(0, .4,0)));
	}
}
