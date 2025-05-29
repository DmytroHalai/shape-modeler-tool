package com.hell.utils;

import builder.MainEditor;
import drawers.LineShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.ShapeEditor;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ShapeEditorTest {

    private ShapeEditor editor;

    @BeforeEach
    void setUp() {
        Frame dummyFrame = new Frame(); // ❌ причина HeadlessException
        MainEditor dummyEditor = Mockito.mock(MainEditor.class);
        editor = new ShapeEditor(dummyEditor, null); // або передавати нуль, якщо це допустимо
    }


    @Test
    void onLBdown_setsDraggingAndInitialCoords() {
        LineShape shape = new LineShape();
        editor.setCurrentShape(shape);

        editor.onLBdown(10, 20);

        Assertions.assertTrue(editor.isDragging);
        Assertions.assertEquals(10, shape.getXs1());
        Assertions.assertEquals(20, shape.getYs1());
        Assertions.assertEquals(10, shape.getXs2());
        Assertions.assertEquals(20, shape.getYs2());
    }

    @Test
    void onLBup_addsShapeAndResetsCurrentShape() throws Exception {
        LineShape shape = new LineShape();
        shape.set(10, 10, 20, 20);
        editor.setCurrentShape(shape);

        editor.onLBup();

        Assertions.assertEquals(1, editor.getShapes().size());
        assertNotSame(shape, editor.getShape());
    }

    @Test
    void onMouseMove_updatesShapeCoordinates() {
        LineShape shape = new LineShape();
        shape.set(10, 10, 10, 10);
        editor.setCurrentShape(shape);
        editor.onLBdown(10, 10);

        editor.onMouseMove(30, 40);

        Assertions.assertEquals(30, shape.getXs2());
        Assertions.assertEquals(40, shape.getYs2());
    }

}
