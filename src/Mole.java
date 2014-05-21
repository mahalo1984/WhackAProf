import java.util.Random;

/**
* This class defines the basic structure of a <code>Mole</code>, which are owned by the 
* <code>PlayingField</code> class. A <code>Mole</code> is a type of <code>GameObject</code> that
* is seen in the program as the <code>Professor</code>s and <code>Dean</code>s that pop up from
* behind the <code>PlayingField</code>'s <code>Seat</code>s.
*/
public abstract class Mole extends GameObject
{
	/**A constant assigned to this mole's lifeState member variable which indicates that
	* the mole has either completed an entire life cycle and needs to be re-spawned or
	* that the mole was smashed by a mallet*/
	public static final int LIFESTATE_DEAD = 0;
	
	/**A constant assigned to this mole's lifeState member variable which indicates that
	* the mole has been spawned and is currently hiding behind a <code>Seat</code>
	* before rising up from behind it.*/
	public static final int LIFESTATE_WAITING = 1;
	
	/**A constant assigned to this mole's lifeState member variable which indicates that
	* that it is currently emerging from behind a <code>Seat</code>*/
	public static final int LIFESTATE_RISING = 2;
	
	/**A constant assigned to this mole's lifeState member variable which indicates that this
	*<code>Mole</code> has finished rising and is staying up for a while.*/
	public static final int LIFESTATE_UP = 3;
	
	/**A constant assigned to this mole's lifeState member variable which indicates that the
	*<code>Mole</code> is retreating back behind the <code>Seat</code>*/
	public static final int LIFESTATE_FALLING = 4;
	
	/**A constant which determines the rate at which the <code>Mole</code> rises and falls*/
	public static final float RISE_AND_FALL_VELOCITY = (Globals.MOLE_HEIGHT / 2.0f) / Globals.MOLE_RISE_AND_FALL_TIME;
	
	
	public static Random generator = new Random(); 
	

	protected float upTime;
	protected float waitTime;
	protected int gridX;
	protected int gridY;
	protected int lifeState; /* 0 is dead, 1 is waiting, 2 is rising, 3 is up, 4 is falling */
	protected float riseAndFallTime;
	protected Seat seatMoleIsAt;
	protected SoundTest[] success;
	protected SoundTest[] failure;
	protected int successCounter;
	protected int failureCounter;
	
	/**
	* Sets this <code>Mole</code> to the first <code>lifeState</code>, assigns it a riseAndFallTime,
	* creates its <code>transform</code>, places its location at <code>(-1, -1,)</code> in the world
	* and sets its <code>seatMoleIsAt</code> member variable to null.
	*/
	public Mole()
	{
		//create sound task arrays, each array holds multiple copies of the same sound
		//its basically a double buffering set up for efficiency purposes
		success = new SoundTest[2];
		failure = new SoundTest[2];
		
		successCounter = 0;
		failureCounter = 0;
	
		lifeState = 1;
		riseAndFallTime = Globals.MOLE_RISE_AND_FALL_TIME;
		
		//manufacture transform with default off screen location
		transform = new Transform(-1.0f, -1.0f);
		seatMoleIsAt = null;
	}
	
	/**
	* Handles the update logic depending upon what state the <code>Mole</code> is currently in.
	*
	* @param elapsedTime the amount of time in milliseconds that has passed since the last iteration.
	*/
	public synchronized void update(long elapsedTime) 
	{
		super.update(elapsedTime);
		
		switch(lifeState)
		{
			case LIFESTATE_DEAD:
				
				break;
				
				
			case LIFESTATE_WAITING:
				
				waitTime -= elapsedTime;
				
				if(waitTime <= 0)
				{
					waitTime = 0;
					lifeState = LIFESTATE_RISING;
					transform.setVelocityY(RISE_AND_FALL_VELOCITY * -1.0f);
				}
				
				break;
			
			case LIFESTATE_RISING:
				upTime -= elapsedTime;
				riseAndFallTime -= elapsedTime;
				
				if(riseAndFallTime <= 0)
				{
					lifeState = LIFESTATE_UP;
					riseAndFallTime = Globals.MOLE_RISE_AND_FALL_TIME;
					transform.setVelocityY(0.0f);
				}
				
				break;
				
			case LIFESTATE_UP:
				upTime -= elapsedTime;
				
				if(upTime <= Globals.MOLE_RISE_AND_FALL_TIME)
				{
					lifeState = LIFESTATE_FALLING;
					transform.setVelocityY(RISE_AND_FALL_VELOCITY);
				}
				
				break;
			
			case LIFESTATE_FALLING:
				upTime -= elapsedTime;
				
				if(upTime <= 0)
				{
					upTime = 0;
					kill();
				}
				
				break;
		
		}
	
	}
	
	/**
	* Sets the <code>Mole</code>'s location on the <code>PlayingField</code> grid by setting
	* two of this mole's member variables, gridX and gridY. This method should be called only 
	* through the <code>PlayingField</code> API because only the playingField is capable of 
	* managing selection conflicts.
	*/
	public void setRandomLocation()
	{
		gridX = generateRandomBoundedBy(0, Globals.NUMBER_OF_ROWS);
		gridY = generateRandomBoundedBy(0, Globals.NUMBER_OF_COLUMNS);
	}
	
	/**
	* Sets the mole's <code>lifeState</code> to <code>LIFESTATE_WAITING</code>
	*/
	public void makeAlive()
	{
		lifeState = 1;
	}
	
	/**
	* Removes the mole from its current seat, sets its grid coordinates to -1's, sets its 
	* <code>Transform</code>'s velocity to 0 and its transform location to (-1,-1)
	*/
	public void kill()
	{
		removeMoleFromSeat();
		lifeState = LIFESTATE_DEAD;
		gridX = -1;
		gridY = -1;
		transform.setVelocityY(0.0f);
		transform.setX(-1.0f);
		transform.setY(-1.0f);
	}
	
	/**
	* @return returns current life state which can be any of the following: 
	* <p>
	* <ul>
	*	<li> 0, <code> LIFESTATE_DEAD</code>: the <code>Mole</code> has finished a life cycle or has 
	*										been struck</li>
	* 	<li> 1, <code> LIFESTATE_WAITING</code>: the <code>Mole</code> is hiding behind a 
	*											<code>Seat</code></li>
	*	<li> 2, <code> LIFESTATE_RISING</code>: the <code>Mole</code> is rising above a 
	*											<code>Seat</code> </li>
	* 	<li> 3, <code> LIFESTATE_UP</code>: the <code>Mole</code> is stationary above the 
	*										<code>Seat</code></li>
	*	<li> 4, <code> LIFESTATE_FALLING</code>: the <code>Mole</code> is returning to behind the 
	*											<code>Seat</code></li>
	* </ul>
	*/
	public int getLifeState()
	{
		return lifeState;
	}
	
	/**
	* @return returns the horizontal component of which <code>Seat</code> the <code>Mole</code> is at
	*/
	public int getGridX()
	{
		return gridX;
	}

	/**
	* @return returns the vertical component of which <code>Seat</code> the <code>Mole</code> is at
	*/
	public int getGridY()
	{
		return gridY;
	}

	/**
	* Sets this <code>Mole</code> to belong at the <code>Seat</code> passed in as a parameter
	* @param the seat the <code>Mole</code> will be set to sit at
	*/
	public void setSeatMoleIsAt(Seat seat)
	{
		seatMoleIsAt = seat;
	}
	
	/**
	* @return returns the <code>Seat</code> this <code>Mole</code> is currently sitting at
	*/
	public Seat getSeatMoleIsAt()
	{
		return seatMoleIsAt;
	}
	
	/**
	* Empties the <code>Seat</code> this <code>Mole</code> is currently sitting at and then sets
	* this current mole's pointer to the <code>Seat</code> that was just emptied to <code>null</code>
	*/
	public void removeMoleFromSeat()
	{
		seatMoleIsAt.emptySeat();
		seatMoleIsAt = null;
	}
	
	/**
	* @param lower the lower-bound of the random number to be generated
	* @param upper the upper-bound of the random number to be generated
	* @return returns a random number bounded by the parameters passed into the function
	*/
	protected static float generateRandomBoundedBy(float lower, float upper)
	{
		return lower + ((float)Math.random() * (upper - lower));
	}

	/**
	* @param lower the lower-bound of the random number to be generated
	* @param upper the upper-bound of the random number to be generated
	* @return returns a random number bounded by the parameters passed into the function
	*/
	protected static int generateRandomBoundedBy(int lower, int upper)
	{
		return generator.nextInt(upper) + lower;
	}
	
	public abstract SoundTest getSuccessSoundTest();
	public abstract SoundTest getFailureSoundTest();

	
	
}