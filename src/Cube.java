import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A representation of a physical Rubik's cube.
 */
public class Cube {
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
    public static int[][] CORNERS = new int[][] {
        new int[3],
        new int[3],
        new int[3],
        new int[3],
        new int[3],
        new int[3],
        new int[3],
        new int[3]
    };

    /**
     * A two-dimensional primitive array of ints representing the location of the edges.
     * The array is 12 rows by 2 columns.
     * Each row represents a physical edge cubie on the Rubik's cube.
     * Each column in the row represents a location in the serialize version of the cube.
     */
    public static int[][] EDGES = new int[][] {
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

    /**
     * An atrocious constructor that will probably have to be redone.
     * Initializes the CORNERS and EDGES with the positions in our serialized
     * representation of a Rubik's cube.
     */
    public Cube() {
        // Oh god this is a monstrosity. What have I done?
        CORNERS[0][0] = 0;
        CORNERS[0][1] = 9;
        CORNERS[0][2] = 51;
        CORNERS[1][0] = 2;
        CORNERS[1][1] = 17;
        CORNERS[1][2] = 53;
        CORNERS[2][0] = 6;
        CORNERS[2][1] = 11;
        CORNERS[2][2] = 12;
        CORNERS[3][0] = 8;
        CORNERS[3][1] = 14;
        CORNERS[3][2] = 15;
        CORNERS[4][0] = 29;
        CORNERS[4][1] = 30;
        CORNERS[4][2] = 36;
        CORNERS[5][0] = 32;
        CORNERS[5][1] = 33;
        CORNERS[5][2] = 38;
        CORNERS[6][0] = 27;
        CORNERS[6][1] = 42;
        CORNERS[6][2] = 45;
        CORNERS[7][0] = 35;
        CORNERS[7][1] = 44;
        CORNERS[7][2] = 47;
        // And it keeps getting worse...
        EDGES[0][0] = 1;
        EDGES[0][1] = 52;
        EDGES[1][0] = 3;
        EDGES[1][1] = 10;
        EDGES[2][0] = 5;
        EDGES[2][1] = 16;
        EDGES[3][0] = 7;
        EDGES[3][1] = 13;
        EDGES[4][0] = 18;
        EDGES[4][1] = 48;
        EDGES[5][0] = 20;
        EDGES[5][1] = 21;
        EDGES[6][0] = 23;
        EDGES[6][1] = 24;
        EDGES[7][0] = 26;
        EDGES[7][1] = 50;
        EDGES[8][0] = 28;
        EDGES[8][1] = 39;
        EDGES[9][0] = 31;
        EDGES[9][1] = 37;
        EDGES[10][0] = 34;
        EDGES[10][1] = 41;
        EDGES[11][0] = 43;
        EDGES[11][1] = 46;
    }

    public static void main(String[] args) {
        Cube cube = new Cube();
    }
}
