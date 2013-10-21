import java.util.ArrayList;
import java.util.Map;

/**
 * While doing the BFS to enumerate all valid permutations in a cube,
 * this will keep track of states and their heuristic value.
 */
public class HeuristicNode {
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
	public HeuristicNode(char[] state, int heuristic) {
		this.state = state;
		this.heuristic = heuristic;
	}

	/**
	 * Generates all successors of the given node.
	 * @param node the node to find successors for
	 * @return an ArrayList<HeuristicNode> of all successors for
	 * the param node
	 */
	public static ArrayList<HeuristicNode> getSuccesors(HeuristicNode node) {
		ArrayList<HeuristicNode> successors = new ArrayList<HeuristicNode>();
		for (Map.Entry<Character, int[]> face : Cube.FACES.entrySet()) {
			// Make a clockwise turn
			char[] newState = Cube.rotate(node.state, face.getKey(), 1);
			int encodedCorner = Integer.parseInt(Cube.encodeCorners(newState));
			successors.add(new HeuristicNode(newState, IDAStar.corners[encodedCorner]));
		}
		return successors;
	}
}
