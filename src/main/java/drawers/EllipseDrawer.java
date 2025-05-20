package drawers;

import java.awt.*;

public interface EllipseDrawer {
    void showEl(Graphics2D g, int x, int y, int width, int height, boolean isMark, boolean isHighlight);
}