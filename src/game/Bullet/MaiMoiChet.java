package game.Bullet;

import game.Coordinate;
import game.ImageLoader;

/**
 * Game effect when the Black hole attacks 
 * 
 * @author basilvetas
 */
public class MaiMoiChet extends Bullet
{
	public MaiMoiChet(Coordinate pos, Coordinate target)
	{
		super(pos, target);

		this.picture = ImageLoader.getLoader().getImage("resources/bullet.png");
	}	
}
