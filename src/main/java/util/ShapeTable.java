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
    final JFileChooser myJFileChooser = new JFileChooser(new File("."));
    private File currentFile;

    public void addRow(String name, int x1, int y1, int x2, int y2, String borderColor, String fillColor, int thickness) {
        tableModel.addRow(new Object[]{name, x1, y1, x2, y2, borderColor, fillColor, thickness});
    }

    public void addBrushRow(String name, String x1, String y1, String x2, String y2, String borderColor, String fillColor, int thickness) {
        tableModel.addRow(new Object[]{name, x1, y1, x2, y2, borderColor, fillColor, thickness});
    }

    private String pointsToString(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        for (Point point : points) {
            sb.append("(").append(point.x).append(",").append(point.y).append(") ");
        }
        return sb.toString().trim();
    }

    public void saveTable(JFileChooser owner) {
        if (currentFile != null) {
            saveTable(currentFile);
        } else {
            saveTableAs(owner);
        }
    }

    public void saveTableAs(JFileChooser owner) {
        if (owner.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = owner.getSelectedFile();
            if (!selectedFile.getName().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
            currentFile = selectedFile;
            saveTable(selectedFile);
        }
    }

    private void saveTable(File file) {
        try (BufferedWriter Writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < myJTable.getColumnCount(); i++) {
                Writer.write(myJTable.getColumnName(i));
                if (i < myJTable.getColumnCount() - 1) {
                    Writer.write("\t");
                }
            }
            Writer.newLine();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Writer.write(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) {
                        Writer.write("\t");
                    }
                }
                Writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadTable(JFileChooser owner) {
        if (owner.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadTable(owner.getSelectedFile());

        }
    }

    private void loadTable(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("The file is empty or injured");
            }

            tableModel.setRowCount(0);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\t");
                tableModel.addRow(values);
            }

            currentFile = file;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
