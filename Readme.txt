**********************************************************************
     Undertale 0.5: Escape From the Banished (v2.0) Readme File
   			   December 2015
   (c) 2015 Team Dee-Aeh-SquaredPlusKay. All rights reserved.

**********************************************************************
	      	    Team Dee-Aeh-SquaredPlusKay

   Allen Nagtalon ........... Team Captain, Head Programmer
   Angie Chong .............. Report Author, Debugger, Programmer
   Dulce Nava ............... Co-Head Programmer
   Karen Chung .............. Supervisor, Debugger, Programmer
				
**********************************************************************

This document provides information about Undertale 0.5: Escape From 
the Banished (v2.0), as well as answers to questions you might have.

Undertale 0.5: Escape From the Banished (v2.0) is a fan-made game based
on the story/lore of the Undertale game (created by Toby Fox). In no
way is this game or its files meant to be produced and sold to the
masses and is simply made for the purpose of a class project.

All images, sprites, concepts, and story belong to their respective
creators from the Undertale development team. Please do not sue us,
we love you all and the game you've created and if we have to 


**********************************************************************
How to use this document
**********************************************************************

To view the Readme file on-screen in Notepad, maximize the Notepad
window. On the Format menu, click Word Wrap. To print the Readme file,
open it in Notepad or another word processor, and then use the Print
command on the File menu.


**********************************************************************
CONTENTS
**********************************************************************

1.0 Story
2.0 Overview
3.0 Known issues
4.0 FAQ


**********************************************************************
1.0 STORY
**********************************************************************

Undertale 0.5: Escape From the Banished is a game in which the main
character, Frisk, has fallen down a hole into the underground, home of
the monsters that the humans have banished. Trapped in a cave, she
must find the room that holds the exit from the accursed cavern. As
she journeys through the darkness, she is constantly being chased by
the skeleton Sans and his clones. You must be direct Frisk to safety
before her soul breaks from the clutches of the monsters.


**********************************************************************
2.0 OVERVIEW
**********************************************************************

Undertale 0.5: Escape From the Banished is a Java-programmed game that
utilizes the concept of Object-Oriented Programming. The entities of
the board, all grouped under the umbrella superclass MapObjects, are
stored in a two-dimensional array that acts as a grid.

Inputs from the user are processed through whichever interface the
player chooses to use (text-based or GUI), and are sent to the
GameEngine and GameBoard classes, where the actual execution/interactions
occur.

Interactions between units on the board are settled and determined
through a series of check and apply methods that first check collision
between units, and then apply the appropriate actions to the game's
internal state baed on the aforementioned interaction.

The GUI utilizes both AWT and Swing components, and intertwines with the
GameEngine and GameBoard in the same way the GUI does.

The story/lore of the game is Undertale based in the GUI, but in the
text-based version, the game is less story-driven and more gameplay
focused.

---------------------------
Changes from version 0.5
---------------------------

In version 0.5, the program was only capable of generating the board
with the proper entities in appropriate locations. In version 0.7,
beta methods were added that allowed the player to perform the
three basic functions of the game: looking, moving, and shooting.

---------------------------
Changes from version 0.7
---------------------------

In version 0.7, only the player was capable of performing any actions.
In version 0.8, methods were added that allows the enemies to move after 
every turn. Ver 0.8 also provided the function that allowed the user to
save his/her progress at any given time in a game to be reloaded at a
later time.
Bugs, such as ghost enemies (enemies that were dead but still on the
board, capable of moving and killing) and always-visible enemies (enemies
that were set visible in the look method remained visible) were refined 
and fixed.

---------------------------
Changes from version 0.8
---------------------------

In version 0.8, bugs existed for cases where the enemies could potentially
be clustered in a way that one enemy would not be able to move into any
location. In this case, the program would be stuck and would no longer
read any of the user's inputs, nor execute the code for said inputs.
In version 1.0, if the aforementioned case ever occured, the enemy would
be excluded from the movement and would remain stationary for that turn,
eliminating any game breaking crashes.

---------------------------
Changes from version 1.0
---------------------------

In version 1.0, the game was only able to play with a text-based UI.
In version 1.5, a set of classes that compose the GUI of the game were
added, with complete keyboard functionality.

---------------------------
Changes from version 1.5
---------------------------

In version 1.5, various bugs, from disappearing enemies to the player
being stuck with no valid moves were found.
In version 2.0, these bugs are accounted for. When a player gets stuck
with no valid move, he/she can now use the unstuck command ('u' for
the UI, 'x' or the drop-down menu option for the GUI) to respawn in the
initial spawn location.


**********************************************************************
3.0 KNOWN ISSUES
**********************************************************************

---------------------------
Player Trapping
---------------------------

While the unstuck method was added with the intent to fix any trapping
of the player, there still exists cases (albiet very minimal) in which 
the player can be trapped in the initial corner, only to be spawned and 
forced to move into death. In this case, the player would essentially
be forced to lose two of three lives, which provides a very unfair
game balance-breaking case.



**********************************************************************
4.0 FREQUENTLY ASKED QUESTIONS
**********************************************************************
Q: Are you the creator of the original Undertale?
A: No, we are just fans of the game and decided to use Undertale as
   the base of our game out of pure respect and love for the original.

Q: What grade did you get for this class project?
A: Hopefully an "A". (Notice me Sensei!)






