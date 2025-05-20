package drawers;

import java.awt.*;

public interface RectDrawer {
    void showRect(Graphics2D g, int x1, int y1, int width, boolean isMark, boolean isHighlight);
}