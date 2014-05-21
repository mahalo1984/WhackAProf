#
# A simple makefile for compiling three java classes
#

# define a makefile variable for the java compiler
#
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
#
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#
default: TestDemo.class

TestDemo.class: TestDemo.java Barker.class
		$(JCC) $(JFLAGS) TestDemo.java

Barker.class: Barker.java Painter.class Environment.class Mallet.class Globals.class NullRepaintManager.class ReportCard.class SoundTest.class ThreadPool.class
		$(JCC) $(JFLAGS) Barker.java
		
SoundTest.class: SoundTest.java
		$(JCC) $(JFLAGS) SoundTest.java

ThreadPool.class: ThreadPool.java
		$(JCC) $(JFLAGS) ThreadPool.java
		
Environment.class: Environment.java Scoreboard.class Clock.class PlayingField.class ScoreBubble.class
		$(JCC) $(JFLAGS) Environment.java

ScoreBubble.class: ScoreBubble.java GameObject.class
		$(JCC) $(JFLAGS) ScoreBubble.java
		
PlayingField.class: PlayingField.java Dean.class Professor.class Seat.class Globals.class ScoreBubble.class
		$(JCC) $(JFLAGS) PlayingField.java

Dean.class: Dean.java Mole.class Globals.class
		$(JCC) $(JFLAGS) Dean.java

Professor.class: Professor.java Mole.class Globals.class
		$(JCC) $(JFLAGS) Professor.java
		
Mole.class: Mole.java GameObject.class Globals.class SoundTest.class
		$(JCC) $(JFLAGS) Mole.java
		
Seat.class: Seat.java GameObject.class Globals.class
		$(JCC) $(JFLAGS) Seat.java
		
Mallet.class: Mallet.java GameObject.class RulerSwing.class HammerSwing.class SoundTest.class
		$(JCC) $(JFLAGS) Mallet.java
		
GameObject.class: GameObject.java Sprite.class BoxCollider.class Transform.class
		$(JCC) $(JFLAGS) GameObject.java

RulerSwing.class: RulerSwing.java PlayerAction.class Globals.class
		$(JCC) $(JFLAGS) RulerSwing.java
		
HammerSwing.class: HammerSwing.java PlayerAction.class Globals.class
		$(JCC) $(JFLAGS) HammerSwing.java
		
PlayerAction.class: PlayerAction.java
		$(JCC) $(JFLAGS) PlayerAction.java
		
Transform.class: Transform.java
		$(JCC) $(JFLAGS) Transform.java

BoxCollider.class: BoxCollider.java
		$(JCC) $(JFLAGS) BoxCollider.java

Sprite.class: Sprite.java 
		$(JCC) $(JFLAGS) Sprite.java

Painter.class: Painter.java Globals.class
		$(JCC) $(JFLAGS) Painter.java
		
Clock.class: Clock.java
		$(JCC) $(JFLAGS) Clock.java

Scoreboard.class: Scoreboard.java
		$(JCC) $(JFLAGS) Scoreboard.java

ReportCard.class: ReportCard.java
		$(JCC) $(JFLAGS) ReportCard.java

NullRepaintManager.class: NullRepaintManager.java
		$(JCC) $(JFLAGS) NullRepaintManager.java
Globals.class: Globals.java
		$(JCC) $(JFLAGS) Globals.java

# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean: 
		$(RM) *.class