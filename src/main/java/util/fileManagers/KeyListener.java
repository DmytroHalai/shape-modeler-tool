package util.fileManagers;

import builder.MainEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyListener {
    MainEditor editor;
    JFileChooser fileChooser;

    public KeyListener(MainEditor editor, JFileChooser fileChooser) {
        this.editor = editor;
        this.fileChooser = fileChooser;
        initKeyBindings();
    }

    public void initKeyBindings() {
        InputMap inputMap = editor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = editor.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ctrl Z"), "undo");
        inputMap.put(KeyStroke.getKeyStroke("ctrl S"), "save");
        inputMap.put(KeyStroke.getKeyStroke("ctrl shift S"), "saveAsPicture");
        inputMap.put(KeyStroke.getKeyStroke("ctrl T"), "showTable");
        inputMap.put(KeyStroke.getKeyStroke("ctrl L"), "load");
        inputMap.put(KeyStroke.getKeyStroke("ctrl X"), "deleteLastShape");

        actionMap.put("undo", undo());
        actionMap.put("deleteLastShape", deleteLastShape());
        actionMap.put("save", save());
        actionMap.put("saveAsPicture", saveAsPicture());
        actionMap.put("showTable", showTable());
        actionMap.put("load", load());
    }

    public Action undo() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.getCurrentShapeEditor().undo();
                editor.repaint();
            }
        };
    }

    public Action saveAsPicture() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SceneSaver(editor, fileChooser).save();
            }
        };
    }

    public Action deleteLastShape() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.getCurrentShapeEditor().undoLastShape();
                editor.repaint();
            }
        };
    }

    public Action save() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.save(fileChooser);
            }
        };
    }

    public Action showTable() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.showTable();
            }
        };
    }

    public Action load() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.load(fileChooser);
            }
        };
    }
}
