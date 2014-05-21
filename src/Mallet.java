import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
* This class maintains the state of the mallet that the player uses to interact with the game.
* Using the mousePressed() method of the MouseListener interface, this class sets flags that track
* whether the right or left button has been pressed. An appropriate flag is set only if neither 
* flag is set. The flag is set to false by other classes that use the mallet object after they 
* have processed the state of the mallet. The mallet relies on two Sprites, hammerSwing and rulerSwing,
* to handle its animations when in that animations respective swing state.
* <p>
* To use the mallet class you would simply check whether or not the left or the right is pressed
* (only at most one at any given time will be due to the logical conditions for setting the flag)
* in the class or program that is using the mallet. After you process the state, you should release
* the state by calling setIsRightPressed() or setIsLeftPressed(). This frees the mallet to 'listen'
* for the next event.
* <p>
* The reason the input handling is mediated this way through the mallet class is to enable an
* input delay so that players must wait the amount of time it takes for a mallet to swing before
* they can swing again. Going through the mallet object, other mouse clicks are effectively blocked
* until a swing has swung to completion.
*/
public class Mallet extends GameObject implements MouseListener, MouseMotionListener
{
	private static final Image CROSSHAIR_IMAGE = new ImageIcon(Mallet.class.getResource("images/Crosshair.png")).getImage();
	
	private static final Image RULER1 = new ImageIcon(Mallet.class.getResource("images/ruler[1].png")).getImage();
	private static final Image RULER2 = new ImageIcon(Mallet.class.getResource("images/ruler[2].png")).getImage();
	private static final Image RULER3 = new ImageIcon(Mallet.class.getResource("images/ruler[3].png")).getImage();
	private static final Image RULER4 = new ImageIcon(Mallet.class.getResource("images/ruler[4].png")).getImage();
	
	private static final Image HAMMER1 = new ImageIcon(Mallet.class.getResource("images/hammer[1].png")).getImage();
	private static final Image HAMMER2 = new ImageIcon(Mallet.class.getResource("images/hammer[2].png")).getImage();
	private static final Image HAMMER3 = new ImageIcon(Mallet.class.getResource("images/hammer[3].png")).getImage();
	private static final Image HAMMER4 = new ImageIcon(Mallet.class.getResource("images/hammer[4].png")).getImage();
	
	private static final Image GHOST = new ImageIcon(Mallet.class.getResource("images/ghost.png")).getImage();
	
	private Sprite crossHair;
	private Sprite rulerSwingSprite;
	private Sprite hammerSwingSprite;
	private Sprite ghost;
	
	private RulerSwing rulerSwing;
	private HammerSwing hammerSwing;
	private boolean isRightPressed;
	private boolean isLeftPressed;
	private boolean isMalletFrozen;
	
	private SoundTest [] hammerSwish;
	private SoundTest [] rulerSwish;
	private int rulerSwishCounter;
	private int hammerSwishCounter;
	private boolean doesSoundNeedToBeTriggered;
	
	/**
	* Initializes the Mallet member variables isRightPressed, isLeftPressed, and isMalletFrozen to false.
	* Creates the Sprites for hammerSwing and rulerSwing. And creates the crosshair and ghost(invisible)
	* sprites for the mallet when it is not in a swing state and when it leaves the program screen.
	*
	* @param p the frame of the program
	*/
	public Mallet(Painter p)
	{
		rulerSwishCounter = 0;
		hammerSwishCounter = 0;
		hammerSwish = new SoundTest[10];
		rulerSwish = new SoundTest[10];
		doesSoundNeedToBeTriggered = false;
	
		//load several swish sounds so that multiple can be played at once
		for(int i = 0; i < 10; i++)
		{
			rulerSwish[i] = new SoundTest("sounds/ruler_swish.wav");
			hammerSwish[i] = new SoundTest("sounds/hammer_swish.wav");
		}
		
		isRightPressed = false;
		isLeftPressed = false;
		isMalletFrozen = false;
		
		p.addMouseListener(this);
		p.addMouseMotionListener(this);
		
		rulerSwing = new RulerSwing();
		hammerSwing = new HammerSwing();
		
		//create mallet sprites for stationary and swinging
		crossHair = new Sprite();
		crossHair.addFrame(CROSSHAIR_IMAGE, Globals.STILL_SPRITE_DURATION);
		sprite = crossHair;
		
		rulerSwingSprite = new Sprite();
		rulerSwingSprite.addFrame(RULER1, Globals.RULER_SPRITE_FRAME_DURATION);
		rulerSwingSprite.addFrame(RULER2, Globals.RULER_SPRITE_FRAME_DURATION);
		rulerSwingSprite.addFrame(RULER3, Globals.RULER_SPRITE_FRAME_DURATION);
		rulerSwingSprite.addFrame(RULER4, Globals.RULER_SPRITE_FRAME_DURATION);
		
		hammerSwingSprite = new Sprite();
		hammerSwingSprite.addFrame(HAMMER1, Globals.HAMMER_SPRITE_FRAME_DURATION);
		hammerSwingSprite.addFrame(HAMMER2, Globals.HAMMER_SPRITE_FRAME_DURATION);
		hammerSwingSprite.addFrame(HAMMER3, Globals.HAMMER_SPRITE_FRAME_DURATION);
		hammerSwingSprite.addFrame(HAMMER4, Globals.HAMMER_SPRITE_FRAME_DURATION);
		
		ghost = new Sprite();
		ghost.addFrame(GHOST, Globals.STILL_SPRITE_DURATION);
		
		//create Mallet Transform
		transform = new Transform(0.0f,0.0f);
		
		
	}
	
	/** 
	* @return the value of the flag that indicates whether or not the right mouse button was pressed 
	*/
	public boolean getIsRightPressed()
	{
		return isRightPressed;
	}
	
	/**
	* @return the value of the flag that indicates whether or not the left mouse button was pressed
	*/
	public boolean getIsLeftPressed()
	{
		return isLeftPressed;
	}
	
	/**
	* Releases the isRightPressed flag, setting it to false, so that a new swing can be initiated
	*/
	public void setIsRightPressedToFalse()
	{
		isRightPressed = false;
	}
	
	/**
	* Releases the isLeftPressed flag, setting it to false, so that a new swing can be initiated
	*/
	public void setIsLeftPressedToFalse()
	{
		isLeftPressed = false;
	}
	
	/**
	* A method from the MouseListener interface that is triggered when mousePressed events occur.
	* The main purpose of this method is to initiate the appropriate playerAction object when a
	* mouse event is received. The appropriate sprite for the corresponding player action is also
	* set to the mallet's sprite member (inherited from GameObject). If a left mousePressed or 
	* mouseClicked event is received, the rulerSwing PlayerAction's initiateAction() method is invoked,
	* if the right mousePressed or mouseClicked event is received, the hammerSwing PlayerAction's 
	* initiateAction() method is invoked. In this implementation, the isLeftPressed or isRightPressed 
	* flags of the mallet object are set to true and the PlayerAction is initiated only if neither is
	* currently set to true and the mallet is not frozen.
	* @param e the mousePressed event that was issued when the user clicked a mouse button
	*/
	public void mousePressed(MouseEvent e)
	{
		if(!isMalletFrozen)
		{
		
			if(e.getButton() == Globals.LEFT_MOUSE_BUTTON)
			{
				if(rulerSwing.getIsActionInProgress() == false && hammerSwing.getIsActionInProgress() == false)
				{
					doesSoundNeedToBeTriggered = true;
					isLeftPressed = true;
					rulerSwing.initiateAction();
					sprite = rulerSwingSprite;
				}
			}
			else if(e.getButton() == Globals.RIGHT_MOUSE_BUTTON)
			{
				if(rulerSwing.getIsActionInProgress() == false && hammerSwing.getIsActionInProgress() == false)
				{
					doesSoundNeedToBeTriggered = true;
					isRightPressed = true;
					hammerSwing.initiateAction();
					sprite = hammerSwingSprite;
				}
			}
		}
	}
	
	/**
	* Sets the transform of the mallet object to the new location contained in the event passed in as
	* a parameter. 
	* @param e the mouseDragged event containing the new location of the mouse cursor
	*/
	public void mouseDragged(MouseEvent e)
	{
		updateMalletLocation(e);
	}
	
	/**
	* Sets the transform of the mallet object to the new location contained in the event passed in as
	* a parameter. 
	* @param e the mouseDragged event containing the new location of the mouse cursor
	*/
	public void mouseMoved(MouseEvent e)
	{
		updateMalletLocation(e);
	}
	
	/**
	* Sets the transform of the mallet object to the new location contained in the event passed in as
	* a parameter. 
	* @param e the mouseDragged event containing the new location of the mouse cursor
	*/
	private void updateMalletLocation(MouseEvent e)
	{
		this.getTransform().setX((float)e.getX());
		this.getTransform().setY((float)e.getY());
	}
	
	/**
	* Calls the standard GameObject update() method and then, depending on if any PlayerAction'screen
	* are in progress, they are updated with the elapsed time parameter as well. Finally, the method
	* checks whether or not the swing is finished and returns accordingly.
	* @param elapsedTime the amount of time that has passed since the last iteration in milliseconds
	* @return whether or not a mallet swing has just finished
	*/
	public boolean updateMallet(long elapsedTime)
	{
		update(elapsedTime);
		
		boolean isSwingFinished = false;
		
		if(rulerSwing.getIsActionInProgress() == true)
		{
			rulerSwing.updateAction(elapsedTime);
			
			if(rulerSwing.getIsActionInProgress() == false)
			{
				isSwingFinished = true;
				sprite = crossHair;
			}
		}
		else if(hammerSwing.getIsActionInProgress() == true)
		{
			hammerSwing.updateAction(elapsedTime);
			
			if(hammerSwing.getIsActionInProgress() == false)
			{
				isSwingFinished = true;
				sprite = crossHair;
			}
		}
		
		return isSwingFinished; 
	}
	
	/**
	* Sets the mallet's sprite member inherited from GameObject to the crossHair sprite.
	*/
	public void setSpriteToCrossHair()
	{
		sprite = crossHair;
	}
	
	/**
	* Sets the mallet's sprite member to be invisible using a blank sprite object named ghost.
	*/
	public void setSpriteToGhost()
	{
		sprite = ghost;
	}
	
	//required by the MouseListener interface but not needed by the program
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	
	/**
	* Sets the mouse cursor sprite to crosshair upon re-entry into the program's frame (when the cursor
	* is off the frame it is currently set to invisible to avoid having a dead crosshair on screen)
	*/
	public void mouseEntered(MouseEvent e)
	{
		setSpriteToCrossHair();
	}
	
	/**
	* Sets the mouse cursor sprite to invisible when the cursor leaves the program's frame so that there
	* is not a dead crosshair on the screen.
	*/
	public void mouseExited(MouseEvent e)
	{
		setSpriteToGhost();
	}
	
	/**
	* Sets a flag called isMalletFrozen to true so that player actions are not initiated while gameplay
	* is not happening. This is useful before and after a play session as well as during a pause. This way
	* meaningless animation won't occur on clicks. The report card also won't record these events in 
	* the final report on the player's performance, keeping the numbers pure.
	*/
	public void freezeMallet()
	{
		isMalletFrozen = true;
	}
	
	/**
	* Sets the isMalletFrozen flag to false so that clicking events once again create player actions and
	* report to the report card. See freezeMallet()
	*/
	public void thawMallet()
	{
		isMalletFrozen = false;
	}
	
	/**
	* @return returns a sound to be played in its own thread from a pool of 10 identical sounds. The one to be returned is 
	* in line in a circular queue
	*/
	private SoundTest getHammerSwish()
	{
		hammerSwish[hammerSwishCounter] = new SoundTest("sounds/hammer_swish.wav");
		hammerSwishCounter++;
		if(hammerSwishCounter > 9)
		{
			hammerSwishCounter = 0;
		}
		
		doesSoundNeedToBeTriggered = false;
		
		return hammerSwish[hammerSwishCounter];
	}

	/**
	* @return returns a sound to be played in its own thread from a pool of 10 identical sounds. The one to be returned is 
	* in line in a circular queue
	*/
	private SoundTest getRulerSwish()
	{
		rulerSwish[rulerSwishCounter] = new SoundTest("sounds/ruler_swish.wav");
		rulerSwishCounter++;
		if(rulerSwishCounter > 9)
		{
			rulerSwishCounter = 0;
		}
		
		doesSoundNeedToBeTriggered = false;
		
		return rulerSwish[rulerSwishCounter];	
	}
	
	public SoundTest getSwish()
	{
		SoundTest swish = null;
		
		if(rulerSwing.getIsActionInProgress() && doesSoundNeedToBeTriggered)
		{
			swish = getRulerSwish();
			doesSoundNeedToBeTriggered = false;
		}
		else if (hammerSwing.getIsActionInProgress() && doesSoundNeedToBeTriggered)
		{
			swish = getHammerSwish();
			doesSoundNeedToBeTriggered = false;
		}
		

		return swish;
	}
	
	public boolean getDoesSoundNeedToBeTriggered()
	{
		return doesSoundNeedToBeTriggered;
	}
	
}