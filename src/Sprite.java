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

*/

import java.awt.Image;
import java.util.ArrayList;

/**
* Creates a group of images as a single data structure for managing an animation. This class has
* an array list of <code>AnimFrame</code> objects which it cycles through, each <code>AnimFrame</code>
* having a specific duration. When the last <code>AnimFrame</code> in the <code>ArrayList</code> has
* been cycled through, this class's <code>currFrameIndex</code> member variable is set to 0 and the
* process begins again. 
* <p>
* The class itself merely tracks the state of this process, a ledger for determining what to
* display when. For a <code>Sprite</code> object to implement the actual animations, the class must
* be updated and accessed periodically by some sort of timed iterative process.
* <p>
* In the case of the program Whack-a-Prof, this is handled in the <code>Barker</code> class's
* {@link Barker#runMainProgramLoop()} which has a loop that iterates as long as a flag is true
* but takes a brief sleep after every iteration to establish a persistent frame rate.
*/
public class Sprite{
	private ArrayList<AnimFrame> frames;
	private int currFrameIndex;
	private long animTime;
	private long totalDuration;
	
	/**
	* Constructs a new <code>ArrayList</code> for the <code>AnimFrame</code> objects, sets the total 
	* duration of this <code>Sprite</code> object's animation sequence to zero, sets the current
	* position in the animation to zero, and the current frame to zero.
	*/
	public Sprite(){
		frames = new ArrayList();
		totalDuration = 0;
		start();
	}
	
	/**
	* This method is used to add a new <code>AnimFrame</code> to this <code>Sprite</code> object's
	* animation sequence. The image and duration of the <code>AnimFrame</code> are determined by
	* the two parameters passed.
	*
	* @param image the image that is to be displayed for the frame 
	* @param duration the amount of time the frame persists for during the animation sequence
	*/
	public synchronized void addFrame(Image image, long duration)
	{
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	/**
	* Sets the animation time and current frame index to 0.
	*/
	public synchronized void start(){
		animTime = 0;
		currFrameIndex = 0;
	}
	
	/**
	* This method is used to update the position in the animation and the frame this <code>Sprite</code>
	* is on. This method is to be used in conjunction with some sort of sequentially organized and likely
	* periodic process in order to display the animation.
	*
	* @param elapsedTime the amount of time that has passed since the last iteration
	*/
	public synchronized void update(long elapsedTime){
		if(frames.size() > 1){
			animTime += elapsedTime;
		
			if(animTime >= totalDuration){
				animTime = animTime % totalDuration;
				currFrameIndex = 0;
			}
			
			while(animTime > getFrame(currFrameIndex).endTime){
				currFrameIndex++;
			}
		}
	}
	
	/**
	* @return returns the current image the <code>Sprite</code> is on as indicated by the
	* <code>currFrameIndex</code> member variable.
	*/
	public synchronized Image getImage(){
		if(frames.size() == 0){
			return null;
		}
		else{
			return getFrame(currFrameIndex).image;
		}
	}
	
	/**
	* @return returns the width of the image of the current frame
	*/
	public int getWidth()
	{
		return this.getImage().getWidth(null);
	}
	
	/**
	* @return returns the height of the image of the current frame
	*/
	public int getHeight()
	{
		return this.getImage().getHeight(null);
	}
	
	/**
	* @return returns how long the entire animation sequence runs for
	*/
	public long getTotalDuration()
	{
		return totalDuration;
	}
	
	/**
	* @param i the frame to be retrieved
	* @return returns the frame specified by the integer passed in as a parameter
	*/
	private AnimFrame getFrame(int i) {
		return (AnimFrame)frames.get(i);
	}
	
	
	/**
	* This class is an object that encapsulates an image and duration, creating a pair. The end
	* result is an image that also has a duration, used by the <code>Sprite</code> class for 
	* animation sequences.
	*/
	private class AnimFrame{
		Image image;
		long endTime;
	
		public AnimFrame(Image image, long endTime){
			this.image = image;
			this.endTime = endTime;
		}
	}
}