package game.Enemies;

import game.PathPosition;
import game.Coordinate;
import java.awt.Graphics;
import java.awt.Image;
import  game.GameEntity;

public abstract class Enemy implements GameEntity
{
	public int health;
	public int armor;
	public int reward;
	public PathPosition position;
	protected Image enemy;
	private int anchorX;
	private int anchorY;
	private double speed;

	protected Enemy(PathPosition position, Image enemy, int anchorX, int anchorY, double speed, int health, int reward, int armor){
		this.position = position;
		this.enemy = enemy;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.speed = speed;
		this.health = health;
		this.reward = reward;
		this.armor = armor;
	}

	public void advance() {
		this.position.advance(speed);
	}

	public void draw(Graphics g)
	{
		Coordinate c = position.getCoordinate();
		g.drawImage(enemy, c.x + anchorX, c.y + anchorY, null);
	}

	public PathPosition getPosition()
	{
		return position;
	}

}
