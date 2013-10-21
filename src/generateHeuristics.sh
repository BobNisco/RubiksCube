# Compile all of the java files
javac Cube.java;
javac CubeNode.java;
javac CornerHeuristics.java;
# Run the Heuristics generation class and pipe the
# output into a csv file
java CornerHeuristics -Xmx2048M > corners.csv;