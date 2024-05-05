package vut.ija2023.room;

import vut.ija2023.common.Environment;
import vut.ija2023.enviroment.Position;

/**
 * Represents an obstacle in the environment.
 * An obstacle is a position in the environment that a robot cannot move to.
 */
public class Obstacle {
    private Position position;
    public Environment environment;

    /**
     * Constructs a new Obstacle with the specified environment and position.
     *
     * @param env the environment in which the obstacle exists
     * @param pos the position of the obstacle in the environment
     */
    public Obstacle(Environment env, Position pos){
        this.position = pos;
        this.environment = env;
    }

    /**
     * Returns the position of the obstacle.
     *
     * @return the position of the obstacle
     */
    public Position getPosition(){
        return position;
    }
}