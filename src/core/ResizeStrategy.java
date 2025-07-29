package core;

import drawing.*;
import java.awt.*;

public interface ResizeStrategy {
    void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy);
}