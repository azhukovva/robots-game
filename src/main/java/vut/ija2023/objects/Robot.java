package vut.ija2023.objects;

import vut.ija2023.Enviroment.Position;

public interface Robot {
    void turn();
    int angle();
    boolean canMove();
    boolean move();
    Position getPosition();
}
