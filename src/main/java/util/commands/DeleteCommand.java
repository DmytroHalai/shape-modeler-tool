package util.commands;

import drawers.Shape;

import java.util.List;

public class DeleteCommand implements ReversibleCommand{
    private final List<Shape> shapes;
    private final List<Tuple> deleted;

    public DeleteCommand(List<Shape> shapes, List<Tuple> deleted) {
        this.shapes = shapes;
        this.deleted = deleted;
    }

    @Override
    public void undo() {
        for (Tuple tuple : deleted) {
            shapes.add(tuple.index, tuple.shape);
        }
    }

    public static class Tuple{
        public final Shape shape;
        public final int index;

        public Tuple(Shape shape, int index) {
            this.shape = shape;
            this.index = index;
        }
    }
}
