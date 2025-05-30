package com.hell.shapes;

import drawers.Shape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class AbstractShapeTest {
    private Shape shape;

    @BeforeEach
    public void setUp(){
        shape = new AbstractShape();
    }

    @Test
    public void set_SetCoordinates(){
        int x1 = 10;
        int y1 = 20;
        int x2 = 30;
        int y2 = 40;

        shape.set(x1, y1, x2, y2);

        Assertions.assertEquals(x1, shape.getXs1());
        Assertions.assertEquals(y1, shape.getYs1());
        Assertions.assertEquals(x2, shape.getXs2());
        Assertions.assertEquals(y2, shape.getYs2());
    }

    @Test
    public void setBorderColor(){
        Color color = Color.RED;

        shape.setBorderColor(color);

        Assertions.assertEquals(color, shape.getBorderColor());
    }

    @Test
    public void setFillColor(){
        Color color = Color.BLUE;

        shape.setFillColor(color);

        Assertions.assertEquals(color, shape.getFillColor());
    }

    @Test
    public void setThickness(){
        int thickness = 10;

        shape.setThickness(thickness);

        Assertions.assertEquals(thickness, shape.getThickness());
    }
}

class AbstractShape extends Shape {
    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighLighted) {

    }

    @Override
    public String getType() {
        return "Shape";
    }
}
