package game.Towers;

import java.awt.Graphics;
import java.awt.Image;
import game.Coordinate;
import game.GameField;
import game.GameTile;

/**
 * This is an abstract superclass for a tower in the game
 * 
 * @author basilvetas
 *
 */
public interface Tower extends GameTile
{
	/* instance variables */
//	protected Coordinate position;	// holds position of tower
//	protected int anchorX;			// shifts X coordinate
//	protected int anchorY;			// shifts Y coordinate
//	private Image tower; 			// holds tower image
//	protected double timeSinceLastFire;// time since last effect was fired
//	private double shootSpeed;
//	private long shootRange;		//tầm bắn
//	private long damage;			//sát thương

//	protected Tower(Coordinate position, Image tower, int anchorX, int anchorY, double shootSpeed, long shootRange, long damage){
//		this.position = position;
//		this.tower = tower;
//		this.anchorX = anchorX;
//		this.anchorY = anchorY;
//		this.shootRange = shootRange;
//		this.shootSpeed = shootSpeed;
//		this.damage = damage;
//	}
	
	public abstract void draw(Graphics g);

	public abstract void setPosition(Coordinate c);
	
	public abstract void interact(GameField game, double deltaTime);
}





