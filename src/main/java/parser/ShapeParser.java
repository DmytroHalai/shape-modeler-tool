package parser;

import drawers.Shape;

import java.awt.*;

public abstract class ShapeParser {
    protected final String separator = "\t";
    protected final String rgbSeparator = ",";

    public abstract Shape stringToShape(String shapeString);

    public abstract String shapeToString(Shape shape);

    public abstract String[] splitStringData(Shape shape);

    protected Color rgbToColor(String rgb) {
        String[] parts = rgb.split(rgbSeparator);
        int red = Integer.parseInt(parts[0].trim());
        int green = Integer.parseInt(parts[1].trim());
        int blue = Integer.parseInt(parts[2].trim());
        return new Color(red, green, blue);
    }

    protected String colorToRGB(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return red + rgbSeparator + green + rgbSeparator + blue;
    }
}
