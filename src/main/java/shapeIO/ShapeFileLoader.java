package shapeIO;

import drawers.Shape;
import parser.ShapeParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShapeFileLoader {
    private final ShapeParser parser;

    public ShapeFileLoader(ShapeParser parser) {
        this.parser = parser;
    }

    public List<Shape> load(File file) {
        List<Shape> shapes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("The file is empty or injured");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                Shape shape = parser.stringToShape(line);
                shapes.add(shape);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return shapes;
    }
}