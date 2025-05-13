package drawers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BrushShape extends Shape{
    private List<Point> points = new ArrayList<>();

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighLighted) {
        g.setColor(isHighLighted ? Color.GREEN : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);

        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public String getType() {
        return "Brush";
    }
}