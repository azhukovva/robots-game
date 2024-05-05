package vut.ija2023.room;

import vut.ija2023.common.Environment;
import vut.ija2023.common.Robot;
import vut.ija2023.enviroment.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the environment.
 * A room is a grid of cells where each cell can contain a robot or an obstacle.
 */
public class Room implements Environment {
    private int rows;
    private int cols;
    private List<Robot> robotsList;
    public Object [][] area;

    /**
     * Private constructor for a Room.
     * This constructor is private to enforce the use of the create method for creating Room instances.
     *
     * @param rows the number of rows in the room
     * @param cols the number of columns in the room
     */
    private Room(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.area = new Object[rows][cols];
        this.robotsList =  new ArrayList<Robot>();
    }

    /**
     * Creates a new Room.
     * This method validates the input parameters and returns a new Room if they are valid.
     *
     * @param rows the number of rows in the room
     * @param cols the number of columns in the room
     * @return a new Room if the input parameters are valid, null otherwise
     */
    public static Room create(int rows, int cols){
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rozměry místnosti musí být větší než 0.");
        }
        return new Room(rows, cols);
    }

    /**
     * Adds a robot to the room.
     *
     * @param robot the robot to be added
     * @return true if the robot was added successfully, false otherwise
     */
    @Override
    public boolean addRobot(Robot robot) {
        Position pos = robot.getPosition();
        if (!containsPosition(pos)) {
            return false;
        }
        if (area[pos.getRow()][pos.getCol()] != null){
            return false;
        }
        area[pos.getRow()][pos.getCol()] = robot;
        robotsList.add(robot);
        return true;
    }

    /**
     * Moves an object from one position to another in the room.
     *
     * @param oldpos the current position of the object
     * @param newpos the new position of the object
     */
    @Override
    public void moveObject(Position oldpos, Position newpos) {
        area[newpos.getRow()][newpos.getCol()] = area[oldpos.getRow()][oldpos.getCol()];
        area[oldpos.getRow()][oldpos.getCol()] = null;
    }

    /**
     * Creates an obstacle at a specified position in the room.
     *
     * @param row the row of the position
     * @param col the column of the position
     * @return true if the obstacle was created successfully, false otherwise
     */
    @Override
    public boolean createObstacleAt(int row, int col) {
        Position pos = new Position(row, col);
        if (!containsPosition(pos))
            return false;
        if (area[row][col] != null)
            return false;
        area[row][col] = new Obstacle(this, new Position(row, col));
        return true;
    }

    @Override
    public boolean obstacleAt(int row, int col) {
        Position pos = new Position(row, col);
        if(!containsPosition(pos))
            return false;
        return area[row][col] instanceof Obstacle;
    }

    @Override
    public boolean obstacleAt(Position p) {
        return obstacleAt(p.getRow(), p.getCol());
    }
    @Override
    public int rows() { return rows; }
    @Override
    public int cols() {
        return cols;
    }

    /**
     * Returns a list of all robots in the room.
     *
     * @return a list of all robots in the room
     */
    @Override
    public List<Robot> robots() {
        return new ArrayList<>(robotsList);
    }

    /**
     * Checks if a robot is at a specified position in the room.
     *
     * @param p the position to check
     * @return true if a robot is at the specified position, false otherwise
     */
    @Override
    public boolean robotAt(Position p) {
        if(!containsPosition(p))
            return false;
        return area[p.getRow()][p.getCol()] instanceof Robot ;
    }

    /**
     * Checks if a position is within the bounds of the room.
     *
     * @param pos the position to check
     * @return true if the position is within the bounds of the room, false otherwise
     */
    @Override
    public boolean containsPosition(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < rows && pos.getCol() >= 0 && pos.getCol() < cols;
    }

    /**
     * Clears the room of all robots.
     */
    @Override
    public void clear() {
        this.robotsList.clear();
    }


}