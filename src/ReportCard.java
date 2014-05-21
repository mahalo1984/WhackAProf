/**
* The purpose of the <code>ReportCard</code> class is to track the performance of a player during
* a gameplay session. It is a ledger that catalogues the number of professors hit, the number
* of deans hit, the number of sorority girls hit, the number of ruler swings, the number of hammer
* swings, the number of F minuses received, and the number of broken hearts (the penalty for hitting
* a sorority girl, which is not yet implemented). From these basic recordings, other data such as
* swing accuracy can be computed. Such information is retrievable by calling the methods of this class.
*/
public class ReportCard
{
	private int numberOfProfessorsHit;
	private int numberOfDeansHit;
	private int numberOfSororityGirlsHit;
	private int numberOfRulerSwings;
	private int numberOfHammerSwings;
	private int numberOfFMinuses;
	private int numberOfBrokenHearts;

	/**
	* Sets every metric that this <code>ReportCard</code> records to 0.
	*/
	public ReportCard()
	{
		numberOfProfessorsHit = 0;
		numberOfDeansHit = 0;
		numberOfSororityGirlsHit = 0;
		numberOfRulerSwings = 0;
		numberOfHammerSwings = 0;
		numberOfFMinuses = 0;
		numberOfBrokenHearts = 0;
	}

	/**
	* Increments the hit count for professors.
	*/
	public void addProfessorHit()
	{
		numberOfProfessorsHit++;
	}
	
	/**
	* Increments the hit count for deans.
	*/
	public void addDeanHit()
	{
		numberOfDeansHit++;
	}
	
	/**
	* Increments the hit count for sorority girls.
	*/
	public void addSororityGirlHit()
	{
		numberOfSororityGirlsHit++;
	}
	
	/**
	* Increments the ruler swing count.
	*/
	public void addRulerSwing()
	{
		numberOfRulerSwings++;
	}
	
	
	/**
	* Increments the hammer swing count.
	*/
	public void addHammerSwing()
	{
		numberOfHammerSwings++;
	}
	
	/**
	* Increments the f minus count.
	*/
	public void addFMinus()
	{
		numberOfFMinuses++;
	}
	
	/**
	* Increments the broken heart count.
	*/
	public void addBrokenHeart()
	{
		numberOfBrokenHearts++;
	}
	
	/**
	* @return returns the total number of professors hit.
	*/
	public int getNumberOfProfessorsHit()
	{
		return numberOfProfessorsHit;
	}
	
	/**
	* @return returns the total number of deans hit.
	*/
	public int getNumberOfDeansHit()
	{
		return numberOfDeansHit;
	}
	
	/**
	* @return returns the total number of girls hit.
	*/
	public int getNumberOfSororityGirlsHit()
	{
		return numberOfSororityGirlsHit;
	}
	
	/**
	* @return returns the total number of ruler swings.
	*/
	public int getNumberOfRulerSwings()
	{
		return numberOfRulerSwings;
	}
	
	/**
	* @return returns the total number of hammer swings.
	*/
	public int getNumberOfHammerSwings()
	{
		return numberOfHammerSwings;
	}
	
	/**
	* @return returns the total number of f minuses received.
	*/
	public int getNumberOfFMinuses()
	{
		return numberOfFMinuses;
	}
	
	/**
	* @return returns the total number of broken hearts received.
	*/
	public int getNumberOfBrokenHearts()
	{
		return numberOfBrokenHearts;
	}
	
	/**
	* @return returns the sum total number of mallet swings, both ruler and hammer swings are
	* included in the total.
	*/
	public int getNumberOfMalletSwings()
	{
		return numberOfRulerSwings + numberOfHammerSwings;
	}
	
	/**
	* @return returns the sum total number of moles hit, including deans and professors.
	*/
	public int getNumberOfMoleHits()
	{
		return numberOfProfessorsHit + numberOfDeansHit;
	}
	
	/**
	* @return returns the total number of mallet swings that didn't hit a mole.
	*/
	public int getNumberOfMisses()
	{
		return getNumberOfMalletSwings() - getNumberOfMoleHits();
	}

	/**
	* @return returns the sum total number of incorrect hits, both f minuses and broken hearts are
	* included in the total.
	*/
	public int getNumberOfBadHits()
	{
		return numberOfFMinuses + numberOfBrokenHearts;
	}
	
	/**
	* @return returns the swing accuracy which is the ratio of moles hit to mallet swings.
	*/
	public double getAccuracy()
	{
		return ((double)getNumberOfMoleHits()) / ((double) getNumberOfMalletSwings());
	}
	
	/**
	* @return returns the total number seconds added to the clock.
	*/
	public int getAmountOfExtraTimeEarned()
	{
		return numberOfDeansHit * (int)Globals.DEAN_HIT_TIME_REWARD;
	}
	
	/**
	* This class grades the player's performance. If the player is
	* less than 500 they will get an F. Increasing in 500 point increments,
	* the grade increases as follows: F,D,D+,C,C+,B,B+,A-,A,A+
	*
	* @param score the final score the player received during a play session
	* @return returns the grade the student received based on his score.
	*
	*/
	public String calculateGrade(int score)
	{
		String grade = "TBA";
		
		if(score < 500)
		{
			grade = "F";
		}
		else if(score < 1000)
		{
			grade = "D";
		}
		else if(score < 1500)
		{
			grade = "D+";
		}
		else if(score < 2000)
		{
			grade = "C";
		}
		else if(score < 2500)
		{
			grade = "C+";
		}
		else if(score < 3000)
		{
			grade ="B";
		}
		else if(score < 3500)
		{
			grade = "B+";
		}
		else if(score < 4000)
		{
			grade = "A-";
		}
		else if(score < 4500)
		{
			grade = "A";
		}
		else if(score >=4500)
		{
			grade = "A+";
		}
		
		return grade;
	}

}