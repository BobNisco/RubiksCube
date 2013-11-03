Optimal Rubik's Cube Solver
==========

[Bob Nisco](https://github.com/BobNisco), [Mike Hunter](https://github.com/acer149) |
Marist College - CMSC 404 - Artificial Intelligence Project

Problem
-----------
Given any Rubik's Cube state, return a list of moves to solve the Rubik's Cube using [Korf's Algorithm](http://en.wikipedia.org/wiki/Optimal_solutions_for_Rubik%27s_Cube#Korf.27s_Algorithm).

Solution
-----------
- Representing a 3D cube in a 2D state
- Generating 3 distinct pattern databases: corner cubies, edge cubies set 1, edge cubies set 2
- Performing Iterative Deepening A* (IDA*) search on the possible moves using the 3 aforementioned pattern databases as the heuristic look up tables
- Return an optimal solution in the form of the face to turn and how many clockwise turns to do

Relaxed Constraints
---------------------------
- Only handle clockwise turns of a face

Running the Solver
------------------
Compile the Java source files in src directory:
- `cd src`
- `javac *.java`

If you would like to generate the heuristic tables, you can run the generateHeuristics.sh file in src directory after giving it sufficient privileges:
- `chmod 777 generateHeuristics.sh`
-	`./generateHeuristics.sh`

To run the program to solve a cube from a file:
- `java Cube "Full file path to input file"`
