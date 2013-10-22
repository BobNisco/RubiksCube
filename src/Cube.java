import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A representation of a physical Rubik's cube.
 */
public class Cube {

	public char[] state;

	/**
	 * The serialized representation of our goal state.
	 */
	public final static String GOAL = "RRRRRRRRRGGGYYYBBBGGGYYYBBBGGGYYYBBBOOOOOOOOOWWWWWWWWW";

	/**
	 * A two-dimensional primitive array of ints representing the location of the corners.
	 * The array is 8 rows by 3 columns.
	 * Each row represents a physical corner cubie on the Rubik's cube.
	 * Each column in the row represents a location in the serialized version of the cube.
	 */
	public final static int[][] CORNERS = {
			{0, 9, 51},
			{2, 17, 53},
			{6, 11, 12},
			{8, 14, 15},
			{29, 30, 36},
			{32, 33, 38},
			{27, 42, 45},
			{35, 44, 47}
	};

	/**
	 * A two-dimensional primitive array of ints representing the location of the edges.
	 * The array is 12 rows by 2 columns.
	 * Each row represents a physical edge cubie on the Rubik's cube.
	 * Each column in the row represents a location in the serialize version of the cube.
	 */
	public final static int[][] EDGES = {
			{1, 52},
			{3, 10},
			{5, 16},
			{7, 13},
			{18, 48},
			{20, 21},
			{23, 24},
			{26, 50},
			{28, 39},
			{31, 37},
			{34, 41},
			{43, 46}
	};

	/**
	 * A HashMap<Character, int[]> where the key is the character of the
	 * color of the face and the int[] is the indices of the string in
	 * which the face represents.
	 */
	public final static HashMap<Character, int[]> FACES = initFaces();

	/**
	 * A HashMap<Character, int[]>, where the key is the character of the
	 * color of the face and the int[] is the indices of the string in
	 * which the side represents.
	 */
	public final static HashMap<Character, int[]> SIDES = initSides();

	/**
	 * A Map<String, Integer> where the key is the sorted state
	 * of the corner and the value is the corner in a goal state.
	 * Used for quicker lookup times while encoding the corners.
	 */
	public final static HashMap<String, Integer> GOALCORNERS = initGoalCorners();

	/**
	 * A HashMap<Character, Integer> where the key is the color of
	 * the center of each side and the value is the index position of
	 * that colored center.
	 */
	public final static HashMap<Character, Integer> CENTERS = initCenters();

	/**
	 * A HashMap<String, Integer> where the key is the sorted state
	 * of the edges and the value is the edge in a goal state.
	 * Used for quicker lookup times while encoding the edges.
	 */
	public final static HashMap<String, Integer> GOALEDGES = initGoalEdges();

	/**
	 * Initializes the state to an empty String.
	 * Initializes the corners and edges with the positions in our serialized
	 * representation of a Rubik's cube.
	 */
	public Cube() {
		this.state = new char[54];
	}

	/**
	 * Initializes the Cube to the input of a file.
	 * @param fileName the filepath of the text file that represents a cube
	 */
	public Cube(String fileName) {
		this.state = this.readTextFile(fileName);
	}

	/**
	 * Initializes the Cube to the given state
	 * @param state the char[] that represents a state
	 */
	public Cube(char[] state) {
		this.state = state;
	}

	private static HashMap<Character, int[]> initFaces() {
		HashMap<Character, int[]> faces = new HashMap<Character, int[]>();
		int[] face = new int[8];
		face[0] = 0;
		face[1] = 1;
		face[2] = 2;
		face[3] = 5;
		face[4] = 8;
		face[5] = 7;
		face[6] = 6;
		face[7] = 3;
		faces.put("R".charAt(0), face);
		face = new int[8];
		face[0] = 9;
		face[1] = 10;
		face[2] = 11;
		face[3] = 20;
		face[4] = 29;
		face[5] = 28;
		face[6] = 27;
		face[7] = 18;
		faces.put("G".charAt(0), face);
		face = new int[8];
		face[0] = 12;
		face[1] = 13;
		face[2] = 14;
		face[3] = 23;
		face[4] = 32;
		face[5] = 31;
		face[6] = 30;
		face[7] = 21;
		faces.put("Y".charAt(0), face);
		face = new int[8];
		face[0] = 15;
		face[1] = 16;
		face[2] = 17;
		face[3] = 26;
		face[4] = 35;
		face[5] = 34;
		face[6] = 33;
		face[7] = 24;
		faces.put("B".charAt(0), face);
		face = new int[8];
		face[0] = 36;
		face[1] = 37;
		face[2] = 38;
		face[3] = 41;
		face[4] = 44;
		face[5] = 43;
		face[6] = 42;
		face[7] = 39;
		faces.put("O".charAt(0), face);
		face = new int[8];
		face[0] = 45;
		face[1] = 46;
		face[2] = 47;
		face[3] = 50;
		face[4] = 53;
		face[5] = 52;
		face[6] = 51;
		face[7] = 48;
		faces.put("W".charAt(0), face);
		return faces;
	}

	private static HashMap<Character, int[]> initSides() {
		HashMap<Character, int[]> sides = new HashMap<Character, int[]>();
		int[] side = new int[12];
		side[0] = 51;
		side[1] = 52;
		side[2] = 53;
		side[3] = 17;
		side[4] = 16;
		side[5] = 15;
		side[6] = 14;
		side[7] = 13;
		side[8] = 12;
		side[9] = 11;
		side[10] = 10;
		side[11] = 9;
		sides.put("R".charAt(0), side);
		side = new int[12];
		side[0] = 0;
		side[1] = 3;
		side[2] = 6;
		side[3] = 12;
		side[4] = 21;
		side[5] = 30;
		side[6] = 36;
		side[7] = 39;
		side[8] = 42;
		side[9] = 45;
		side[10] = 48;
		side[11] = 51;
		sides.put("G".charAt(0), side);
		side = new int[12];
		side[0] = 6;
		side[1] = 7;
		side[2] = 8;
		side[3] = 15;
		side[4] = 24;
		side[5] = 33;
		side[6] = 38;
		side[7] = 37;
		side[8] = 36;
		side[9] = 29;
		side[10] = 20;
		side[11] = 11;
		sides.put("Y".charAt(0), side);
		side = new int[12];
		side[0] = 8;
		side[1] = 5;
		side[2] = 2;
		side[3] = 53;
		side[4] = 50;
		side[5] = 47;
		side[6] = 44;
		side[7] = 41;
		side[8] = 38;
		side[9] = 32;
		side[10] = 23;
		side[11] = 14;
		sides.put("B".charAt(0), side);
		side = new int[12];
		side[0] = 30;
		side[1] = 31;
		side[2] = 32;
		side[3] = 33;
		side[4] = 34;
		side[5] = 35;
		side[6] = 47;
		side[7] = 46;
		side[8] = 45;
		side[9] = 27;
		side[10] = 28;
		side[11] = 29;
		sides.put("O".charAt(0), side);
		side = new int[12];
		side[0] = 42;
		side[1] = 43;
		side[2] = 44;
		side[3] = 35;
		side[4] = 26;
		side[5] = 17;
		side[6] = 2;
		side[7] = 1;
		side[8] = 0;
		side[9] = 9;
		side[10] = 18;
		side[11] = 27;
		sides.put("W".charAt(0), side);
		return sides;
	}

	private static HashMap<String, Integer> initGoalCorners() {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		result.put("GRW", 0);
		result.put("BRW", 1);
		result.put("GRY", 2);
		result.put("BRY", 3);
		result.put("GOY", 4);
		result.put("BOY", 5);
		result.put("GOW", 6);
		result.put("BOW", 7);
		return result;
	}

	private static HashMap<Character, Integer> initCenters() {
		HashMap<Character, Integer> centers = new HashMap<Character, Integer>();
		centers.put("R".charAt(0), 4);
		centers.put("G".charAt(0), 19);
		centers.put("Y".charAt(0), 22);
		centers.put("B".charAt(0), 25);
		centers.put("O".charAt(0), 40);
		centers.put("W".charAt(0), 49);
		return centers;
	}

	private static HashMap<String, Integer> initGoalEdges() {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		result.put("RW", 0);
		result.put("GR", 1);
		result.put("BR", 2);
		result.put("RY", 3);
		result.put("GW", 4);
		result.put("GY", 5);
		result.put("BY", 6);
		result.put("BW", 7);
		result.put("GO", 8);
		result.put("OY", 9);
		result.put("BO", 10);
		result.put("OW", 11);
		return result;
	}

	/**
	 * Method for reading in a file to set up the initial
	 * state of the Cube.
	 * @param fileName the path of the file
	 * @return a string of the file with all newlines and spaces removed.
	 */
	public char[] readTextFile(String fileName) {
		String returnValue = "";
		FileReader file = null;
		String line;
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				returnValue += line.trim();
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
		return returnValue.trim().toCharArray();
	}

	/**
	 * Verifies if the current state of the cube is valid.
	 * @return true if state is valid, false if not.
	 */
	private static boolean verifyCube(char[] state) {
		// Check that the length is 54
		if (state.length != 54) {
			return false;
		}
		// Check that the centers are of the proper color
		for (Map.Entry<Character, Integer> center : Cube.CENTERS.entrySet()) {
			if (state[center.getValue()] != center.getKey()) {
				return false;
			}
		}
		try {
			// Encode the corners
			String encodedCorners = Cube.encodeCorners(state);
			String encodedEdges = Cube.encodeEdges(state);
			int encodedEdgesSetOne = Integer.parseInt(encodedEdges.substring(0, 6));
			int encodedEdgesSetTwo = Integer.parseInt(encodedEdges.substring(6, 12));
			int h = IDAStar.corners[Integer.parseInt(encodedCorners)];
			h = IDAStar.edgesSetOne[encodedEdgesSetOne];
			h = IDAStar.edgesSetTwo[encodedEdgesSetTwo];
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @return true if the state equals the goal, false otherwise
	 */
	public boolean isSolved() {
		if (new String(this.state).equals(Cube.GOAL)) {
			return true;
		}
		return false;
	}

	/**
	 * Does the actual rotation of a cube given the face and amount of clockwise turns.
	 * @param face the character representing the color face to turn clockwise.
	 * @param turns the number of clockwise turns.
	 * @return true if rotate was successful, else false if there was an error.
	 */
	public static char[] rotate(char[] state, Character face, int turns) {
		// Turning it by any number of turns mod 4 would be wasting CPU cycles
		if (turns % 4 == 0) {
			return state;
		}
		int[] thisFace = FACES.get(face);
		int[] theSides = Cube.SIDES.get(face);
		// Error checking to verify that the given character is valid
		// and in the map and that we got both theFace and theSide
		if (thisFace == null || theSides == null) {
			return state;
		}

		// An array representing the chars of the state after rotations
		char[] newStateArray = state.clone();

		// Rotate the face
		rotateFace(state, thisFace, turns, newStateArray);
		// Rotate the sides
		rotateSide(state, theSides, turns, newStateArray);

		// Set the state to the newly rotated cube
		state = newStateArray;
		// Return true because it was successful!
		return state;
	}

	/**
	 * Internal handler to rotate a face of the cube
	 * @param thisFace the int[] representing the face we are rotating.
	 *                 Should be an element of the Cube.FACES
	 * @param turns the amount of times we will be rotating the cube
	 * @param newStateArray the array that will represent the state of
	 *                      the cube after all rotations
	 */
	private static void rotateFace(char[] state, int[] thisFace, int turns, char[] newStateArray) {
		for (int i = 0; i < thisFace.length; i++) {
			newStateArray[thisFace[(i + (2 * turns)) % 8]] = state[thisFace[i]];
		}
	}

	/**
	 * Internal handler to rotate a side of the cube
	 * @param theSides the int[] representing the face we are rotating.
	 *                 Should be an element of the Cube.SIDES
	 * @param turns the amount of times we will be rotating the cube
	 * @param newStateArray the array that will represent the state of
	 *                      the cube after all rotations
	 */
	private static void rotateSide(char[] state, int[] theSides, int turns, char[] newStateArray) {
		for (int i = 0; i < theSides.length; i++) {
			int moveInt = theSides[(i + (3 * turns)) % theSides.length];
			newStateArray[moveInt] = state[theSides[i]];
		}
	}

	/**
	 * Internal handler for encoding of corners or edges.
	 * Does all of the variable-based encoding for either the corners
	 * or edges. This function shouldn't necessarily be called directly,
	 * but rather be called from the encodeCorners or encodeEdges functions.
	 * @param mappedSides a HashMap<Integer, Integer> of the mapped edges/corners
	 *                    we want to encode.
	 * @param resultLength the expected result length. This function will
	 *                     return a string that is resultLength - 1 to save
	 *                     space since the last number in the string will
	 *                     always be 0.
	 * @return a String that represents the variable-based number representation
	 *         of the given corner or edge.
	 */
	private static String encode(HashMap<Integer, Integer> mappedSides, int resultLength) {
		String[] result = new String[resultLength];
		for (int i = 0; i < mappedSides.size(); i++) {
			// Find the shift amount for this current position
			int diffAmount = 0;
			int thisCorner = mappedSides.get(i);
			for (int j = 0; j < i; j++) {
				if (mappedSides.get(j) < thisCorner) {
					diffAmount++;
				}
			}
			result[i] = Integer.toString(thisCorner - diffAmount);
		}
		// Take the last number off to save space since
		// it will always be 0.
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < result.length - 1; i++) {
			builder.append(result[i]);
		}
		return builder.toString();
	}

	/**
	 * Calls the internal encode() function for the corners.
	 * @return A string that represents the unique state of the corners
	 * 		   in a variable-based numbering system.
	 */
	public static String encodeCorners(char[] state) {
		return encode(mapCorners(state), 8);
	}

	/**
	 * Maps each corner to its current position in the cube.
	 * @return a HashMap<Integer, Integer> where the key is the
	 * corner and the value is the current position.
	 * Please reference the CORNERS[][] variable to find out
	 * which corners are which.
	 */
	private static HashMap<Integer, Integer> mapCorners(char[] state) {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		int[][] corners = Cube.CORNERS;
		HashMap<String, Integer> goalCorners = initGoalCorners();
		for(int j = 0; j < corners.length; j++) {
			String needle = "";
			for (int s : corners[j]) {
				needle += state[s];
			}
			char[] chars = needle.toCharArray();
			Arrays.sort(chars);
			result.put(goalCorners.get(new String(chars)), j);
		}
		return result;
	}

	/**
	 * Calls the internal encode() function for the edges.
	 * @return A string that represents the unique state of the edges
	 * 		   in a variable-based numbering system.
	 */
	public static String encodeEdges(char[] state) {
		HashMap<Integer, Integer> mappedEdges = mapEdges(state);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < mappedEdges.size(); i++) {
			builder.append(mappedEdges.get(i));
		}
		return builder.toString();
	}

	/**
	 * Maps each edge to its current position in the cube.
	 * @return a HashMap<Integer, Integer> where the key is the
	 * corner and the value is the current position.
	 * Please reference the EDGES[][] variable to find out
	 * which edges are which.
	 */
	private static HashMap<Integer, Integer> mapEdges(char[] state) {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (int i = 0; i < Cube.EDGES.length; i++) {
			char[] needle = new char[Cube.EDGES[i].length];
			for (int j = 0; j < Cube.EDGES[i].length; j++) {
				needle[j] = state[Cube.EDGES[i][j]];
			}
			Arrays.sort(needle);
			result.put(Cube.GOALEDGES.get(new String(needle)), i);
		}
		return result;
	}

	/**
	 * Random generate a valid cube by starting in the goal state
	 * then doing 100 random rotations just as outlined the
	 * "Experimental Results" section of Korf's paper
	 * Finding Optimal Solutions to Rubik's Cube Using Pattern Databases
	 * @return a randomly generated cube
	 */
	public static Cube generateRandomCube() {
		// Initialize a cube in a goal state
		Cube cube = new Cube(Cube.GOAL.toCharArray());

		// As per Korf's paper, they generated random Cubes by doing
		// 100 random moves from the goal state.
		for (int i = 0; i < 100; i++) {
			Random r = new Random();
			// Pick a random face to rotate
			ArrayList<Character> keys = new ArrayList<Character>(Cube.FACES.keySet());
			// Perform the rotation
			cube.state = Cube.rotate(cube.state, keys.get(r.nextInt(keys.size())), 1);
		}
		return cube;
	}

	/**
	 * Super nice way to print out the cube in a 2D fashion
	 * @return a string of the 2D representation of the cube
	 */
	@Override
	public String toString() {
		String result = "";

		for (int i = 0; i < this.state.length; i++) {
			if (i < 9 || i > 35) {
				if (i % 3 == 0) {
					result += "   " + this.state[i];
				} else if (i % 3 == 2) {
					result += this.state[i] + "\n";
				} else {
					result += this.state[i];
				}
			} else {
				if (i % 9 == 8) {
					result += this.state[i] + "\n";
				} else {
					result += this.state[i];
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		Cube cube;
		if (args.length <= 0) {
			cube = new Cube("input5.txt");
		} else {
			cube = new Cube(args[0]);
		}
		System.out.println(cube.toString());
		System.out.println("Is a valid cube: " + Cube.verifyCube(cube.state));
		if (Cube.verifyCube(cube.state)) {
			System.out.println(cube.isSolved());
			cube.state = Cube.rotate(cube.state, "R".charAt(0), 1);
			System.out.println(cube);
		}

	}
}
