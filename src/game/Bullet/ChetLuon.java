package game.Bullet;

import game.Effect;
import game.ImageLoader;
import game.Coordinate;

/**
 * Game effect when the Black hole attacks 
 * 
 * @author basilvetas
 */
public class ChetLuon extends Bullet
{
	public ChetLuon(Coordinate pos, Coordinate target)
	{
		super(pos, target);

		this.picture = ImageLoader.getLoader().getImage("resources/bomb.png");
	}	
}
