import java.awt.Image;
import javax.swing.ImageIcon;

/**
* This class is a particular kind of <code>Mole</code>. It is mostly a factory for making moles with
* the properties of a <code>Dean</code>. Distinct to this <code>Mole</code> are its sprites and its 
* uptime and waiting and point values which are all set in the {@link Globals} class. It also overrides
* the basic mole <code>kill()</code> method to randomly reassign uptimes and wait times for when the
* mole spawns to a new <code>Seat</code>.
*
* @see Mole
* @see Seat
* @see Globals
* @see Mole#kill()
*/
public class Dean extends Mole
{
	//images for the Dean sprite
	
	private static final Image DEAN_IMAGE = new ImageIcon(Dean.class.getResource("images/dean.png")).getImage();
	private static final Image DEAN_IMAGE1_5 = new ImageIcon(Dean.class.getResource("images/dean1.5.png")).getImage();
	private static final Image DEAN_IMAGE2 = new ImageIcon(Dean.class.getResource("images/dean2.png")).getImage();
	private static final Image DEAN_IMAGE2_5 = new ImageIcon(Dean.class.getResource("images/dean2.5.png")).getImage();
	private static final Image DEAN_IMAGE3 = new ImageIcon(Dean.class.getResource("images/dean3.png")).getImage();
	private static final Image DEAN_IMAGE4 = new ImageIcon(Dean.class.getResource("images/dean4.png")).getImage();
	private static final Image DEAN_IMAGE5 = new ImageIcon(Dean.class.getResource("images/dean5.png")).getImage();
	
	/**
	* Creates the sprites for this <code>GameObject</code> and randomly assigns a <code> waitTime </code>
	* and <code> upTime </code> restricted by the bounds for a <code> Dean </code> set in the
	* <code> Globals </code> class.
	*/
	public Dean()
	{
		super();
		
		for(int i = 0; i < 2; i++)
		{
			success[i] = new SoundTest("sounds/hammer_hit2.wav");
			failure[i] = new SoundTest("sounds/buzz2.wav");
		}
		
		//randomly select upTime and waitTime according to staff type
		upTime = generateRandomBoundedBy(Globals.DEAN_UP_TIME_LOWER_BOUND,
											Globals.DEAN_UP_TIME_UPPER_BOUND);
		waitTime = generateRandomBoundedBy(Globals.DEAN_WAIT_TIME_LOWER_BOUND, 
											Globals.DEAN_WAIT_TIME_UPPER_BOUND);
											
		//manufacture Seat sprite
		Sprite s = new Sprite();
		s.addFrame(DEAN_IMAGE, (long)generateRandomBoundedBy(1500,2500));
		s.addFrame(DEAN_IMAGE1_5, 50);
		s.addFrame(DEAN_IMAGE2, 50);
		s.addFrame(DEAN_IMAGE2_5, 50);
		s.addFrame(DEAN_IMAGE5, 250);
		s.addFrame(DEAN_IMAGE3, 250);
		s.addFrame(DEAN_IMAGE5, 250);
		s.addFrame(DEAN_IMAGE4, 250);
		s.addFrame(DEAN_IMAGE5, 250);
		s.addFrame(DEAN_IMAGE2_5, 50);
		s.addFrame(DEAN_IMAGE2, 50);
		s.addFrame(DEAN_IMAGE1_5, 50);
		sprite = s;
		
		//manufacture box collider
		collider = new BoxCollider(Globals.DEAN_COLLIDER_WIDTH, 
									Globals.DEAN_COLLIDER_HEIGHT, 
									Globals.DEAN_COLLIDER_PADDING_TOP,
									Globals.DEAN_COLLIDER_PADDING_LEFT 
									);
									
	}

	/**
	* In addition to calling the super class, this method also sets new random <code>upTime</code> and
	* <code>waitTime</code> for the <code>Mole</code> within the bounds of the Dean mole's properties
	* set in the <code>Globals</code> file, which contains the constants for game world properties.
	*/
	public void kill()
	{
		super.kill();
		
		upTime = generateRandomBoundedBy(Globals.DEAN_UP_TIME_LOWER_BOUND,
											Globals.DEAN_UP_TIME_UPPER_BOUND);
		waitTime = generateRandomBoundedBy(Globals.DEAN_WAIT_TIME_LOWER_BOUND, 
											Globals.DEAN_WAIT_TIME_UPPER_BOUND);
	}
	
	/**
	*	@return returns the <code>String</code> "Dean" 
	*/
	public String toString()
	{
		String dStr = "Dean";
		return dStr;
	}
	
	public SoundTest getSuccessSoundTest()
	{
		success[successCounter] = new SoundTest("sounds/hammer_hit2.wav");
		
		successCounter++;
		
		if(successCounter >= 2)
		{
			successCounter = 0;
		}
		
		return success[successCounter];
	}
	
	public SoundTest getFailureSoundTest()
	{
		failure[failureCounter] = new SoundTest("sounds/buzz2.wav");
		
		failureCounter++;
		
		if(failureCounter >= 2)
		{
			failureCounter = 0;
		}
		
		return failure[failureCounter];
	}
}