package game.Towers;

import game.Bullet.BinhThuong;
import game.Bullet.ChetLuon;
import game.Bullet.MaiMoiChet;
import game.Coordinate;
import game.Enemies.Enemy;
import game.GameField;
import game.ImageLoader;

import java.awt.*;
import java.util.List;

public class SniperTower implements Tower {
    protected Coordinate position;
    protected int anchorX;
    protected int anchorY;
    final private Image tower = ImageLoader.getLoader().getImage("resources/SniperTower.png");
    protected double timeSinceLastFire;// time since last effect was fired
    private double shootSpeed;
    final private long shootRange = 200;		//tầm bắn
    private long damage;			//sát thương

//    protected SniperTower(Coordinate position, int anchorX, int anchorY, double shootSpeed, long shootRange, long damage){
//        this.position = position;
//        this.anchorX = anchorX;
//        this.anchorY = anchorY;
//        this.shootRange = shootRange;
//        this.damage = damage;
//    }

    public SniperTower(Coordinate position){
        this.position = position;
    }

    @Override
    public void setPosition(Coordinate c){
        this.position = c;
    }

    @Override
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
            Coordinate enemyPos = e.getPosition().getCoordinate();

            double dx, dy, dist;
            dx = enemyPos.x - position.x;
            dy = enemyPos.y - position.y;
            dist = Math.sqrt((dx*dx) + (dy*dy));

            // holds position of effect
            Coordinate pos = new Coordinate(position.x, position.y);

            // if enemy is in range, fire salt
            if(dist < shootRange) {
                ChetLuon chetLuon = new ChetLuon(pos, enemyPos);
                game.bullet.add(chetLuon);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}
