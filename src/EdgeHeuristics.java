import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class EdgeHeuristics {
	public static void generateEdgeHeuristics(int set) {
		// Make a cube and initialize it with a solved cube state
		Cube cube = new Cube(Cube.GOAL.toCharArray());

		// Make a new Queue to perform BFS
		Queue<CubeNode> q = new LinkedList<CubeNode>();

		// Put the solved/initial state of the cube on the queue
		q.add(new CubeNode(cube.state, 0));
		int[] edgeHeuristics = new int[42577920];
		int[][] edges = new int[6][2];
		int base = 6 * set;
		int limit = 6 + base;

		// Select the proper set of edges to work with
		if (set == 0 || set == 1) {
			int[][] tempEdges = Cube.EDGES;
			for (int i = base; i < limit; i++) {
				edges[i - base] = tempEdges[i];
			}
		} else {
			System.err.println("Please specify a valid set of edges");
		}

		Set<Map.Entry<Character, int[]>> faces = Cube.FACES.entrySet();

		// Iterate until we can't anymore
		while (!q.isEmpty()) {
			CubeNode current = q.poll();
			// For each cube state we're given, we need to try all of
			// possible turns of each other other faces
			for (Map.Entry<Character, int[]> face : faces) {
				// Do a clockwise turn
				char[] newState = Cube.rotate(current.state, face.getKey(), 1);
				int encodedEdge = Integer.parseInt(Cube.encodeEdges(newState).substring(base, limit));
				// Check to see if this combination has been made before
				if (edgeHeuristics[encodedEdge] == 0) {
					// This is a new combination, let's add it to the queue
					q.add(new CubeNode(newState, current.heuristic + 1));
				}
			}

			// Handle the current node. We'll encode the edgeHeuristics, and check to
			// see if we've seen this permutation before.
			String encodedEdge = Cube.encodeEdges(current.state).substring(base, limit);
			int encodedEdgeInt = Integer.parseInt(encodedEdge);
			if (edgeHeuristics[encodedEdgeInt] == 0) {
				edgeHeuristics[encodedEdgeInt] = current.heuristic;
				// Print this out
				System.out.println(encodedEdgeInt + "," + current.heuristic);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length <= 0) {
			// Generate first set of edge heuristics
			//EdgeHeuristics.generateEdgeHeuristics(0);
			// Generate second set of edge heuristics
			EdgeHeuristics.generateEdgeHeuristics(1);
		} else {
			EdgeHeuristics.generateEdgeHeuristics(Integer.parseInt(args[0]));
		}
	}
}
