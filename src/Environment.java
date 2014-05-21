/*
 * This class is responsible for updating the state of the 3 objects on screen during gameplay.
 * It functions as a mediator class between the Scoreboard, Clock, and PlayingField class.
*/
import java.awt.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.net.URL;

/** This class manages the state of the {@link Scoreboard}, {@link Clock}, and 
* {@link PlayingField} objects. It also handles player visual feedback during 
* {@link Mallet} strikes by maintaining a list and array of {@link ScoreBubble} objects. 
*/
public class Environment
{
	private Scoreboard scoreboard;
	private Clock clock;
	private PlayingField playingField;
	private Image background;
	private Image instructions;
	private ArrayList<ScoreBubble> scoreBubbles;
	private ScoreBubble[][] fminuses;	
	

	/**
	* Creates a new scoreboard, clock, playingField, ArrayList of ScoreBubble objects for positive hits,
	* and 2-dimensional array for ScoreBubble objects that are fminuses. Additionally, the background
	* and instruction images are loaded.
	*/
	public Environment()
	{
		scoreboard = new Scoreboard(Globals.SCOREBOARD_X, Globals.SCOREBOARD_Y);
		clock = new Clock(Globals.CLOCK_STARTING_TIME_IN_SECONDS, Globals.CLOCK_X, Globals.CLOCK_Y, Globals.CLOCK_MAX_TIME_IN_SECONDS);
		playingField = new PlayingField();
		//background = new ImageIcon("images/Background.png").getImage();
		URL backgroundUrl = Environment.class.getResource("images/Background.png");
		background = new ImageIcon(backgroundUrl).getImage();
		URL instructionsUrl = Environment.class.getResource("images/InstructionPanel.png");
		instructions = new ImageIcon(instructionsUrl).getImage();
		scoreBubbles = new ArrayList<ScoreBubble>();
		fminuses = new ScoreBubble[Globals.NUMBER_OF_ROWS][Globals.NUMBER_OF_COLUMNS];
		
	}
	
	/**
	* This method updates the score according to what type of mole was passed in as a parameter.
	* It also adds positive scoreBubbles to the Environment class's ArrayList, giving them the 
	* location of the mole being killed. Finally, the playingField's killMole method is invoked
	* passing the mole parameter through.
	*
	* @param mole a mole in the game that is to be terminated
	*/
	public void killMole(Mole mole)
	{
		if(mole instanceof Professor)
		{
			//Professor was hit!
			scoreboard.professorHit();
			scoreBubbles.add(new ScoreBubble("professorRuler", mole.getTransform().getX(), mole.getTransform().getY()));
		}
		else if(mole instanceof Dean)
		{
			//Dean was hit!
			scoreboard.deanHit();
			clock.incrementTime(Globals.DEAN_HIT_TIME_REWARD);
			scoreBubbles.add(new ScoreBubble("deanHammer", mole.getTransform().getX(), mole.getTransform().getY()));
			scoreBubbles.add(new ScoreBubble("plusSeconds", mole.getTransform().getX(),mole.getTransform().getY()));
		}
	
		playingField.killMole(mole);
	}
	
	/**
	* @return the player's current score
	*/
	public Scoreboard getScoreboard()
	{
		return scoreboard;
	}
	
	/**
	* @return the object that keeps track of how much time is left
	*/
	public Clock getClock()
	{
		return clock;
	}

	/**
	* @return the playingField which contains all the moles
	*/
	public PlayingField getPlayingField()
	{
		return playingField;
	}
	
	/**
	* @return the background image for the program
	*/
	public Image getBackground()
	{
		return background;
	}
	
	/**
	* @return the badge that contains the instructions for the sidebar
	*/
	public Image getInstructions()
	{
		return instructions;
	}
	
	/**
	* @return the list of positive scorebubbles that are floating on the screen
	*/
	public ArrayList getScoreBubbles()
	{
		return scoreBubbles;
	}
	
	/**
	* @return the array of professors giving you an f minus
	*/
	public ScoreBubble[][] getFMinuses()
	{
		return fminuses;
	}
	
	/**
	* This method updates all of the objects under the control of the environment including the
	* scoreboard, the clock, and the playingField (which holds the moles). It also runs through and 
	* checks which scorebubbles need to be removed from the game field.
	*
	* @param elapsedTime the amount of time that has passed since the last iteration of the program loop
	*/
	public void updateEnvironment(long elapsedTime)
	{
		scoreboard.update(elapsedTime);
		clock.update(elapsedTime);
		playingField.updatePlayingField(elapsedTime);

		for(int i = 0; i < scoreBubbles.size(); i++)
		{
			scoreBubbles.get(i).update(elapsedTime);
			
			if(scoreBubbles.get(i).getIsMarkedForDeath())
			{
				scoreBubbles.remove(i);
			}
		}
		
		for(int i = 0; i < Globals.NUMBER_OF_ROWS; i++)
		{
			for(int j = 0; j <Globals.NUMBER_OF_COLUMNS; j++)
			{
				if(fminuses[i][j] != null)
				{
					fminuses[i][j].update(elapsedTime);
					
					if(fminuses[i][j].getIsMarkedForDeath())
					{
						fminuses[i][j] = null;
					}
				
				}
			
			}
		}
	}
	
	
}
