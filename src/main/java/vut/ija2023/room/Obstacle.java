package vut.ija2023.room;

import vut.ija2023.common.Environment;
import vut.ija2023.enviroment.Position;

public class Obstacle {
    private Position position;
    public Environment environment;
    public Obstacle(Environment env, Position pos){
        this.position = pos;
        this.environment = env;
    }

    public Position getPosition(){
        return position;
    }
}