package vut.ija2023.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import vut.ija2023.enviroment.Position;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigurationLoader {

    public static Configuration loadConfiguration(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // functionality for reading and writing JSON
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Position.class, new PositionDeserializer());
        mapper.registerModule(module);

        File jsonFile = new File(filePath);
        Configuration config = mapper.readValue(jsonFile, Configuration.class);
        return config;
    }

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

        @Override
        public String toString() {
            return "Configuration{" +
                    "robots=" + robots +
                    ", obstacles=" + obstacles +
                    '}';
        }
    }

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

        @Override
        public String toString() {
            return "Robot{" +
                    "position=" + position +
                    ", angle=" + angle +
                    ", type=" + type +
                    '}';
        }
    }

    public static class Obstacle {
        private Position position;

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        @Override
        public String toString() {
            return "Obstacle{" +
                    "position=" + position +
                    '}';
        }
    }



}
