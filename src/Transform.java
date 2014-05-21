/* 
 * This code was adapted by Matthew Conroy from source 
 * code provided by tutorials from the book: 
 *
 * Developing Games in Java 
 * by David Brackeen 
 * New Riders Publishing 2004

Copyright (c) 2003, David Brackeen
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
Neither the name of David Brackeen nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

It's important to note that this code originally appeared in the class called Sprite. The code for coordinates and 
translation via velocity from the original Sprite class have been here extracted into a new custom class called
Transform so that the Sprite (which now simply handles image display and animation) and BoxCollider class (a class of my own
for facilitating collision detection) can access the same coordinates and aren't each responsible for maintaining their
own, which would introduce the prospect of update anomalies. 
*/

public class Transform
{
	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	
	
	
	public Transform(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.dx = 0.0f;
		this.dy = 0.0f;
	}

	public float getVelocityX()
	{
		return dx;
	}
	
	public float getVelocityY()
	{
		return dy;
	}
	
	public void setVelocityX(float dx)
	{
		this.dx = dx;
	}
	
	public void setVelocityY(float dy)
	{
		this.dy = dy;
	}
	

	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	




}