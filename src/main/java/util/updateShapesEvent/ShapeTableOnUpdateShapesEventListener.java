package util.updateShapesEvent;

import drawers.Shape;
import util.ShapeTable;

import java.util.List;

public class ShapeTableOnUpdateShapesEventListener implements UpdateShapesEventListener {
    private final ShapeTable shapeTable;

    public ShapeTableOnUpdateShapesEventListener(ShapeTable shapeTable) {
        this.shapeTable = shapeTable;
    }

    @Override
    public void invoke(List<Shape> target) {
        shapeTable.updateTable(target);
    }
}
