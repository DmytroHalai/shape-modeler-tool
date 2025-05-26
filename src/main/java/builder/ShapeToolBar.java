package builder;

import drawers.*;
import drawers.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeToolBar {
    private final JPanel panel;
    private JButton lastPressedButton;
    private final MainEditor editor;

    public ShapeToolBar(MainEditor editor) {
        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(2, 1));
        this.editor = editor;
        initShapeButtons();
        initSettingsPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initShapeButtons() {
        JPanel shapeButtonsPanel = new JPanel();
        shapeButtonsPanel.setLayout(new FlowLayout());

        addToolBarButton("/pic/ellipse.png", e -> setCurrentShape(new EllipseShape(), e), shapeButtonsPanel);
        addToolBarButton("/pic/rect.png", e -> setCurrentShape(new RectShape(), e), shapeButtonsPanel);
        addToolBarButton("/pic/line.png", e -> setCurrentShape(new LineShape(), e), shapeButtonsPanel);
        addToolBarButton("/pic/point.png", e -> setCurrentShape(new PointShape(), e), shapeButtonsPanel);
        addToolBarButton("/pic/lineOO.png", e -> setCurrentShape(new LineOOShape(), e), shapeButtonsPanel);
        addToolBarButton("/pic/cube.png", e -> setCurrentShape(new CubeShape(), e), shapeButtonsPanel);
        addToolBarButton("/pic/brush.png", e -> setCurrentShape(new BrushShape(), e), shapeButtonsPanel);

        panel.add(shapeButtonsPanel);
    }

    private void initSettingsPanel() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new FlowLayout());

        JButton borderColorButton = new JButton("Border Color");
        borderColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "Choose Border Color", editor.getBorderColor());
            if (selectedColor != null) {
                editor.setBorderColor(selectedColor);
            }
        });
        settingsPanel.add(borderColorButton);

        JButton fillColorButton = new JButton("Fill Color");
        fillColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "Choose Fill Color", editor.getFillColor());
            if (selectedColor != null) {
                editor.setFillColor(selectedColor);
            }
        });
        settingsPanel.add(fillColorButton);

        JLabel thicknessLabel = new JLabel("Thickness:");
        JSpinner thicknessSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        thicknessSpinner.addChangeListener(e -> {
            int thickness = (int) thicknessSpinner.getValue();
            editor.setBorderThickness(thickness);
        });
        settingsPanel.add(thicknessLabel);
        settingsPanel.add(thicknessSpinner);

        panel.add(settingsPanel);
    }

    private void addToolBarButton(String iconPath, ActionListener action, JPanel parentPanel) {
        JButton button = createButtonWithIcon(iconPath);
        button.addActionListener(action);
        button.setBackground(Color.WHITE);
        parentPanel.add(button);
    }

    private JButton createButtonWithIcon(String iconPath) {
        java.net.URL imgURL = ShapeToolBar.class.getResource(iconPath);
        if (imgURL == null) {
            throw new RuntimeException("Icon resource not found: " + iconPath);
        }
        ImageIcon icon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        return new JButton(icon);
    }


    private void setCurrentShape(Shape shape, ActionEvent e) {
        editor.setCurrentShape(shape);
        changeButtonColor(e);
    }

    private void changeButtonColor(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        boolean isButtonPressed = lastPressedButton == null;
        boolean isButtonChanged = lastPressedButton != sourceButton;
        if (!isButtonPressed && isButtonChanged) {
            lastPressedButton.setBackground(Color.WHITE);
        }
        sourceButton.setBackground(Color.PINK);
        lastPressedButton = sourceButton;
    }
}


