/*
 * This is a game called Flappy Bird
 * Should have moving columns and a square "Bird" that can be controlled with
 * mouse clicks and spacebar.
 */
package flappybird;

//Learned that java.awt is abstract window toolkit to help develop GUI apps
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

//abstract event handlers and listeners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;
/**
 *Implements abstract classes
 * @author cindyakahl
 */
public class FlappyBird implements ActionListener, MouseListener, KeyListener
{

    public static FlappyBird flappyBird;
    
    public final int WIDTH = 800, HEIGHT = 800;
    
    public Renderer renderer; //calls the class
    
    public Rectangle bird; //creates a class for the bird
    
    public ArrayList<Rectangle> columns; //Creating an arraylist of rectangles
    
    public int ticks, yMotion, score; //Motion of the bird
    
    public boolean gameOver, started;
    
    public Random rand;
    
    /**
     * Flappy bird method creates the JFrame window and sets it's size
     * adds the mouse and key listeners to JFrame, creates the objects inside
     * the JFrame. Calls the renderer method to ensure the graphics are
     * buffered twice.
     */
    public FlappyBird()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);
        
        renderer = new Renderer();//calls the Renderer method
        rand = new Random();
        
        //Setting the JFrame
        jframe.add(renderer);
        jframe.setTitle("Flappy Bird"); //creates title for game
        
        //Closes the app when exiting
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jframe.setSize(WIDTH, HEIGHT); //sets width and height to 800
        jframe.setResizable(false); //prevents resizing of jframe box
        jframe.setVisible(true);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        
        //Creating the "Bird"
        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        
        //creates empty arraylist called columns
        columns = new ArrayList<Rectangle>(); 
        
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        
        timer.start();
    }
    
    /**
     * Sets the columns
     * @param start 
     */
    public void addColumn(boolean start)
    {
        
        int space = 300;
        int width = 100;
        
        //Height of columns varies
        int height = 50 + rand.nextInt(300);
        
        if (start)//if its the starting column
        {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 
                    300, HEIGHT - height -120, width, height));
            
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 
                    300, 0, width, HEIGHT - height - space));
            
        }
        else //appending columns
        {
            //starts at 0
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 
                    600, HEIGHT - height - 120, width, height));
            
            //adds new column
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0,
                    width, HEIGHT - height - space));
            
        }
    }
    
    /**
     * Sets the color of the columns
     * @param g sets graphics
     * @param column sets the columns
     */
    public void paintColumn(Graphics g, Rectangle column)
    {
        
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }
    
    public void jump()
    {
        
        if (gameOver)
        {
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;
            
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            
            gameOver = false;
        }
        
        if (!started)
        {
            started = true;
        }
        else if (!gameOver)
        {
            if (yMotion > 0)
            {
                yMotion = 0;
            }
            
            yMotion -= 10;
        }
    }
    
    /**
     * Controls the bird's movement up and down throughout the game
     * A
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        int speed = 10; //Speed of columns

        ticks++; //number of clicks

        if (started)
        {
            for (int i = 0; i < columns.size(); i++)
            {
                Rectangle column = columns.get(i);

                column.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15)
            {
                yMotion += 2;
            }

            for (int i = 0; i < columns.size(); i++)
            {
                Rectangle column = columns.get(i);

                if (column.x + column.width < 0)
                {
                    columns.remove(column);

                    if (column.y == 0)
                    {
                        addColumn(false);
                    }
                }
            }

            bird.y += yMotion;

            //What happens if the bird touches a column as it's passing
            for (Rectangle column : columns)
            {
                if (column.y == 0 && bird.x + bird.width / 2 > column.x + 
                        column.width / 2 - 10 && bird.x + 
                        bird.width / 2 < column.x + column.width / 2 + 10)
                {
                    score++;

                }

                if (column.intersects(bird))
                {
                    gameOver = true;

                    if (bird.x <= column.x)
                    {
                        bird.x = column.x - bird.width;

                    }
                    else
                    {
                        if (column.y != 0)
                        {
                            bird.y = column.y - bird.height;
                        }
                        else if (bird.y < column.height)
                        {
                            bird.y = column.height;
                        }
                    }

                }
            }

            if (bird.y > HEIGHT - 120 || bird.y < 0)
            {
                gameOver = true;
            }

            if (bird.y + yMotion >= HEIGHT - 120)
            {
                bird.y = HEIGHT - 120 - bird.height;
                gameOver = true;
            }

            renderer.repaint(); //not 100% clear on why this is here but doesn't
            //work if it's not.

            }

    }

    /**
     * paints the background, the bird, the grass, and ground.
     * @param g 
     */
    public void repaint(Graphics g)
    {
        //Sets color for JFrame background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Sets color for the "Dirt"
        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        //Sets color for "Grass"
        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);
        
        //Sets color for "Bird"
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        //painting the columns for each rectangle in columns
        for (Rectangle column : columns)
        {
            paintColumn(g, column);
        }

        //Sets the font type, color, and size
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        if (!started)
        {
            g.drawString("Click to start!", 75, HEIGHT / 2 - 50);

        }

        if (gameOver)
        {
            g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
        }

        if (!gameOver && started)
        {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);

        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        flappyBird = new FlappyBird();//Set flappyBird to new a new flappy bird
    }
    
    //Event handlers for the key and mouseclicks
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            jump();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        jump();
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        
    }
}
