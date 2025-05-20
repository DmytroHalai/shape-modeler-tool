package drawers;

import java.awt.*;

public class PointShape extends Shape {
    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighlight) {
        g.setColor(isHighlight ? Color.green : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);
        g.fillOval(xs1, ys1, 8, 8);
    }

    @Override
    public String getType() {
        return "Point";
    }
}