package game.Enemies;

import game.PathPosition;
import game.Coordinate;
import java.awt.Graphics;
import java.awt.Image;
import  game.GameEntity;
import javax.imageio.ImageIO;

/**
 * This is an abstract superclass for an enemy in the game
 * 
 * @author basilvetas
 *
 */
public abstract class Enemy implements GameEntity
{
	/* instance variables */
	public PathPosition position;	// holds current position of enemy
	protected Image enemy;				// holds image of enemy
	private int anchorX;				// shifts position on x axis
	private int anchorY;				// shifts position on y axis
	private double speed; 			// increases or decreases advance speed
	private long health;				//máu
	private long armor;				//giáp
	private long reward;				//phần thưởng

	//Constructor
	protected Enemy(PathPosition position, Image enemy, int anchorX, int anchorY, double speed, long health, long armor, long reward){
		this.position = position;
		this.enemy = enemy;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.speed = speed;
		this.health = health;
		this.armor = armor;
		this.reward = reward;
	}

	 // Tiến về phía trước
	public void advance(){
		this.position.advance(10 + this.speed);	// advances position 10 units plus velocity
	}
	
	/**
	 * Draws the enemy to the screen
	 * 
	 * @param g
	 */
	public void draw(Graphics g)
	{
		// Draws Enemy object
		Coordinate c = position.getCoordinate();
		g.drawImage(enemy, c.x + anchorX, c.y + anchorY, null);
		
		// Draws dot on Enemy's (x, y) coordinates
		//g.setColor(Color.WHITE);
		//g.fillOval(c.x, c.y, 5, 5);
	}

	/**
	 *
	 * @return
	 */
	public PathPosition getPosition()
	{
		return position;
	}

}
