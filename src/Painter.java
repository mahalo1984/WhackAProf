import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
 
 /*
  * This code has been adapted by Matthew Conroy from an example provided by Game Programming wiki:
  * http://content.gpwiki.org/index.php/Java:Tutorials:Double_Buffering
 */

/**
* This class is responsible for initializing the frame, retrieving the 
* graphics context, and drawing the screen. This class uses a bufferStrategy 
* object for drawing. BufferStrategy is for double buffering.
* @author Matthew Scott Conroy
* @see java.awt.image.BufferStrategy
*/ 
public class Painter extends JFrame 
{
	/**
	* This constructor sets up the properties of the program's frame
	*/
	public Painter() 
	{
		this.setUndecorated(false);
		this.setSize(Globals.FRAME_WIDTH, Globals.FRAME_HEIGHT + Globals.TASK_BAR_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIgnoreRepaint(true);
		this.setVisible(true);
		this.createBufferStrategy(2);
		this.setBackground(Color.blue);
		this.setForeground(Color.white);
		this.setFont(new Font("Times New Roman", Font.PLAIN, 70));
	}

	/**
	* This method returns the graphics context from the bufferStrategy used for double buffering
	* @return the graphics context from the bufferStrategy object that handles double buffering
	*/
	public Graphics2D getGraphics()
	{
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		return (Graphics2D)bufferStrategy.getDrawGraphics();
	}
 
	/**
	* This method prints the contents contained in the bufferStrategy object's context to the screen.
	* There is also a Toolkit call to sync() for handling a latency issue in Linux
	*/
	public void update() 
	{
		BufferStrategy bufferStrategy = this.getBufferStrategy();
	 
		if(!bufferStrategy.contentsLost())
		{
			bufferStrategy.show();
		}
		
		Toolkit.getDefaultToolkit().sync();	//for Linux
	}
 
}