package game.Bullet;

import game.*;
import java.awt.*;

public abstract class Bullet implements GameEntity {
    public int satThuong;
    protected Image picture;
    protected int posX, posY;
    protected double velocityX, velocityY;
    protected double ageInSeconds;

    public Bullet(Coordinate pos, Coordinate target, int satThuong, Image pic)
    {
        this.posX = pos.x;
        this.posY = pos.y;
        this.satThuong = satThuong;
        this.picture = pic;

        this.velocityX = target.x - this.posX;
        this.velocityY = target.y - this.posY;

        // Sets time to 0
        this.ageInSeconds = 0;
    }

    public abstract void interact(GameField game, double deltaTime);

    public boolean isDone()
    {
        return ageInSeconds >= 1;
    }

    public void draw(Graphics g)
    {
        g.drawImage(picture, posX, posY, null);
    }
}
