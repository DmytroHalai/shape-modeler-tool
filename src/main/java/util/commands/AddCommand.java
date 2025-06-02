package util.commands;

import drawers.Shape;

import java.util.ArrayList;
import java.util.List;

public class AddCommand implements ReversibleCommand {
    private final List<Shape> shapes;
    private final List<Shape> added;

    public AddCommand(List<Shape> shapes, List<Shape> added) {
        this.shapes = shapes;
        this.added = added;
    }

    public AddCommand(List<Shape> shapes, Shape added) {
        this.shapes = shapes;
        this.added = new ArrayList<>();
        this.added.add(added);
    }

    @Override
    public void undo() {
        shapes.removeAll(added);
    }
}
