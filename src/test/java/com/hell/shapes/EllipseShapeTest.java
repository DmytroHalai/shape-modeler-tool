package com.hell.shapes;

import drawers.EllipseShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EllipseShapeTest {
    private EllipseShape ellipse;
    private Graphics2D graphics;
    int x1, y1, x2, y2;
    Color borderColor;

    @BeforeEach
    public void setUp() {
        x1 = 10;
        y1 = 10;
        x2 = 20;
        y2 = 20;
        borderColor = Color.RED;
        ellipse = new EllipseShape();
        ellipse.set(x1, y1, x2, y2);
        ellipse.setBorderColor(borderColor);
        graphics = mock(Graphics2D.class);
    }

    @Test
    public void getType_ReturnTypeOfShape() {
        String type = "Ellipse";

        Assertions.assertEquals(type, ellipse.getType());
    }

    @Test
    public void show_WithoutFillColor(){
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);

        ellipse.show(graphics, false, false);

        verify(graphics, times(1)).setColor(borderColor);
        verify(graphics, times(1)).drawOval(x, y, width, height);
        verify(graphics, never()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());

        ellipse.show(graphics, true, true);

        verify(graphics, times(1)).setColor(Color.green);
    }

    @Test
    public void show_WithFillColor(){
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        Color fillColor = Color.BLUE;
        ellipse.setFillColor(fillColor);

        ellipse.show(graphics, false, false);

        verify(graphics, times(1)).setColor(fillColor);
        verify(graphics, times(1)).fillOval(eq(x), eq(y), eq(width), eq(height));

        verify(graphics, times(1)).setColor(borderColor);
        verify(graphics, times(1)).drawOval(eq(x), eq(y), eq(width), eq(height));
    }
}
