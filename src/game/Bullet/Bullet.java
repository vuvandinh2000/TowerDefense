package game.Bullet;

import game.Coordinate;
import game.Effect;
import game.GameEntity;

public class Bullet extends Effect implements GameEntity {
    public Bullet(Coordinate pos, Coordinate target)
    {
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
