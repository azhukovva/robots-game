package vut.ija2023.common;

import javafx.scene.image.ImageView;
import vut.ija2023.BotsController;
import vut.ija2023.common.log.Log;
import vut.ija2023.enviroment.Position;

public abstract class AbstractRobot implements Robot {
    private BotsController controller;
    public boolean messageFlag = false;
    private ImageView view;
    public AbstractRobot(BotsController controller, ImageView view) {
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
