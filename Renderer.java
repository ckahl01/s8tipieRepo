package flappybird;

import java.awt.Graphics;

import javax.swing.JPanel;



/**
 *Retains everything inside this class
 * @author cindyakahl
 */
public class Renderer extends JPanel
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * constructs a new graphics object
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        
        super.paintComponent(g); //Calls component from parent JPanel first
        
        FlappyBird.flappyBird.repaint(g);//passes Graphics into flappyBird
    }
}

