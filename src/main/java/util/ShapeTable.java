package util;

import builder.MainEditor;
import drawers.BrushShape;
import drawers.PointShape;
import drawers.Shape;
import util.updateTableEvent.UpdateTableEventSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ShapeTable extends JDialog {
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "x1", "y1", "x2", "y2", "Border Color", "Fill Color", "Thickness"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable myJTable = new JTable(tableModel);
    private final JFileChooser myJFileChooser = new JFileChooser(new File("."));
    private File currentFile;

    public final UpdateTableEventSource onTableUpdate = new UpdateTableEventSource();

    public ShapeTable(Frame owner, MainEditor editor) {
        super(owner, "Objects list", false);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new java.awt.GridLayout(1, 2));

        JButton jbtSave = new JButton("Save");
        JButton jbtSaveAs = new JButton("Save As");
        JButton jbtLoad = new JButton("Load");

        panel.add(jbtSave);
        panel.add(jbtSaveAs);
        panel.add(jbtLoad);

        add(panel, BorderLayout.SOUTH);

        myJTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = myJTable.getSelectedRow();
            if (selectedRow >= 0) {
                Shape selectedShape = editor.getCurrentShapeEditor().getShapes().get(selectedRow);
                editor.highlightShape(selectedShape);
            }
        });

        JPopupMenu popupMenu = getjPopupMenu(editor);

        myJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = myJTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < myJTable.getRowCount()) {
                        myJTable.setRowSelectionInterval(row, row);
                        popupMenu.show(myJTable, e.getX(), e.getY());
                    }
                }
            }
        });

        jbtSave.addActionListener(e -> saveTable(myJFileChooser));
        jbtSaveAs.addActionListener(e -> saveTableAs(myJFileChooser));
        jbtLoad.addActionListener(e -> loadAndRepaint(editor, myJFileChooser));

        JScrollPane scrollPane = new JScrollPane(myJTable);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 300);
        setLocationRelativeTo(owner);
    }

    private JPopupMenu getjPopupMenu(MainEditor editor) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Delete");

        deleteMenuItem.addActionListener(e -> {
            int row = myJTable.getSelectedRow();
            if (row >= 0) {
                deleteRow(row, editor);
            }
        });

        JMenuItem openBrushCoordinatesMenuItem = new JMenuItem("Open Coordinates");

        openBrushCoordinatesMenuItem.addActionListener(e -> {
            int row = myJTable.getSelectedRow();
            if (row >= 0) {
                Shape shape = editor.getCurrentShapeEditor().getShapes().get(row);
                if (shape instanceof BrushShape) {
                    showBrushCoordinates((BrushShape) shape);
                }
            }
        });

        popupMenu.add(openBrushCoordinatesMenuItem);

        popupMenu.add(deleteMenuItem);
        return popupMenu;
    }

    private void deleteRow(int row, MainEditor editor) {
        if (row >= 0 && row < tableModel.getRowCount()) {
            tableModel.removeRow(row);
            editor.getCurrentShapeEditor().removeShape(row);
            editor.repaintShapes();
        }
    }

    public void updateTable(List<Shape> shapes) {
        tableModel.setRowCount(0);
        for (Shape shape : shapes) {
            if (shape instanceof BrushShape) {
                List<Point> points = ((BrushShape) shape).getPoints();
                String coordinates = PointShape.pointsToString(points);
                addBrushRow(shape.getType(), "Array of coords", coordinates, "", "", colorToRGB(shape.getBorderColor()),
                        colorToRGB(shape.getFillColor()), shape.getThickness());
            } else {
                boolean isFilled = shape.isFilled();
                String fillColorStr = isFilled ? colorToRGB(shape.getFillColor()) : "";
                addRow(shape.getType(), shape.getXs1(), shape.getYs1(), shape.getXs2(), shape.getYs2(),
                        colorToRGB(shape.getBorderColor()), fillColorStr, shape.getThickness());
            }
        }
    }

    public void addRow(String name, int x1, int y1, int x2, int y2, String borderColor, String fillColor, int thickness) {
        tableModel.addRow(new Object[]{name, x1, y1, x2, y2, borderColor, fillColor, thickness});
    }

    public void addBrushRow(String name, String x1, String y1, String x2, String y2, String borderColor, String fillColor, int thickness) {
        tableModel.addRow(new Object[]{name, x1, y1, x2, y2, borderColor, fillColor, thickness});
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

    public void showTable(){
        this.setVisible(true);
    }

    public void loadAndRepaint(MainEditor editor, JFileChooser myJFileChooser) {
        loadTable(myJFileChooser);
        List<Shape> shapes = updateShapesArrayFromTable();
        onTableUpdate.invoke(shapes);
        editor.repaintShapes();
    }

    private List<Shape> updateShapesArrayFromTable(){
        List<Shape> shapes = new ArrayList<>();
        int columnCount = tableModel.getRowCount();
        Vector<Vector> dataVector = tableModel.getDataVector();
        for (int i = 0; i < columnCount; i++){
            Shape shape = createShapeFromRow(dataVector.get(i));
            if (shape != null) {
                shapes.add(shape);
            }
        }
        return shapes;
    }

    private Shape createShapeFromRow(Vector row) {
        try {
            Shape shape;
            String name = (String) row.getFirst();
            if (name.equals("Brush")) shape = createBrushShapeFromRow(row);
            else shape = createStaticShapeFromRow(row, name);
            return shape;
        } catch (NumberFormatException e) {
            System.err.println("Error during conversion of coordinates: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Uncorrected row in a table: " + e.getMessage());
        }
        return null;
    }

    private Shape createStaticShapeFromRow(Vector row, String name){
        int x1 = Integer.parseInt((String) row.get(1));
        int y1 = Integer.parseInt((String) row.get(2));
        int x2 = Integer.parseInt((String) row.get(3));
        int y2 = Integer.parseInt((String) row.get(4));
        Color borderColor = rgbToColor((String) row.get(5));
        String fillColorStr = (String) row.get(6);
        int thickness = Integer.parseInt((String) row.get(7));

        Shape shape = ShapeFactory.createShape(name);
        shape.set(x1, y1, x2, y2);
        shape.setBorderColor(borderColor);
        if(fillColorStr.trim() != ""){
            shape.setFillColor(rgbToColor(fillColorStr));
        } else {
            shape.makeEmpty();
        }
        shape.setThickness(thickness);
        return shape;
    }

    private Shape createBrushShapeFromRow(Vector row) {
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

    private Color rgbToColor(String rgb) {
        String[] parts = rgb.split(",");
        int red = Integer.parseInt(parts[0].trim());
        int green = Integer.parseInt(parts[1].trim());
        int blue = Integer.parseInt(parts[2].trim());
        return new Color(red, green, blue);
    }

    private void saveTable(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < myJTable.getColumnCount(); i++) {
                writer.write(myJTable.getColumnName(i));
                if (i < myJTable.getColumnCount() - 1) {
                    writer.write("\t");
                }
            }
            writer.newLine();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.write(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.write("\t");
                    }
                }
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadTable(JFileChooser owner) {
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

    private String colorToRGB(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return red + "," + green + "," + blue;
    }

    public void setCurrentFile(File file) {
        currentFile = file;
    }

    private void showBrushCoordinates(BrushShape brush) {
        JFrame frame = new JFrame("Brush Coordinates");
        DefaultTableModel model = new DefaultTableModel(new String[]{"X", "Y"}, 0);
        JTable coordinatesTable = new JTable(model);

        for (Point point : brush.getPoints()) {
            model.addRow(new Object[]{point.x, point.y});
        }

        frame.add(new JScrollPane(coordinatesTable));
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
}
