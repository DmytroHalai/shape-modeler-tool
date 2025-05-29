package com.hell.shapes;

import drawers.PointShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PointShapeTest {
    @Test
    public void getType_ReturnTypeOfShape() {
        PointShape point = new PointShape();
        String type = "Point";

        Assertions.assertEquals(type, point.getType());
    }

    @Test
    public void show_DrawPoint(){
        PointShape point = new PointShape();
        int x = 10;
        int y = 20;
        int width = 8;
        Color color = Color.RED;
        point.set(x, y, x, y);
        point.setBorderColor(color);
        Graphics2D graphics = mock(Graphics2D.class);

        point.show(graphics, false, false);

        verify(graphics, times(1)).setColor(color);
        verify(graphics, times(1)).fillOval(eq(x), eq(y), eq(width), eq(width));

        point.show(graphics, false, true);

        point.show(graphics, true, true);

        verify(graphics, times(1)).setColor(Color.green);
    }

    @Test
    public void pointsToString_ConvertPoints(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(10,20));
        points.add(new Point(30,30));
        points.add(new Point(43,40));
        String expected = "(10,20) (30,30) (43,40)";

        String res = PointShape.pointsToString(points);

        Assertions.assertEquals(expected, res);
    }

    @Test
    public void StringToPoints_ConvertPoints(){
        String pointsStr = "(10,20) (30,30) (43,40)";
        List<Point> points = new ArrayList<>();
        points.add(new Point(10,20));
        points.add(new Point(30,30));
        points.add(new Point(43,40));

        List<Point> res = PointShape.stringToPoints(pointsStr);

        Assertions.assertArrayEquals(points.toArray(), res.toArray());
    }
}
