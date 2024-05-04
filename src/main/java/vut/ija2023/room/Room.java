package vut.ija2023.room;

import vut.ija2023.common.Environment;
import vut.ija2023.common.Robot;
import vut.ija2023.enviroment.Position;

import java.util.ArrayList;
import java.util.List;

public class Room implements Environment {
    private int rows;
    private int cols;
    private List<Robot> robotsList;
    public Object [][] area;

    private Room(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.area = new Object[rows][cols];
        this.robotsList =  new ArrayList<Robot>();
    }
    public static Room create(int rows, int cols){
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rozměry místnosti musí být větší než 0.");
        }
        return new Room(rows, cols);
    }
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

    @Override
    public void moveObject(Position oldpos, Position newpos) {
        area[newpos.getRow()][newpos.getCol()] = area[oldpos.getRow()][oldpos.getCol()];
        area[oldpos.getRow()][oldpos.getCol()] = null;
    }

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
    @Override
    public List<Robot> robots() {
        return new ArrayList<>(robotsList);
    }
    @Override
    public boolean robotAt(Position p) {
        if(!containsPosition(p))
            return false;
        return area[p.getRow()][p.getCol()] instanceof Robot ;
    }
    @Override
    public boolean containsPosition(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < rows && pos.getCol() >= 0 && pos.getCol() < cols;
    }

    @Override
    public void clear() {
        this.robotsList.clear();
    }


}