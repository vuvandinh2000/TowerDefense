package game.Enemies;

import game.ImageLoader;
import game.PathPosition;

public class SmallerEnemy extends Enemy{

    public SmallerEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/SmallerEnemy.png"),-20,-20,5, 3, 10,1);
	}
}
