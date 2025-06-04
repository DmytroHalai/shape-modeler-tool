package builder;

import drawers.Shape;
import parser.ShapeParser;
import parser.UltimateShapeParser;
import util.fileManagers.ShapeFileLoader;
import util.fileManagers.ShapeFileSaver;
import util.ShapeEditor;
import util.ShapeTable;
import util.updateShapesEvent.ShapeTableOnUpdateShapesEventListener;
import util.updateTableEvent.ShapeEditorOnUpdateTableEventListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainEditor extends JPanel {
    private static MainEditor instance;
    private final transient ShapeEditor shapeEditor;
    private final transient ShapeTable shapeTable;

    public final ShapeFileSaver shapeFileSaver;
    public final ShapeFileLoader shapeFileLoader;

    public MainEditor(Frame owner) {
        shapeEditor = new ShapeEditor();
        ShapeParser shapeParser = new UltimateShapeParser();
        shapeFileSaver = new ShapeFileSaver(shapeParser);
        shapeFileLoader = new ShapeFileLoader(shapeParser);
        shapeTable = new ShapeTable(owner, this, shapeParser, shapeFileSaver, shapeFileLoader);
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
        try {
            shapeEditor.setShapeColor();
            if (shapeEditor.isFillShape()) {
                shapeEditor.setShapeFillColor();
            }
            shapeEditor.setShapeThickness();
            shapeEditor.unHighlightShape();
            shapeEditor.onLBdown(x, y);
        } catch (Exception error) {
            throw new Exception("Shape is not selected. Select the shape to start editing!");
        }
    }

    public void onLBup() throws InstantiationException, IllegalAccessException {
        try {
            shapeEditor.onLBup();
            repaintShapes();
        } catch (Exception ignored) {
        }
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

    public void repaintShapes() {
        repaint();
    }

    public Dimension getCanvasSize() {
        return this.getSize();
    }

    public void renderScene(Graphics2D g2d) {
        shapeEditor.onPaint(g2d);
    }

    public void saveAs(JFileChooser owner) {
        shapeFileSaver.setShapes(shapeEditor.getShapes());
        shapeFileSaver.saveAs(owner, this);
    }

    public void save(JFileChooser owner) {
        shapeFileSaver.setShapes(shapeEditor.getShapes());
        shapeFileSaver.tryToSave(owner, this);
    }

    public void load(JFileChooser myJFileChooser) {
        if (myJFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            List<Shape> shapes = shapeFileLoader.load(myJFileChooser.getSelectedFile());
            shapeEditor.updateShapes(shapes);
            repaintShapes();
        }
    }

    public void setCurrentFile(File file) {
        shapeFileSaver.setFile(file);
    }
}