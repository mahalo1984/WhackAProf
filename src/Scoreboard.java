/**
* This class keeps track of the amount of points the player has earned in the game
*/
public class Scoreboard extends GameObject
{
	private int score;

	/**
	* Sets the initial score to 0 and set this object's <code>Transform</code> to the location
	* specified by the parameters.
	*
	* @param x the horizontal component of this object's location in the world 
	* @param y the vertical component of this object's location in the world
	*/
	public Scoreboard(float x, float y)
	{
		score = 0;
		transform = new Transform(x,y);
	}
	
	/**
	* Increments the player's score by the value of a <code>Professor</code> hit as defined in
	* the <code>Global</code> class.
	*/
	public void professorHit()
	{
		score += Globals.PROFESSOR_HIT_POINTS;
	}
	
	/**
	* Increments the player's score by the value of a <code>Dean</code> hit as defined in
	* the <code>Global</code> class.
	*/
	public void deanHit()
	{
		score += Globals.DEAN_HIT_POINTS;
	}
	
	/**
	* ???Something fishy is going on here...???
	*/
	public void update(long elapsedTime)
	{
		//super.update(elapsedTime);
	}
	
	/**
	* @return returns the current score of the <code>Scoreboard</code>
	*/
	public int getScore()
	{
		return score;
	}
	
	/**
	* @return returns a <code>String</code> representation of the score padded with as many zeros
	* to the left as is necessary to keep the number of numerals in the score at a consistent five
	* digits.
	*/
	public String toString()
	{
		String scoreStr = Integer.toString(score);
		String padding = "";
		
		if(score < 10)
		{
			padding = "0000";
		}
		else if(score < 100)
		{
			padding = "000";
		}
		else if(score < 1000)
		{
			padding = "00";
		}
		else if(score < 10000)
		{
			padding = "0";
		}
		
		return padding + scoreStr; 
	}




}