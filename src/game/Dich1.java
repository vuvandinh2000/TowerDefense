package game;

/**
 * This class creates a single alien enemy
 *
 * @author basilvetas
 */
public class Dich1 extends QuanDich
{
    /**
     * Constructor
     */
    Dich1(PathPosition p)
    {
        ImageLoader loader = ImageLoader.getLoader();
        this.enemy = loader.getImage("resources/Alien-Ship.png");
        this.position = p;
        this.anchorX = -20;
        this.anchorY = -20;
        this.velocity = 6;
    }

}
