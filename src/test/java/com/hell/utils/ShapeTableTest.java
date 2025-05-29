package com.hell.utils;

import drawers.RectShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ShapeTable;
import drawers.Shape;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTableTest {

    private ShapeTable shapeTable;

    @BeforeEach
    void setUp() {
        shapeTable = new ShapeTable(null, null);
    }

    @Test
    void addRow_withValidData() {
        shapeTable.addRow("Rect", 1, 2, 3, 4, "255,0,0", "0,255,0", 2);

        DefaultTableModel model = getTableModel(shapeTable);
        assertEquals(1, model.getRowCount());
        assertEquals("Rect", model.getValueAt(0, 0));
        assertEquals(1, model.getValueAt(0, 1));
        assertEquals(2, model.getValueAt(0, 2));
        assertEquals(3, model.getValueAt(0, 3));
        assertEquals(4, model.getValueAt(0, 4));
        assertEquals("255,0,0", model.getValueAt(0, 5));
        assertEquals("0,255,0", model.getValueAt(0, 6));
        assertEquals(2, model.getValueAt(0, 7));
    }

    @Test
    void addBrushRow_withValidData() {
        shapeTable.addBrushRow("Brush", "Array of coords", "1,1;2,2", "", "", "0,0,0", "255,255,255", 1);

        DefaultTableModel model = getTableModel(shapeTable);
        assertEquals(1, model.getRowCount());
        assertEquals("Brush", model.getValueAt(0, 0));
        assertEquals("Array of coords", model.getValueAt(0, 1));
        assertEquals("1,1;2,2", model.getValueAt(0, 2));
        assertEquals("", model.getValueAt(0, 3));
        assertEquals("", model.getValueAt(0, 4));
        assertEquals("0,0,0", model.getValueAt(0, 5));
        assertEquals("255,255,255", model.getValueAt(0, 6));
        assertEquals(1, model.getValueAt(0, 7));

    }

    @Test
    void saveAndLoadTable_withValidRow() throws IOException {
        shapeTable.addRow("Rect", 10, 20, 30, 40, "255,0,0", "0,255,0", 5);

        File tempFile = File.createTempFile("test_table", ".txt");
        tempFile.deleteOnExit();

        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public int showSaveDialog(Component parent) {
                setSelectedFile(tempFile);
                return APPROVE_OPTION;
            }

            @Override
            public int showOpenDialog(Component parent) {
                setSelectedFile(tempFile);
                return APPROVE_OPTION;
            }
        };

        shapeTable.saveTable(fileChooser);

        ShapeTable loadedTable = new ShapeTable(null, null);
        loadedTable.loadTable(fileChooser);

        DefaultTableModel model = getTableModel(loadedTable);
        assertEquals(1, model.getRowCount());
        assertEquals("Rect", model.getValueAt(0, 0));
    }

    @Test
    void updateTable_withTestShape() {
        Shape testShape = new RectShape();

        shapeTable.updateTable(List.of(testShape));

        DefaultTableModel model = getTableModel(shapeTable);
        assertEquals(1, model.getRowCount());
        assertEquals("Rectangle", model.getValueAt(0, 0));
        assertEquals("0,0,0", model.getValueAt(0, 5));
        assertEquals("255,255,255", model.getValueAt(0, 6));
    }

    @Test
    void setCurrentFile_andSaveTable() throws IOException {
        File file = File.createTempFile("dummy", ".txt");
        file.deleteOnExit();

        shapeTable.setCurrentFile(file);
        shapeTable.addRow("Line", 0, 0, 1, 1, "0,0,0", "255,255,255", 1);
        shapeTable.saveTable(null);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    private DefaultTableModel getTableModel(ShapeTable shapeTable) {
        try {
            Field modelField = ShapeTable.class.getDeclaredField("tableModel");
            modelField.setAccessible(true);
            return (DefaultTableModel) modelField.get(shapeTable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
