package game;

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
    
    protected GamePanel gamePanel;		// đối tượng gamePanel
    
    protected int frameCounter;			// keeps track of frame updates
    protected long lastTime;				// keeps track of time

    protected boolean placingNormalTower;	// true if tower is being placed
    protected Tower newNormalTower; 		// variable to hold new tower objects

    protected boolean placingMachineGunTower;			// true if tower is being placed
    protected Tower newMachineGunTower;

    protected boolean placingSniperTower;			// true if tower is being placed
    protected Tower newSniperTower; 				// variable to hold new tower objects
    
    protected int livesCounter; 					// đếm mạng
    protected int scoreCounter;					// đếm điểm
    protected int killsCounter;					// số lượng quân địch đã giết

    public List<Enemy> enemies;
    protected List<Tower> towers;
    public List<Effect> effects;				// hiệu ứng

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