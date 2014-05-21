import java.awt.Image;
import javax.swing.ImageIcon;

/**
* This class represents a type of <code>GameObject</code> whose job is to provide feedback to the
* player after they hit a <code>Mole</code>. There are currently 5 legal 'types' of <code>ScoreBubble
* </code> objects each uniquely instantiated by passing the name of its type as a parameter to the
* constructor. All of these types are of the same class as their behavior is identical in each case,
* differing only by the images they display. <code>ScoreBubble</code> objects present themselves as
* floating textual graphics that rise up like helium balloons and slowly fade or moles holding failing
* papers.
*/
public class ScoreBubble extends GameObject
{
	public static final float RISE_VELOCITY = -0.05f; //makes bubbles rise
	
	
	//the images for the plus five Sprite
	private static final Image PROFESSOR_RULER_IMAGE1 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler1.png")).getImage();
	private static final Image PROFESSOR_RULER_IMAGE2 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler2.png")).getImage();
	private static final Image PROFESSOR_RULER_IMAGE3 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler3.png")).getImage();
	private static final Image PROFESSOR_RULER_IMAGE4 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler4.png")).getImage();
	private static final Image PROFESSOR_RULER_IMAGE5 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler5.png")).getImage();
	private static final Image PROFESSOR_RULER_IMAGE6 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler6.png")).getImage();
	private static final Image PROFESSOR_RULER_IMAGE7 = new ImageIcon(ScoreBubble.class.getResource("images/professorRuler7.png")).getImage();
	
	//images for the plus 10 sprite
	private static final Image DEAN_HAMMER_IMAGE1 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer1.png")).getImage();
	private static final Image DEAN_HAMMER_IMAGE2 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer2.png")).getImage();
	private static final Image DEAN_HAMMER_IMAGE3 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer3.png")).getImage();
	private static final Image DEAN_HAMMER_IMAGE4 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer4.png")).getImage();
	private static final Image DEAN_HAMMER_IMAGE5 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer5.png")).getImage();
	private static final Image DEAN_HAMMER_IMAGE6 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer6.png")).getImage();
	private static final Image DEAN_HAMMER_IMAGE7 = new ImageIcon(ScoreBubble.class.getResource("images/deanHammer7.png")).getImage();
	
	//images for the plus 2 seconds sprite
	private static final Image PLUS_SECONDS_IMAGE1 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds1.png")).getImage();
	private static final Image PLUS_SECONDS_IMAGE2 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds2.png")).getImage();
	private static final Image PLUS_SECONDS_IMAGE3 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds3.png")).getImage();
	private static final Image PLUS_SECONDS_IMAGE4 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds4.png")).getImage();
	private static final Image PLUS_SECONDS_IMAGE5 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds5.png")).getImage();
	private static final Image PLUS_SECONDS_IMAGE6 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds6.png")).getImage();
	private static final Image PLUS_SECONDS_IMAGE7 = new ImageIcon(ScoreBubble.class.getResource("images/plusSeconds7.png")).getImage();
	
	//images for the f minus sprites
	private static final Image DEAN_FMINUS = new ImageIcon(ScoreBubble.class.getResource("images/fminus.png")).getImage();
	private static final Image PROFESSOR_FMINUS = new ImageIcon(ScoreBubble.class.getResource("images/professorfminus.png")).getImage();

	private String scoreType; //name of the kind of scorebubble to create
	private long timeAlive; //how long the object has existed
	private boolean isMarkedForDeath; //whether or not the object has run its course
	
	
	/**
	* This constructor builds a <code>ScoreBubble</code> of the type indicated by the <code>String</code>
	* passed in as a parameter.
	*
	* @param type the name of the kind of <code>ScoreBubble</code> to create, either professorHammer,
	* professorRuler, deanHammer, deanRuler, or plusSeconds.
	* @param x the horizontal component of the object's location on the frame
	* @param y the vertical component of the object's location on the frame
	*/
	public ScoreBubble(String type, float x, float y)
	{
		scoreType = type;
		isMarkedForDeath = false;
		timeAlive = 0;
		transform = new Transform(x,y);
		transform.setVelocityY(RISE_VELOCITY);
		Sprite s = new Sprite();
		//use this switch statement to determine what sprite to fabricate
		//on a hit, a score will such as +10 will materialize and slowly rise and fade to transparency
		//on a miss, a red F- bubble appears along with the bad sound
		switch(type)
		{
			case "professorRuler":
				s.addFrame(PROFESSOR_RULER_IMAGE1, 200);
				s.addFrame(PROFESSOR_RULER_IMAGE2, 200);
				s.addFrame(PROFESSOR_RULER_IMAGE3, 200);
				s.addFrame(PROFESSOR_RULER_IMAGE4, 200);
				s.addFrame(PROFESSOR_RULER_IMAGE5, 200);
				s.addFrame(PROFESSOR_RULER_IMAGE6, 200);
				s.addFrame(PROFESSOR_RULER_IMAGE7, 200);
				break;
				
			case "professorHammer":
				transform.setVelocityY(0f);
				s.addFrame(PROFESSOR_FMINUS, 800);
				break;
				
			case "deanRuler":
				transform.setVelocityY(0f);
				s.addFrame(DEAN_FMINUS,800);
				break;
				
			case "deanHammer":
				s.addFrame(DEAN_HAMMER_IMAGE1, 200);
				s.addFrame(DEAN_HAMMER_IMAGE2, 200);
				s.addFrame(DEAN_HAMMER_IMAGE3, 200);
				s.addFrame(DEAN_HAMMER_IMAGE4, 200);
				s.addFrame(DEAN_HAMMER_IMAGE5, 200);
				s.addFrame(DEAN_HAMMER_IMAGE6, 200);
				s.addFrame(DEAN_HAMMER_IMAGE7, 200);
				break;
				
			case "miss":
				break;
			
			case "plusSeconds":
				s.addFrame(PLUS_SECONDS_IMAGE1, 200);
				s.addFrame(PLUS_SECONDS_IMAGE2, 200);
				s.addFrame(PLUS_SECONDS_IMAGE3, 200);
				s.addFrame(PLUS_SECONDS_IMAGE4, 200);
				s.addFrame(PLUS_SECONDS_IMAGE5, 200);
				s.addFrame(PLUS_SECONDS_IMAGE6, 200);
				s.addFrame(PLUS_SECONDS_IMAGE7, 200);
				
		}
		
		sprite = s;
		
	}
	
	/**
	* Marks this <code>ScoreBubble</code> object to be removed if the amount of time it has existed
	* exceeds the total duration of the animation sequence of this object's <code>Sprite</code>
	*/
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		timeAlive += elapsedTime;
		
		if(timeAlive >= sprite.getTotalDuration())
		{
			isMarkedForDeath = true;
		}
	}
	
	/**
	* @return returns true if this <code>ScoreBubble</code> object is marked to be removed, and false
	* otherwise.
	*/
	public boolean getIsMarkedForDeath()
	{
		return isMarkedForDeath;
	}
}