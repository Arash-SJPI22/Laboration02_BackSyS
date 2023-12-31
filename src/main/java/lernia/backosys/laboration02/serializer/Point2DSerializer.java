package lernia.backosys.laboration02.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.io.IOException;

public class Point2DSerializer extends JsonSerializer<Point<G2D>> {
    @Override
    public void serialize(Point<G2D> G2DPoint, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("lat", G2DPoint.getPosition().getCoordinate(1));
            jsonGenerator.writeNumberField("lng", G2DPoint.getPosition().getCoordinate(0));
        jsonGenerator.writeEndObject();
    }
}
