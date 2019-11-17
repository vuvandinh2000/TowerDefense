package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class TankerEnemy extends Enemy{
	private int health = 3;				//máu
	private long armor;				//giáp
	final private long reward = 200;				//phần thưởng

	public TankerEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/TankerEnemy.png"),-20,-20,4);
	}
}
