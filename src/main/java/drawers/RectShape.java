package drawers;

import java.awt.*;

public class RectShape extends Shape{
    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighlight) {
        int x = Math.min(xs1, xs2);
        int y = Math.min(ys1, ys2);
        int width = Math.abs(xs2 - xs1);
        int height = Math.abs(ys2 - ys1);

        if (fillColor != Color.WHITE) {
            g.setColor(fillColor);
            new StrokeSetter(g, thickness, isMark, 10);
            g.fillRect(x, y, width, height);
        }

        g.setColor(isHighlight ? Color.green : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);
        g.drawRect(x, y, width, height);
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

}