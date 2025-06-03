package util;

import drawers.BrushShape;
import drawers.Shape;
import util.commands.AddCommand;
import util.commands.DeleteCommand;
import util.commands.ReversibleCommand;
import util.commands.UpdateCommand;
import util.updateShapesEvent.UpdateShapesEventSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ShapeEditor {
    protected final List<Shape> shapes = new ArrayList<>();
    public boolean isDragging = false;
    protected Shape currentShape;
    private Shape highlightedShape;

    private boolean fillShape = false;
    private final Stack<ReversibleCommand> changes = new Stack<>();

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
            Shape added = currentShape;
            shapes.add(currentShape);
            currentShape = temp;
            onUpdateShapes.invoke(shapes);

            changes.push(new AddCommand(shapes, added));
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
            Shape deleted = shapes.removeLast();
            onUpdateShapes.invoke(shapes);

            int index = shapes.size();
            changes.push(new DeleteCommand(shapes, deleted, index));
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
            List<Shape> previous = new ArrayList<>(shapes);
            shapes.clear();
            onUpdateShapes.invoke(shapes);

            changes.push(new UpdateCommand(shapes, previous));
        }
    }

    public void removeShape(int index) {
        if (index >= 0 && index < shapes.size()) {
            Shape deleted = shapes.remove(index);
            onUpdateShapes.invoke(shapes);

            changes.push(new DeleteCommand(shapes, deleted, index));
        }
    }

    public void updateShapes(List<Shape> shapes) {
        List<Shape> previous = new ArrayList<>(this.shapes);
        this.shapes.clear();
        this.shapes.addAll(shapes);

        changes.push(new UpdateCommand(this.shapes, previous));
    }

    public Shape getShape(){
        return currentShape;
    }

    public void undo(){
        if (!changes.isEmpty()) {
            changes.pop().undo();
            onUpdateShapes.invoke(shapes);
        }
    }

    public boolean isFillShape() {
        return fillShape;
    }

    public void fillShape() {
        fillShape = true;
    }

    public void makeShapeEmpty() {
        fillShape = false;
    }
}
