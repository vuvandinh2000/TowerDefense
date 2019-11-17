package game.Bullet;

import game.ImageLoader;
import game.Coordinate;

public class BinhThuong extends Bullet
{
    public BinhThuong(Coordinate pos, Coordinate target)
    {
        super(pos, target);
        this.picture = ImageLoader.getLoader().getImage("resources/rocket.png");
    }
}
