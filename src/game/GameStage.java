package game;

import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JFrame;
import game.Bullet.Bullet;
import game.Enemies.*;
import game.Towers.*;
import game.Tile.*;

/**GameStage định nghĩa ra trạng thái bắt đầu của một GameField trong 1 màn chơi */
enum GameState { SETUP, UPDATE, DRAW, WAIT, END }

public class GameStage extends GameField
{
    private GameState state;
    private boolean gameIsOver;
    private boolean nextRound = false;
    private int round = 1;
    private double elapsedTime;

    public GameStage()
    {
        // Game bắt đầu ở trạng thái SETUP
        this.state = GameState.SETUP;

        Thread t = new Thread(this);
        t.start();
    }

    public void run()
    {
        while (true)
        {
            if (state == GameState.SETUP)
            {
                doSetupStuff();
            }
            else if (state == GameState.UPDATE)
            {
                doUpdateTasks();
            }
            else if (state == GameState.DRAW)
            {
                gamePanel.repaint();

                try { Thread.sleep(5); } catch (Exception e) {}
            }
            else if (state == GameState.WAIT)
            {
                try { Thread.sleep(100); } catch (Exception e) {}   //1/10th (s)
                state = GameState.UPDATE;
            }
            else if (state == GameState.END)
            {

            }
        }
    }

    /**Hàm setup đồ thực hiện khi game ở trạng thái UPDATE*/
    private void doSetupStuff()
    {
        JFrame f = new JFrame("Game Tower Defense by Vũ Văn Định");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(gamePanel);
        f.pack();
        f.setVisible(true);

        killsCounter = 0;

        if (nextRound) {
            if (round == 4){
                state = GameState.DRAW;
                return;
            }
            round++;
            nextRound = false;
        }

        if (round == 2){
            backdrop = ImageLoader.getLoader().getImage("resources/background2.jpg");
            livesCounter = 20;
            scoreCounter = 300;

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path1round2.txt");
            line1 = new Road(new Scanner(inputStream));
            inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path2round2.txt");
            line2 = new Road(new Scanner(inputStream));
        }
        else if (round == 3){
            backdrop = ImageLoader.getLoader().getImage("resources/background3.jpg");
            livesCounter = 20;
            scoreCounter = 300;
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path1round3.txt");
            line1 = new Road(new Scanner(inputStream));
            inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path2round3.txt");
            line2 = new Road(new Scanner(inputStream));
        }
        else if (round == 4){
            backdrop = ImageLoader.getLoader().getImage("resources/background4.jpg");
            livesCounter = 20;
            scoreCounter = 300;

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path1round4.txt");
            line1 = new Road(new Scanner(inputStream));
            inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path2round4.txt");
            line2 = new Road(new Scanner(inputStream));
        }
        else{
            backdrop = ImageLoader.getLoader().getImage("resources/background1.jpg");
            livesCounter = 20;
            scoreCounter = 300;

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path1round1.txt");
            line1 = new Road(new Scanner(inputStream));
            inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/path3round1.txt");
            line2 = new Road(new Scanner(inputStream));

            SoundPlayer.play("C://Users/HAC/Desktop/TowerDefense/src/resources/backgroundSound1.wav");
        }

        frameCounter = 0;
        lastTime = System.currentTimeMillis();

        enemies = new LinkedList<Enemy>();
        towers = new LinkedList<Tower>();
        bullet = new LinkedList<Bullet>();
        
        // initialize
        placingMachineGunTower = false;
        newMachineGunTower = null;

        placingNormalTower = false;
        newNormalTower = null;

        placingSniperTower = false;
        newSniperTower = null;

        this.gameIsOver = false;

        state = GameState.UPDATE;
    }

    private void doUpdateTasks()
    {
    	if(gameIsOver){
            state = GameState.DRAW;
    		return;
    	}
    	
    	if(nextRound){
            state = GameState.SETUP;
    		return;
    	}

        long currentTime = System.currentTimeMillis();
        elapsedTime = ((currentTime - lastTime) / 1000.0);
        lastTime = currentTime;

    	for(Tower t: new LinkedList<Tower>(towers))
    	{	
    		t.interact(this, elapsedTime);    		
    	}
    	for(Bullet e: new LinkedList<Bullet>(bullet))
    	{	
    		e.interact(this, elapsedTime);
    		if(e.isDone())
                    bullet.remove(e);
    	}

    	for(Enemy e: new LinkedList<Enemy>(super.enemies))
    	{	
    		e.advance();
     		if(e.getPosition().isAtTheEnd())
    		{
    			super.enemies.remove(e);
    			super.livesCounter--;
    		}
    	}

        this.generateEnemies();
        frameCounter++;

    	this.placeMachineGunTower();
    	this.placeNormalTower();
    	this.placeSniperTower();
    	
    	if(livesCounter <= 0)
    	{	gameIsOver = true;
    		livesCounter = 0;
    	}
    	
    	if(round == 1 && killsCounter >= 18)
    	{
    	    nextRound = true;
    		killsCounter = 0;
    	}
    	else if (round == 2 && killsCounter >= 48){
    	    nextRound = true;
    	    killsCounter = 0;
        }
        else if (round == 3 && killsCounter >= 68){
            nextRound = true;
            killsCounter = 0;
        }
        else if (round == 4 && killsCounter >= 158){
            nextRound = true;
        }
        
        state = GameState.DRAW;
    }

    public void draw(Graphics g)
    {
        if (state != GameState.DRAW)
            return;

        g.drawImage(backdrop, 0, 0, null);

    	for(Enemy e: new LinkedList<Enemy>(enemies))
    		e.draw(g);
        for(Tower t: new LinkedList<Tower>(towers))
        	t.draw(g);
    	for(Bullet s: new LinkedList<Bullet>(bullet))
    		s.draw(g);

        g.setColor(Color.YELLOW);
        g.fillRect(700, 0, 200, 600); //i: lề bên trái thanh Menu

        g.setColor(Color.RED);
        g.setFont(new Font("Lucidia Sans", Font.PLAIN, 15));
        g.drawString("Round: " + round, 700, 20);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Consolas", Font.PLAIN, 16));
        g.drawString("Số mạng:", 750, 100);
        g.drawString("Số tiền:", 745, 150);
        g.drawString("Số địch bị giết:", 710, 200);

        g.setFont(new Font("Lucidia Sans", Font.BOLD, 15));
        g.drawString("Thông tin Game", 700, 50);
        g.drawLine(700, 50, 805, 50);
        g.drawString("Towers", 750, 240);
        g.drawLine(750, 240, 800, 240);
        g.setColor(Color.RED);
        g.setFont(new Font("Consolas", Font.BOLD, 16));
        g.drawString(" " + livesCounter, 815, 100);
        g.drawString(" " + scoreCounter, 820, 150);
        g.drawString(" " + killsCounter, 850, 200);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Giá: 100", 725, 335);
        g.drawString("Giá: 200", 810, 335);
        g.drawString("Giá: 300", 770, 405);

        MachineGunTower machineGunTower = new MachineGunTower(new Coordinate(810, 270));
        machineGunTower.draw(g);
        NormalTower normalTower = new NormalTower(new Coordinate(730, 270));
        normalTower.draw(g);
        SniperTower sniperTower = new SniperTower(new Coordinate(770, 340));
        sniperTower.draw(g);
        
        // tower chạy theo chuột
        if(newMachineGunTower != null)
        	newMachineGunTower.draw(g);
        if(newNormalTower != null)
            newNormalTower.draw(g);
        if(newSniperTower != null)
        	newSniperTower.draw(g);

        if(livesCounter <= 0)
        	g.drawImage(ImageLoader.getLoader().getImage("resources/game_over.png"), 10, 60, null);

		if(round >= 4 && nextRound)
		{
            g.drawImage(ImageLoader.getLoader().getImage("resources/winGame.png"), 0, 0, null);
        	state = GameState.END;
        	return;
		}

        state = GameState.WAIT;
    }

     @Override
    public void generateEnemies()
    {
    	if(frameCounter % 30 == 0)
    	{
    	    int random = getRandomNumberInRange(1,2);
    	    if (random == 1) {
                enemies.add(new SmallerEnemy(line1.getStart()));
            } else {
    	        enemies.add(new SmallerEnemy(line2.getStart()));
            }
    	}
 		else if(frameCounter % 25 == 0 && frameCounter >= 50)	// slow
 		{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new SmallerEnemy(line1.getStart()));
                enemies.add(new NormalEnemy(line1.getStart()));
            } else {
                enemies.add(new SmallerEnemy(line2.getStart()));
                enemies.add(new NormalEnemy(line2.getStart()));
            }
 		}
	 	else if(frameCounter % 20 == 0 && frameCounter >= 100)	// medium
	 	{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
            } else {
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
            }
	 	}	
 		else if(frameCounter % 15 == 0 && frameCounter >= 150)	// medium
 		{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new SmallerEnemy(line1.getStart()));
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
            } else {
                enemies.add(new SmallerEnemy(line2.getStart()));
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
            }
 		}
	 	else if(frameCounter % 10 == 0 && frameCounter >= 200)	// fast
	 	{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new SmallerEnemy(line1.getStart()));
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
                enemies.add(new BossEnemy(line1.getStart()));
            } else {
                enemies.add(new SmallerEnemy(line2.getStart()));
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
                enemies.add(new BossEnemy(line2.getStart()));
            }
	 	}
	 	else if(frameCounter % 5 == 0 && frameCounter >= 250)	// fast
	 	{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new SmallerEnemy(line1.getStart()));
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
                enemies.add(new BossEnemy(line1.getStart()));
            } else {
                enemies.add(new SmallerEnemy(line2.getStart()));
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
                enemies.add(new BossEnemy(line2.getStart()));
            }
	 	}
    }

     @Override
    public void placeMachineGunTower()
    {
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

    	if(gamePanel.mouseX > 770 && gamePanel.mouseX < 850 &&
    		gamePanel.mouseY > 230 && gamePanel.mouseY < 310 &&
    		gamePanel.mouseIsPressed && scoreCounter >= 100)
    	{
	    		placingMachineGunTower = true;
	    		newMachineGunTower = new MachineGunTower(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 700 &&
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 405 &&
        	gamePanel.mouseIsPressed && placingMachineGunTower
        	&& line1.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 30
            && line2.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 30)
    	{
	    		newMachineGunTower.setPosition(mouseLocation);
	    		towers.add(new MachineGunTower(mouseLocation));
	    		scoreCounter -= 200;
	    		newMachineGunTower = null;
	    		placingMachineGunTower = false;
    	}

    	if(newMachineGunTower != null)
    	{
    		newMachineGunTower.setPosition(mouseLocation);
    	}	
    }

    @Override
    public void placeNormalTower()
    {
        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

        if(gamePanel.mouseX > 690 && gamePanel.mouseX < 770 &&
                gamePanel.mouseY > 230 && gamePanel.mouseY < 310 &&
                gamePanel.mouseIsPressed && scoreCounter >= 100)
        {
            placingNormalTower = true;
            newNormalTower = new NormalTower(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 700 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 405 &&
                gamePanel.mouseIsPressed && placingNormalTower
                && line1.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 30
                && line2.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 30)
        {
            newNormalTower.setPosition(mouseLocation);
            towers.add(new NormalTower(mouseLocation));
            scoreCounter -= 100;
            newNormalTower = null;
            placingNormalTower = false;
        }

        if(newNormalTower != null)
        {
            newNormalTower.setPosition(mouseLocation);
        }
    }

     @Override
    public void placeSniperTower()
    {
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

    	if(gamePanel.mouseX > 730 && gamePanel.mouseX < 810 &&
    		gamePanel.mouseY > 300 && gamePanel.mouseY < 380 &&
    		gamePanel.mouseIsPressed && scoreCounter >= 300)
    	{
	    		placingSniperTower = true;
	    		newSniperTower = new SniperTower(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 700 &&
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 405 &&
        	gamePanel.mouseIsPressed && placingSniperTower
        	&& line1.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 30
            && line2.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 30)
    	{
	    		newSniperTower.setPosition(mouseLocation);
	    		towers.add(new SniperTower(mouseLocation));
	    		scoreCounter -= 300;
	    		newSniperTower = null;
	    		placingSniperTower = false;
    	}

    	if(newSniperTower != null)
    	{
    		newSniperTower.setPosition(mouseLocation);
    	}
    }

    private static int getRandomNumberInRange(int min, int max) {
        return (new java.util.Random()).nextInt((max - min) + 1) + min;
    }
}