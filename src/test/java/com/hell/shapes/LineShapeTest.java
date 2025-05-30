package com.hell.shapes;

import drawers.LineShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

public class LineShapeTest {
    private LineShape line;

    @BeforeEach
    public void setUp() {
        line = new LineShape();
    }

    @Test
    public void getType_ReturnTypeOfShape() {
        String type = "Line";

        Assertions.assertEquals(type, line.getType());
    }

    @Test
    public void show_DrawLine(){
        int x1 = 10;
        int y1 = 10;
        int x2 = 20;
        int y2 = 20;
        line.set(x1, y1, x2, y2);
        line.setBorderColor(Color.RED);
        Graphics2D graphics = mock(Graphics2D.class);

        line.show(graphics, false, false);

        verify(graphics, times(1)).setColor(line.getBorderColor());
        verify(graphics, times(1)).drawLine(eq(x1), eq(y1), eq(x2), eq(y2));

        line.show(graphics, false, true);

        verify(graphics, times(1)).setColor(Color.green);
    }
}
