package game.Enemies;

import game.PathPosition;
import game.Coordinate;
import java.awt.Graphics;
import java.awt.Image;
import  game.GameEntity;
import java.awt.Color;

public abstract class Enemy implements GameEntity
{
	/* instance variables */
	public PathPosition position;	// holds current position of enemy
	protected Image enemy;				// holds image of enemy
	private int anchorX;				// shifts position on x axis
	private int anchorY;				// shifts position on y axis
	private double speed; 			// increases or decreases advance speed


	//Constructor
	protected Enemy(PathPosition position, Image enemy, int anchorX, int anchorY, double speed){
		this.position = position;
		this.enemy = enemy;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.speed = speed;
	}

	 // Tiến về phía trước
	public void advance(){
		this.position.advance(10 + this.speed);	// advances position 10 units plus velocity
	}

	public void draw(Graphics g)
	{
		// Draws Enemy object
		Coordinate c = position.getCoordinate();
		g.drawImage(enemy, c.x + anchorX, c.y + anchorY, null);
	}

	public PathPosition getPosition()
	{
		return position;
	}

}
