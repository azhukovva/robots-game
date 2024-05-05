package vut.ija2023.room;

import javafx.scene.image.ImageView;
import vut.ija2023.HelloController;
import vut.ija2023.common.AbstractRobot;
import vut.ija2023.common.Environment;
import vut.ija2023.common.Log;
import vut.ija2023.common.Robot;
import vut.ija2023.enviroment.Position;

public class ControlledRobot extends AbstractRobot implements Robot {
    private Position position;
    private Environment environment;
    private int angle = 0;
    private boolean selected = false;
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private ControlledRobot(Environment env, Position pos, HelloController controller, ImageView view) {
        super(controller, view);
        this.environment = env;
        this.position = pos;
    }

    public static ControlledRobot create(Environment env, Position pos, HelloController controller, ImageView view) {
        if (env == null || pos == null) {
            return null;
        }
        if (!env.containsPosition(pos)) {
            return null;
        }
        if (env.obstacleAt(pos)) {
            return null;
        }
        if (env.robotAt(pos)) {
            return null;
        }
        ControlledRobot thisBot = new ControlledRobot(env, pos, controller, view);
        env.addRobot(thisBot);
        return thisBot;
    }

    @Override
    public int angle() {
        return this.angle*45;
    }

    public void rotateImageView() {
        this.getImageView().setRotate(this.angle());
    }

    @Override
    public void turn() {
        super.notifyController(position, Log.MovementType.TURN, angle);
        angle = (angle + 1) % 8;
        rotateImageView();
    }

    @Override
    public void turnReverse() {
        super.notifyController(position, Log.MovementType.TURN, angle);
        angle = (angle - 1 + 8) % 8; // Add 8 before taking modulus to handle negative values
        rotateImageView();
    }
    /**
     * Ověřuje, zda je možný přesun na vedlejší políčko ve směru natočení robota. Vrací výsledek ověření.
     *
     * @return Operace je úspěšná, pokud vedlejší políčko existuje (tj. je součástí prostředí) a je prázdné.
     */
    @Override
    public boolean canMove() {
        int x=0, y=0;
        switch (angle){
            case 7: y = -1; x = -1; break;
            case 0: y = -1; break;
            case 1: y = -1; x = 1; break;
            case 2: x = 1; break;
            case 3: x = 1; y = 1; break;
            case 4: y = 1; break;
            case 5: x = -1; y = 1; break;
            case 6: x = -1; break;

        }
        Position newpos = new Position(position.getRow() + x,position.getCol() + y);
        if (environment.containsPosition(newpos)){
            return !(environment.obstacleAt(newpos)) && !(environment.robotAt(newpos));
        }

        return false;
    }

    /**
     * Provede přesun robota na vedlejší políčko ve směru natočení robota. Vrací výsledek akce.
     *
     * @return Operace je úspěšná, pokud vedlejší políčko existuje (tj. je součástí prostředí) a je prázdné.
     */
    @Override
    public boolean move() {
        if(canMove()){
            int x=0, y=0;
            switch (angle){
                case 7: y = -1; x = -1; break;
                case 0: y = -1; break;
                case 1: y = -1; x = 1; break;
                case 2: x = 1; break;
                case 3: x = 1; y = 1; break;
                case 4: y = 1; break;
                case 5: x = -1; y = 1; break;
                case 6: x = -1; break;
            }
            Position newpos = new Position(position.getRow() + x, position.getCol() + y);
            super.notifyController(position, Log.MovementType.MOVE, angle);
            this.environment.moveObject(position, newpos);
            this.position = newpos;
            return true;
        }
        return false;
    }
    @Override
    public Position getPosition() {
        return this.position;
    }

}
