package builder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShapeEditorFrame extends JFrame {
    private final MainEditor editor;

    private final JFileChooser fileChooser = new JFileChooser(new File("."));

    public ShapeEditorFrame() {
        editor = MainEditor.getInstance(this);
        ShapeToolBar shapeToolBar = new ShapeToolBar(editor);

        setTitle("sHapE modeLer tooL");

        ImageIcon appIcon = new ImageIcon("/pic/icon.png");
        setIconImage(appIcon.getImage());

        editor.setPreferredSize(new Dimension(2000, 2000));

        JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        setJMenuBar(createMenuBar());
        add(scrollPane, BorderLayout.CENTER);
        add(shapeToolBar.getPanel(), BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        initMouseListeners();
        initKeyBindings();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu shapeMenu = new JMenu("Objects");
        JMenu fileMenu = new JMenu("File");

        JMenuItem showTableItem = new JMenuItem("Show table");
        showTableItem.addActionListener(e -> editor.showTable());
        shapeMenu.add(showTableItem);

        JMenuItem saveTableAs = new JMenuItem("Save as");
        saveTableAs.addActionListener(e -> editor.saveTableAs(fileChooser));
        fileMenu.add(saveTableAs);

        JMenuItem saveTable = new JMenuItem("Save");
        saveTable.addActionListener(e -> editor.saveTable(fileChooser));
        fileMenu.add(saveTable);

        JMenuItem loadTable = new JMenuItem("Load from");
        loadTable.addActionListener(e -> editor.loadAndRepaint(editor, fileChooser));
        fileMenu.add(loadTable);

        JMenuItem deleteAllShapes = new JMenuItem("Delete all shapes");
        deleteAllShapes.addActionListener(e -> {
            editor.getCurrentShapeEditor().deleteShapes();
            editor.setCurrentFile(null);
            editor.repaintShapes();
        });
        shapeMenu.add(deleteAllShapes);

        JMenuItem saveSceneItem = new JMenuItem("Save as picture");
        saveSceneItem.addActionListener(e -> saveSceneAsImage());
        fileMenu.add(saveSceneItem);

        menuBar.add(fileMenu);
        menuBar.add(shapeMenu);

        return menuBar;
    }

    private void saveSceneAsImage() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
                Dimension size = editor.getCanvasSize();
                BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();

                editor.renderScene(g2d);
                g2d.setBackground(Color.WHITE);
                g2d.dispose();

                javax.imageio.ImageIO.write(image, "png", file);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during saving scene");
            }
        }
    }

    private void initMouseListeners() {
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    editor.onLBdown(e.getX(), e.getY());
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(
                            new JFrame(),
                            error.getMessage(),
                            "Помилка",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    editor.onLBup();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                editor.repaint();
            }
        });

        editor.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.onMouseMove(e.getX(), e.getY());
                editor.repaint();
            }
        });
    }

    private void initKeyBindings() {
        InputMap inputMap = editor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = editor.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ctrl Z"), "undo");
        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.getCurrentShapeEditor().undoLastShape();
                editor.repaint();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ctrl S"), "save");
        actionMap.put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.saveTable(fileChooser);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ctrl T"), "showTable");
        actionMap.put("showTable", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.showTable();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ctrl L"), "load");
        actionMap.put("load", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.loadAndRepaint(editor, fileChooser);
            }
        });
    }
}
