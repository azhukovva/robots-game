package vut.ija2023.enviroment;

/**
 * Represents a position in a 2D grid.
 * A position is defined by its row (x) and column (y) coordinates.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a new Position with the specified row and column.
     *
     * @param row the row of the position
     * @param col the column of the position
     */
    public Position(int row, int col) {
        this.x = row;
        this.y = col;
    }

    /**
     * Returns the row of the position.
     *
     * @return the row of the position
     */
    public int getRow() {
        return this.x;
    }

    /**
     * Returns the column of the position.
     *
     * @return the column of the position
     */
    public int getCol() {
        return this.y;
    }

    /**
     * Checks if this position is equal to another object.
     *
     * @param var1 the object to compare with this position
     * @return true if the object is a position and has the same row and column as this position, false otherwise
     */
    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof Position)) {
            return false;
        } else {
            Position var2 = (Position)var1;
            return this.x == var2.x && this.y == var2.y;
        }
    }

    /**
     * Returns a hash code value for the position.
     *
     * @return a hash code value for the position
     */
    public int hashCode() {
        int var1 = 3;
        var1 = 71 * var1 + this.x;
        var1 = 71 * var1 + this.y;
        return var1;
    }

    /**
     * Returns a string representation of the position.
     *
     * @return a string representation of the position
     */
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}