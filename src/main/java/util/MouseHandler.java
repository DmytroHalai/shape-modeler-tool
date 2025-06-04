package util;

import builder.MainEditor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private final MainEditor editor;

    public MouseHandler(MainEditor editor) {
        this.editor = editor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            editor.onLBdown(e.getX(), e.getY());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            editor.onLBup();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        editor.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        editor.onMouseMove(e.getX(), e.getY());
        editor.repaint();
    }
}

