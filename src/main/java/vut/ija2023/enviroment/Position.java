package vut.ija2023.enviroment;

public class Position {
    private final int x;
    private final int y;

    public Position(int row, int col) {
        this.x = row;
        this.y = col;
    }

    public int getRow() {
        return this.x;
    }

    public int getCol() {
        return this.y;
    }

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

    public int hashCode() {
        int var1 = 3;
        var1 = 71 * var1 + this.x;
        var1 = 71 * var1 + this.y;
        return var1;
    }

    public String toString() {
        return "[" + this.x + "@" + this.y + "]";
    }
}