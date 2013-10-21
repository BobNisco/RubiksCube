import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class IDAStar {

	public static final int[] corners = readCornerHeuristics();
	public static int nextBound;

	public static void performIDAStar(char[] startState) {
		int[] corners = readCornerHeuristics();
		HeuristicNode start = new HeuristicNode(startState, corners[Integer.parseInt(Cube.encodeCorners(startState))]);
		System.out.println("Beginning heuristic value: " + start.heuristic);
		nextBound = start.heuristic;
		HeuristicNode end = null;

		while (end == null) {
			int currentBound = nextBound;
			end = search(start, 0, currentBound);
			nextBound++;
		}
		System.out.println(new Cube(end.state));
	}

	public static HeuristicNode search(HeuristicNode node, int g, int bound) {
		System.out.println("searching! g: " + g + " bound: " + bound);
/*		int f = g + node.heuristic;
		// If we are above the bound, then don't bother
		if (f > bound) {
			return null;
		}*/
		// If we have found the goal
		if (Arrays.equals(node.state, Cube.GOAL.toCharArray())) {
			return node;
		}
		// TODO: Add to solution set
		//double min = Double.POSITIVE_INFINITY;
		//HeuristicNode result = null;
		ArrayList<HeuristicNode> successors = HeuristicNode.getSuccesors(node);
		for (HeuristicNode successor : successors) {
			int f = g + successor.heuristic;
			if (f <= bound) {
				HeuristicNode t = search(successor, g + 1, bound);
				if (t != null) {
					return t;
/*					if (Arrays.equals(t.state, Cube.GOAL.toCharArray())) {
						result = t;
					}
					if (t.heuristic < min) {
						min = t.heuristic;
					}*/
				}
			}
		}
		return null;
	}

	public static int[] readCornerHeuristics() {
		int[] corners = new int[88179840];

		String fileName = "src/corners.csv";
		FileReader file = null;
		String line;
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				String[] lineData = line.split(",");
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

	public static void main(String[] args) {
		Cube cube = new Cube("input2.txt");
		System.out.println(cube);
		IDAStar.performIDAStar(cube.state);
	}
}
