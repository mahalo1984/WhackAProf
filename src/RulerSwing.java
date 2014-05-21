/**
 * This class is used by the Mallet class as part of a basic strategy pattern for the two
 * different types of swings available in gameplay. Admittedly, there is a bit of parallel
 * code duplication for the this class and its brother, HammerSwing class, but having two
 * separate strategy behaviors open the doors to developing several different types of 
 * instruments for smashing moles which could be implemented in future versions.
 */
 
public class RulerSwing extends PlayerAction
{

	public RulerSwing()
	{
		super();
	}
	
	public void initiateAction()
	{
		super.initiateAction();
		duration = Globals.RULER_SWING_DURATION;
	}

}