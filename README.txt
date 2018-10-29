M. Kaan Tasbas | mktasbas@gmail.com
https://github.com/ktasbas/15-puzzle

This project is heavily based on the open-source algorithms from the textbook
"Artificial Intelligence - A Modern Approach" by Russel and Norvig. Their original
code can be found at https://github.com/aimacode/aima-java.

/////////////////////////////////////////////////////////////////////////
Originally developed with Oracle JDK11 
on Linux Ubuntu 16.04 kernel 4.15.0-36-generic

Also tested on FAU servers at portal.eng.fau.edu with JDK8

/////////////////////////////////////////////////////////////////////////
To run on FAU servers double click ./release/fifteen-gui-8.jar
This file is prepared specifically for JDK8 on these servers.




OLD INSTRUCTIONS (tested on Ubuntu):
/////////////////////////////////////////////////////////////////////////
For the GUI:

While in the directory of this README.txt:
>java -jar ./release/fifteen-gui.jar

OR

While in the directory of this README.txt:
>cd ./aima-core/src/main/java
>java aima.gui.swing.applications.search.games.FifteenPuzzleApp

The board can be selected from the first drop down menu
The search method can be selected from the second drop down menu
The 'Clear' button clears the log on the right
The 'Prepare' button is used to randomize the board when 'Random' type is selcted
The 'Run' button is used to begin the search

The results are displayed in the log on the right. The moves taken are followed by statistics:
current queue size, max queue size, path cost to goal, and number of nodes expanded

To close the GUI, use the 'X' on the window border, or <CTRL> + C from the terminal used to run

/////////////////////////////////////////////////////////////////////////
For the terminal interface:

While in the directory of this README.txt:
>java -cp ./release/fifteen-gui.jar aima.demo.search.FifteenPuzzleDemo

OR

While in the directory of this README.txt:
>cd ./aima-core/src/main/java
>java aima.demo.search.FifteenPuzzleDemo

The initial state is { 1, 5, 2, 3, 9, 6, 7, 11, 4, 8, 10, 0, 12, 13, 14, 15 }
The misplaced tile heuristic runs, followed by the manhattan heuristic

The moves taken are listed and the statistics follow them as:
current queue size, max queue size, path cost to goal, and number of nodes expanded

/////////////////////////////////////////////////////////////////////////
Project requirements:

a) Implement two heuristic functions based on “Manhattan Distance” and “Misplaced Numbered Tiles” to 
calculate the distance between any state and the goal state for 15- puzzle (1 pt) (Please explain the 
implementation details in your report)

b) Given any initial random state of a 15-puzzle problem, please calculate whether the goal state is 
reachable from this initial random state for 15-puzzle (1 pt). (Please explain the implementation of 
your algorithm in your report)

c) For A* search, given a specific initial state, please compare the running-time performance (or # of steps) 
of using “Manhattan Distance” and “Misplaced Numbered Tiles”. (1 pt) (Explain the results and how you implement 
this part in your report)

d) Capture a screenshot showing the running results of your 15-puzzle program. Report the initial state, the 
goal state, and the movement of the tiles (1 pt). You do not need to design a GUI interface for your 15-puzzle.

