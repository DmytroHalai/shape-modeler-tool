package util;

import builder.MainEditor;
import drawers.BrushShape;
import drawers.Shape;
import parser.ShapeParser;
import util.fileManagers.ShapeFileLoader;
import util.fileManagers.ShapeFileSaver;
import util.updateTableEvent.UpdateTableEventSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeTable extends JDialog {
    public final UpdateTableEventSource onTableUpdate = new UpdateTableEventSource();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Name", "x1", "y1", "x2", "y2", "Border Color", "Fill Color", "Thickness"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable myJTable = new JTable(tableModel);
    private final JFileChooser myJFileChooser = new JFileChooser(new File("."));
    private final ShapeParser shapeParser;
    private final ShapeFileSaver shapeFileSaver;
    private final ShapeFileLoader shapeFileLoader;

    public ShapeTable(Frame owner, MainEditor editor, ShapeParser shapeParser, ShapeFileSaver shapeFileSaver, ShapeFileLoader shapeFileLoader) {
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

        JScrollPane scrollPane = new JScrollPane(myJTable);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 300);
        setLocationRelativeTo(owner);

        this.shapeParser = shapeParser;
        this.shapeFileSaver = shapeFileSaver;
        this.shapeFileLoader = shapeFileLoader;

        jbtSave.addActionListener(e -> saveTable(myJFileChooser, editor));
        jbtSaveAs.addActionListener(e -> saveTableAs(myJFileChooser, editor));
        jbtLoad.addActionListener(e -> loadAndRepaint(editor, myJFileChooser));
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
            addRow(shape);
        }
    }

    public void addRow(Shape shape) {
        String[] sp = shapeParser.splitStringData(shape);
        tableModel.addRow(new Object[]{sp[0], sp[1], sp[2], sp[3], sp[4], sp[5], sp[6], sp[7]});
    }

    private void saveTable(JFileChooser owner, MainEditor editor) {
        shapeFileSaver.setShapes(editor.getCurrentShapeEditor().getShapes());
        shapeFileSaver.tryToSave(owner, editor);
    }

    private void saveTableAs(JFileChooser owner, MainEditor editor) {
        shapeFileSaver.setShapes(editor.getCurrentShapeEditor().getShapes());
        shapeFileSaver.saveAs(owner, this);
    }

    public void showTable() {
        this.setVisible(true);
    }

    public void loadAndRepaint(MainEditor editor, JFileChooser owner) {
        if (owner.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            List<Shape> shapes = shapeFileLoader.load(owner.getSelectedFile());
            updateTable(shapes);
            onTableUpdate.invoke(shapes);
            editor.repaintShapes();
        }
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
