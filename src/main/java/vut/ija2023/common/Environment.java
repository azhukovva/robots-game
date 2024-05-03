package vut.ija2023.common;

import vut.ija2023.enviroment.Position;

import java.util.List;

public interface Environment  {
    boolean addRobot(Robot robot);
    void moveObject(Position oldpos, Position newpos);
    boolean createObstacleAt(int row, int col);
    boolean obstacleAt(int row, int col);
    boolean obstacleAt(Position p);
    boolean robotAt(Position p);
    boolean containsPosition(Position pos);
    int rows();
    int cols();
    List<Robot> robots();
}
