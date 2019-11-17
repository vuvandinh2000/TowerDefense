package game;

import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JFrame;
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
        }

        frameCounter = 0;
        lastTime = System.currentTimeMillis();

        enemies = new LinkedList<Enemy>();
        towers = new LinkedList<Tower>();
        effects = new LinkedList<Effect>();
        
        // initialize
        placingMachineGunTower = false;
        newMachineGunTower = null;

        placingNormalTower = false;
        newNormalTower = null;

        placingSniperTower = false;
        newSniperTower = null;

        this.gameIsOver = false;
        
        // Change the game state to start the game.
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
    	
    	// See how long it was since the last frame.
        long currentTime = System.currentTimeMillis();
        elapsedTime = ((currentTime - lastTime) / 1000.0);  // Tính số giây đã trôi qua
        lastTime = currentTime;  // Our current time is the next frame's last time
        
    	// for each tower, interact in this game
    	for(Tower t: new LinkedList<Tower>(towers))
    	{	
    		t.interact(this, elapsedTime);    		
    	}
        // for each effect, interact in this game      
    	for(Effect e: new LinkedList<Effect>(effects))
    	{	
    		e.interact(this, elapsedTime);
    		if(e.isDone())
    			effects.remove(e);	// add to list that has reached the end
    	}
    	
    	// Advance each enemy on the path.
    	for(Enemy e: new LinkedList<Enemy>(super.enemies))
    	{	
    		e.advance();
     		if(e.getPosition().isAtTheEnd())
    		{
    			super.enemies.remove(e);	// add to list that has reached the end
    			super.livesCounter--;		// if they have reached the end, reduce lives
    		}

    	}
    	
        // Fill elements in an enemy list
        this.generateEnemies();
        
    	// increments frame counter
        frameCounter++;
    	
    	// Place towers if user chooses
    	this.placeMachineGunTower();
    	this.placeNormalTower();
    	this.placeSniperTower();
    	
    	if(livesCounter <= 0)
    	{	gameIsOver = true;
    		livesCounter = 0;
    	}
    	
    	if(round == 1 && killsCounter >= 8)
    	{
    	    nextRound = true;
    		killsCounter = 0;
    	}
    	else if (round == 2 && killsCounter >= 10){
    	    nextRound = true;
    	    killsCounter = 0;
        }
        else if (round == 3 && killsCounter >= 12){
            nextRound = true;
            killsCounter = 0;
        }
        else if (round == 4 && killsCounter >= 10){
            nextRound = true;
        }
        
        state = GameState.DRAW;
       
        // Careful!  At ANY time after we set this state, the draw method
        //   may execute.  Don't do any further updating.
    }

    public void draw(Graphics g)
    {
        if (state != GameState.DRAW)
            return;

        g.drawImage(backdrop, 0, 0, null);
        
        // Vẽ đích đến
//        g.setColor(new Color(0,255,136));
//        g.fillArc(450, 385, 100, 100, 90, 180);
//        g.setColor(Color.GREEN);
//        int[] xCor = new int[]{600, 588, 574, 566, 557, 557, 563, 572, 576, 584, 600};
//        int[] yCor = new int[]{459, 464, 462, 453, 454, 448, 438, 435, 422, 414, 415};
//        g.fillPolygon(xCor, yCor, 11);
        
        // Vẽ đường thẳng dọc theo đường
//        line.drawLine(g);
        
        // Vẽ tất cả quân địch trước thanh Menu
    	for(Enemy e: new LinkedList<Enemy>(enemies))
    		e.draw(g);
    	
        // Vẽ tất cả tháp trong danh sách
        for(Tower t: new LinkedList<Tower>(towers))
        	t.draw(g);
        	
    	// Hiệu ứng
    	for(Effect s: new LinkedList<Effect>(effects))
    		s.draw(g);
    	
        // draw thanh Menu
        g.setColor(Color.WHITE);
        g.fillRect(700, 0, 200, 600); //i: lề bên trái thanh Menu
        
        // Viết điểm số và đếm mạng trên thanh Menu Bar
        g.setColor(Color.BLACK);
        g.setFont(new Font("Consolas", Font.PLAIN, 16));
        g.drawString("Số mạng: " + livesCounter, 750, 100);	// lives counter
        g.drawString("Số tiền: " + scoreCounter, 745, 150);	// score counter
        g.drawString("Số địch bị giết: " + killsCounter, 710, 200);
        g.drawString("Cost: 100", 610, 380);				// cost for black hole towers
        g.drawString("Cost: 300", 640, 530);					// cost for sun towers
        g.setFont(new Font("Lucidia Sans", Font.ITALIC, 28));		
        g.drawString("Thông tin Game", 700, 50);
        g.drawLine(700, 50, 900, 50);								// underscore
        g.drawString("Towers", 750, 240);
        g.drawLine(750, 240, 850, 240);								// underscore

        // draw tower in menu area
        MachineGunTower machineGunTower = new MachineGunTower(new Coordinate(810, 270));
        machineGunTower.draw(g);

        // draw tower in menu area
        NormalTower normalTower = new NormalTower(new Coordinate(730, 270));
        normalTower.draw(g);

        // draw tower in menu area
        SniperTower sniperTower = new SniperTower(new Coordinate(770, 340));
        sniperTower.draw(g);
        
        // draws machine gun tower object with mouse movements
        if(newMachineGunTower != null)
        	newMachineGunTower.draw(g);

        // draws normal tower object with mouse movements
        if(newNormalTower != null)
            newNormalTower.draw(g);

        // draws sniper tower object with mouse movements
        if(newSniperTower != null)
        	newSniperTower.draw(g);

        if(livesCounter <= 0)
        	g.drawImage(ImageLoader.getLoader().getImage("resources/game_over.png"), 0, 0, null);

		if(round >= 4 && nextRound)
		{
            g.drawImage(ImageLoader.getLoader().getImage("resources/winGame.png"), 0, 0, null);
        	state = GameState.END;
        	return;
		}
		
        // Drawing is now complete.  Enter the WAIT state to create a small
        //   delay between frames.
        
        state = GameState.WAIT;
    }
    
    /**
     * Generates a stream of enemies
     */
     @Override
    public void generateEnemies()
    {	
    	// adds enemies to list dependent on how many frames have passed
    	if(frameCounter % 30 == 0)								// slow 
    	{
    	    int random = getRandomNumberInRange(1,2);
    	    if (random == 1) {
                enemies.add(new NormalEnemy(line1.getStart()));
            } else {
    	        enemies.add(new NormalEnemy(line2.getStart()));
            }
    	}
 		else if(frameCounter % 25 == 0 && frameCounter >= 50)	// slow
 		{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new NormalEnemy(line1.getStart()));
            } else {
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
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
            } else {
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
            }

// 			enemies.add(new NormalEnemy(line.getStart()));
// 			enemies.add(new TankerEnemy(line.getStart()));
 		}
	 	else if(frameCounter % 10 == 0 && frameCounter >= 200)	// fast
	 	{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
                enemies.add(new BossEnemy(line1.getStart()));
            } else {
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
                enemies.add(new BossEnemy(line2.getStart()));
            }

//	 		enemies.add(new NormalEnemy(line.getStart()));
//	 		enemies.add(new TankerEnemy(line.getStart()));
//	 		enemies.add(new BossEnemy(line.getStart()));
	 	}
	 	else if(frameCounter % 5 == 0 && frameCounter >= 250)	// fast
	 	{
            int random = getRandomNumberInRange(1,2);
            if (random == 1) {
                enemies.add(new NormalEnemy(line1.getStart()));
                enemies.add(new TankerEnemy(line1.getStart()));
                enemies.add(new BossEnemy(line1.getStart()));
            } else {
                enemies.add(new NormalEnemy(line2.getStart()));
                enemies.add(new TankerEnemy(line2.getStart()));
                enemies.add(new BossEnemy(line2.getStart()));
            }

//	 		enemies.add(new NormalEnemy(line.getStart()));
//	 		enemies.add(new TankerEnemy(line.getStart()));
//	 		enemies.add(new BossEnemy(line.getStart()));
	 	}
    }

     @Override
    public void placeMachineGunTower()
    {
    	/* Tạo để tránh đặt tháp trên đường đi hoặc ngoài màn hình */
    	
    	 // variable to hold mouse location
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
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
        	&& line1.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 40
            && line2.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 40)
    	{	// if mouse is pressed on game screen, place tower on game screen
	    		newMachineGunTower.setPosition(mouseLocation);
	    		towers.add(new MachineGunTower(mouseLocation));
	    		scoreCounter -= 100;
	    		newMachineGunTower = null;
	    		placingMachineGunTower = false;
    	}
    	
    	// đặt tháp xuống vị trí click chuột
    	if(newMachineGunTower != null)
    	{
    		newMachineGunTower.setPosition(mouseLocation);
    	}	
    }

    @Override
    public void placeNormalTower()
    {
        /* I need to make it so you can't place towers on path or off the screen */

        // variable to hold mouse location
        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

        // nếu chọn tháp
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
                && line1.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 40
                && line2.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 40)
        {
            // nếu vị trí chọn hợp lí
            newNormalTower.setPosition(mouseLocation);
            towers.add(new NormalTower(mouseLocation));
            scoreCounter -= 100;
            newNormalTower = null;
            placingNormalTower = false;
        }

        // moves tower object with mouse movements
        if(newNormalTower != null)
        {
            newNormalTower.setPosition(mouseLocation);
        }
    }

     @Override
    public void placeSniperTower()
    {
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
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
        	&& line1.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 40
            && line2.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 40)
    	{	// if mouse is pressed on game screen, place tower on game screen
	    		newSniperTower.setPosition(mouseLocation);
	    		towers.add(new SniperTower(mouseLocation));
	    		scoreCounter -= 300;
	    		newSniperTower = null;
	    		placingSniperTower = false;
    	}
    	
    	// moves tower object with mouse movements
    	if(newSniperTower != null)
    	{
    		newSniperTower.setPosition(mouseLocation);
    	}	
    }

    private static int getRandomNumberInRange(int min, int max) {
        return (new java.util.Random()).nextInt((max - min) + 1) + min;
    }
}