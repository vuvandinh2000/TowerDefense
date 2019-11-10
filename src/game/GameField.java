package game;

import game.Enemies.Enemy;
import java.awt.*;
import java.util.List;
import game.Towers.*;
import game.Tile.Road;

/** GameField: Class này chứa và quản lý tất cả các GameEntity trên sân chơi*/
public abstract class GameField implements Runnable
{
    protected Image backdrop;				// background
    protected Road line;			// path coordinates
    
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
    
    /* create enemies */
    public List<Enemy> enemies;				// quân địch
    
    /* create towers */
    protected List<Tower> towers;					// tháp chiến đấu
    
    /* create effects */
    public List<Effect> effects;				// hiệu ứng
    
    // You will declare other variables here.  These variables will last for
    //   the lifetime of the game, so don't store temporary values or loop counters
    //   here.

    /**
     * Constructor:  Builds a thread of execution, then starts it
     * on 'this' object.  This extra thread of execution will be
     * responsible for doing all the work of creating, running,
     * and playing the game.
     * 
     * (Note:  Drawing the screen happens inside of -another-
     * thread of execution controlled by Java.  Fortunately, we
     * don't care, but we are aware that some other threads
     * do exist.)
     */
    public GameField()
    {
        gamePanel = new GamePanel(this);
    }

    public abstract void draw(Graphics g);
    
    /**
     * Generates a stream of enemies
     * 
     */
    public abstract void generateEnemies();
    
    /**
     * Method for placing black holes on the screen
     */
    public abstract void placeNormalTower();
    public abstract void placeMachineGunTower();
    public abstract void placeSniperTower();
    /* Static methods */
    

    

}	