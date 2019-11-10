package game.Enemies;

import java.awt.Graphics;
import java.awt.Image;
import game.Config;
import game.ImageLoader;
import game.PathPosition;

public class NormalEnemy extends Enemy {
    	/**
	 * Constructor
	 */
	public NormalEnemy(PathPosition p)
	{
		super(p,ImageLoader.getLoader().getImage("resources/NormalEnemy.png"),-20,-20,5, Config.NORMAL_HEALTH,Config.NORMAL_ARMOR,Config.NORMAL_REWARD);
//		ImageLoader loader = ImageLoader.getLoader();
//		this.enemy = loader.getImage("resources/Alien-Ship.png");
//		this.position = p;
//		this.anchorX = -20;
//		this.anchorY = -20;
//		this.velocity = 6;
	}
}
