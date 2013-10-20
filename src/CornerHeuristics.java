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
		Queue<HeuristicNode> q = new LinkedList<HeuristicNode>();

		// Put the solved/initial state of the corners on the queue
		q.add(new HeuristicNode(cube.state, 0));
		int[] corners = new int[88179840];
		Set<Map.Entry<Character, int[]>> faces = Cube.FACES.entrySet();

		// Iterate until we can't anymore
		while (!q.isEmpty()) {
			HeuristicNode current = q.poll();
			// For each cube state we're given, we need to try all of
			// possible turns of each other other faces
			for (Map.Entry<Character, int[]> face : faces) {
				// For each face, we're going to rotate it clockwise
				// two separate times. Any more rotations will end up
				// in just generating states that will show up in other
				// ways, so that is not necessary

				// First clockwise turn
				char[] newState = Cube.rotate(current.state, face.getKey(), 1);
				int encodedCorner = Integer.parseInt(Cube.encodeCorners(newState));
				// Check to see if this combination has been made before
				if (corners[encodedCorner] == 0) {
					// This is a new combination, let's add it to the queue
					q.add(new HeuristicNode(newState, current.heuristic + 1));
				}

				// Second clockwise turn
				newState = Cube.rotate(newState, face.getKey(), 1);
				encodedCorner = Integer.parseInt(Cube.encodeCorners(newState));
				// Check to see if this combination has been made before
				if (corners[encodedCorner] == 0) {
					// This is a new combination, let's add it to the queue
					q.add(new HeuristicNode(newState, current.heuristic + 1));
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
