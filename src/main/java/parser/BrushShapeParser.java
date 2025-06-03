package parser;

import drawers.BrushShape;
import drawers.PointShape;
import drawers.Shape;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BrushShapeParser extends ShapeParser {

    @Override
    public Shape stringToShape(String shapeString) {
        String[] shapeParts = shapeString.split(separator);
        return stringToShape(shapeParts);
    }

    @Override
    public Shape stringToShape(String[] shapeParts) {
        String points = shapeParts[1];
        Color borderColor = rgbToColor(shapeParts[2]);
        String fillColorStr = shapeParts[3];
        int thickness = Integer.parseInt(shapeParts[4]);

        BrushShape shape = new BrushShape();
        shape.setPoints(PointShape.stringToPoints(points));
        shape.setBorderColor(borderColor);
        if (!fillColorStr.isEmpty()) {
            shape.setFillColor(rgbToColor(fillColorStr));
        } else {
            shape.makeEmpty();
        }
        shape.setThickness(thickness);
        return shape;
    }

    @Override
    public String shapeToString(Shape shape) {
        BrushShape brushShape = (BrushShape) shape;
        String points = PointShape.pointsToString(brushShape.getPoints());

        String name = shape.getType();
        String borderColor = colorToRGB(shape.getBorderColor());
        String fillColor = shape.isFilled() ? colorToRGB(shape.getFillColor()) : "";
        String thickness = Integer.toString(shape.getThickness());

        List<String> shapeParts = Arrays.asList(name, points, borderColor, fillColor, thickness);
        return String.join(separator, shapeParts);
    }

    @Override
    public String[] splitStringData(Shape shape) {
        BrushShape brushShape = (BrushShape) shape;
        List<Point> points = brushShape.getPoints();
        String x1 = Integer.toString(points.getFirst().x);
        String y1 = Integer.toString(points.getFirst().y);
        String x2 = Integer.toString(points.getLast().x);
        String y2 = Integer.toString(points.getLast().y);

        String name = shape.getType();
        String borderColor = colorToRGB(shape.getBorderColor());
        String fillColor = shape.isFilled() ? colorToRGB(shape.getFillColor()) : "";
        String thickness = Integer.toString(shape.getThickness());

        return new String[] { name, x1, y1, x2, y2, borderColor, fillColor, thickness };
    }
}
