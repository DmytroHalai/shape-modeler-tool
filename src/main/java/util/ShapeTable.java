package util;

import drawers.BrushShape;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class ShapeTable extends JDialog {
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "x1", "y1", "x2", "y2", "Border Color", "Fill Color", "Thickness"}, 0);
    private final JTable myJTable = new JTable(tableModel);
    private final JFileChooser myJFileChooser = new JFileChooser(new File("."));
    private File currentFile

    public void addRow(String name, int x1, int y1, int x2, int y2, String borderColor, String fillColor, int thickness) {
        tableModel.addRow(new Object[]{name, x1, y1, x2, y2, borderColor, fillColor, thickness});
    }

}
