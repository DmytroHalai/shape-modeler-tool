package com.hell.shapes;

import drawers.StrokeSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

public class StrokeSetterTest {
    private Graphics2D graphics;
    private int weight, interval;

    @BeforeEach
    public void setUp() {
        graphics = mock(Graphics2D.class);
        weight = 5;
        interval = 10;
    }

    @Test
    public void isNotMark_SetBasicStrokeSetter(){
        BasicStroke bs = new BasicStroke(weight);
        new StrokeSetter(graphics, weight, false, interval);
        verify(graphics, times(1)).setStroke(bs);
    }

    @Test
    public void isMark_SetStrokeSetterWithInterval(){
        float[] dashPattern = {interval, interval};
        BasicStroke bs = new BasicStroke(weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, interval, dashPattern, 0);

        new StrokeSetter(graphics, weight, true, interval);

        verify(graphics, times(1)).setStroke(bs);
    }
}
