package com.hell.shapes;

import drawers.CubeShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CubeShapeTest {
    private CubeShape cube;
    private Graphics2D graphics;
    Color borderColor;

    @BeforeEach
    public void setUp() {
        int x1 = 10;
        int y1 = 10;
        int x2 = 50;
        int y2 = 40;
        borderColor = Color.RED;
        cube = new CubeShape();
        cube.set(x1, y1, x2, y2);
        cube.setBorderColor(borderColor);
        graphics = mock(Graphics2D.class);
    }

    @Test
    public void getType_ReturnTypeOfShape() {
        String type = "Cube";

        Assertions.assertEquals(type, cube.getType());
    }

    @Test
    public void show_WithoutFillColor(){
        cube.show(graphics, false, false);

        verify(graphics, times(6)).setColor(borderColor);
        verify(graphics, times(1)).drawRect(eq(10), eq(10), eq(35), eq(35));
        verify(graphics, times(1)).drawLine(eq(10), eq(10), eq(15), eq(5));
        verify(graphics, times(1)).drawLine(eq(45), eq(10), eq(50), eq(5));
        verify(graphics, times(1)).drawLine(eq(10), eq(45), eq(15), eq(40));
        verify(graphics, times(1)).drawLine(eq(45), eq(45), eq(50), eq(40));
        verify(graphics, times(1)).drawRect(eq(15), eq(5), eq(35), eq(35));

        verify(graphics, never()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());

        cube.show(graphics, false, true);

        verify(graphics, times(6)).setColor(Color.green);
    }

    @Test
    public void show_WithFillColor(){
        Color fillColor = Color.BLUE;
        cube.setFillColor(fillColor);

        cube.show(graphics, false, false);

        verify(graphics, times(6)).setColor(borderColor);
        verify(graphics, times(2)).setColor(fillColor);
        verify(graphics, times(1)).fillRect(eq(10), eq(10), eq(35), eq(35));
        verify(graphics, times(1)).drawRect(eq(10), eq(10), eq(35), eq(35));
        verify(graphics, times(1)).drawLine(eq(10), eq(10), eq(15), eq(5));
        verify(graphics, times(1)).drawLine(eq(45), eq(10), eq(50), eq(5));
        verify(graphics, times(1)).drawLine(eq(10), eq(45), eq(15), eq(40));
        verify(graphics, times(1)).drawLine(eq(45), eq(45), eq(50), eq(40));
        verify(graphics, times(1)).drawRect(eq(15), eq(5), eq(35), eq(35));
        verify(graphics, times(1)).fillRect(eq(15), eq(5), eq(35), eq(35));
    }
}
