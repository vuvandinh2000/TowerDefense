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
	
	public abstract void draw(Graphics g);

	public abstract void setPosition(Coordinate c);
	
	public abstract void interact(GameField game, double deltaTime);
}





