@author M. Kaan Tasbas | mktasbas@gmail.com

/////////////////////////////////////////////////////////////////////////
All files compiled with Oracle JDK11 
on Linux kernel 4.15.0-36-generic Ubuntu 16.04


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

