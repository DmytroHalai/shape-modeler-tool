package drawers;

import java.awt.*;

public abstract class Shape {
    public int xs1;
    public int ys1;
    protected int xs2;
    protected int ys2;
    protected boolean isFilled = false;
    protected Color borderColor = Color.BLACK;
    protected Color fillColor = Color.WHITE;
    protected int thickness = 1;

    public void set(int x1, int y1, int x2, int y2) {
        xs1 = x1;
        xs2 = x2;
        ys1 = y1;
        ys2 = y2;
    }

    public abstract void show(Graphics2D g, boolean isMark, boolean isHighLighted);

    public int getXs1() {
        return xs1;
    }

    public int getYs1() {
        return ys1;
    }

    public int getXs2() {
        return xs2;
    }

    public int getYs2() {
        return ys2;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setFillColor(Color fillColor) {
        isFilled = true;
        this.fillColor = fillColor;
    }

    public void makeEmpty() {
        isFilled = false;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public abstract String getType();

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public int getThickness() {
        return thickness;
    }
}