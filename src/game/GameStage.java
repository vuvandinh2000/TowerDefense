package game;

import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import game.Enemies.*;
import game.Towers.*;
import game.Tile.*;

/*GameStage định nghĩa ra trạng thái bắt đầu của một GameField trong 1 màn chơi */
enum GameState { SETUP, UPDATE, DRAW, WAIT, END }

public class GameStage extends GameField
{
    private GameState state;

    private boolean gameIsOver;
    private boolean nextRound;
    private int round;

    private double elapsedTime;			// time trackers

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
    public GameStage()
    {
        // Game bắt đầu ở trạng thái SETUP
        this.state = GameState.SETUP;

        // Tạo ra một chuỗi thực thi và chạy nó
        Thread t = new Thread(this);
        t.start();
    }

    public void run()
    {
        // Lặp lại vô tận, hoặc cho tới khi người dùng đóng cửa sổ game (bất cứ thứ gì khi nó đến trước)
        while (true)
        {
            // Test trạng thái game và làm hành động thích hợp        
            if (state == GameState.SETUP)
            {
                switch (round)
                {
                    case 2: doSetupStuff(2); break;
                    case 3: doSetupStuff(3); break;
                    case 4: doSetupStuff(4); break;
                    case 1:
                    default: doSetupStuff(1);
                }
                if (nextRound == true){
                    round++;
                    nextRound = false;
                }
            }
            else if (state == GameState.UPDATE)
            {
                doUpdateTasks();
            }
            else if (state == GameState.DRAW)
            {
                // We don't actually force the drawing to happen.
                //   Instead, we 'request' it of the panel.

                gamePanel.repaint();  // redraw screen

                // We must wait for the drawing.  It will happen at some time in the near future.
                //   Since we are in an infinite loop, we could just loop until we leave the draw
                //   state.  This would waste battery life on a low power device, so instead
                //   I choose to sleep the current thread for a very short while (so that it
                //   will be briefly inactive).
                
                try { Thread.sleep(5); } catch (Exception e) {}
                
                // Do not advance the state here.  The 'draw' method will advance the state after it draws.
            }
            else if (state == GameState.WAIT)
            {
                // Wait 1/10th a second.  This code is not ideal, we'll explore a better way soon.
                
                try { Thread.sleep(100); } catch (Exception e) {}
                
                // Drawing is complete, waiting is complete.  It is time to move
                //   the objects in the game again.  Re-enter the UPDATE state.
                
                state = GameState.UPDATE;
            }
            
            else if (state == GameState.END)
            {
                // Do cleanup if any.  (We don't need to do anything here yet.)
            }
        }
    }
    
    /**
     * Hàm setup đồ thực hiện khi game ở trạng thái UPDATE.
     * It just sets up a game, then enters any valid game state.
     */
    private void doSetupStuff(int round)
    {
        // Create the JFrame and the JPanel
        JFrame f = new JFrame("Game Tower Defense by Vũ Văn Định");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(gamePanel);
        f.pack();
        f.setVisible(true); 
            
    	// creates a new ImageLoader object and loads the background image
		ImageLoader loader = ImageLoader.getLoader();
		if (round == 1){
            backdrop = loader.getImage("resources/background2.jpg");
        }
		else if (round == 2){
            backdrop = loader.getImage("resources/background2.jpg");
        }
        else if (round == 3){
            backdrop = loader.getImage("resources/background3.jpg");
        }
        else{
            backdrop = loader.getImage("resources/background4.jpg");
        }
        
        // fill counters
        livesCounter = 20;
        scoreCounter = 500;
        killsCounter = 0;

        frameCounter = 0;
        lastTime = System.currentTimeMillis();
        
        // Use the loader to build a scanner on the path data text file, then build the 
        // path points object from the data in the file.
		ClassLoader myLoader = this.getClass().getClassLoader();

		String nameStr = "";
		if (round == 1) {
            int random = getRandomNumberInRange(1,3);
		    switch (random) {
                case 1: nameStr = "resources/path2round2.txt"; break;
                case 2: nameStr = "resources/path2round2.txt"; break;
                case 3: nameStr = "resources/path2round2.txt"; break;
            }
        }
		else if (round == 2) {
            int random = getRandomNumberInRange(1,2);
            switch (random) {
                case 1: nameStr = "resources/path1round2.txt"; break;
                case 2: nameStr = "resources/path2round2.txt"; break;
            }
        }
        else if (round == 3) {
            int random = getRandomNumberInRange(1,3);
            switch (random) {
                case 1: nameStr = "resources/path1round3.txt"; break;
                case 2: nameStr = "resources/path2round3.txt"; break;
                case 3: nameStr = "resources/path3round3.txt"; break;
            }
        }
        else {
            int random = getRandomNumberInRange(1,3);
            switch (random) {
                case 1: nameStr = "resources/path1round4.txt"; break;
                case 2: nameStr = "resources/path2round4.txt"; break;
                case 3: nameStr = "resources/path3round4.txt"; break;
            }
        }
        InputStream pointStream = myLoader.getResourceAsStream(nameStr);
        Scanner s = new Scanner (pointStream);
        line = new Road(s);

        enemies = new LinkedList<game.Enemies.Enemy>();
        towers = new LinkedList<Tower>();
        effects = new LinkedList<Effect>();
        
        // initialize
        placingMachineGunTower = false;
        newMachineGunTower = null;

        // initialize
        placingNormalTower = false;
        newNormalTower = null;
        
        // initialize
        placingSniperTower = false;
        newSniperTower = null;
        	
        // initialize
        this.gameIsOver = false;
    	this.nextRound = false;
        
        // Change the game state to start the game.
        state = GameState.UPDATE;  // You could also enter the 'DRAW' state.
    }
    
    /**
     * This function is called repeatedly (once per game 'frame').
     * The update function should change the positions of objects in the game.
     * (It could also add new enemies, detect collisions, etc.)  This
     * function is responsible for the 'physics' of the game.
     */
    private void doUpdateTasks()
    {	
    	if(gameIsOver){
            state = GameState.DRAW;
    		return;
    	}
    	
    	if(nextRound){
            state = GameState.DRAW;
    		return;
    	}
    	
    	// See how long it was since the last frame.
        long currentTime = System.currentTimeMillis();  // Integer number of milliseconds since 1/1/1970.
        elapsedTime = ((currentTime - lastTime) / 1000.0);  // Tính số giây đã trôi qua
        lastTime = currentTime;  // Our current time is the next frame's last time
    	
        /* I think my elapsed time may be wrong */
        
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
    	
    	if(killsCounter >= 300)
    	{	nextRound = true;
    		killsCounter = 300;
    	}
    	
        // After we have updated the objects in the game, we need to
        //   redraw them.  Enter the 'DRAW' state.
        
        state = GameState.DRAW;
       
        // Careful!  At ANY time after we set this state, the draw method
        //   may execute.  Don't do any further updating.
    }

        /**
     * Draws all the game objects, then enters the wait state.
     * 
     * @param g a valid graphics object.
     */
    public void draw(Graphics g)
    {
        // Nếu game không ở trạng thái vẽ thì không vẽ gì!
        if (state != GameState.DRAW)
            return;
        	
        // Vẽ hình nền.
        g.drawImage(backdrop, 0, 0, null);
     
        // Vẽ đường đi
        g.setColor(new Color (0,76, 153));
        int[] xPos = new int[]{0, 64, 118, 251, 298, 344, 396, 416, 437, 459, 460, 498, 542, 600, 600, 568, 535, 509, 490, 481, 456, 414, 345, 287, 227, 98, 0};
        int[] yPos = new int[]{329, 316, 291, 189, 163, 154, 165, 186, 233, 344, 364, 415, 444, 461, 410, 396, 372, 331, 226, 195, 151, 117, 105, 117, 143, 244, 280};
        g.fillPolygon(xPos, yPos, 27);
        
        // Vẽ đích đến
        g.setColor(new Color(0,255,136));
        g.fillArc(450, 385, 100, 100, 90, 180);
        g.setColor(Color.GREEN);
        int[] xCor = new int[]{600, 588, 574, 566, 557, 557, 563, 572, 576, 584, 600};
        int[] yCor = new int[]{459, 464, 462, 453, 454, 448, 438, 435, 422, 414, 415};
        g.fillPolygon(xCor, yCor, 11);
        
        // Vẽ đường thẳng dọc theo đường
        line.drawLine(g);
        
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
        g.drawString("Số mạng: " + livesCounter, 605, 100);	// lives counter
        g.drawString("Money Earned: " + scoreCounter, 605, 150);	// score counter
        g.drawString("Enemies Stopped: " + killsCounter, 605, 200);
        g.drawString("Blackhole Cost: 100", 610, 380);				// cost for black hole towers
        g.drawString("Sun Cost: 300", 640, 530);					// cost for sun towers
        g.setFont(new Font("Lucidia Sans", Font.ITALIC, 28));		
        g.drawString("Planet Defense", 600, 50);					// writes title
        g.drawLine(600, 50, 800, 50);								// underscore
        g.drawString("Towers", 640, 240);							// writes towers
        g.drawLine(620, 240, 780, 240);								// underscore	
        
        // draw box around Machine Gun Tower icon
        g.setColor(new Color(224, 224, 224));
        g.fillRect(650, 250, 100, 100);
        
        // draw tower in menu area
        MachineGunTower machineGunTower = new MachineGunTower(new Coordinate(700, 300));
        machineGunTower.draw(g);

        // draw box around Normal Tower icon
        g.setColor(new Color(224, 224, 224));
        g.fillRect(660, 270, 110, 100);

        // draw tower in menu area
        NormalTower normalTower = new NormalTower(new Coordinate(700, 300));
        normalTower.draw(g);
        
        // draw box around Sniper Tower icon
        g.setColor(new Color(224, 224, 224));
        g.fillRect(650, 400, 100, 100);
        
        // draw tower in menu area
        SniperTower sniperTower = new SniperTower(new Coordinate(700, 450));
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
        
        ImageLoader loader = ImageLoader.getLoader();	
		Image endGame = loader.getImage("resources/game_over.png"); // load game over image
    	
        if(livesCounter <= 0)										// if game is lost
        	g.drawImage(endGame, 0, 0, null);						// draw "game over"

		if(killsCounter >= 500)										// if game is lost
		{	g.setFont(new Font("Braggadocio", Font.ITALIC, 90));		
        	g.drawString("You Win!!!", 10, 250);					// draw "game over"
		}
		
        // Drawing is now complete.  Enter the WAIT state to create a small
        //   delay between frames.
        
        state = GameState.WAIT;
    }
    
    /**
     * Generates a stream of enemies
     * 
     */
     @Override
    public void generateEnemies()
    {	
    	// adds enemies to list dependent on how many frames have passed
    	if(frameCounter % 30 == 0)								// slow 
    	{
    		enemies.add(new NormalEnemy(line.getStart()));
    	}
 		else if(frameCounter % 25 == 0 && frameCounter >= 50)	// slow
 		{
 			enemies.add(new NormalEnemy(line.getStart()));
 		}
	 	else if(frameCounter % 20 == 0 && frameCounter >= 100)	// medium
	 	{
	 		enemies.add(new NormalEnemy(line.getStart()));
	 		enemies.add(new TankerEnemy(line.getStart()));
	 	}	
 		else if(frameCounter % 15 == 0 && frameCounter >= 150)	// medium
 		{
 			enemies.add(new NormalEnemy(line.getStart()));
 			enemies.add(new TankerEnemy(line.getStart()));
 		}
	 	else if(frameCounter % 10 == 0 && frameCounter >= 200)	// fast
	 	{
	 		enemies.add(new NormalEnemy(line.getStart()));
	 		enemies.add(new TankerEnemy(line.getStart()));
	 		enemies.add(new BossEnemy(line.getStart()));
	 	}
	 	else if(frameCounter % 5 == 0 && frameCounter >= 250)	// fast
	 	{
	 		enemies.add(new NormalEnemy(line.getStart()));
	 		enemies.add(new TankerEnemy(line.getStart()));
	 		enemies.add(new BossEnemy(line.getStart()));
	 	}
    }

     @Override
    public void placeMachineGunTower()
    {
    	/* I need to make it so you can't place towers on path or off the screen */
    	
    	 // variable to hold mouse location
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
    	if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 && 
    		gamePanel.mouseY > 250 && gamePanel.mouseY < 350 && 
    		gamePanel.mouseIsPressed && scoreCounter >= 100)
    	{	// if mouse is pressed on tower icon, create a new object
	    		placingMachineGunTower = true;
	    		newMachineGunTower = new MachineGunTower(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 && 
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 600 && 
        	gamePanel.mouseIsPressed && placingMachineGunTower
        	&& line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
    	{	// if mouse is pressed on game screen, place tower on game screen
	    		newMachineGunTower.setPosition(mouseLocation);
	    		towers.add(new MachineGunTower(mouseLocation));
	    		scoreCounter -= 100;
	    		newMachineGunTower = null;
	    		placingMachineGunTower = false;
    	}
    	
    	// moves tower object with mouse movements
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

        // moves the tower object as mouse moves
        if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 &&
                gamePanel.mouseY > 250 && gamePanel.mouseY < 350 &&
                gamePanel.mouseIsPressed && scoreCounter >= 100)
        {	// if mouse is pressed on tower icon, create a new object
            placingMachineGunTower = true;
            newNormalTower = new NormalTower(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 600 &&
                gamePanel.mouseIsPressed && placingNormalTower
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {	// if mouse is pressed on game screen, place tower on game screen
            newMachineGunTower.setPosition(mouseLocation);
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
    	/* I need to make it so you can't place towers on path or off the screen */
    	
    	 // variable to hold mouse location
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
    	if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 && 
    		gamePanel.mouseY > 400 && gamePanel.mouseY < 500 && 
    		gamePanel.mouseIsPressed && scoreCounter >= 300)
    	{	// if mouse is pressed on tower icon, create a new object
	    		placingSniperTower = true;
	    		newSniperTower = new SniperTower(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 && 
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 600 && 
        	gamePanel.mouseIsPressed && placingSniperTower
        	&& line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
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

    public static void main (String[] args)
    {
        /** Just create a game object.  The game constructor
        *   will do the rest of the work.*/

        new GameStage();

        /** Main exits, but our other thread of execution
        *   will keep going.  We could do other work here if
        *   needed.*/
    }
}