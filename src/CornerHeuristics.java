import java.util.*;

public class CornerHeuristics {
	/**
	 * As per Korf's paper, we should generate all of the permutations
	 * by starting with a solved cube and then performing a breadth-
	 * first search.
	 */
	public static void generateCornerHeuristics() {
		// Make a cube and initialize it with a solved cube state
		Cube cube = new Cube(Cube.GOAL.toCharArray());

		// Make a new Queue to perform BFS
		Queue<CubeNode> q = new LinkedList<CubeNode>();

		// Put the solved/initial state of the corners on the queue
		q.add(new CubeNode(cube.state, 0));
		int[] corners = new int[88179840];
		Set<Map.Entry<Character, int[]>> faces = Cube.FACES.entrySet();

		// Iterate until we can't anymore
		while (!q.isEmpty()) {
			CubeNode current = q.poll();
			// For each cube state we're given, we need to try all of
			// possible turns of each other other faces
			for (Map.Entry<Character, int[]> face : faces) {
				// Do a clockwise turn
				char[] newState = Cube.rotate(current.state, face.getKey(), 1);
				int encodedCorner = Integer.parseInt(Cube.encodeCorners(newState));
				// Check to see if this combination has been made before
				if (corners[encodedCorner] == 0) {
					// This is a new combination, let's add it to the queue
					q.add(new CubeNode(newState, current.heuristic + 1));
				}
			}

			// Handle the current node. We'll encode the corners, and check to
			// see if we've seen this permutation before.
			String encodedCorner = Cube.encodeCorners(current.state);
			int encodedCornerInt = Integer.parseInt(encodedCorner);
			if (corners[encodedCornerInt] == 0) {
				corners[encodedCornerInt] = current.heuristic;
				// Print this out
				System.out.println(encodedCorner + "," + current.heuristic);
			}
		}
	}

	/**
	 * A main function to kick off the heuristic table generation
	 * @param args
	 */
	public static void main(String[] args) {
		CornerHeuristics.generateCornerHeuristics();
	}
}
