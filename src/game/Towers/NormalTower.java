package game.Towers;

import game.Bullet.BinhThuong;
import game.Bullet.MaiMoiChet;
import game.Coordinate;
import game.Enemies.Enemy;
import game.GameField;
import java.awt.*;
import java.util.List;

public class NormalTower implements Tower {
    protected Coordinate position;	// holds position of tower
    protected int anchorX;			// shifts X coordinate
    protected int anchorY;			// shifts Y coordinate
    private Image tower; 			// holds tower image
    protected double timeSinceLastFire;// time since last effect was fired
    private double shootSpeed;
    private long shootRange;		//tầm bắn
    private long damage;			//sát thương

    public NormalTower(Coordinate position, Image tower, int anchorX, int anchorY, double shootSpeed, long shootRange, long damage){
        this.position = position;
        this.tower = tower;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.shootRange = shootRange;
        this.shootSpeed = shootSpeed;
        this.damage = damage;
    }

    public NormalTower(Coordinate position){
        this.position = position;
    }

    @Override
    public void setPosition(Coordinate c){
        this.position = c;
    }

    public void draw(Graphics g)
    {
        // Draws tower object to location specified by user
        g.drawImage(tower, position.getX() + anchorX, position.getY() + anchorY, null);

        // Draws dot on Enemy's (x, y) coordinates
//		g.setColor(Color.WHITE);
//		g.fillOval(position.getX(), position.getY(), 5, 5);
    }

    @Override
    public void interact(GameField game, double deltaTime)
    {

        List<Enemy> enemies = game.enemies; // new list of enemies

        // tracks time that effect has existed
        timeSinceLastFire += deltaTime;

        // if time less than 1.5 seconds, don't interact
        if(timeSinceLastFire < 0.2)
            return;

        // Gives position of an enemy in enemy list
        for(Enemy e: enemies)
        {
            // holds position of enemy
            Coordinate enemyPos = e.getPosition().getCoordinate();

            // Compute distance of enemy to tower
            double dx, dy, dist;	// change in x, y, and total distance

            // calculates change in x and y position
            dx = enemyPos.x - position.x; // x position of enemy - tower
            dy = enemyPos.y - position.y; // y position of enemy - tower

            // use Pythagorean theorem to calculate distance
            dist = Math.sqrt((dx*dx) + (dy*dy));

            // holds position of effect
            Coordinate pos = new Coordinate(position.x, position.y);

            // if enemy is in range, fire salt
            if(dist < 100) {
                BinhThuong binhThuong = new BinhThuong(pos, enemyPos);
                game.effects.add(binhThuong);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}
