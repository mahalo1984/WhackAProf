import java.awt.Image;
import javax.swing.ImageIcon;

/**
* This class is a particular kind of <code>Mole</code>. It is mostly a factory for making moles with
* the properties of a professor. Distinct to this <code>Mole</code> are its sprites and its uptime and 
* waiting and point values which are all set in the {@link Globals} class. It also overrides the basic 
* mole <code>kill()</code> method to randomly reassign uptimes and wait times for when the mole spawns 
* to a new <code>Seat</code>.
*
* @see Mole
* @see Seat
* @see Globals
* @see Mole#kill()
*/
public class Professor extends Mole
{
	//images for when the professor is blinking
	private static final Image PROFESSOR_IMAGE = new ImageIcon(Professor.class.getResource("images/professor.png")).getImage();
	private static final Image PROFESSOR_IMAGE2 = new ImageIcon(Professor.class.getResource("images/professor2.png")).getImage();
	private static final Image PROFESSOR_IMAGE3 = new ImageIcon(Professor.class.getResource("images/professor3.png")).getImage();
	private static final Image PROFESSOR_IMAGE4 = new ImageIcon(Professor.class.getResource("images/professor4.png")).getImage();
	private static final Image PROFESSOR_IMAGE5 = new ImageIcon(Professor.class.getResource("images/professor5.png")).getImage();
	
	//images for when the professor holds up his magnifying glass
	private static final Image PROFESSOR_IMAGE_MAG1 = new ImageIcon(Professor.class.getResource("images/professormag1.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG2 = new ImageIcon(Professor.class.getResource("images/professormag2.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG3 = new ImageIcon(Professor.class.getResource("images/professormag3.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG4 = new ImageIcon(Professor.class.getResource("images/professormag4.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG5 = new ImageIcon(Professor.class.getResource("images/professormag5.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG6 = new ImageIcon(Professor.class.getResource("images/professormag6.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG7 = new ImageIcon(Professor.class.getResource("images/professormag7.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG8 = new ImageIcon(Professor.class.getResource("images/professormag8.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG9 = new ImageIcon(Professor.class.getResource("images/professormag9.png")).getImage();
	private static final Image PROFESSOR_IMAGE_MAG10 = new ImageIcon(Professor.class.getResource("images/professormag10.png")).getImage();
	
	/**
	* Creates the sprites for this <code>GameObject</code> and randomly assigns a <code> waitTime </code>
	* and <code> upTime </code> restricted by the bounds for a <code> Professor </code> set in the
	* <code> Globals </code> class.
	*/
	public Professor()
	{
		super();
		
		for(int i = 0; i < 2; i++)
		{
			success[i] = new SoundTest("sounds/ruler_hit.wav");
			failure[i] = new SoundTest("sounds/buzz2.wav");
		}
		
		upTime = generateRandomBoundedBy(Globals.PROFESSOR_UP_TIME_LOWER_BOUND,
											Globals.PROFESSOR_UP_TIME_UPPER_BOUND);
		waitTime = generateRandomBoundedBy(Globals.PROFESSOR_WAIT_TIME_LOWER_BOUND, 
											Globals.PROFESSOR_WAIT_TIME_UPPER_BOUND);
	
		//manufacture Seat sprite
		Sprite s = new Sprite();
		s.addFrame(PROFESSOR_IMAGE, (long) generateRandomBoundedBy(1500,2500));
		s.addFrame(PROFESSOR_IMAGE2, 25);
		s.addFrame(PROFESSOR_IMAGE3, 25);
		s.addFrame(PROFESSOR_IMAGE4, 25);
		s.addFrame(PROFESSOR_IMAGE5, 75);
		s.addFrame(PROFESSOR_IMAGE4, 25);
		s.addFrame(PROFESSOR_IMAGE3, 25);
		s.addFrame(PROFESSOR_IMAGE2, 25);
		
		if(generateRandomBoundedBy(0,2) == 1)
		{
			System.out.println("Inside add mag frames.");
			//bring magnifying glass up
			s.addFrame(PROFESSOR_IMAGE_MAG1, 40);
			s.addFrame(PROFESSOR_IMAGE_MAG2, 35);
			s.addFrame(PROFESSOR_IMAGE_MAG3, 35);
			s.addFrame(PROFESSOR_IMAGE_MAG4, 40);
			s.addFrame(PROFESSOR_IMAGE_MAG5, 800);
			//blink
			s.addFrame(PROFESSOR_IMAGE_MAG6, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG7, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG8, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG9, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG10, 75);
			s.addFrame(PROFESSOR_IMAGE_MAG9, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG8, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG7, 25);
			s.addFrame(PROFESSOR_IMAGE_MAG6, 25);
			
			int secondBlinkSwitch = generateRandomBoundedBy(0,2);
			
			if(secondBlinkSwitch == 1)
			{
				System.out.println("Inside inner ad mag frames.");
				//pause
				s.addFrame(PROFESSOR_IMAGE_MAG5, 1200);
				//second blink
				s.addFrame(PROFESSOR_IMAGE_MAG6, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG7, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG8, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG9, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG10, 75);
				s.addFrame(PROFESSOR_IMAGE_MAG9, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG8, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG7, 25);
				s.addFrame(PROFESSOR_IMAGE_MAG6, 25);
			}
			
			
			//pause
			s.addFrame(PROFESSOR_IMAGE_MAG5, 650);
			
			//bring magnifying glass down
			s.addFrame(PROFESSOR_IMAGE_MAG4, 40);
			s.addFrame(PROFESSOR_IMAGE_MAG3, 35);
			s.addFrame(PROFESSOR_IMAGE_MAG2, 35);
			s.addFrame(PROFESSOR_IMAGE_MAG1, 40);
		}
		
		sprite = s;
	
		//manufacture box collider
		collider = new BoxCollider(Globals.PROFESSOR_COLLIDER_WIDTH, 
									Globals.PROFESSOR_COLLIDER_HEIGHT, 
									Globals.PROFESSOR_COLLIDER_PADDING_TOP, 
									Globals.PROFESSOR_COLLIDER_PADDING_LEFT
									);
										
	}
	
	/**
	* In addition to calling the super class, this method also sets new random <code>upTime</code> and
	* <code>waitTime</code> for the <code>Mole</code> within the bounds of the Professor mole's properties
	* set in the <code>Globals</code> file, which contains the constants for game world properties.
	*/
	public void kill()
	{
		super.kill();
		
		upTime = generateRandomBoundedBy(Globals.PROFESSOR_UP_TIME_LOWER_BOUND,
											Globals.PROFESSOR_UP_TIME_UPPER_BOUND);
		waitTime = generateRandomBoundedBy(Globals.PROFESSOR_WAIT_TIME_LOWER_BOUND, 
											Globals.PROFESSOR_WAIT_TIME_UPPER_BOUND);
	}
	
	public String toString()
	{
		String pStr = "Professor";
		return pStr;
	}
	
	public SoundTest getSuccessSoundTest()
	{
		success[successCounter] = new SoundTest("sounds/ruler_hit.wav");
		
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