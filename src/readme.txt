You are free to edit and redistribute this software as you please for the enjoyment and betterment of mankind. Just make sure you provide a copy of the files license.txt and readme.txt (this file) which gives credit to all of those who contributed to the project and describes the terms under which you are allowed to use this software. No person or thing is allowed to profit financially from this software for all of eternity. This software is part of the public domain and is distributed under a Creative Commons License (Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License). Detailed information about the terms of this license in semi-lawyer-ese is found in the license.txt file.

I'd like to express special thanks to Murray Gross, faculty advisor of the Brooklyn College-CUNY Computer and Information Systems Department. He is responsible for supervising this project and engendering its initial conception. Without him, there would be no Whack-a-Prof.

This program began as an instructional class project for the Brooklyn College Design and Implementation of Software II class of Fall 2013 taught by Professor Murray Gross. The aim of the class was to teach the engineering, social, and organizational practices necessary to build sizeable projects being developed by a larger team. The class was divided into 4 groups: one in charge of sound and graphics, another in charge of user specifications and software architecture, another in charge of gameplay mechanics, and a final group in charge of coordinating all of the separate components into a cohesive whole. I was on the team in charge of developing the object-oriented architecture of the software and the behaviour of the program. 

By the time the semester was finished, our class had done significant work detailing the program's behaviour and structure and had also pooled a bunch of sound and graphics resources together. Only very preliminary code had been written, most of which did not make it in the final project due to issues with not adhering to the prescribed API, making it unable to be successfully modularized and assimilated into the final structure. Nonetheless, the meetings and discussions about the project were important to the shaping of the final design. When I sat to code it the following semester as part of my final student project, many of the thorny design and technical issues had been explored, easing difficulties during the implementation phase.

The music for the game was composed and recorded by Craig C. Kowalick. Also, the book Programming Games in Java by David Brackeen from New Riders Press was instrumental in the writing of this program. Much of the final structure of the program is owed to tutorials and sample code found in that book. Any classes that were adapted from or borrowed heavily from that book have an additional license included as a comment at the top of the .java file. These include Sprite.java, NullRepaintManager.java, and ThreadPool.java. The Barker.java file also owes the design of the program loop structure to that book. The implementation of double buffering was taken from a programming wiki online, referenced in the appropriate file. Also, a small segment from the file SoundTest.java, which reads sound streams was taken from www.stackoverflow.com, also noted appropriately as a comment in that file.

My main contributions to this project are the software architecture, the actual written code, the additional animation frames, and the deployment of the project.

The project is not over. There's plenty more to be done. That's where you come in. I made a list of some features that didn't quite make it in to the game. Feel free to work on those or do whatever you want to make something really cool. Please, by all means, have as much fun as your imagination will allow.

Sincerely,
          Matt C.

		  
		  
		  
		  
		  
		  
		  
		  

Like every project, this one had a lot of good ideas that never made it into the final version because of time. Here are the top ten missing features:

1. A highscore board, preferably one that is networked and reports to a database on a server
2. The Sorority Girl mole, to penalize players for being too wanton with their mallet swings
3. Dynamic passing to make the game more challenging and interesting
4. A recording of how many moles get away that the player never hits during a mole's
life cycle.
5. A barker who yells and taunts at the player.
6. Lobby and highscore music
7. A more efficient sound loading system, right now, there are multiple copies of the same sound, it would be more efficient to have one copy of each sound that gets loaded onto multiple streams as needed.
8. A title screen.
9. The start button should blink to grab everyone's attention better; people have had trouble finding it.
10. Better mallet animations, the off-to-the-sideness makes people swing inaccurately
11. A demo screen
12. A nice way to package and distribute the game besides just source and jar
13. Stylized red marker final grades
14. The percentage for swing accuracy needs to be clipped before it is printed.

Hopefully otherwise will carry on.