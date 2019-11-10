package game.Enemies;

import java.awt.Graphics;
import java.awt.Image;
import game.Config;
import game.ImageLoader;
import game.PathPosition;

public class BossEnemy extends Enemy{
    public BossEnemy(PathPosition p)
	{
        super(p,ImageLoader.getLoader().getImage("resources/BossEnemy.png"),-20,-20,2,Config.BOSS_HEALTH,Config.BOSS_ARMOR,Config.BOSS_REWARD);
//		ImageLoader loader = ImageLoader.getLoader();
//		this.enemy = loader.getImage("resources/BossEnemy.png");
//		this.position = p;
//		this.anchorX = -20;
//		this.anchorY = -20;
//		this.velocity = 2;
	}
}
