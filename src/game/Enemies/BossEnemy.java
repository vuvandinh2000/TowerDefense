package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class BossEnemy extends Enemy{

    public BossEnemy(PathPosition p)
	{
        super(p,ImageLoader.getLoader().getImage("resources/BossEnemy.png"),-20,-20,4, 150, 100, 4);
	}
}
