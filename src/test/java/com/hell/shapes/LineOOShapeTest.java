package com.hell.shapes;

import drawers.LineOOShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LineOOShapeTest {
    private LineOOShape lineOO;
    private Graphics2D graphics;
    int x1, y1, x2, y2, r, halfR;
    Color borderColor;

    @BeforeEach
    public void setUp() {
        lineOO = new LineOOShape();
        r = 10;
        halfR = r / 2;
        x1 = 10;
        y1 = 10;
        x2 = 20;
        y2 = 20;
        borderColor = Color.RED;
    }

    @Test
    public void getType_ReturnTypeOfShape() {
        String type = "LineOO";

        Assertions.assertEquals(type, lineOO.getType());
    }

    @Test
    public void show_WithoutFillColor(){
        lineOO.set(x1, y1, x2, y2);
        lineOO.setBorderColor(borderColor);
        graphics = mock(Graphics2D.class);

        lineOO.show(graphics, false, false);

        verify(graphics, times(3)).setColor(borderColor);
        verify(graphics, times(1)).drawLine(eq(x1), eq(y1), eq(x2), eq(y2));
        verify(graphics, times(1)).drawOval(eq(x1-halfR), eq(y1-halfR), eq(r), eq(r));
        verify(graphics, times(1)).drawOval(eq(x2-halfR), eq(y2-halfR), eq(r), eq(r));

        verify(graphics, never()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());

        lineOO.show(graphics, false, true);

        verify(graphics, times(3)).setColor(Color.GREEN);
    }

    @Test
    public void show_WithFillColor(){
        Color fillColor = Color.BLUE;
        lineOO.set(x1, y1, x2, y2);
        lineOO.setBorderColor(borderColor);
        lineOO.setFillColor(fillColor);
        graphics = mock(Graphics2D.class);

        lineOO.show(graphics, false, false);

        verify(graphics, times(3)).setColor(borderColor);
        verify(graphics, times(2)).setColor(fillColor);
        verify(graphics, times(1)).drawLine(eq(x1), eq(y1), eq(x2), eq(y2));
        verify(graphics, times(1)).drawOval(eq(x1-halfR), eq(y1-halfR), eq(r), eq(r));
        verify(graphics, times(1)).fillOval(eq(x1-halfR), eq(y1-halfR), eq(r), eq(r));
        verify(graphics, times(1)).drawOval(eq(x2-halfR), eq(y2-halfR), eq(r), eq(r));
        verify(graphics, times(1)).fillOval(eq(x2-halfR), eq(y2-halfR), eq(r), eq(r));

        lineOO.show(graphics, false, true);

        verify(graphics, times(3)).setColor(Color.GREEN);
    }
}
