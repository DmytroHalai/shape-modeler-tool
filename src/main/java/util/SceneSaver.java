package util;

import builder.MainEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SceneSaver {
    private final MainEditor editor;
    private final JFileChooser fileChooser;

    public SceneSaver(MainEditor editor, JFileChooser fileChooser) {
        this.editor = editor;
        this.fileChooser = fileChooser;
    }

    public void saveSceneAsImage() {
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
                Dimension size = editor.getCanvasSize();
                BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();

                editor.renderScene(g2d);
                g2d.dispose();

                javax.imageio.ImageIO.write(image, "png", file);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error during saving scene");
            }
        }
    }
}

