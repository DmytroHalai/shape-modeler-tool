package util;

import drawers.BrushShape;
import drawers.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeEditor {
    protected final List<Shape> shapes = new ArrayList<>();
    protected boolean isDragging = false;
    protected Shape currentShape;
    private Shape highlightedShape;

    public void onLBdown(int x, int y) {
        isDragging = true;
        if (currentShape != null) {
            currentShape.set(x, y, x, y);
        }
    }

    public void onMouseMove(int x, int y) {
        if (isDragging && currentShape != null) {
            if (currentShape instanceof BrushShape) {
                ((BrushShape) currentShape).addPoint(x, y);
            } else {
                currentShape.set(currentShape.xs1, currentShape.ys1, x, y);
            }
        }
    }

    public void onPaint(Graphics2D g) {
        for (Shape shape : shapes) {
            shape.show(g, false, shape == highlightedShape);
        }
        if (isDragging) {
            currentShape.show(g, true, true);
        }
    }

    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
        if (shape instanceof BrushShape) {
            shape.set(shape.getXs1(), shape.getYs1(), shape.getXs1(), shape.getYs1());
        }
    }


    public void highlightShape(Shape shape) {
        if (this.highlightedShape != shape) {
            this.highlightedShape = shape;
        }
    }

    public void unHighlightShape() {
        this.highlightedShape = null;
    }



    public List<Shape> getShapes() {
        return shapes;
    }

    private List<Point> stringToPoints(String pointsString) {
        List<Point> points = new ArrayList<>();

        String[] pointPairs = pointsString.split(" ");
        for (String pair : pointPairs) {
            pair = pair.trim().replaceAll("[()]", "");
            String[] coordinates = pair.split(",");
            if (coordinates.length == 2) {
                try {
                    int x = Integer.parseInt(coordinates[0].trim());
                    int y = Integer.parseInt(coordinates[1].trim());
                    points.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return points;
    }
}
