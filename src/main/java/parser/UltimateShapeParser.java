package parser;

import drawers.Shape;

public class UltimateShapeParser extends ShapeParser {
    private final StaticShapeParser staticShapeParser = new StaticShapeParser();
    private final BrushShapeParser brushShapeParser = new BrushShapeParser();

    @Override
    public Shape stringToShape(String shapeString) {
        String[] shapeParts = shapeString.split(separator);
        return stringToShape(shapeParts);
    }

    @Override
    public Shape stringToShape(String[] shapeParts) {
        if (shapeParts[0].equals("Brush")) {
           return brushShapeParser.stringToShape(shapeParts);
        }
        return staticShapeParser.stringToShape(shapeParts);
    }

    @Override
    public String shapeToString(Shape shape) {
        if (shape.getType().equals("Brush")) {
            return brushShapeParser.shapeToString(shape);
        }
        return staticShapeParser.shapeToString(shape);
    }

    @Override
    public String[] splitStringData(Shape shape) {
        if (shape.getType().equals("Brush")) {
            return brushShapeParser.splitStringData(shape);
        }
        return staticShapeParser.splitStringData(shape);
    }
}
