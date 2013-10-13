import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A representation of a physical Rubik's cube.
 */
public class Cube {

	public String state;

	/**
	 * The serialized representation of our goal state.
	 */
	public static String GOAL = "RRRRRRRRRGGGYYYBBBGGGYYYBBBGGGYYYBBBOOOOOOOOOWWWWWWWWW";

	/**
	 * A two-dimensional primitive array of ints representing the location of the corners.
	 * The array is 8 rows by 3 columns.
	 * Each row represents a physical corner cubie on the Rubik's cube.
	 * Each column in the row represents a location in the serialized version of the cube.
	 */
	public static int[][] CORNERS = initCorners();

	/**
	 * A two-dimensional primitive array of ints representing the location of the edges.
	 * The array is 12 rows by 2 columns.
	 * Each row represents a physical edge cubie on the Rubik's cube.
	 * Each column in the row represents a location in the serialize version of the cube.
	 */
	public static int[][] EDGES = initEdges();

	/**
	 * A Map<Character, int[]> where the key is the character of the
	 * color of the face and the int[] is the indices of the string in
	 * which the face represents.
	 */
	public static Map<Character, int[]> FACES = initFaces();

	/**
	 * A Map<Character, int[]>, where the key is the character of the
	 * color of the face and the int[] is the indices of the string in
	 * which the side represents.
	 */
	public static Map<Character, int[]> SIDES = initSides();

	/**
	 * Initializes the state to an empty String.
	 * Initializes the corners and edges with the positions in our serialized
	 * representation of a Rubik's cube.
	 */
	public Cube() {
		this.state = "";
	}

	/**
	 * Initializes the Cube to the input of a file.
	 * @param fileName the filepath of the text file that represents a cube
	 */
	public Cube(String fileName) {
		this.state = this.readTextFile(fileName);
	}

	private static int[][] initCorners() {
		int[][] corners = new int[][] {
				new int[3],
				new int[3],
				new int[3],
				new int[3],
				new int[3],
				new int[3],
				new int[3],
				new int[3]
		};
		// Oh god this is a monstrosity. What have I done?
		corners[0][0] = 0;
		corners[0][1] = 9;
		corners[0][2] = 51;
		corners[1][0] = 2;
		corners[1][1] = 17;
		corners[1][2] = 53;
		corners[2][0] = 6;
		corners[2][1] = 11;
		corners[2][2] = 12;
		corners[3][0] = 8;
		corners[3][1] = 14;
		corners[3][2] = 15;
		corners[4][0] = 29;
		corners[4][1] = 30;
		corners[4][2] = 36;
		corners[5][0] = 32;
		corners[5][1] = 33;
		corners[5][2] = 38;
		corners[6][0] = 27;
		corners[6][1] = 42;
		corners[6][2] = 45;
		corners[7][0] = 35;
		corners[7][1] = 44;
		corners[7][2] = 47;
		return corners;
	}

	private static int[][] initEdges() {
		int[][] edges = new int[][] {
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2],
				new int[2]
		};
		// And it keeps getting worse...
		edges[0][0] = 1;
		edges[0][1] = 52;
		edges[1][0] = 3;
		edges[1][1] = 10;
		edges[2][0] = 5;
		edges[2][1] = 16;
		edges[3][0] = 7;
		edges[3][1] = 13;
		edges[4][0] = 18;
		edges[4][1] = 48;
		edges[5][0] = 20;
		edges[5][1] = 21;
		edges[6][0] = 23;
		edges[6][1] = 24;
		edges[7][0] = 26;
		edges[7][1] = 50;
		edges[8][0] = 28;
		edges[8][1] = 39;
		edges[9][0] = 31;
		edges[9][1] = 37;
		edges[10][0] = 34;
		edges[10][1] = 41;
		edges[11][0] = 43;
		edges[11][1] = 46;
		return edges;
	}

	private static Map<Character, int[]> initFaces() {
		Map<Character, int[]> faces = new HashMap<Character, int[]>();
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

	private static Map<Character, int[]> initSides() {
		Map<Character, int[]> sides = new HashMap<Character, int[]>();
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
		side[11] = 1;
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

	/**
	 * Method for reading in a file to set up the initial
	 * state of the Cube.
	 * @param fileName the path of the file
	 * @return a string of the file with all newlines and spaces removed.
	 */
	public String readTextFile(String fileName) {
		String returnValue = "";
		FileReader file = null;
		String line = "";
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
		return returnValue.trim();
	}

	/**
	 * Verifies if the current state of the cube is valid.
	 * @return true if state is valid, false if not.
	 */
	private boolean verifyCube() {
		if (this.state.length() != 54) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @return true if the state equals the goal, false otherwise
	 */
	public boolean isSolved() {
		if (this.state.equals(Cube.GOAL)) {
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
	public boolean rotate(Character face, int turns) {
		// Turning it by any number of turns mod 4 would be wasting CPU cycles
		if (turns % 4 == 0) {
			return true;
		}
		int[] thisFace = FACES.get(face);
		int[] theSides = Cube.SIDES.get(face);
		// Error checking to verify that the given character is valid
		// and in the map and that we got both theFace and theSide
		if (thisFace == null || theSides == null) {
			return false;
		}

		// Make a copy of the state array so we can reference it
		final char[] stateArray = this.state.toCharArray();
		// An array representing the chars of the state after rotations
		char[] newStateArray = this.state.toCharArray();

		// Rotate the face
		this.rotateFace(thisFace, turns, newStateArray, stateArray);
		// Rotate the sides
		this.rotateSide(theSides, turns, newStateArray, stateArray);

		// Set the state to the newly rotated cube
		this.state = new String(newStateArray);
		// Return true because it was successful!
		return true;
	}

	/**
	 * Internal handler to rotate a face of the cube
	 * @param thisFace the int[] representing the face we are rotating.
	 *                 Should be an element of the Cube.FACES
	 * @param turns the amount of times we will be rotating the cube
	 * @param newStateArray the array that will represent the state of
	 *                      the cube after all rotations
	 * @param stateArray a reference to the state of the cube before turning
	 */
	private void rotateFace(int[] thisFace, int turns, char[] newStateArray, char[] stateArray) {
		for (int i = 0; i < thisFace.length; i++) {
			newStateArray[thisFace[(i + (2 * turns)) % 8]] = stateArray[thisFace[i]];
		}
	}

	/**
	 * Internal handler to rotate a side of the cube
	 * @param theSides the int[] representing the face we are rotating.
	 *                 Should be an element of the Cube.SIDES
	 * @param turns the amount of times we will be rotating the cube
	 * @param newStateArray the array that will represent the state of
	 *                      the cube after all rotations
	 * @param stateArray a reference to the state of the cube before turning
	 */
	private void rotateSide(int[] theSides, int turns, char[] newStateArray, char[] stateArray) {
		for (int i = 0; i < theSides.length; i++) {
			int moveInt = theSides[(i + (3 * turns)) % theSides.length];
			newStateArray[moveInt] = stateArray[theSides[i]];
		}
	}

	/**
	 * Encodes the corners to a variable-based numbering system.
	 * @return A string that represents the unique state of the corners
	 * 		   in a variable-based numbering system.
	 */
	private String encodeCorners() {
		Map<Integer, Integer> mappedCorners = mapCorners();
		String[] result = new String[8];
		for (int i = 0; i < mappedCorners.size(); i++) {
			// Find the shift amount for this current position
			int diffAmount = 0;
			int thisCorner = mappedCorners.get(i);
			for (int j = 0; j < i; j++) {
				if (mappedCorners.get(j) < thisCorner) {
					diffAmount++;
				}
			}
			result[i] = Integer.toString(thisCorner - diffAmount, 8 - i);
		}
		String stringResult = "";
		// Take the last number off to save space since
		// it will always be 0.
		for (int i = 0; i < result.length - 1; i++) {
			stringResult += result[i];
		}
		System.out.println(stringResult);
		return stringResult;
	}

	/**
	 * Maps each corner to its current position in the cube.
	 * @return a Map<Integer, Integer> where the key is the
	 * corner and the value is the current position.
	 * Please reference the CORNERS[][] variable to find out
	 * which corners are which.
	 */
	private Map<Integer, Integer> mapCorners() {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (int i = 0; i < Cube.CORNERS.length; i++) {
			String searchingFor = "";
			for (int s : Cube.CORNERS[i]) {
				searchingFor += Cube.GOAL.charAt(s);
			}
			// We need to sort this string so we can find it
			char[] chars = searchingFor.toCharArray();
			Arrays.sort(chars);
			searchingFor = new String(chars);
			// Now we know what colors we're looking for
			for(int j = 0; j < Cube.CORNERS.length; j++) {
				String haystack = "";
				for (int s : Cube.CORNERS[j]) {
					haystack += this.state.charAt(s);
				}
				char[] chars2 = haystack.toCharArray();
				Arrays.sort(chars2);
				haystack = new String(chars2);
				if (haystack.equals(searchingFor)) {
					result.put(i, j);
				}
			}
		}
		for (int i = 0; i < result.size(); i++) {
			System.out.println( i + " : " + result.get(i));
		}
		return result;
	}

	/**
	 * Super nice way to print out the cube in a 2D fashion
	 * @return a string of the 2D representation of the cube
	 */
	@Override
	public String toString() {
		String result = "";

		char[] stateArray = this.state.toCharArray();

		for (int i = 0; i < stateArray.length; i++) {
			if (i < 9 || i > 35) {
				if (i % 3 == 0) {
					result += "   " + stateArray[i];
				} else if (i % 3 == 2) {
					result += stateArray[i] + "\n";
				} else {
					result += stateArray[i];
				}
			} else {
				if (i % 9 == 8) {
					result += stateArray[i] + "\n";
				} else {
					result += stateArray[i];
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		Cube cube = null;
		if (args.length <= 0) {
			cube = new Cube("input1.txt");
		} else {
			cube = new Cube(args[0]);
		}
		System.out.println(cube.toString());
		System.out.println("Is a valid cube: " + cube.verifyCube());
		System.out.println(cube.isSolved());
		cube.rotate("R".charAt(0), 0);
		cube.encodeCorners();
		System.out.println(cube.toString());
	}
}
