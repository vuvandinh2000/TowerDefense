package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class NormalEnemy extends Enemy {

	public NormalEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/NormalEnemy.png"),-20,-20,8, 18,30,2);
	}
}
