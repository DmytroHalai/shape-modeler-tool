package util;

import builder.MainEditor;
import drawers.BrushShape;
import drawers.PointShape;
import drawers.Shape;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ShapeEditor {
    protected final List<Shape> shapes = new ArrayList<>();
    private final ShapeTable shapeTable;
    protected boolean isDragging = false;
    protected Shape currentShape;
    private Shape highlightedShape;

    public ShapeEditor(MainEditor editor, Frame owner) {
        shapeTable = new ShapeTable(owner, editor);
    }

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
            updateTable();
            currentShape = temp;
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
            updateTable();
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

    private void updateTable() {
        shapeTable.updateTable(shapes);
    }

    public void showTable() {
        shapeTable.setVisible(true);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void deleteShapes(){
        if (!shapes.isEmpty()) {
            shapes.clear();
            updateTable();
        }
    }

    public void updateShapesArrayFromTable(DefaultTableModel table){
        shapes.clear();
        int columnCount = table.getRowCount();
        Vector<Vector> dataVector = table.getDataVector();
        for (int i = 0; i < columnCount; i++){
            createShapeFromRow(dataVector.get(i));
        }
        updateTable();

    }

    private void createShapeFromRow(Vector row) {
        try {
            Shape shape;
            String name = (String) row.getFirst();
            if (name.equals("Brush")) shape = createBrushShapeFromRow(row);
            else shape = createStaticShapeFromRow(row, name);
            shapes.add(shape);
        } catch (NumberFormatException e) {
            System.err.println("Error during conversion of coordinates: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Uncorrected row in a table: " + e.getMessage());
        }
    }

    public Shape createStaticShapeFromRow(Vector row, String name){
        int x1 = Integer.parseInt((String) row.get(1));
        int y1 = Integer.parseInt((String) row.get(2));
        int x2 = Integer.parseInt((String) row.get(3));
        int y2 = Integer.parseInt((String) row.get(4));
        Color borderColor = rgbToColor((String) row.get(5));
        Color fillColor = rgbToColor((String) row.get(6));
        int thickness = Integer.parseInt((String) row.get(7));

        Shape shape = ShapeFactory.createShape(name);
        shape.set(x1, y1, x2, y2);
        shape.setBorderColor(borderColor);
        shape.setFillColor(fillColor);
        shape.setThickness(thickness);
        return shape;
    }

    public Shape createBrushShapeFromRow(Vector row) {
        String points = (String) row.get(2);
        Color borderColor = rgbToColor((String) row.get(5));
        Color fillColor = rgbToColor((String) row.get(6));
        int thickness = Integer.parseInt((String) row.get(7));

        BrushShape shape = new BrushShape();
        shape.setPoints(PointShape.stringToPoints(points));
        shape.setBorderColor(borderColor);
        shape.setFillColor(fillColor);
        shape.setThickness(thickness);
        return shape;
    }

    public void removeShape(int index) {
        if (index >= 0 && index < shapes.size()) {
            shapes.remove(index);
            updateTable();
        }
    }

    public void saveTableAs(JFileChooser owner) {
        shapeTable.saveTableAs(owner);
    }

    public void saveTable(JFileChooser owner) {
        shapeTable.saveTable(owner);
    }

    public void loadAndRepaint(MainEditor editor, JFileChooser myJFileChooser) {
        shapeTable.loadAndRepaint(editor, myJFileChooser);
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

    public void setCurrentFile(File file){
        shapeTable.setCurrentFile(file);
    }
}
