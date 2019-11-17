package game.Enemies;

import game.Config;
import game.ImageLoader;
import game.PathPosition;

public class BossEnemy extends Enemy{
    private int health = 18;				//máu
    private long armor;				//giáp
    private long reward = 500;				//phần thưởng

    public BossEnemy(PathPosition p)
	{
        super(p,ImageLoader.getLoader().getImage("resources/BossEnemy.png"),-20,-20,2);
	}
}
