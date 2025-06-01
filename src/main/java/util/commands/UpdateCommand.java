package util.commands;

import drawers.Shape;

import java.util.List;

public class UpdateCommand implements ReversibleCommand {
    private final List<Shape> shapes;
    private final List<Shape> oldShapes;

    public UpdateCommand(List<Shape> shapes, List<Shape> oldShapes) {
        this.shapes = shapes;
        this.oldShapes = oldShapes;
    }

    @Override
    public void undo() {
        shapes.clear();
        shapes.addAll(oldShapes);
    }
}
