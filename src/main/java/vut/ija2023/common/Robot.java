package vut.ija2023.common;

import javafx.scene.image.ImageView;
import vut.ija2023.enviroment.Position;

public interface Robot {
    int angle();
    void turn();
    void turnReverse();
    boolean canMove();
    boolean move();
    Position getPosition();
    ImageView getImageView();
    void toggleFlag();
}
