package vut.ija2023.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import vut.ija2023.enviroment.Position;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Handles the loading of game configurations from JSON files.
 * This includes deserializing Position objects and loading robot and obstacle configurations.
 */
public class ConfigurationLoader {

    /**
     * Loads a game configuration from a JSON file.
     *
     * @param filePath the path of the JSON file
     * @return the loaded game configuration
     * @throws IOException if an input or output exception occurred
     */
    public static Configuration loadConfiguration(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // functionality for reading and writing JSON
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Position.class, new PositionDeserializer());
        mapper.registerModule(module);

        File jsonFile = new File(filePath);
        Configuration config = mapper.readValue(jsonFile, Configuration.class);
        return config;
    }

    /**
     * Represents a game configuration.
     * A game configuration includes a list of robots and a list of obstacles.
     */
    public static class Configuration {
        private List<Robot> robots;
        private List<Obstacle> obstacles;

        public List<Robot> getRobots() {
            return robots;
        }

        public void setRobots(List<Robot> robots) {
            this.robots = robots;
        }

        public List<Obstacle> getObstacles() {
            return obstacles;
        }

        public void setObstacles(List<Obstacle> obstacles) {
            this.obstacles = obstacles;
        }
    }

    /**
     * Represents a robot configuration.
     * A robot configuration includes a position, an angle, and a type.
     */
    public static class Robot {
        private Position position;
        private int angle;
        private String type;

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
        }

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * Represents an obstacle configuration.
     * An obstacle configuration includes a position.
     */
    public static class Obstacle {
        private Position position;

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }
}
