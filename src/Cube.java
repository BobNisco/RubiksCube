import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public static int[][] edges = initEdges();

    /**
     * Initializes the state to an empty String.
     * Initializes the corners and edges with the positions in our serialized
     * representation of a Rubik's cube.
     */
    public Cube() {
        this.state = "";
    }

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
        return returnValue;
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

    public static void main(String[] args) {
        Cube cube = new Cube(args[0]);
        System.out.println(cube.state);
        System.out.print(cube.isSolved());
    }
}
