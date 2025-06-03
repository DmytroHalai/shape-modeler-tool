package builder;

import util.MouseHandler;
import util.fileManagers.MenuBarFactory;
import util.fileManagers.KeyListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ShapeEditorFrame extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(800, 600);
    private static final Dimension EDITOR_SIZE = new Dimension(2000, 2000);
    private final MainEditor editor;
    private final JFileChooser fileChooser = new JFileChooser(new File("."));

    public ShapeEditorFrame() {
        editor = MainEditor.getInstance(this);
        ShapeToolBar shapeToolBar = new ShapeToolBar(editor);

        setTitle("sHapE modeLer tooL");
        setIconImage(new ImageIcon("/pic/icon.png").getImage());

        editor.setPreferredSize(FRAME_SIZE);
        JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());
        setPreferredSize(EDITOR_SIZE);
        setJMenuBar(MenuBarFactory.create(editor, fileChooser));
        add(scrollPane, BorderLayout.CENTER);
        add(shapeToolBar.getPanel(), BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        initListeners();
    }

    private void initListeners() {
        MouseHandler mouseHandler = new MouseHandler(editor);
        editor.addMouseListener(mouseHandler);
        editor.addMouseMotionListener(mouseHandler);

        new KeyListener(editor, fileChooser);
    }
}
