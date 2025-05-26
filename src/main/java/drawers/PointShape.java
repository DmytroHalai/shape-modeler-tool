package drawers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointShape extends Shape {
    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighlight) {
        g.setColor(isHighlight ? Color.green : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);
        g.fillOval(xs1, ys1, 8, 8);
    }

    public static String pointsToString(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        for (Point point : points) {
            sb.append("(").append(point.x).append(",").append(point.y).append(") ");
        }
        return sb.toString().trim();
    }

    public static List<Point> stringToPoints(String pointsString) {
        List<Point> points = new ArrayList<>();

        String[] pointPairs = pointsString.split(" ");
        for (String pair : pointPairs) {
            pair = pair.trim().replaceAll("[()]", "");
            String[] coordinates = pair.split(",");
            if (coordinates.length == 2) {
                try {
                    int x = Integer.parseInt(coordinates[0].trim());
                    int y = Integer.parseInt(coordinates[1].trim());
                    points.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return points;
    }

    @Override
    public String getType() {
        return "Point";
    }
}