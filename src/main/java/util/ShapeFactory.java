package util;

import drawers.*;

public class ShapeFactory {
    public static Shape createShape(String type) {
        return switch (type) {
            case "Ellipse" -> new EllipseShape();
            case "Rectangle" -> new RectShape();
            case "LineSegment" -> new LineSegmentShape();
            case "Line" -> new LineShape();
            case "Point" -> new PointShape();
            case "Cube" -> new CubeShape();
            case "Brush" -> new BrushShape();
            default -> throw new IllegalArgumentException("Unknown shape type: " + type);
        };
    }
}
