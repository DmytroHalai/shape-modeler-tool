package util.fileManagers;

import drawers.Shape;
import parser.ShapeParser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ShapeFileSaver implements FileSaver {

    private List<Shape> shapes;
    private File currentFile;
    private final ShapeParser parser;
    private final String HEADER = "Shape\tX1\tY1\tX2\tY2\tBorderColor\tFillColor\tThickness";

    public ShapeFileSaver(ShapeParser parser, File currentFile) {
        this.parser = parser;
        this.currentFile = currentFile;
    }

    public ShapeFileSaver(ShapeParser parser) {
        this.parser = parser;
        this.currentFile = null;
    }

    @Override
    public void save() {
        try (FileWriter writer = new FileWriter(currentFile)) {
            writer.write(HEADER + System.lineSeparator());
            for (Shape shape : shapes) {
                String shapeStr = parser.shapeToString(shape);
                writer.write(shapeStr + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAs(JFileChooser owner, Component parent){
        chooseFile(owner, parent);
        save();
    }

    public void tryToSave(JFileChooser owner, Component parent){
        if (readyToSave()){
            save();
        } else {
            saveAs(owner, parent);
        }
    }

    private void chooseFile(JFileChooser owner, Component parent) {
        if (owner.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = owner.getSelectedFile();
            if (!selectedFile.getName().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
            currentFile = selectedFile;
        }
    }

    public void setFile(File file) {
        currentFile = file;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public boolean readyToSave() {
        return currentFile != null;
    }
}
