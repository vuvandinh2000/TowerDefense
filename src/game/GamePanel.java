package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener
{
    private GameField enclosingGame;  	// A reference back to the Game object that created 'this' object.
    public int mouseX;				// Tracks X position of mouse events
    public int mouseY;				// Tracks Y position of mouse events
    public boolean mouseIsPressed;

    public GamePanel (GameField enclosingGame)
    {
        // Keep track of the Game object that created this panel.
        //   That way, we can call methods in the game object when needed.
    	
    	this.addMouseListener(this); 			// Listen to our own mouse events.
    	this.addMouseMotionListener(this);		// Listen to mouse movements
        this.enclosingGame = enclosingGame;
    }

    public void paintComponent (Graphics g)
    {
        enclosingGame.draw(g);
    }

    public Coordinate getCoordinate()
    {
    	return new Coordinate(mouseX, mouseY);
    }
    
    /* Overridden methods that report the correct panel size when needed. */
    public Dimension getMinimumSize()
    {
        return new Dimension(50,50);
    }
    public Dimension getMaximumSize()
    {
        return new Dimension(50,50);
    }
    public Dimension getPreferredSize ()
    {
        return new Dimension(900,405); //Size hiển thị
    }

    /* MouseListener methods */
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{	
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;
	}
}
