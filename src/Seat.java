import java.awt.*;
import javax.swing.ImageIcon;

/**
* This class holds the <code>Transform</code>, <code>Sprite</code>, and <code>BoxCollider</code> for the 
* auditorium seats seen on the <code>Playingfield</code> during a game session.
* @see Sprite
* @see BoxCollider
* @see PlayingField
* @see Transform
*/
public class Seat extends GameObject
{
	private int gridX;
	private int gridY;
	private Mole moleAtSeat;
	
	private static final Image SEAT_IMAGE = new ImageIcon(Seat.class.getResource("images/lectureseat.png")).getImage();

	/**
	* This constructor assigns the <code>Seat</code> a coordinate in 
	* the <code>PlayingField</code> using the 
	* parameters passed in and initializes this seat's moleAtSeat member variable, used for tracking what
	* <code>Mole</code> is placed behind this <code>Seat</code>, to <code>null</code>. This constructor
	* also manufactures the seat's {@link Transform}, {@link Sprite}, and {@link BoxCollider} using 
	* appropriate formulas, resources, and constants as specified in the {@link Global} class.
	* 
	* @param x the horizontal component of this <code>Seat</code> object's placement on the {@link
	* PlayingField} grid which is measured in units of seats.
	* @param y the vertical component of this <code>Seat</code> object's placement on the <code>
	* PlayingField</code> grid which is measured in units of seats.
	*
	* @see PlayingField
	*/
	public Seat(int x, int y)
	{
		//assign to PlayingField Coordinate grid
		gridX = x;
		gridY = y;
		moleAtSeat = null;
		
		//manufacture Seat sprite
		Sprite s = new Sprite();
		s.addFrame(SEAT_IMAGE, Globals.STILL_SPRITE_DURATION);
		sprite = s;
	
		//manufacture transform location
		float transX = Globals.LEFT_MARGIN + (Globals.SEAT_WIDTH  * (gridX + 1)); //X coordinate on frame
		float transY = Globals.TOP_MARGIN + (Globals.SEAT_HEIGHT * (gridY + 1)); //Y coordinate on frame
		Transform t = new Transform(transX, transY);
		transform = t;
		
		//manufacture box collider
		//6px margin on each side left and right
		//10px margin from top
		float boxWidth = Globals.SEAT_WIDTH - (2 * Globals.SEAT_COLLIDER_LEFT_PADDING);
		float boxHeight = Globals.SEAT_HEIGHT - Globals.SEAT_COLLIDER_TOP_PADDING;
		
		BoxCollider b = new BoxCollider(boxWidth, boxHeight, Globals.SEAT_COLLIDER_LEFT_PADDING, 
										Globals.SEAT_COLLIDER_TOP_PADDING);
										
		collider = b;
		
		
	}
	
	/**
	* This method overrides the <code>GameObject</code> class's <code>update()</code> method. Currently it simply calls the
	* super class's method.
	* @see GameObject#update(long)
	*/
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		//add stuff here
	
	}
	
	/**
	* Places the <code>Mole</code> passed in as a parameter into a member variable of this <code>
	* Seat</code> . It also calls the <code>Mole</code> parameter's {@link Mole#setSeatMoleIsAt(Mole)}
	* method for the <code>Mole</code> to register what <code>Seat</code> it is at. 
	*
	* @param m the <code>Mole</code> to be placed at this <code>Seat</code>
	*/
	public void fillSeat(Mole m)
	{
		moleAtSeat = m;
		moleAtSeat.setSeatMoleIsAt(this);
	}
	
	/**
	* Sets this seat's <code>moleAtSeat</code> variable which 
	* points to the <code>Mole</code> that currently occupies this <code>
	* Seat</code> to <code>null</code>. This method is used when a <code>Mole</code> finishes an up and 
	* down phase to prep it for being moved to another <code>Seat</code> or 
	* when a player whacks the <code>Mole</code> either correctly or incorrectly.
	*/
	public void emptySeat()
	{
		//moleAtSeat.removeMoleFromSeat(); //don't use this, creates a stackoverflow
		moleAtSeat = null;
	}
	
	/**
	* @return returns the <code>Mole</code> object at this <code>Seat</code> 
	*/
	public Mole getMoleAtSeat()
	{
		return moleAtSeat;
	}
	
	/**
	* @return returns the <code>String</code> "Seat"
	*/
	public String toString()
	{
		String dStr = "Seat";
		return dStr;
	}
	
}