/**
* This class represents the PlayerAction that is initiated when the player right clicks during a game
* session.
*/
public class HammerSwing extends PlayerAction
{

	public HammerSwing()
	{
		super();
	}
	
	
	public void initiateAction()
	{
		super.initiateAction();
		duration = Globals.HAMMER_SWING_DURATION;
	}

}