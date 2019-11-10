package game.Enemies;

import java.awt.Graphics;
import java.awt.Image;
import game.Config;
import game.ImageLoader;
import game.PathPosition;

public class TankerEnemy extends Enemy{

	 //Constructor
	public TankerEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/TankerEnemy.png"),-20,-20,4, Config.TANKER_HEALTH,Config.TANKER_ARMOR,Config.TANKER_REWARD);
//		ImageLoader loader = ImageLoader.getLoader();
////		this.enemy = loader.getImage("resources/TankerEnemy.png");
////		this.position = p;
////		this.anchorX = -20;
////		this.anchorY = -20;
////		this.velocity = 4;
	}
}
