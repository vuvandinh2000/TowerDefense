package game.Bullet;

import game.Enemies.Enemy;
import game.GameField;
import game.ImageLoader;
import game.Coordinate;
import game.SoundPlayer;

import java.util.LinkedList;
import java.util.List;

public class ChetLuon extends Bullet
{
	public ChetLuon(Coordinate pos, Coordinate target)
	{
		super(pos, target,10,ImageLoader.getLoader().getImage("resources/bomb.png"));
	}

	@Override
	public void interact(GameField game, double deltaTime)
	{
		ageInSeconds += deltaTime;

		posX += velocityX*deltaTime;
		posY += velocityY*deltaTime;

		List<Enemy> enemies = game.enemies;
		for(Enemy e: new LinkedList<Enemy>(enemies))
		{
			double dx, dy, distance;

			dx = e.position.getCoordinate().x - posX;
			dy = e.position.getCoordinate().y - posY;
			distance = Math.sqrt((dx*dx) + (dy*dy));

			if(distance < 20)
			{
				if (e.health <= 0) {
					SoundPlayer.play("C://Users/HAC/Desktop/TowerDefense/src/resources/Explosion.wav");
					game.enemies.remove(e);
					game.scoreCounter += e.reward;
					game.killsCounter++;
				} else {
					e.health--;
				}
			}
		}
	}
}
