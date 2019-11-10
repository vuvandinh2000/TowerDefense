package game.Enemies;

import java.awt.Graphics;
import java.awt.Image;
import game.Config;
import game.ImageLoader;
import game.PathPosition;

public class SmallerEnemy extends Enemy{
    public SmallerEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/SmallerEnemy.png"),-20,-20,10, Config.SMALLER_HEALTH,Config.SMALLER_ARMOR,Config.SMALLER_REWARD);
//		ImageLoader loader = ImageLoader.getLoader();
//		this.enemy = loader.getImage("resources/Alien-Ship.png");
//		this.position = p;
//		this.anchorX = -20;
//		this.anchorY = -20;
//		this.velocity = 10;
	}
}
