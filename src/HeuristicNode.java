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
}
