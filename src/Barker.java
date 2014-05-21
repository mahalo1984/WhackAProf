 /*
  * Aspects of the code in this file have been adapted by Matthew Conroy from the 
  * textbook Developing Games in Java by David Brackeen.
  *
  * Above all, I owe my understanding of the software architecture of a videogame to that
  * book, and the structure of this program as a whole reflects that influence. I am
  * forever gratefully indebted to David Brackeen for his reference book and tutorials.
  * 
  * Additionally, I'd like to thank Professor Murray Gross whose instruction and guidance during
  * the development of this project gave me the focus, motivation, and understanding necessary
  * to see it to its current form. His wisdom and knowledge has been priceless.
  * 
  * And lastly, the Design and Implementation of Software II class of Fall 2013 at Brooklyn College 
  * did a lot of hard work developing the specification and source material from which this project 
  * is drawn. Without them, this game would not at all be what it is, for better and worse :)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.io.*;
import java.net.URL;

/** The Barker class is responsible making all the parts of the Whack-a-Prof program
* work together. It manages the {@link Painter}, {@link Environment}, 
* {@link Mallet}, and {@link ReportCard} classes and
* also initializes and loads a few images and buttons that don't belong specifically to
* the other classes. The Barker initializes all the objects that initialize all the other
* objects in the game. The Barker has each object under its control call its appropriate methods as 
* certain conditions are met within the program loop.
* <p>
* There are actually three main loops that run inside the program each nested inside each other. 
* They are: runIdleLoop(), runGamePlayLoop(), and runHighScoreLoop(). The function that begins this is
* runMainProgramLoop() whose only job is to call the method that is responsible for running the
* outermost loop in the program, runIdleLoop(). runIdleLoop() is responsible for maintaining the
* system just after initialization when the program is running but the player has not yet pushed 
* start to begin a play session. Once the player pushes the start button, runIdleLoop() calls
* runGamePlayLoop(), the loop that is responsible for maintaining a play session. When time has run
* out, the gameplay ends and runGamePlayLoop() calls another method named runHighScoreLoop() which runs
* yet another nested loop for allowing the viewer to look at their play statistics. Once the player
* accepts their score by pressing the highScoreButton, the objects the Barker is in control of are
* reconstructed by calling initialize(). From here, the program exits runHighScoreLoop() and then 
* exits runGamePlayLoop(), leaving the player back in runIdleLoop() where they are free to click the
* start button again to play another session or click the x on the window to exit the program.
* <p>
* runMainProgramLoop() --D runIdleLoop() --D runGamePlayLoop() --D runHighScoreLoop() -^
* <p>
* This is the basic logical flow of the program.
* <p>
* To use the Barker class, you may simply create a new Barker object using the parameterless
* constructor inside Main, and then call the object's runMainProgramLoop(). 
* <p>
* Perhaps we will add more modularized public methods in the future to better facilitate collaboration
* on new designs. Also missing currently is sound.
* <p>
* Finally, the basic design of the loop structure was taken from the book Developing Games in Java
* by David Brackeen. His book inspired many other design decisions throughout the program, and I am
* indebted to him for his work. The class NullRepaintManager, used in this file, is also entirely his,
* and the appropriate license has been included with the source code for that class.
*
* @author Matthew Scott Conroy
* @version 0.9 8 May 2014
* @see Painter
* @see Environment
* @see Mallet
* @see ReportCard
*/
public class Barker implements ActionListener
{
	/*
	 * These three flags help the program determine what state it is in and as a result, what
	 * program loop it should be executing.
	*/
	
	/**This flag is true after the Barker is initialized and then for the entire life of the program*/
	private boolean isIdling;  
	
	/**This flag is true after the player presses the start button and until the player presses the high score button to accept their score. */
	private boolean isRunning;
	
	/**This flag is true when the player finishes a session and until they accept their high score*/
	private boolean isInHighScoreMenu; 
	
	
	//main objects of the program
	private Painter frame; 				//a JFrame that paints using a double buffer
	private Environment environment; 	//manages the playingField
	private Mallet mallet;
	private ReportCard reportCard; 		//keeps track of player statistics during a session
	private SoundTest soundManager;
	
	
	//utility and resource objects in the program
	private Font clockFont; 		//font for timer
	private Font scoreFont; 		//font for scoreboard
	private Font typewriterFont; 	//font for report card printing
	
	private JButton startButton; 	//button the player clicks to begin play
	private JButton highScoreButton; //button player clicks to exit the highscore screen
	
	private Image readyImage; 		//text image displayed after player hits start
	private Image goImage; 			//text image displayed after readyImage
	private Image maskImage; 		//a gray translucent film for darkening the play screen
	private Image paperImage; 		//the pop up the report card is drawn on
	private Image timeUpImage;		//text displayed at end of gameplay
	
	//sound members
	private SoundTest music;
	
	//threads
	private Thread musicThread;
	private ThreadPool threadPool; 
	
	/**
	* This constructor initializes the entire program. It creates a new JFrame of the subclass Painter
	* which uses a double buffer. It creates all the utility and resource objects such as buttons, 
	* fonts, cursors, and images. Once all of this is in place, the constructor calls the initialize()
	* method which instantiates all of the objects used during gameplay.
	*/
	public Barker()
	{
		//create a frame that uses double buffering
		frame = new Painter();

		//create threadpool
		threadPool = new ThreadPool(32);
		
		//set the flags for what loops should start running
		isIdling = true;
		isRunning = false;
		isInHighScoreMenu = false;
		
		//create the fonts for the scoreboard and the clock
		clockFont = new Font("Times New Roman", Font.PLAIN, 60);
		scoreFont = new Font("Times New Roman", Font.PLAIN, 70);
		typewriterFont = new Font("Lucida Sans Typewriter",Font.PLAIN, 16);
		
		//make the cursor invisible in preparation for creating the mallet
		Cursor ghost = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(0,0), "invisible");
		frame.setCursor(ghost);
		
		Cursor fingerCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		
		//override the repainting of JComponents to prevent tear and flicker
		NullRepaintManager.install() ;
		
		//create and add start button
		URL iconUrl = Barker.class.getResource("images/StartButton.png");
		ImageIcon icon = new ImageIcon(iconUrl);
		startButton = new JButton(icon);
		startButton.setText("");
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.setPreferredSize(new Dimension(250, 130));
		startButton.addActionListener(this);
		startButton.setCursor(fingerCursor);
		
		Container contentPane = frame.getContentPane();
		
		//create and add highScoreButton
		highScoreButton = new JButton();
		highScoreButton.setText("Accept Grade");
		highScoreButton.setPreferredSize(new Dimension(125, 50));
		highScoreButton.addActionListener(this);
		highScoreButton.setCursor(fingerCursor);
		

		
		//make the pane clear
		if(contentPane instanceof JComponent)
		{
			((JComponent)contentPane).setOpaque(false);
		
		}
		
		//set up pane to absolutely position button
		contentPane.setLayout(null);
		Insets insets = frame.getInsets(); //get edges values
		
		//get button dimensions
		Dimension size = startButton.getPreferredSize();
		Dimension highScoreSize = highScoreButton.getPreferredSize();
		
		//position buttons
		startButton.setBounds(754 + insets.left, 495 + insets.top,
             size.width, size.height);
		highScoreButton.setBounds(330 + insets.left, 550 + insets.top,
			highScoreSize.width, highScoreSize.height);
			
		//make highscore button invisible
		highScoreButton.setVisible(false);
		
		//add button to contentpane
		contentPane.add(startButton);
		contentPane.add(highScoreButton);
		frame.validate(); //ensure the button has been placed


		//load ready and go images as well as mask and paper images for the report card display
		/*
		readyImage = new ImageIcon("images/ready.png").getImage();
		goImage = new ImageIcon("images/go.png").getImage();
		maskImage = new ImageIcon("images/mask.png").getImage();
		paperImage = new ImageIcon("images/paper.png").getImage();
		timeUpImage = new ImageIcon("images/timeUp.png").getImage();
		*/
		
		URL readyImageUrl = Barker.class.getResource("images/ready.png");
		readyImage = new ImageIcon(readyImageUrl).getImage();
		URL goImageUrl = Barker.class.getResource("images/go.png");
		goImage = new ImageIcon(goImageUrl).getImage();
		URL maskImageUrl = Barker.class.getResource("images/mask.png");
		maskImage = new ImageIcon(maskImageUrl).getImage();
		URL paperImageUrl = Barker.class.getResource("images/paper.png");
		paperImage = new ImageIcon(paperImageUrl).getImage();
		URL timeUpImageUrl = Barker.class.getResource("images/timeUp.png");
		timeUpImage = new ImageIcon(timeUpImageUrl).getImage();
		
		
		//set up game entities
		this.initialize();
	}
	
	/**
	* This method instantiates all the main objects used during gameplay including
	* the mallet, the environment, and the report card. This method also sets the 
	* startButton to visible and freezes the mallet so that swings don't register
	* to the report card outside of the gameplay loop.
	*/
	public void initialize()
	{
		//prepare music
		music = new SoundTest("sounds/wap.wav", true); //wap.wav
		musicThread = new Thread(music);
		
		//load game objects here
		startButton.setVisible(true);
		mallet = new Mallet(frame);
		environment = new Environment();
		reportCard = new ReportCard();
		
		mallet.freezeMallet(); //prevents swings from registering while not in play
		
	}
	
	/**
	* This method is here as a place holder for a pause function. At the moment it is not implemented
	* though if you call it, it will set the isRunning flag to false, which is the condition for 
	* executing code in the idle loop and the condition for iterating through the gameplay loop. In its
	* current state, there will be many unexpected behaviours if you call it.
	*/
	public void pause()
	{
		isRunning = false;
	}
	
	/**
	* This method calls the runIdleLoop. It is the public interface for jump-starting the program.
	*/
	public void runMainProgramLoop(){
		runIdleLoop();
	}

	/**
	* This method is called by runMainProgramLoop() and runs just after the program is initialized.
	* This loop continues to run throughout the life of the program, passing execution off to other
	* methods which are called inside it.
	*/
	private void runIdleLoop()
	{
		//put sound loop and blinking start button
		//on start button click, stop idle processes and begin runGameplayLoop()
		
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		
		while(isIdling)
		{
			long elapsedTime = System.currentTimeMillis() - currentTime;
			currentTime += elapsedTime;
			
			
			
			if(isRunning)
			{
				//counters for the ready and go prep titles just before gameplay after clicking start
				int ready = 50;
				int go = 10;
				
				//loop for ready title
				while(ready > 0)
				{
					Graphics2D g = frame.getGraphics();
					this.draw(g);
					g.drawImage(readyImage, 225, 300, null);
					g.dispose();
					frame.update();	
					
					try
					{
						Thread.sleep(20);
					}
					catch(InterruptedException ex)
					{
					
					}
					
					ready--;
				}
				
				//loop for Go! title
				while(go > 0)
				{
					Graphics2D g = frame.getGraphics();
					this.draw(g);
					g.drawImage(goImage, 250, 275, null);
					g.dispose();
					frame.update();	
					
					try
					{
						Thread.sleep(20);
					}
					catch(InterruptedException ex)
					{
					
					}
					
					go--;
				}
				
				//begin the main game
				runGameplayLoop();
			}
			
			mallet.update(elapsedTime);
			
			paintScreen();
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException ex)
			{ 
			
			}
			
		}
	}
	
	/**
	* This method is responsible for the logic of the program during a gameplay session. 
	* It is called by runIdleLoop(). It runs just after the start button is pushed and
	* continues to run until the player accepts their high score at the end of a play session by
	* pushing the highScoreButton.
	*/
	private void runGameplayLoop()
	{
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		
		mallet.thawMallet(); //mallet was previously paused to prevent logical errors
		
		musicThread.start();
		while(isRunning)
		{
			long elapsedTime = System.currentTimeMillis() - currentTime;
			currentTime += elapsedTime;
			
			this.update(elapsedTime);
			
			paintScreen();
			
			if(environment.getClock().getIsTimeUp())
			{
				isRunning = false;
				
			}
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException ex)
			{ 
			
			}
			
		}
		
		//turn off gameplay music
		music.stopLooping();
		//prevent the mallet from pounding after play has ended
		mallet.freezeMallet();
		//make sure the sprite is not stuck on a mallet animation
		mallet.setSpriteToCrossHair();
		
		runHighScoreLoop();
	}
	
	
	/**
	* This method is responsible for displaying player stats in the form of a report card. 
	* It is called by runGamePlayLoop(). runHighScoreloop() runs when a gameplay session
	* ends. It continues running until the player pushes the highScoreButton.
	*/
	private void runHighScoreLoop()
	{
		int timeUp = 50;
		
		while(timeUp > 0)
		{
			Graphics2D g = frame.getGraphics();
			this.draw(g);
			g.drawImage(timeUpImage, 125, 300, null);
			g.dispose();
			frame.update();	
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException ex)
			{
			
			}
			
			timeUp--;
		}
	
		isInHighScoreMenu = true;
		highScoreButton.setVisible(true);
		
		writePlayerPerformanceToFile();
		
		//present menu
		while(isInHighScoreMenu)
		{
		
			Graphics2D g = frame.getGraphics();
			this.draw(g);
			

			
			g.dispose();
			frame.update();	
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException ex)
			{ 
			
			}
		
		}
		
		//create new game objects in case the player wants to play again
		initialize();
	}
	
	/**
	* This method is called to process player input and then update the state of all game objects
	* both as a consequence of the players actions and as a result of the passing of time. 
	*@param elapsedTime the amount of time that has passed since the last call to update
	*/
	public void update(long elapsedTime)
	{
		//handle player input
		processInput(elapsedTime);
		
		//update game objects
		environment.updateEnvironment(elapsedTime);
	
	}
	
	/**
	* This method draws all the objects on screen in their current state using the graphics context
	* passed in as a parameter. It calls drawBackground(), drawField(), drawClock(), drawScoreboard(),
	* paintComponents(), drawMallet(), and drawScoreBubbles() unless it is at the report card screen
	* where it will call drawReportCard(), paintComponents(), and drawMallet().
	*
	* @param g a Graphics2D object
	*/
	public void draw(Graphics2D g)
	{
		//set up antialiasing for smooth text on the clock and the scoreboard
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//draw all of the objects on screen
		drawBackground(g);
		drawField(g);
		drawClock(g);
		drawScoreboard(g);
		frame.getLayeredPane().paintComponents(g); //paint buttons and such
		drawMallet(g);
		drawScoreBubbles(g);
		
		
		//draw report card
		if(isInHighScoreMenu)
		{
			drawReportCard(g);
			frame.getLayeredPane().paintComponents(g);
			drawMallet(g);
		
		}
		
	
	}

	
	/**
	* This method draws the background and the instruction patch.
	*@param g graphics context
	*/
	private void drawBackground(Graphics2D g)
	{
		g.drawImage(environment.getBackground(), 0, Globals.TASK_BAR_HEIGHT, null);
		g.drawImage(environment.getInstructions(), 790, 280, null);
	}
	
	/**
	* This method draws all the objects contained in the playingField object, basically,
	* the moles and the seats. It also draws all of the fminus animations that are currently
	* on screen.
	* @param g the graphics context
	*/
	private void drawField(Graphics2D g)
	{
		PlayingField p = environment.getPlayingField();
		Seat seat = null;
		Mole mole = null;
		ScoreBubble[][] f;
		
		//draw rows
		for(int i = 0; i < Globals.NUMBER_OF_ROWS; i++)
		{
			for(int j = 0; j < Globals.NUMBER_OF_COLUMNS; j++)
			{
				seat = p.getSeat(i,j);
				f = environment.getFMinuses();
				
				//draw mole at seat
				mole = seat.getMoleAtSeat();
				if(mole != null)
				{
					g.drawImage(mole.getSprite().getImage(),
							Math.round(mole.getTransform().getX()), 
							Math.round(mole.getTransform().getY()), 
							null);
				}
				else if(f[i][j] != null) 
				{
					g.drawImage(f[i][j].getSprite().getImage(),
								Math.round(f[i][j].getTransform().getX()),
								Math.round(f[i][j].getTransform().getY()),
								null);
				}
				
				//draw seat
				g.drawImage(seat.getSprite().getImage(), 
							Math.round(seat.getTransform().getX()), 
							Math.round(seat.getTransform().getY()), 
							null);
			}
		}
	
	}
	
	/**
	* This method draws the clock object on the side panel.
	* @param g graphics context
	*/
	private void drawClock(Graphics2D g) 
	{

		Clock c = environment.getClock();
		float x = c.getTransform().getX();
		float y = c.getTransform().getY();
		
		g.setFont(clockFont);
		g.drawString(c.toString(), x, y);
	}

	/**
	* This method draws the scoreboard.
	*@param g graphics context
	*/
	private void drawScoreboard(Graphics2D g) 
	{

		Scoreboard s = environment.getScoreboard();
		float x = s.getTransform().getX();
		float y = s.getTransform().getY();
		
		g.setFont(scoreFont);
		g.drawString(s.toString(), x, y);

	}
	
	/**
	* This method draws the mallet
	*@param g graphics context
	*/
	private void drawMallet(Graphics2D g)
	{
		if(mallet.getIsLeftPressed() || mallet.getIsRightPressed())
		{
			g.drawImage(mallet.getSprite().getImage(),
						Math.round(mallet.getTransform().getX() - (Globals.MALLET_WIDTH)),
						Math.round(mallet.getTransform().getY() - (Globals.MALLET_HEIGHT / 2)),
						null);
		}
		else
		{
			g.drawImage(mallet.getSprite().getImage(),
						Math.round(mallet.getTransform().getX() - (Globals.CROSSHAIR_WIDTH / 2)),
						Math.round(mallet.getTransform().getY() - (Globals.CROSSHAIR_HEIGHT / 2)),
						null);
		}
		
	}
	
	/**
	* This method draws the scorebubbles that rise from clobbered moles.
	* @param g graphics context
	*/
	private void drawScoreBubbles(Graphics2D g)
	{
		ArrayList<ScoreBubble> scoreBubbles = environment.getScoreBubbles();
		
		for(int i = 0; i < scoreBubbles.size(); i++)
		{
			ScoreBubble scoreBubble = scoreBubbles.get(i);
			
			g.drawImage(scoreBubble.getSprite().getImage(),
						Math.round(scoreBubble.getTransform().getX()), Math.round(scoreBubble.getTransform().getY()),
						null);
		}
	
	}
	
	/**
	* This method draws the report card.
	* @param g graphics context
	*/
	public void drawReportCard(Graphics2D g)
	{
			//draw translucent mask
			g.drawImage(maskImage, 0, Globals.TASK_BAR_HEIGHT, null);
			//insert paper background
			g.drawImage(paperImage,135, Globals.TASK_BAR_HEIGHT, null);
			
			//write ReportCard stats on the paper
			g.setFont(typewriterFont);
			g.setColor(Color.BLACK);
			g.drawString("Professors killed:                    " + reportCard.getNumberOfProfessorsHit(), 217, 110);
			g.drawString("Deans killed:                         " + reportCard.getNumberOfDeansHit(),217, 129); //2
			g.drawString("Number of times you swung the ruler:  " + reportCard.getNumberOfRulerSwings(), 217, 148); //4
			g.drawString("Number of times you swung the hammer: " + reportCard.getNumberOfHammerSwings(), 217, 167); //6
			g.drawString("Number of F minuses you received:     " + reportCard.getNumberOfFMinuses(), 217, 186);
			g.drawString("Total number of mallet swings:        " + reportCard.getNumberOfMalletSwings(), 217, 205);
			g.drawString("Total number of moles killed:         " + reportCard.getNumberOfMoleHits(), 217, 224);
			g.drawString("Total number of misses:               " + reportCard.getNumberOfMisses(), 217, 243);
			g.drawString("Total amount of extra time earned:    " + reportCard.getAmountOfExtraTimeEarned(), 217, 262);
			
			double accuracy = reportCard.getAccuracy() * 100;
			
			g.drawString("Swing accuracy:                       " + accuracy + " %", 217, 281);
			g.drawString("Final Score:                         " + environment.getScoreboard().getScore(), 217, 300);
			
			String finalGradeString = "And your final grade is:         " + reportCard.calculateGrade(environment.getScoreboard().getScore());
			g.drawString(finalGradeString, 217, 319);
	
	}
	
	/**
	* This method updates the mallet object and then performs necessary game logic to determine if
	* anything has been hit but only if the mallet has just completed a full swing. If the mallet
	* has not been swung or is still in mid-swing, no checks are performed.
	*<p>
	* The collision detection is primitive with O(n) complexity where n is the number of moles. The
	* algorithm checks to see if the mallet has struck any of the moles by looping through the 
	* collision criteria for each of them. If the criteria has been met for a mole, it handles what
	* to do based on the rules of the game setting up success or failure scoreBubble objects, reporting
	* to the report card object, and updating time and score appropriately. There are some serious 
	* decisions in the game logic being made here. So much so that perhaps this could be its own 
	* object eventually.
	* @param elapsedTime the amount of time that has passed since the last iteration
	*/
	private void processInput(long elapsedTime)
	{
		boolean hasImpactOccurred = mallet.updateMallet(elapsedTime);
		
		
		//check to see if the swish sound must be played and then execute the task if so
		if(mallet.getDoesSoundNeedToBeTriggered())
		{	
			SoundTest temp = mallet.getSwish();
			
			if(temp != null)
			{
				threadPool.runTask(temp);
			}
		}
		
		//process input
		if(hasImpactOccurred == true) //in other words, did the mallet swing finish
		{
			//An impact has occurred
			PlayingField playingField = environment.getPlayingField();
			float malletX = mallet.getTransform().getX();
			float malletY = mallet.getTransform().getY();
			boolean isRightPressed = mallet.getIsRightPressed();
			boolean isLeftPressed = mallet.getIsLeftPressed();
		
			//register the swing with the reportCard
			if(isLeftPressed)
			{
				reportCard.addRulerSwing();
			}
			else if(isRightPressed)
			{
				reportCard.addHammerSwing();
			}
			
			//determine if anything was collided with at the present location
			for(int i = 0; i < Globals.NUMBER_OF_MOLES; i++)
			{
				Mole mole = playingField.getMole(i);
				int lifeState = mole.getLifeState();
	
				//complicated logic for did the mallet smash a mole
				if((lifeState == Mole.LIFESTATE_RISING || 
					lifeState == Mole.LIFESTATE_UP ||
					lifeState == Mole.LIFESTATE_FALLING) && 
					mole.insideBoxCollider(malletX, malletY) &&
					!(mole.getSeatMoleIsAt().insideBoxCollider(malletX, malletY)))
				{
					//The mole collision decision has been met
					if(mole instanceof Professor)
					{
						if(mallet.getIsLeftPressed())
						{
							//play the smash sound
							threadPool.runTask(mole.getSuccessSoundTest()); 
							
							environment.killMole(mole);
							mallet.setIsLeftPressedToFalse();
							
							reportCard.addProfessorHit();
						}
						else if(mallet.getIsRightPressed())
						{
							
							//load an fminus death animation for professor
							environment.getFMinuses()[mole.getGridX()][mole.getGridY()] =
																	new ScoreBubble("professorHammer", 
																					mole.getTransform().getX(), 
																					mole.getTransform().getY());
							
							//play the error sound
							threadPool.runTask(mole.getFailureSoundTest()); 
							
							//kill mole without awarding points
							playingField.killMole(mole);
							
							mallet.setIsRightPressedToFalse();
							
							reportCard.addFMinus();
						}
					}
					else if(mole instanceof Dean)
					{
						if(mallet.getIsLeftPressed())
						{
							
							//load an fminus death animation for dean
							environment.getFMinuses()[mole.getGridX()][mole.getGridY()] = 
																new ScoreBubble("deanRuler", 
																				mole.getTransform().getX(), 
																				mole.getTransform().getY());

							//play the error sound
							threadPool.runTask(mole.getFailureSoundTest()); 
							
							//kill mole without awarding points													
							playingField.killMole(mole);
							
							mallet.setIsLeftPressedToFalse();
							
							reportCard.addFMinus();
						}
						else if(mallet.getIsRightPressed())
						{
							//play the smash sound
							threadPool.runTask(mole.getSuccessSoundTest()); 
						
							environment.killMole(mole);
							mallet.setIsRightPressedToFalse();
							
							reportCard.addDeanHit();
						}
					}
					
				}
			}
			mallet.setIsRightPressedToFalse();
			mallet.setIsLeftPressedToFalse();
		
		}	
	
	
	}
	

	/**
	* Calls the main draw function and also updates the frame (the window of type Painter, which 
	* uses double buffering). This method is invoked at the end of each of the three nested 
	* program loops to update the screen as the state of objects change.
	*/
	private void paintScreen()
	{
		Graphics2D g = frame.getGraphics();
		this.draw(g);
		g.dispose();
		frame.update();	
	
	}
	
	/**
	* This function creates a line in a comma separated value output file. The line contains all
	* of the player's session statistics. The values are as follows: number of professors hit,
	* number of deans hit, number of ruler swings, number of hammer swings, number of F minuses,
	* number of mallet swings, number of mole hits, number of misses (swing whiffs), amount of
	* extra time earned, swing accuracy (misses to total swings ratio), and score.
	*<p>
	* This method inside the runHighScoreLoop() method after a play session ends.
	*/
	private void writePlayerPerformanceToFile()
	{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true)))) {
			out.print(reportCard.getNumberOfProfessorsHit() + ", ");
			out.print(reportCard.getNumberOfDeansHit() + ", ");
			out.print(reportCard.getNumberOfRulerSwings() + ", ");
			out.print(reportCard.getNumberOfHammerSwings() + ", ");
			out.print(reportCard.getNumberOfFMinuses() + ", ");
			out.print(reportCard.getNumberOfMalletSwings() + ", ");
			out.print(reportCard.getNumberOfMoleHits() + ", ");
			out.print(reportCard.getNumberOfMisses() + ", ");
			out.print(reportCard.getAmountOfExtraTimeEarned() + ", ");
			out.print(reportCard.getAccuracy() + ", ");
			out.print(environment.getScoreboard().getScore() + ", ");
			out.println("");
			
		}catch (IOException e) {
    
		}

	
	}
	
	/**
	* This method is invoked when either the startButton or the highScoreButton is pressed.
	* @param e a button press
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source == startButton)
		{
			//Start button event triggered!
			startButton.setVisible(false);
			isRunning = true;
		}
		
		if(source == highScoreButton)
		{
			//High score button even triggered!
			highScoreButton.setVisible(false);
			isInHighScoreMenu = false;
		}
	
	
	}

}
