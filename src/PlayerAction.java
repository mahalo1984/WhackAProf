/**
 * Classes that inherit from this one implement a strategy pattern. The class's job is to translate user * input into a player action, the execution of which varies according to what behaviour is initiated.
*/
public abstract class PlayerAction
{
	protected boolean isActionInProgress; 
	protected long duration; //how much time is remaining on the player action
	
	/**
	* Sets the flag isActionInProgress to false and the duration of the PlayerAction to 0.
	* Classes that inherit from this one will set the duration to whatever is appropriate
	*/
	public PlayerAction()
	{
		isActionInProgress = false;
		duration = 0;
	}
	
	/**
	* @return returns true if the action is in progress and false otherwise
	*/
	public boolean getIsActionInProgress()
	{
		return isActionInProgress;
	}
	
	/**
	* @return the amount of time the PlayerAction has left before the action completes
	*/
	public long getDuration()
	{
		return duration;
	}
	
	/**
	* @return returns true if the action is in progress and false if it is not
	*/
	public void initiateAction()
	{
		isActionInProgress = true;
	}
	
	/**
	* Decreases the duration remaining on the PlayerAction by the parameter passed in. If the time
	* remaining is 0 or less than 0, the duration is set to 0.
	* @param elapsedTime the amount of time that has passed since the last iteration
	*/
	public void updateAction(long elapsedTime)
	{
		if(isActionInProgress)
		{
			duration -= elapsedTime;
			
			if(duration <= 0)
			{
				duration = 0;
				isActionInProgress = false;
			}
		}
	}
}