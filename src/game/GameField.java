package game;

import game.Bullet.Bullet;
import game.Enemies.Enemy;
import java.awt.*;
import java.util.List;
import game.Towers.*;
import game.Tile.Road;

/** GameField: Class này chứa và quản lý tất cả các GameEntity trên sân chơi*/
public abstract class GameField implements Runnable
{
    protected Image backdrop;
    protected Road line1;
    protected Road line2;
    
    protected GamePanel gamePanel;
    
    protected int frameCounter;
    protected long lastTime;

    protected boolean placingNormalTower;
    protected Tower newNormalTower;

    protected boolean placingMachineGunTower;
    protected Tower newMachineGunTower;

    protected boolean placingSniperTower;
    protected Tower newSniperTower;
    
    public int livesCounter;
    public int scoreCounter;
    public int killsCounter;

    public List<Enemy> enemies;
    protected List<Tower> towers;
    public List<Bullet> bullet;

    public GameField()
    {
        gamePanel = new GamePanel(this);
    }

    public abstract void draw(Graphics g);

    public abstract void generateEnemies();

    public abstract void placeNormalTower();
    public abstract void placeMachineGunTower();
    public abstract void placeSniperTower();
}	