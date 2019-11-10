package game.Bullet;

import game.Effect;
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
		// Loads star dust image
		ImageLoader loader = ImageLoader.getLoader();
		this.picture = loader.getImage("resources/star_dust.png");
		
		// X and Y position of Effect
		this.posX = pos.x;
		this.posY = pos.y;		
		
		// X and Y position of target enemy
		this.velocityX = target.x - this.posX;
		this.velocityY = target.y - this.posY;
		
		// Sets time to 0
		this.ageInSeconds = 0;
	}	
}
