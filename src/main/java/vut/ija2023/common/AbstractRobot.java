package vut.ija2023.common;

import javafx.scene.image.ImageView;
import vut.ija2023.HelloController;
import vut.ija2023.enviroment.Position;

public abstract class AbstractRobot implements Robot {
    private HelloController controller;
    private boolean messageFlag = false;
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

    public void notifyController(Position pos, String type ) {
        if (!messageFlag){
        controller.addMessage(pos, this,  type);
        messageFlag = true;
        }
    }

}
