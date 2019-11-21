package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class TankerEnemy extends Enemy{

	public TankerEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/TankerEnemy.png"),-20,-20,15, 32,50,3);
	}
}
