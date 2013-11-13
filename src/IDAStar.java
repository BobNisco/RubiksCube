import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IDAStar {

	public static final int[] corners = readHeuristics(88179840, "corners.csv");
	public static final int[] edgesSetOne = readHeuristics(42577920, "edgesSetOne.csv");
	public static final int[] edgesSetTwo = readHeuristics(42577920, "edgesSetTwo.csv");
	public static int nextBound;
	public static int nodesVisited;
	public static PriorityQueue<CubeNode> frontier = new PriorityQueue<CubeNode>();
	public static HashSet<CubeNode> explored = new HashSet<CubeNode>();

	/**
	 * Performs the IDA* search for our Rubik's cube.
	 * @param startState the starting state of the cube
	 * @param verbose true if we want to print out more details about the IDA* algorithm
	 * @return the string that represents the optimal solution
	 */
	public static String performIDAStar(char[] startState, boolean verbose) {
		// Don't bother wasting CPU cycles for an already solved Cube
		if (Arrays.equals(startState, Cube.GOAL.toCharArray())) {
			return "The given cube is already in a solved state";
		}
		// Initialize the root node with the start state
		CubeNode start = new CubeNode(startState, corners[Integer.parseInt(Cube.encodeCorners(startState))]);
		// And put the start node on the openSet
		if (verbose) {
			System.out.println("Beginning heuristic value: " + start.heuristic);
		}
		// Initialize nextBound with our starting heuristic value
		nextBound = start.heuristic;
		// Initialize nodesVisited
		nodesVisited = 0;
		// The end node once IDA* finishes
		CubeNode end = null;

		// Loop until we find a solution
		while (end == null) {
			if (verbose) {
				System.out.println("Current bound is: " + nextBound);
				System.out.println("# of Nodes visited: " + nodesVisited);
			}
			frontier.add(start);
			end = search(nextBound);
			// The iterative-deepening portion of IDA*
			// Increment the bound if we haven't found a solution
			nextBound++;
			// Reset the frontier and exploredSet
			frontier.clear();
			explored.clear();
		}
		if (verbose) {
			System.out.println("Solved!");
			System.out.println("Total # of nodes visited: " + nodesVisited);
		}
		return formatOptimalSolution(end.path);
	}

	/**
	 * The recursive expanding function that will expand nodes as per
	 * the rules of IDA*
	 * @param bound the current bound - used to determine if we should
	 *              expand nodes or not
	 * @return the node representation of the goal state
	 */
	private static CubeNode search(int bound) {
		nodesVisited++;

		while (!frontier.isEmpty()) {
			nodesVisited++;
			CubeNode current = frontier.poll();
			// If we have found the goal, return the goal node
			if (Arrays.equals(current.state, Cube.GOAL.toCharArray())) {
				return current;
			}
			// Add this current node to our explored set
			explored.add(current);
			// Get all of the possible successors from the given node
			ArrayList<CubeNode> successors = CubeNode.getSuccessors(current);
			// Iterate over each of the successors
			for (CubeNode successor : successors) {
				int f = current.g + successor.heuristic;
				successor.g = current.g + 1;
				if (f <= bound && !explored.contains(successor)) {
					// Add it to our frontier
					frontier.add(successor);
				}
			}
		}
		// We did not find the solution at this bound
		return null;
	}

	/**
	 * Formats the solution so that it prints out nicely. Without this function,
	 * a solution would duplicate turns of the same face eg: O1R1R1R1.
	 * This function will turn that solution into a prettier, O1R3.
	 * @param solution the unformatted solution
	 * @return a properly formatted optimal solution
	 */
	private static String formatOptimalSolution(String solution) {
		try {
			char[] s = solution.toCharArray();
			// Initialize the solution with the beginning 2 characters
			String optimalSolution = solution.substring(0, 2);
			for (int i = 2; i < s.length; i ++) {
				// Add each character to the optimal solution
				optimalSolution += s[i];
				// If the current character is equal to the last character in the string
				// and if i % 2 == 0 so that we are only comparing characters
				if (s[i] == s[i - 2] && i % 2 == 0) {
					// Get the number that we're going to increment
					Integer oldNumber = Integer.parseInt(optimalSolution.substring(
							optimalSolution.length() - 2, optimalSolution.length() - 1));
					// Trim the optimal solution to remove the old number
					optimalSolution = optimalSolution.substring(0, optimalSolution.length() - 2);
					// Add the incremented value
					optimalSolution += (oldNumber + 1);
					// Manually increment i so that we skip over the values we just handled
					// in this case.
					i++;
				}
			}
			return optimalSolution;
		} catch (Exception e) {
			// If anything abnormal happens while trying to format the string,
			// just return the non-pretty version of it. This is a fail-safe.
			return solution;
		}
	}

	/**
	 * Reads the CSV files for the heuristics and returns an int[]
	 * where the values are the heuristic values
	 * @param h the size of the array
	 * @param fileName the name of the CSV file to read from
	 * @return an int[]
	 */
	private static int[] readHeuristics(int h, String fileName) {
		// Our corners heuristics array will have 88179840
		// elements, but not all of them will have a value
		// as we only calculated heuristics for valid corner
		// positions starting at the goal state rather than
		// all possible permutations of corners.
		int[] heuristics = new int[h];
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
				if (!(lineData[0].equals("") || lineData[1].equals(""))) {
					heuristics[Integer.parseInt(lineData[0])] = Integer.parseInt(lineData[1]);
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			throw new RuntimeException("IO error occurred");
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return heuristics;
	}

	/**
	 * A quick tester for IDA*
	 * @param args
	 */
	public static void main(String[] args) {
		Cube cube = new Cube("input/cube07");
		//cube = Cube.generateRandomCube();
		System.out.println(cube);
		String result = IDAStar.performIDAStar(cube.state, true);
		System.out.println(result);
	}
}
