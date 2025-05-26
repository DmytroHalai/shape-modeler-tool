package drawers;

import java.awt.*;
import java.util.List;

public class PointShape extends Shape {
    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighlight) {
        g.setColor(isHighlight ? Color.green : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);
        g.fillOval(xs1, ys1, 8, 8);
    }

    private String pointsToString(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        for (Point point : points) {
            sb.append("(").append(point.x).append(",").append(point.y).append(") ");
        }
        return sb.toString().trim();
    }

    @Override
    public String getType() {
        return "Point";
    }
}