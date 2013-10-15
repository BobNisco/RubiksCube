import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

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
	public final static int[][] CORNERS = initCorners();

	/**
	 * A two-dimensional primitive array of ints representing the location of the edges.
	 * The array is 12 rows by 2 columns.
	 * Each row represents a physical edge cubie on the Rubik's cube.
	 * Each column in the row represents a location in the serialize version of the cube.
	 */
	public final static int[][] EDGES = initEdges();

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
	private boolean verifyCube() {
		// Check that the length is 54
		if (this.state.length != 54) {
			return false;
		}
		// Check that each color is either R O Y G B or W
		for (int i = 0; i < this.state.length; i++) {
			if (!(this.state[i] == "R".charAt(0) ||
				this.state[i] == "O".charAt(0) ||
				this.state[i] == "Y".charAt(0) ||
				this.state[i] == "G".charAt(0) ||
				this.state[i] == "B".charAt(0) ||
				this.state[i] == "W".charAt(0))) {
				return false;
			}
		}
		// Check that the centers are of the proper color
		for (Map.Entry<Character, Integer> center : Cube.CENTERS.entrySet()) {
			if (this.state[center.getValue()] != center.getKey()) {
				return false;
			}
		}
		// TODO:
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

		// An array representing the chars of the state after rotations
		char[] newStateArray = this.state.clone();

		// Rotate the face
		this.rotateFace(thisFace, turns, newStateArray);
		// Rotate the sides
		this.rotateSide(theSides, turns, newStateArray);

		// Set the state to the newly rotated cube
		this.state = newStateArray;
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
	 */
	private void rotateFace(int[] thisFace, int turns, char[] newStateArray) {
		for (int i = 0; i < thisFace.length; i++) {
			newStateArray[thisFace[(i + (2 * turns)) % 8]] = this.state[thisFace[i]];
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
	private void rotateSide(int[] theSides, int turns, char[] newStateArray) {
		for (int i = 0; i < theSides.length; i++) {
			int moveInt = theSides[(i + (3 * turns)) % theSides.length];
			newStateArray[moveInt] = this.state[theSides[i]];
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
	private String encode(HashMap<Integer, Integer> mappedSides, int resultLength) {
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
	private String encodeCorners() {
		return encode(mapCorners(), 8);
	}

	/**
	 * Maps each corner to its current position in the cube.
	 * @return a HashMap<Integer, Integer> where the key is the
	 * corner and the value is the current position.
	 * Please reference the CORNERS[][] variable to find out
	 * which corners are which.
	 */
	private HashMap<Integer, Integer> mapCorners() {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		for(int j = 0; j < Cube.CORNERS.length; j++) {
			String needle = "";
			for (int s : Cube.CORNERS[j]) {
				needle += this.state[s];
			}
			char[] chars = needle.toCharArray();
			Arrays.sort(chars);
			result.put(Cube.GOALCORNERS.get(new String(chars)), j);
		}
		return result;
	}

    //*************

    /**
     *
     * @param i  the key that will identify the stored cube state
     * @param cube the value (cube state) after a rotation
     * @return a Map<Integer, String> where the key is a integer and the value is
     * a cube state after a rotation.
     */
    private Map<Integer, String> cornersTable(int i, String cube) {
        Map<Integer, String> cornerTable;
        cornerTable = new HashMap<Integer, String>();

        cornerTable.put(i, cube);

        return cornerTable;
    }
    //***************

	/**
	 * Calls the internal encode() function for the edges.
	 * @return A string that represents the unique state of the edges
	 * 		   in a variable-based numbering system.
	 */
	private String encodeEdges() {
		return encode(mapEdges(), 12);
	}

	/**
	 * Maps each edge to its current position in the cube.
	 * @return a HashMap<Integer, Integer> where the key is the
	 * corner and the value is the current position.
	 * Please reference the EDGES[][] variable to find out
	 * which edges are which.
	 */
	private HashMap<Integer, Integer> mapEdges() {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (int i = 0; i < Cube.EDGES.length; i++) {
			char[] needle = new char[Cube.EDGES[i].length];
			for (int j = 0; j < Cube.EDGES[i].length; j++) {
				needle[j] = this.state[Cube.EDGES[i][j]];
			}
			Arrays.sort(needle);
			result.put(Cube.GOALEDGES.get(new String(needle)), i);
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
			cube = new Cube("input1.txt");
		} else {
			cube = new Cube(args[0]);
		}
		System.out.println(cube.toString());
		System.out.println("Is a valid cube: " + cube.verifyCube());
		if (cube.verifyCube()) {
			System.out.println(cube.isSolved());
			//cube.rotate("R".charAt(0), 1);

            //Rotates the R face three times and stores the state after each individual rotation
            //Then rotates G face three times and stores the state after each individual rotation
            //And so on...
            String faceColors[] = {"R", "G", "B", "O", "Y", "W"};
            for (int q = 0; q < faceColors.length; q++) {

                for (int i = 0; i < 3; i++) {
                    cube.rotate(faceColors[q].charAt(0), 1);
                    String x = cube.toString();
                    Map state = cube.cornersTable(i, x);

                    System.out.println(state.get(i));
                }
            }
		}
	}
}
