import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class IDAStar {

	public static final int[] corners = readCornerHeuristics();
	public static int nextBound;

	/**
	 * Performs the IDA* search for our Rubik's cube.
	 * @param startState the starting state of the cube
	 */
	public static void performIDAStar(char[] startState) {
		// Load in the corner heuristics table from the file
		int[] corners = readCornerHeuristics();
		// Initialize the root node with the start state
		HeuristicNode start = new HeuristicNode(startState, corners[Integer.parseInt(Cube.encodeCorners(startState))]);
		System.out.println("Beginning heuristic value: " + start.heuristic);
		// Initialize nextBound with our starting heuristic value
		nextBound = start.heuristic;
		// The end node once IDA* finishes
		HeuristicNode end = null;

		// Loop until we find a solution
		while (end == null) {
			end = search(start, 0, nextBound);
			// The iterative-deepening portion of IDA*
			// Increment the bound if we haven't found a solution
			nextBound++;
		}
		System.out.println(new Cube(end.state));
	}

	/**
	 * The recursive expanding function that will expand nodes as per
	 * the rules of IDA*
	 * @param node the current node
	 * @param g the current value of g
	 * @param bound the current bound - used to determine if we should
	 *              expand nodes or not
	 * @return the node representation of the goal state
	 */
	public static HeuristicNode search(HeuristicNode node, int g, int bound) {
		// If we have found the goal, return the goal node
		if (Arrays.equals(node.state, Cube.GOAL.toCharArray())) {
			return node;
		}
		// TODO: Add to solution set
		// Get all of the possible successors from the given node
		ArrayList<HeuristicNode> successors = HeuristicNode.getSuccesors(node);
		// Iterate over each of the successors
		for (HeuristicNode successor : successors) {
			// Calculate f for this successor node
			int f = g + successor.heuristic;
			// Don't expand more nodes if we are above the bound
			if (f <= bound) {
				// Expand this node's successors
				HeuristicNode t = search(successor, g + 1, bound);
				if (t != null) {
					return t;
				}
			}
		}
		return null;
	}

	/**
	 * Reads the CSV file for the CornerHeuristics and returns an
	 * int[] of size 88179840.
	 * @return int[88179840] where the values will be of the previously
	 * generated heuristics for the valid corner states.
	 */
	public static int[] readCornerHeuristics() {
		// Our corners heuristics array will have 88179840
		// elements, but not all of them will have a value
		// as we only calculated heuristics for valid corner
		// positions starting at the goal state rather than
		// all possible permutations of corners.
		int[] corners = new int[88179840];

		String fileName = "src/corners.csv";
		FileReader file = null;
		String line;
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				// For each line, split by the comma
				String[] lineData = line.split(",");
				// lineData[0] will be the encoded corner value
				// lineData[1] will be the calculated heuristic
				corners[Integer.parseInt(lineData[0])] = Integer.parseInt(lineData[1]);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("IO Error occurred");
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return corners;
	}

	/**
	 * A quick tester for IDA*
	 * @param args
	 */
	public static void main(String[] args) {
		Cube cube = new Cube("input2.txt");
		System.out.println(cube);
		IDAStar.performIDAStar(cube.state);
	}
}
