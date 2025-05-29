package com.hell.shapes;

import drawers.BrushShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BrushShapeTest {
    private BrushShape brush;
    private Point[] points;
    private Color color;
    private Graphics2D graphics;

    @BeforeEach
    public void setUp() {
        brush = new BrushShape();
        points = new Point[3];
        points[0] = new Point(0, 0);
        points[1] = new Point(1, 5);
        points[2] = new Point(6, 5);
        color = Color.RED;
        brush.setBorderColor(color);
        graphics = mock(Graphics2D.class);
    }

    @Test
    public void getType_ReturnTypeOfShape() {
        String type = "Brush";

        Assertions.assertEquals(type, brush.getType());
    }

    @Test
    public void addAndGetPoint_ReturnListOfPoints() {
        for (Point point : points) {
            brush.addPoint(point.x, point.y);
        }

        Point[] res = brush.getPoints().toArray(new Point[0]);

        Assertions.assertArrayEquals(points, res);
    }

    @Test
    public void show_DrawBrush(){
        for (Point point : points) {
            brush.addPoint(point.x, point.y);
        }

        brush.show(graphics, false, false);

        verify(graphics, times(1)).setColor(color);
        for (int i = 1; i < points.length; i++) {
            Point from = points[i - 1];
            Point end = points[i];
            verify(graphics, times(1)).drawLine(eq(from.x), eq(from.y), eq(end.x), eq(end.y));
        }

        brush.show(graphics, false, true);

        verify(graphics, times(1)).setColor(Color.GREEN);
    }
}
