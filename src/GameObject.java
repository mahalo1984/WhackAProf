/**
* This class is a superclass for all entities that are present in the game outside basic utility objects
* such as buttons or backgrounds. This class encapsulates a Sprite object for creating animations, a 
* Transform object for tracking the location and velocity of an object in game, and a BoxCollider which
* is used to determine if an object has made contact with a point in the game world's plane.
*/
public class GameObject
{
	protected Sprite sprite;
	protected BoxCollider collider;
	protected Transform transform;
	
	/**
	* Sets the sprite for the GameObject
	*/
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}

	/**
	* @return the member variable that represents a box surrounding the GameObject's sprite, used for
	* collision detection
	*/
	public BoxCollider getBoxCollider()
	{
		return collider;
	}
	
	/**
	* @return the member variable responsible for tracking the location of the GameObject and its velocity
	*/
	public Transform getTransform()
	{
		return transform;
	}
	
	/**
	* @return the member variable that contains the object that displays and animates the GameObject
	*/
	public Sprite getSprite()
	{
		return sprite;
	}
	
	/**
	* Tests whether or not a point is inside the GameObject's BoxCollider
	* @param x the x coordinate of the point you are testing to see if it is inside the BoxCollider
	* @param y the y coordinate of the point you are testing to see if it is inside the BoxCollider
	* @return the method returns true if the point is inside the BoxCollider and false otherwise
	*/
	public boolean insideBoxCollider(float x, float y)
	{
		boolean isInsideBoxCollider = false;
		
		float endOfColliderWide = this.transform.getX() + collider.getWidth() + collider.getLeftOffset();
		float endOfColliderTall = this.transform.getY() + collider.getHeight() + collider.getTopOffset();
		
		
		if(x >= this.transform.getX() + collider.getLeftOffset() && x <= endOfColliderWide &&
		   y >= this.transform.getY() + collider.getTopOffset() && y <= endOfColliderTall)
		{
			isInsideBoxCollider = true;
		}
		
		return isInsideBoxCollider;
	}
	
	/**
	* Updates the x and y coordinates of the transform and the temporal location (spot in animation)
	* of the sprite.
	*/
	public void update(long elapsedTime)
	{
		transform.setX(transform.getX() + transform.getVelocityX() * elapsedTime);
		transform.setY(transform.getY() + transform.getVelocityY() * elapsedTime);
		sprite.update(elapsedTime);
	}
	
	



}