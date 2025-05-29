package com.hell.shapes;

import drawers.RectShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.awt.*;

public class RectShapeTest {
    private RectShape rectangle;
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
        rectangle = new RectShape();
        rectangle.set(x1, y1, x2, y2);
        rectangle.setBorderColor(borderColor);
        graphics = mock(Graphics2D.class);
    }

    @Test
    public void getType_ReturnTypeOfShape() {
        String type = "Rectangle";

        Assertions.assertEquals(type, rectangle.getType());
    }

    @Test
    public void show_WithoutFillColor(){
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);

        rectangle.show(graphics, false, false);

        verify(graphics, times(1)).setColor(borderColor);
        verify(graphics, times(1)).drawRect(x, y, width, height);
        verify(graphics, never()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());

        rectangle.show(graphics, true, true);

        verify(graphics, times(1)).setColor(Color.green);
    }

    @Test
    public void show_WithFillColor(){
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        Color fillColor = Color.BLUE;
        rectangle.setFillColor(fillColor);

        rectangle.show(graphics, false, false);

        verify(graphics, times(1)).setColor(fillColor);
        verify(graphics, times(1)).fillRect(eq(x), eq(y), eq(width), eq(height));

        verify(graphics, times(1)).setColor(borderColor);
        verify(graphics, times(1)).drawRect(eq(x), eq(y), eq(width), eq(height));
    }
}
