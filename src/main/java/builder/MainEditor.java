package builder;


import drawers.Shape;
import util.ShapeEditor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainEditor extends JPanel {
    private final transient ShapeEditor shapeEditor;
    private static MainEditor instance;
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private int borderThickness = 1;

    private MainEditor(Frame owner) {
        shapeEditor = new ShapeEditor(this, owner);
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
            shapeEditor.getShape().setBorderColor(borderColor);
            shapeEditor.getShape().setFillColor(fillColor);
            shapeEditor.getShape().setThickness(borderThickness);
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
        catch (Exception ignored){};
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
        shapeEditor.showTable();
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
        shapeEditor.saveTableAs(owner);
    }

    public void saveTable(JFileChooser owner){
        shapeEditor.saveTable(owner);
    }

    public void loadAndRepaint(MainEditor editor, JFileChooser myJFileChooser) {
        shapeEditor.loadAndRepaint(editor, myJFileChooser);
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
    }

    public void setCurrentFile(File file){
        shapeEditor.setCurrentFile(file);
    }
}