package vut.ija2023.common;

import javafx.scene.image.ImageView;
import vut.ija2023.HelloController;
import vut.ija2023.enviroment.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRobot implements Robot {
    private HelloController controller;
    public boolean messageFlag = false;
    private ImageView view;
    public AbstractRobot(HelloController controller, ImageView view) {
        this.controller = controller;
        this.view = view;
    }

    public ImageView getImageView(){
        return view;
    }

    public void toggleFlag(){
        messageFlag = !(messageFlag);
    }

    public void notifyController(Position pos, Log.MovementType type, int angle ) {
        if (!messageFlag){
            controller.addMessage(pos, this,  type, angle );
            messageFlag = true;
        }
    }

}
