package vut.ija2023.common;

import vut.ija2023.enviroment.Position;

public interface Robot {
    int angle();
    void turn(int var1);
    void turn();
    boolean canMove();
    boolean move();
    Position getPosition();
}
