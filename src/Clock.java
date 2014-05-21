/**
* This class manages the state of the timer that counts down in the videogame during a play session.
*/
public class Clock extends GameObject
{
	private int time; //in seconds
	private boolean isTimeUp;
	private int maxTime;
	private long counter;
	
	
	/**
	* 
	* @param time the initial time set on the clock to count down from
	* @param x the horizontal component of the location of the clock on screen to be assigned to the
	*          transform of this <code>GameObject</code>
	* @param y the vertical component of the location of the clock on screen to be assigned to the
	*          transform of this <code>GameObject</code>
	* @param maxTime the maximum amount of time the clock is allowed to be assigned, useful if the 
	*       		player is awarded time during gameplay in order to prevent exorbitant amounts
	* 				of time
	*/
	public Clock(int time, float x, float y, int maxTime)
	{
		this.time = time;
		transform = new Transform(x,y);
		this.maxTime = maxTime;
		isTimeUp = false;
	}
	
	/**
	* Adds the amount passed in as a parameter to a counter. If the counter total exceeds
	* or equals a thousand, the clock is decremented by a second and the remainder of
	* milliseconds greater than 1000 is assigned as the new counter value.
	*
	* @param millisecondsElapsed the number of milliseconds since the last iteration
	*/
	public void update(long millisecondsElapsed)
	{
		//super.update(millisecondsElapsed);
		
		counter += millisecondsElapsed;
		
		//1000 is in the unit of milliseconds, so here we are dealing with a single second as 
		//the atomic unit
		if(counter >= 1000)
		{
			long decrement = counter / 1000;
			long remainder = counter % 1000;
			
			decrementTime(decrement);
			counter = remainder;
		}
	}
	
	/**
	* Sets the time to the value passed in as a parameter. If the new time is less than
	* 0, the clock's <code>isTimeUp</code> flag is set to true. If time is greater than
	* the maximum time allowed, the clock is set to the maximum amount of time allowed.
	* @param seconds the time in units of seconds to set the clock to
	*/
	public void setTime(int seconds)
	{
		this.time = seconds;
		
		if(time <= 0)
		{
			isTimeUp = true;
		}
		
		if(time > maxTime)
		{
			time = maxTime;
		}
		
	}
	
	/**
	* @return the amount of time remaining on the clock in seconds
	*/
	public long getTime()
	{
		return time;
	}
	
	/**
	* Increases the time on this <code>Clock</clock>by the number of seconds passed in as a parameter. If
	* the new time is greater than the maximum allowable time, the clock is set to the maximum
	* allowable time.
	*
	* @param seconds the amount of seconds to increase the clock by
	*/
	public void incrementTime(long seconds)
	{
		time += seconds;
		
		if(time > maxTime)
		{
			time = maxTime;
		}
	}
	
	/**
	* Decreases the time on this <code>Clock</code> by the number of seconds passed in as a parameter. If
	* the new time is less than 0, the <code>isTimeUp</code> flag is set to <code>true</code>
	*
	* @param seconds the amount of seconds to increase the clock by
	*/
	public void decrementTime(long seconds)
	{
		time -= seconds;
		
		if (time <= 0)
		{
			isTimeUp = true;
		}
	}
	
	/**
	* @return returns the number of minutes remaining on the <code>Clock</clock>
	*/
	public int getMinutes()
	{
		return time / 60;
	}
	
	/**
	* @return returns the number of seconds remaining on this <code>Clock</clock>
	*/
	public int getSeconds()
	{
		return time % 60;
	}
	
	/**
	* @return returns true if the timer is 0 or less than zero and false otherwise
	*/
	public boolean getIsTimeUp()
	{
		return isTimeUp;
	}
	
	/**
	* @return returns a nicely formatted String of the remaining time of the format mm:ss
	*/
	public String toString()
	{
		String minutesStr = Integer.toString(getMinutes());
		String secondsStr;
		
		int seconds = getSeconds();
		if(seconds < 10)
		{
			secondsStr = "0" + Integer.toString(seconds);
		}
		else
		{
			secondsStr = Integer.toString(seconds);
		}
		
		String timeAsText = minutesStr + ":" + secondsStr;
		
		return timeAsText;
	}
	
}