package util.fileManagers;

import builder.MainEditor;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBarFactory {
    public static JMenuBar create(MainEditor editor, JFileChooser fileChooser) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu(editor, fileChooser));
        menuBar.add(createShapeMenu(editor));
        return menuBar;
    }

    private static JMenu createFileMenu(MainEditor editor, JFileChooser fileChooser) {
        JMenu menu = new JMenu("File");
        menu.add(createMenuItem("Save", e -> editor.save(fileChooser)));
        menu.add(createMenuItem("Save as", e -> editor.saveAs(fileChooser)));
        menu.add(createMenuItem("Load from", e -> editor.load(fileChooser)));
        menu.add(createMenuItem("Save as picture", e -> new SceneSaver(editor, fileChooser).save()));
        return menu;
    }

    private static JMenu createShapeMenu(MainEditor editor) {
        JMenu menu = new JMenu("Objects");
        menu.add(createMenuItem("Show table", e -> editor.showTable()));
        menu.add(createMenuItem("Delete all shapes", e -> {
            editor.getCurrentShapeEditor().deleteShapes();
            editor.setCurrentFile(null);
            editor.repaintShapes();
        }));
        return menu;
    }

    private static JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }
}


