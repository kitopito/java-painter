package util;

import java.awt.*;

public class MyDashStroke extends BasicStroke {
    private static float pattern[] = {10, 15};
    public MyDashStroke(float width) {
        super(width, CAP_BUTT, JOIN_MITER, 1.0f, pattern, 0);
    }

    public MyDashStroke(float width, float[] pattern) {
        super(width, CAP_BUTT, JOIN_MITER, 1.0f, pattern, 0);
    }
}