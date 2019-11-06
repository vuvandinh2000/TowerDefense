package game;

/**
 * This class creates a single comet enemy
 * 
 * @author basilvetas
 */
public class Asteroid extends QuanDich
{
	/**
	 * Constructor
	 */
	Asteroid(PathPosition p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/asteroid.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 2;
	}
	
}