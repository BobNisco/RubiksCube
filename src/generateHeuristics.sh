# Compile all of the java files
javac Cube.java;
javac HeuristicNode.java;
javac Heuristics.java;
# Run the Heuristics generation class and pipe the
# output into a csv file
java Heuristics -Xmx2048M > corners.csv;