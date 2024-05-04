package vut.ija2023.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import vut.ija2023.enviroment.Position;

public class ConfigurationLoader {

    public static void main(String[] args) {
//        try {
//            Configuration config = loadConfiguration("config.json");
//            System.out.println(config);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public static Configuration loadConfiguration(String filePath) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(new File(filePath), Configuration.class);
//    }

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
    }

    public static class Obstacle {
        private Position position;
    }

}
