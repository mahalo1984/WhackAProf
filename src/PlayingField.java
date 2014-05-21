/**
 * This class manages the moles and seats on screen to ensure that more than one {@link Mole} 
 * doesn't occupy a particular {@link Seat} at any given time. It is a mediator 
 * class between the {@link Seat} and {@link Mole} classes. It
 * is responsible for generating the array of seats and moles and for updating their states.
 */
public class PlayingField
{
	private Seat[][] seats;
	private Mole[] moles;
	

	/**
	* Creates an array of Moles and a 2-dimensional array of Seats and then fills 
	* the moles array with {@link Professor} and {@link Dean} objects. The constructor
	* also places the moles at their initial locations (grid seating arrangement location,
	* not world coordinate location).
	*/
	public PlayingField()
	{
		//recall that the rows and columns terminology is accidentally swapped, consult Globals for
		//more clarification
		seats = new Seat[Globals.NUMBER_OF_ROWS][Globals.NUMBER_OF_COLUMNS]; 
		moles = new Mole[Globals.NUMBER_OF_MOLES];

		//fill seat array with seats
		for(int i = 0; i < Globals.NUMBER_OF_ROWS; i++)
		{
			for(int j = 0; j < Globals.NUMBER_OF_COLUMNS; j++)
			{
				seats[i][j] = new Seat(i, j);
			}	
		}
		
		//add professors to mole array and place them at starting locations
		for(int i = 0; i < Globals.NUMBER_OF_PROFESSORS; i++)
		{
			moles[i] = new Professor();
			placeMole(moles[i]);
		}
		
		//add deans to mole array and place them at starting locations
		for(int i = Globals.NUMBER_OF_PROFESSORS; i < Globals.NUMBER_OF_DEANS + Globals.NUMBER_OF_PROFESSORS; i++)
		{
			moles[i] = new Dean();
			placeMole(moles[i]);
		}
	
	}
	
	/**
	* This method updates all the moles in the <code>PlayingField</code>. If a mole object is registered
	* as dead, it is respawned by calling its <code>makeAlive()</code> method and placed back onto the
	* field. All moles are updated here by calling their <code> update() </code> method.
	*
	* @param elapsedTime the amount of time that has passed in milliseconds since the last iteration
	*/
	public void updatePlayingField(long elapsedTime)
	{
		for(int i = 0; i < Globals.NUMBER_OF_MOLES; i++)
		{
			Mole mole = moles[i];
			
			if(mole.getLifeState() == Mole.LIFESTATE_DEAD)
			{
				mole.makeAlive();
				placeMole(mole);
			}
			
			moles[i].update(elapsedTime);
		}

	}
	

	/**
	* This method removes the <code>Mole</code> passed in as a parameter from the current <code> 
	* Seat </code> it is at (which is recorded by the seat itself) by calling the <code>Seat</code>'s
	* <code>emptySeat()</code> method and then calls the <code> Mole</code>'s <code> kill() </code> 
	* method.
	*
	* @see Seat#emptySeat()
	* @see Mole#kill()
	* @param mole the mole that is to be killed by the method
	*/
	public void killMole(Mole mole)
	{
		seats[mole.getGridX()][mole.getGridY()].emptySeat();
		mole.kill();
	
	}
	
	/**
	* @return returns the <code>Seat</code> at the specified location in this <code>PlayingField</code>,
	* recall that the seats are arranged in a 2-dimensional array.
	* 
	* @param i the column of the seat that is being referred to
	* @param j the row of the seat that is being referred to
	*/
	public Seat getSeat(int i, int j)
	{
		return seats[i][j];
	}
	
	/**
	* @param i the location of the mole in the array contained in <code>PlayingField</code>
	* @returns the mole from this <code> PlayingField </code> at the location in the array specified
	* by the parameter passed in.
	*/
	public Mole getMole(int i)
	{
		return moles[i];
	}
	

	/**
	* Assigns a mole to a particular randomly chosen <code>Seat</code> but only if that seat is empty,
	* meaning that it's <code>moleAtSeat</code> variable is <code>null</code>. 
	*
	* @param the <code>Mole</code> that is to be placed in a new <code>Seat</code>
	*/
	private void placeMole(Mole mole)
	{
		boolean hasMoleBeenPlaced = false;
		
		while(hasMoleBeenPlaced == false)
		{
			
			mole.setRandomLocation();
			Seat seat = seats[mole.getGridX()][mole.getGridY()];
			
			if(seat.getMoleAtSeat() == null)
			{
				//put the mole in this seat's moleAtSeat variable
				seat.fillSeat(mole);
				
				//update the mole's transform
				mole.getTransform().setX(seat.getTransform().getX()); 
				mole.getTransform().setY(seat.getTransform().getY());
				
				hasMoleBeenPlaced = true;
			}
			

		}
		
	}
	
}
