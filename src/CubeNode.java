import java.util.ArrayList;
import java.util.Map;

/**
 * While doing the BFS to enumerate all valid permutations in a cube,
 * this will keep track of states and their heuristic value.
 * Implements the Comparable interface to allow for easy comparison of
 * during IDA* to determine which node has the lower heuristic.
 */
public class CubeNode implements Comparable<CubeNode> {
	/**
	 * The state of the cube
	 */

	public char[] state;
	/**
	 * The heuristic value
	 */
	public int heuristic;

	/**
	 * The path from the goal state to this node
	 */
	public String path;

	/**
	 *
	 * @param state the state of the cube
	 * @param heuristic the heuristic value
	 */
	public CubeNode(char[] state, int heuristic) {
		this.state = state;
		this.heuristic = heuristic;
		this.path = "";
	}

	/**
	 *
	 * @param state the state of the cube
	 * @param heuristic the heuristic value
	 * @param path the current path
	 */
	public CubeNode(char[] state, int heuristic, String path) {
		this.state = state;
		this.heuristic = heuristic;
		this.path = path;
	}


	/**
	 * Generates all successors of the given node.
	 * @param node the node to find successors for
	 * @return an ArrayList<CubeNode> of all successors for
	 * the param node
	 */
	public static ArrayList<CubeNode> getSuccessors(CubeNode node) {
		ArrayList<CubeNode> successors = new ArrayList<CubeNode>();
		for (Map.Entry<Character, int[]> face : Cube.FACES.entrySet()) {
			// Make a clockwise turn
			char[] newState = Cube.rotate(node.state, face.getKey(), 1);
			// Encode the corner
			int encodedCorner = Integer.parseInt(Cube.encodeCorners(newState));
			// Encode the edges
			String encodedEdges = Cube.encodeEdges(newState);
			int encodedEdgeSetOne = Integer.parseInt(encodedEdges.substring(0, 6));
			int encodedEdgeSetTwo = Integer.parseInt(encodedEdges.substring(6, 12));
			// Find all of the heuristic values for the given corner,
			// and two edge sets
			int[] possibleHeuristics = new int[3];
			possibleHeuristics[0] = IDAStar.corners[encodedCorner];
			possibleHeuristics[1] = IDAStar.edgesSetOne[encodedEdgeSetOne];
			possibleHeuristics[2] = IDAStar.edgesSetTwo[encodedEdgeSetTwo];
			// Find the maximum of the 3 heuristics as per the details of Korf's paper
			int max = possibleHeuristics[0];
			for (int i = 1; i < possibleHeuristics.length; i++) {
				if (possibleHeuristics[i] > max) {
					max = possibleHeuristics[i];
				}
			}
			// Add the rotated state and it's heuristic value to the successors
			successors.add(new CubeNode(newState, IDAStar.corners[encodedCorner], node.path + face.getKey() + "1")) ;
		}
		return successors;
	}

	@Override
	public int compareTo(CubeNode b) {
		if (this.heuristic < b.heuristic) {
			return -1;
		} else if (this.heuristic > b.heuristic) {
			return 1;
		}
		return 0;
	}
}
