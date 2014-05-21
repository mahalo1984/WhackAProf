/**
* This class is a type of collider representing a rectangle. It contains a width and a height as well
* as a top and a left offset, for colliders that would not find themselves perfectly aligned with 
* their GameObject's sprite and transform. It is modeled after cascading style sheet positioning.
*/
public class BoxCollider
{
	private float width;
	private float height;
	private float topOffset;
	private float leftOffset;
	
	/**
	* @param width how wide the collider's rectangle is
	* @param height how tall the collider's rectangle is
	* @param topOffset how far offset the collider is from the GameObject's transform's y coordinate
	* @param leftOffset how far offset the collider is from the GameObject's transform's x coordinate
	*/
	public BoxCollider(float width, float height, float topOffset, float leftOffset)
	{
		this.width = width;
		this.height = height;
		this.topOffset = topOffset;
		this.leftOffset = leftOffset;
	}

	/**
	* @return the width of the collider
	*/
	public float getWidth()
	{
		return width;
	}
	
	/**
	* @return the height of the collider
	*/
	public float getHeight()
	{
		return height;
	}
	
	/**
	* @return how far the collider's y coordinate is from the transform's y coordinate, when encapsulated
	* in a GameObject
	*/
	public float getTopOffset()
	{
		return topOffset;
	}

	/**
	* @return how far the collider's x coordinate is from the transform's x coordinate, when encapsulated
	* in a GameObject
	*/
	public float getLeftOffset()
	{
		return leftOffset;
	}

}