package parser;

import drawers.Shape;
import util.ShapeFactory;

import java.awt.*;

public class StaticShapeParser extends ShapeParser {

    @Override
    public Shape stringToShape(String shapeString) {
        String[] shapeParts = shapeString.split(separator);

        String name = shapeParts[0];
        int x1 = Integer.parseInt(shapeParts[1]);
        int y1 = Integer.parseInt(shapeParts[2]);
        int x2 = Integer.parseInt(shapeParts[3]);
        int y2 = Integer.parseInt(shapeParts[4]);
        Color borderColor = rgbToColor(shapeParts[5]);
        String fillColorStr = shapeParts[6];
        int thickness = Integer.parseInt(shapeParts[7]);

        Shape shape = ShapeFactory.createShape(name);
        shape.set(x1, y1, x2, y2);
        shape.setBorderColor(borderColor);
        if (fillColorStr.trim() != "") {
            shape.setFillColor(rgbToColor(fillColorStr));
        } else {
            shape.makeEmpty();
        }
        shape.setThickness(thickness);
        return shape;
    }

    @Override
    public String shapeToString(Shape shape) {
        String[] shapeParts = splitStringData(shape);
        return String.join(separator, shapeParts);
    }

    @Override
    public String[] splitStringData(Shape shape) {
        String name = shape.getType();
        String x1 = Integer.toString(shape.getXs1());
        String y1 = Integer.toString(shape.getYs1());
        String x2 = Integer.toString(shape.getXs2());
        String y2 = Integer.toString(shape.getYs2());
        String borderColor = colorToRGB(shape.getBorderColor());
        String fillColor = shape.isFilled() ? colorToRGB(shape.getFillColor()) : "";
        String thickness = Integer.toString(shape.getThickness());

        return new String[] {name, x1, y1, x2, y2, borderColor, fillColor, thickness};
    }
}
