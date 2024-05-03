package vut.ija2023.common;

import vut.ija2023.HelloController;
import vut.ija2023.enviroment.Position;

public abstract class AbstractRobot implements Robot {
    private HelloController controller;
    public AbstractRobot(HelloController controller) {
        this.controller = controller;
    }
    private void notifyController(Position pos, ) {
        controller.notify(());
    }

}
