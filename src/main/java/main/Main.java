package main;

import builder.ShapeEditorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        ShapeEditorFrame mainFrame = new ShapeEditorFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);

        JWindow introWindow = new JWindow();
        JLabel gifLabel = new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/builder/pic/my_intro.gif"))));
        introWindow.getContentPane().add(gifLabel);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        introWindow.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        gd.setFullScreenWindow(introWindow);

        introWindow.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                introWindow.setVisible(false);
                introWindow.dispose();

                SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
            }
        }, 11000);

        introWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                introWindow.setVisible(false);
                introWindow.dispose();

                timer.cancel();

                SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
            }
        });
    }
}