package drawers;

import java.awt.*;

@SuppressWarnings("SuspiciousNameCombination")
public class CubeShape extends Shape implements LineDrawer, RectDrawer {

    @Override
    public void showLine(Graphics2D g, int x, int y, int x2, int y2, boolean isMark, boolean isHighlight) {
        g.setColor(isHighlight ? Color.green : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);
        g.drawLine(x, y, x2, y2);
    }

    @Override
    public void showRect(Graphics2D g, int x, int y, int width, boolean isMark, boolean isHighlight) {
        if (fillColor != Color.WHITE) {
            g.setColor(fillColor);
            new StrokeSetter(g, thickness, isMark, 10);
            g.fillRect(x, y, width, width);
        }

        g.setColor(isHighlight ? Color.green : borderColor);
        new StrokeSetter(g, thickness, isMark, 10);
        g.drawRect(x, y, width, width);
    }

    private void drawAllShapes(Graphics2D g, boolean isMark, boolean isHighlight) {
        int sideLength = (int) findSideLength(xs1, ys1, xs2, ys2);
        int[] secondCoords = findSecondRectCoord(xs2, ys2, sideLength);

        showRect(g, xs1, ys1, sideLength, isMark, isHighlight);
        showLine(g, xs1, ys1, secondCoords[0], secondCoords[1], isMark, isHighlight);
        showLine(g, xs1 + sideLength, ys1, secondCoords[0] + sideLength, secondCoords[1], isMark, isHighlight);
        showLine(g, xs1, ys1 + sideLength, secondCoords[0], secondCoords[1] + sideLength, isMark, isHighlight);
        showLine(g, xs1 + sideLength, ys1 + sideLength, secondCoords[0] + sideLength, secondCoords[1] + sideLength, isMark, isHighlight);
        showRect(g, secondCoords[0], secondCoords[1], sideLength, isMark, isHighlight);
    }

    private double calcVectorLength(int x, int y, int x2, int y2) {
        double xPow = Math.pow((x2 - x), 2);
        double yPow = Math.pow((y2 - y), 2);
        return Math.sqrt(xPow + yPow);
    }

    private int[] findSecondRectCoord(int x, int y, int sideLength) {
        return new int[]{x - sideLength, y - sideLength};
    }

    private double findSideLength(int x, int y, int x2, int y2) {
        double centralDiag = calcVectorLength(x, y, x2, y2);
        double sqrtOf2 = Math.sqrt(2);
        return centralDiag / sqrtOf2;
    }

    @Override
    public void show(Graphics2D g, boolean isMark, boolean isHighlight) {
        drawAllShapes(g, isMark, isHighlight);
    }

    @Override
    public String getType() {
        return "Cube";
    }
}