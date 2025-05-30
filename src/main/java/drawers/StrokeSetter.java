package drawers;

import java.awt.*;

public class StrokeSetter {
    public StrokeSetter( Graphics2D g, int weight, boolean isMark, int interval) {
        if (isMark) {
            float[] dashPattern = {interval, interval};
            g.setStroke(new BasicStroke(weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, interval, dashPattern, 0));
        } else {
            g.setStroke(new BasicStroke(weight));
        }
    }
}