/**
* This class contains all the global constants in the game. This class prevents there from being any 
* arbitrary values in the program and ensures that any constants that depend on the values of other 
* constants are defined in terms of those constants so as not to break the logical structure of the
* reality of the game. 
* <p>
* For instance, there is a constant for NUMBER_OF_DEANS and NUMBER_OF_PROFESSORS and then there is
* another constant that is the sum of those two constants named NUMBER_OF_MOLES. The definition for
* NUMBER_OF_MOLES is defined as NUMBER_OF_DEANS + NUMBER_OF_PROFESSORS thus preventing any update
* anomalies inside the program if any of these values should need to change.
* <p>
* The game continues to behave as expected even as many of these values are altered as a result of the way
* the program was designed. You will here find a listing of all the constants and the properties they
* govern. ahh
*/
public class Globals{
//put world properties and all other game object property values in this file
//as constants to be referenced by the actual game

	/**The width of the window in pixels not including the taskbar*/
	public static final int FRAME_WIDTH = 1024;
	/**The height of the window in pixels not including the taskbar*/
	public static final int FRAME_HEIGHT = 650;
	/**The height of the taskbar in pixels*/
	public static final int TASK_BAR_HEIGHT = 25;

	/**The maximum number of deans that could pop up at any given time*/
	public static final int NUMBER_OF_DEANS = 2;
	/**The maximum number of professors that could pop up at any given time*/
	public static final int NUMBER_OF_PROFESSORS = 7;
	/**The maximum number of moles that could pop up at any given time*/
	public static final int NUMBER_OF_MOLES = NUMBER_OF_DEANS + NUMBER_OF_PROFESSORS;
	
	/**The number of columns in the playing field, this field was misnamed and has not been fixed yet*/
	public static final int NUMBER_OF_ROWS = 5;
	/**The number of rows in the playing field, this field was misnamed and has not been fixed yet*/
	public static final int NUMBER_OF_COLUMNS = 4;
	
	/**The width of the field containing the chairs and moles in pixels*/
	public static final int FIELD_WIDTH = 724;
	/**The height of the field containing the chairs and moles in pixels*/
	public static final int FIELD_HEIGHT = 650;
	
	/**The width of a Seat in pixels, which is a type of GameObject*/
	public static final int SEAT_WIDTH = 100;
	/**The height of a Seat in pixels, which is a type of GameObject*/
	public static final int SEAT_HEIGHT = 114;
	/**The vertical offset for a Seat's BoxCollider in pixels*/
	public static final float SEAT_COLLIDER_TOP_PADDING = 10.0f;
	/**The horizontal offset for a Seat's BoxCollider in pixels*/
	public static final float SEAT_COLLIDER_LEFT_PADDING = 6.0f;
	
	/**The width of a mole in pixels*/
	public static final int MOLE_WIDTH = 100;
	/**The height of a mole in pixels*/
	public static final int MOLE_HEIGHT = 114;
	
	/**The amount of points a professor is worth when a player hits one with a ruler*/
	public static final int PROFESSOR_HIT_POINTS = 5;
	/**The amount of points a dean is worth when you hit a player with a hammer*/
	public static final int DEAN_HIT_POINTS = 10;
	/**The number of seconds added to the clock when the player hits a dean with a hammer*/
	public static final long DEAN_HIT_TIME_REWARD = 2;
	
	/**The amount of time in milliseconds a mole spends rising from a chair or lowering back down*/
	public static final float MOLE_RISE_AND_FALL_TIME = 250f;
	
	/**The maximum amount of time in milliseconds a professor may wait before rising from a chair*/
	public static final float PROFESSOR_WAIT_TIME_UPPER_BOUND = 5000f;
	/**The minimum amount of time in milliseconds a professor may wait before rising from a chair*/
	public static final float PROFESSOR_WAIT_TIME_LOWER_BOUND = 500f;
	/**The maximum amount of time in milliseconds a professor may be above ground.*/
	public static final float PROFESSOR_UP_TIME_UPPER_BOUND = 4500f;
	/**The minimum amount of time a professor may be above ground. This value cannot be less than 2 
	* times the MOLE_RISE_AND_FALL_TIME as this is included in its total up time.*/
	public static final float PROFESSOR_UP_TIME_LOWER_BOUND = MOLE_RISE_AND_FALL_TIME * 2f;
	
	/**The maximum amount of time in milliseconds a dean may wait before rising from a chair*/
	public static final float DEAN_WAIT_TIME_UPPER_BOUND = 4000f;
	/**The minimum amount of time in milliseconds a dean may wait before rising from a chair*/
	public static final float DEAN_WAIT_TIME_LOWER_BOUND = 3000f;
	/**The maximum amount of time in milliseconds a dean may be above ground.*/
	public static final float DEAN_UP_TIME_UPPER_BOUND = 2000f;
	/**The minimum amount of time a dean may be above ground. This value cannot be less than 2 
	* times the MOLE_RISE_AND_FALL_TIME as this is included in its total up time.*/
	public static final float DEAN_UP_TIME_LOWER_BOUND = MOLE_RISE_AND_FALL_TIME * 2f + 250f;
	
	/**The width of the professor's BoxCollider in pixels*/
	public static final float PROFESSOR_COLLIDER_WIDTH = 55.0f;
	/**The height of the professor's BoxCollider in pixels*/
	public static final float PROFESSOR_COLLIDER_HEIGHT = 83.0f;
	/**The professor's BoxCollider's distance from the transform vertically*/
	public static final float PROFESSOR_COLLIDER_PADDING_TOP = 13.0f;
	/**The professor's BoxCollider's distance from the transform horizontally*/
	public static final float PROFESSOR_COLLIDER_PADDING_LEFT = 24.0f;
	
	/**The width of the dean's BoxCollider in pixels*/
	public static final float DEAN_COLLIDER_WIDTH = 36.0f;
	/**The height of the dean's BoxCollider in pixels*/
	public static final float DEAN_COLLIDER_HEIGHT = 96.0f;
	/**The dean's BoxCollider's distance from the transform vertically*/
	public static final float DEAN_COLLIDER_PADDING_TOP = 9.0f;
	/**The dean's BoxCollider's distance from the transform horizontally*/
	public static final float DEAN_COLLIDER_PADDING_LEFT = 33.0f;

	/**If a sprite has only a single frame (no animation) it is given a large duration to 
	* prevent the sprite's only image from reloading unnecessarily (which would happen at the end
	* of a duration)*/
	public static final long STILL_SPRITE_DURATION = 999999;
	
	/**The width of the crosshair cursor sprite in pixels*/
	public static final int CROSSHAIR_WIDTH = 50;
	/**The height of the crosshair cursor sprite in pixels*/
	public static final int CROSSHAIR_HEIGHT = 46;
	
	/**The width of the mallet cursor sprite in pixels*/
	public static final int MALLET_WIDTH = 100;
	/**The height of the mallet cursor sprite in pixels*/
	public static final int MALLET_HEIGHT = 114;
	
	/**The amount of time the in game clock begins with in seconds*/
	public static final int CLOCK_STARTING_TIME_IN_SECONDS = 90; //90;
	/**The maximum amount of time the clock is allowed to have in seconds.*/
	public static final int CLOCK_MAX_TIME_IN_SECONDS = CLOCK_STARTING_TIME_IN_SECONDS;
	
	/**The java code number for left mouse button */
	public static final int LEFT_MOUSE_BUTTON = 1;
	/**The java code number for right mouse button */
	public static final int RIGHT_MOUSE_BUTTON = 3;
	
	/**How long it takes for a RulerSwing PlayerAction to complete*/
	public static final long RULER_SWING_DURATION = 192; //these values limit how frequently the ruler and hammer can be swung
	/**How long it takes for a HammerSwing PlayerAction to complete*/
	public static final long HAMMER_SWING_DURATION = 224;
	
	
	
	//The point of the two constant formulas below is to make sure the frame duration sums to the
	//length of its corresponding PlayerAction duration
	
	/**The length each frame of the ruler cursor sprites are to last. This is assuming there
	* are four frames in the sprite.*/
	public static final long RULER_SPRITE_FRAME_DURATION = RULER_SWING_DURATION / 4;
	/**The length each frame of the hammer cursor sprites are to last. This is assuming there
	* are four frames in the sprite.*/
	public static final long HAMMER_SPRITE_FRAME_DURATION = HAMMER_SWING_DURATION / 4;
	
	
	/**How far the background is placed from the top*/
	public static final float TOP_MARGIN = 60.0f;
	/**How far the background is placed from the left*/
	public static final float LEFT_MARGIN = 25.0f;
	
	/**The x coordinate of the scoreboard*/
	public static final float SCOREBOARD_X = 793f;
	/**The y coordinate of the scoreboard*/
	public static final float SCOREBOARD_Y = 260f;

	/**The x coordinate of the clock*/
	public static final float CLOCK_X = 828f;
	/**The y coordinate of the clock*/
	public static final float CLOCK_Y = 150f;
	
}