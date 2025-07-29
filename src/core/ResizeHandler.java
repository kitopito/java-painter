package core;

import drawing.*;
import java.awt.*;

public class ResizeHandler {
    public ResizeStrategy getResizeStrategy(ResizeHandle handle) {
        switch (handle) {
            case TOP_LEFT:
                return new TopLeftResizeStrategy();
            case TOP_CENTER:
                return new TopCenterResizeStrategy();
            case TOP_RIGHT:
                return new TopRightResizeStrategy();
            case LEFT:
                return new LeftResizeStrategy();
            case RIGHT:
                return new RightResizeStrategy();
            case BOTTOM_LEFT:
                return new BottomLeftResizeStrategy();
            case BOTTOM_CENTER:
                return new BottomCenterResizeStrategy();
            case BOTTOM_RIGHT:
                return new BottomRightResizeStrategy();
            default:
                return new NoResizeStrategy();
        }
    }
    
    class TopLeftResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            int delta;
            if(dx > 0 && dy > 0)
                delta = Math.min(dx, dy);
            else if(dx < 0 && dy < 0)
                delta = Math.max(dx, dy);
            else if(dx < 0)
                delta = dx;
            else
                delta = dy;
            
            drawing.setLocation(originalBounds.x + delta, originalBounds.y + delta);
            drawing.setSize(originalBounds.width - delta, originalBounds.height - delta);
        }
    }
    
    class TopCenterResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            drawing.setLocation(originalBounds.x, originalBounds.y + dy);
            drawing.setSize(originalBounds.width, originalBounds.height - dy);
        }
    }

    class TopRightResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            int delta;
            if(dx > 0 && dy > 0)
                delta = Math.abs(dx);
            else if(dx < 0 && dy < 0)
                delta = Math.abs(dy);
            else if(dx < 0)
                delta = -Math.min(Math.abs(dx), Math.abs(dy));
            else
                delta = Math.min(Math.abs(dx), Math.abs(dy));
            
            drawing.setLocation(originalBounds.x, originalBounds.y - delta);
            drawing.setSize(originalBounds.width + delta, originalBounds.height + delta);
        }
    }

    class LeftResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            drawing.setLocation(originalBounds.x + dx, originalBounds.y);
            drawing.setSize(originalBounds.width - dx, originalBounds.height);
        }
    }

    class RightResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            drawing.setLocation(originalBounds.x, originalBounds.y);
            drawing.setSize(originalBounds.width + dx, originalBounds.height);
        }
    }

    class BottomLeftResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            int delta;
            if(dx > 0 && dy > 0)
                delta = Math.abs(dy);
            else if(dx < 0 && dy < 0)
                delta = Math.abs(dx);
            else if(dx < 0)
                delta = Math.min(Math.abs(dx), Math.abs(dy));
            else
                delta = -Math.min(Math.abs(dx), Math.abs(dy));
            
            drawing.setLocation(originalBounds.x - delta, originalBounds.y);
            drawing.setSize(originalBounds.width + delta, originalBounds.height + delta);
        }
    }

    class BottomCenterResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            drawing.setLocation(originalBounds.x, originalBounds.y);
            drawing.setSize(originalBounds.width, originalBounds.height + dy);
        }
    }

    class BottomRightResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            int delta;
            if(dx > 0 && dy > 0)
                delta = Math.min(Math.abs(dx), Math.abs(dy));
            else if(dx < 0 && dy < 0)
                delta = -Math.min(Math.abs(dx), Math.abs(dy));
            else if(dx < 0)
                delta = Math.abs(dy);
            else
                delta = Math.abs(dx);
            
            drawing.setLocation(originalBounds.x, originalBounds.y);
            drawing.setSize(originalBounds.width + delta, originalBounds.height + delta);
        }
    }
    
    class NoResizeStrategy implements ResizeStrategy {
        public void resize(DrawingComponent drawing, Rectangle originalBounds, int dx, int dy) {
            System.out.println("No resize strategy for this handle.");
        }
    }
}