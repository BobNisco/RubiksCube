import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * While doing the BFS to enumerate all valid permutations in a cube,
 * this will keep track of states and their heuristic value.
 */
public class CubeNode {
	/**
	 * The state of the cube
	 */
	public char[] state;
	/**
	 * The heuristic value
	 */
	public int heuristic;

	/**
	 *
	 * @param state the state of the cube
	 * @param heuristic the heuristic value
	 */
	public CubeNode(char[] state, int heuristic) {
		this.state = state;
		this.heuristic = heuristic;
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
			int encodedCorner = Integer.parseInt(Cube.encodeCorners(newState));
			int encodedEdgeSetOne = Integer.parseInt(Cube.encodeEdges(newState).substring(0, 6));
			int[] possibleHeuristics = new int[3];
			possibleHeuristics[0] = IDAStar.corners[encodedCorner];
			possibleHeuristics[1] = IDAStar.edgesSetOne[encodedEdgeSetOne];
			int max = possibleHeuristics[0];
			for (int i = 1; i < possibleHeuristics.length; i++) {
				if (possibleHeuristics[i] > max) {
					max = possibleHeuristics[i];
				}
			}
			successors.add(new CubeNode(newState, max));
		}
		return successors;
	}
}
