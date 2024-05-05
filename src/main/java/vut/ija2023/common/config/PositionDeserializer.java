package vut.ija2023.common.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import vut.ija2023.enviroment.Position;

import java.io.IOException;

/**
 * Handles the deserialization of Position objects from JSON.
 * This class extends the JsonDeserializer class and overrides its deserialize method.
 */
public class PositionDeserializer extends JsonDeserializer<Position> {
    /**
     * Deserializes a Position object from JSON.
     *
     * @param p the JSON parser
     * @param ctxt the deserialization context
     * @return the deserialized Position object
     * @throws IOException if an input or output exception occurred
     */
    @Override
    public Position deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        int row = node.get("x").asInt();
        int col = node.get("y").asInt();
        return new Position(row, col);
    }
}