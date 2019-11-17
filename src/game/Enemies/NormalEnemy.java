package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class NormalEnemy extends Enemy {
	private int health = 2;				//máu
	private long armor;				//giáp
	final private long reward = 100;				//phần thưởng

	public NormalEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/NormalEnemy.png"),-20,-20,5);
	}
}
