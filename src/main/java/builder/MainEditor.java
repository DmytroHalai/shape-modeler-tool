package builder;

import drawers.Shape;
import util.ShapeEditor;
import util.ShapeTable;
import util.updateShapesEvent.ShapeTableOnUpdateShapesEventListener;
import util.updateTableEvent.ShapeEditorOnUpdateTableEventListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainEditor extends JPanel {
    private final transient ShapeEditor shapeEditor;
    private final transient ShapeTable shapeTable;
    private static MainEditor instance;

    public MainEditor(Frame owner) {
        shapeTable = new ShapeTable(owner, this);
        shapeEditor = new ShapeEditor();
        ShapeTableOnUpdateShapesEventListener tableListener = new ShapeTableOnUpdateShapesEventListener(shapeTable);
        shapeEditor.onUpdateShapes.addListener(tableListener);
        ShapeEditorOnUpdateTableEventListener editorListener = new ShapeEditorOnUpdateTableEventListener(shapeEditor);
        shapeTable.onTableUpdate.addListener(editorListener);
    }

    public static MainEditor getInstance(Frame owner) {
        if (instance == null) {
            instance = new MainEditor(owner);
        }
        return instance;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(2000, 2000);
    }

    public void setCurrentShape(Shape shape) {
        shapeEditor.setCurrentShape(shape);
    }

    public ShapeEditor getCurrentShapeEditor() {
        return shapeEditor;
    }

    public void onLBdown(int x, int y) throws Exception {
        try{
            shapeEditor.setShapeColor();
            if(shapeEditor.isFillShape()){
                shapeEditor.setShapeFillColor();
            }
            shapeEditor.setShapeThickness();
            shapeEditor.unHighlightShape();
            shapeEditor.onLBdown(x, y);
        }
        catch (Exception error){
            throw new Exception("Shape is not selected. Select the shape to start editing!");
        }
    }

    public void onLBup() throws InstantiationException, IllegalAccessException {
        try{
            shapeEditor.onLBup();
            repaintShapes();
        }
        catch (Exception ignored){}
    }

    public void onMouseMove(int x, int y) {
        shapeEditor.onMouseMove(x, y);
        repaintShapes();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        shapeEditor.onPaint(g2d);
    }

    public void showTable() {
        shapeTable.showTable();
    }

    public void highlightShape(Shape shape) {
        shapeEditor.highlightShape(shape);
        repaintShapes();
    }

    public void repaintShapes(){
        repaint();
    }

    public Dimension getCanvasSize() {
        return this.getSize();
    }

    public void renderScene(Graphics2D g2d) {
        shapeEditor.onPaint(g2d);
    }

    public void saveTableAs(JFileChooser owner){
        shapeTable.saveTableAs(owner);
    }

    public void saveTable(JFileChooser owner){
        shapeTable.saveTable(owner);
    }

    public void loadAndRepaint(MainEditor editor, JFileChooser myJFileChooser) {
        shapeTable.loadAndRepaint(editor, myJFileChooser);
    }

    public void setCurrentFile(File file){
        shapeTable.setCurrentFile(file);
    }
}