package game.Bullet;

import game.Effect;
import game.ImageLoader;
import game.Coordinate;

/**
 * Game effect when the Black hole attacks
 *
 * @author basilvetas
 */
public class BinhThuong extends Bullet
{
    public BinhThuong(Coordinate pos, Coordinate target)
    {
        // Loads star dust image
        ImageLoader loader = ImageLoader.getLoader();
        this.picture = loader.getImage("resources/sun_spot.png");

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
