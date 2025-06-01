package util;

import drawers.BrushShape;
import drawers.Shape;
import util.updateShapesEvent.UpdateShapesEventSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ShapeEditor {
    protected final List<Shape> shapes = new ArrayList<>();
    public boolean isDragging = false;
    protected Shape currentShape;
    private Shape highlightedShape;

    public final UpdateShapesEventSource onUpdateShapes = new UpdateShapesEventSource();

    public void onLBdown(int x, int y) {
        isDragging = true;
        if (currentShape != null) {
            currentShape.set(x, y, x, y);
        }
    }

    public void onLBup() throws InstantiationException, IllegalAccessException {
        isDragging = false;
        Shape temp = currentShape.getClass().newInstance();
        if (currentShape != null) {
            shapes.add(currentShape);
            currentShape = temp;
            onUpdateShapes.invoke(shapes);
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

    public void undoLastShape() {
        if (!shapes.isEmpty()) {
            shapes.removeLast();
            onUpdateShapes.invoke(shapes);
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

    public void deleteShapes(){
        if (!shapes.isEmpty()) {
            shapes.clear();
            onUpdateShapes.invoke(shapes);
        }
    }

    public void removeShape(int index) {
        if (index >= 0 && index < shapes.size()) {
            shapes.remove(index);
            onUpdateShapes.invoke(shapes);
        }
    }

    public void updateShapes(List<Shape> shapes) {
        this.shapes.clear();
        this.shapes.addAll(shapes);
    }

    public Shape getShape(){
        return currentShape;
    }

    private Color rgbToColor(String rgb) {
        String[] parts = rgb.split(",");
        int red = Integer.parseInt(parts[0].trim());
        int green = Integer.parseInt(parts[1].trim());
        int blue = Integer.parseInt(parts[2].trim());
        return new Color(red, green, blue);
    }
}
