package game.Towers;

import game.Bullet.MaiMoiChet;
import game.Coordinate;
import game.Enemies.Enemy;
import game.GameField;
import game.ImageLoader;

import java.awt.*;
import java.util.List;

public class MachineGunTower implements Tower {
    protected Coordinate position;
    protected int anchorX;
    protected int anchorY;
    final private Image tower = ImageLoader.getLoader().getImage("resources/MachineGunTower.png");
    protected double timeSinceLastFire;// time since last effect was fired
    private double shootSpeed;
    final private long shootRange = 90;		//tầm bắn
    private long damage;			//sát thương

    @Override
    public void setPosition(Coordinate c){
        this.position = c;
    }

    public MachineGunTower(Coordinate position){
        this.position = position;
    }

    public void draw(Graphics g)
    {
        // Draws tower object to location specified by user
        g.drawImage(tower, position.getX() + anchorX, position.getY() + anchorY, null);
    }

    @Override
    public void interact(GameField game, double deltaTime)
    {

        List<Enemy> enemies = game.enemies;

        timeSinceLastFire += deltaTime;

        if(timeSinceLastFire < 0.2)
            return;

        for(Enemy e: enemies)
        {
            Coordinate enemyPos = e.getPosition().getCoordinate();

            double dx, dy, dist;

            dx = enemyPos.x - position.x;
            dy = enemyPos.y - position.y;
            dist = Math.sqrt((dx*dx) + (dy*dy));

            Coordinate pos = new Coordinate(position.x, position.y);

            if(dist <= shootRange)
            {	MaiMoiChet maiMoiChet = new MaiMoiChet(pos, enemyPos);
                game.bullet.add(maiMoiChet);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}
