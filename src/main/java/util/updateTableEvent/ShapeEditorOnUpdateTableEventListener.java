package util.updateTableEvent;

import drawers.Shape;
import util.ShapeEditor;

import java.util.List;

public class ShapeEditorOnUpdateTableEventListener implements UpdateTableEventListener{
    private final ShapeEditor editor;

    public ShapeEditorOnUpdateTableEventListener(ShapeEditor editor) {
        this.editor = editor;
    }

    @Override
    public void invoke(List<Shape> target) {
        editor.updateShapes(target);
    }
}
