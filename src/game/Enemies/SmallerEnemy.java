package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class SmallerEnemy extends Enemy{
	private int health = 1;				//máu
	private long armor;				//giáp
	final private long reward = 50;				//phần thưởng

    public SmallerEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/SmallerEnemy.png"),-20,-20,10);
	}
}
