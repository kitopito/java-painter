package drawing;

import java.awt.*;
import java.util.*;

public class MyDrawingGroup implements DrawingComponent {
    private Vector<DrawingComponent> children;
    private boolean isSelected;
    private int x, y, w, h;
    
    // private Color groupLineColor;
    // private Color groupFillColor;
    // private int groupLineWidth;
    // private boolean groupIsDashed;
    // private boolean groupHasShadow;
    // private float[] groupDashPattern;
    
    public MyDrawingGroup() {
        this.children = new Vector<DrawingComponent>();
        this.isSelected = false;
        // this.groupLineColor = Color.BLACK;
        // this.groupFillColor = Color.WHITE;
        // this.groupLineWidth = 1;
        // this.groupIsDashed = false;
        // this.groupHasShadow = false;
        // this.groupDashPattern = new float[]{10, 15};
    }
    
    public MyDrawingGroup(Vector<DrawingComponent> components) {
        this();
        for (DrawingComponent component : components) {
            addChild(component);
        }
        calculateBounds();
    }
    
    public void draw(Graphics g) {
        for (DrawingComponent child : children) {
            child.draw(g);
        }
        
        if (isSelected) {
            drawGroupSelectionBounds(g);
        }
    }
    
    private void drawGroupSelectionBounds(Graphics g) {
        final int HANDLE_SIZE = 7;
        g.setColor(Color.BLACK);
        int[][] handles = {
            {x, y}, // top-left
            {x + w / 2, y}, // top-center
            {x + w, y}, // top-right
            {x, y + h / 2}, // left-center
            {x + w, y + h / 2}, // right-center
            {x, y + h}, // bottom-left
            {x + w / 2, y + h}, // bottom-center
            {x + w, y + h} // bottom-right
        };
        
        for (int[] handle : handles) {
            g.fillRect(handle[0] - HANDLE_SIZE/2, handle[1] - HANDLE_SIZE/2, 
                      HANDLE_SIZE, HANDLE_SIZE);
        }
    }
    
    public void move(int dx, int dy) {
        for (DrawingComponent child : children) {
            child.move(dx, dy);
        }
        x += dx;
        y += dy;
    }
    
    public void setLocation(int x, int y) {
        int dx = x - this.x;
        int dy = y - this.y;
        move(dx, dy);
    }
    
    public void setSize(int w, int h) {
        calculateBounds();
        
        // double scaleX = (double) w / this.w;
        // double scaleY = (double) h / this.h;
        
        // for (DrawingComponent child : children) {
        //     int newX = this.x + (int)((child.getX() - this.x) * scaleX);
        //     int newY = this.y + (int)((child.getY() - this.y) * scaleY);
        //     int newW = (int)(child.getW() * scaleX);
        //     int newH = (int)(child.getH() * scaleY);
            
        //     child.setLocation(newX, newY);
        //     child.setSize(newW, newH);
        // }
        
        this.w = w;
        this.h = h;
    }
    
    private void calculateBounds() {
        if (children.isEmpty()) {
            x = 0;
            y = 0;
            w = 0;
            h = 0;
            return;
        }
        
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for (DrawingComponent child : children) {
            minX = Math.min(minX, child.getX());
            minY = Math.min(minY, child.getY());
            maxX = Math.max(maxX, child.getX() + child.getW());
            maxY = Math.max(maxY, child.getY() + child.getH());
        }
        
        x = minX;
        y = minY;
        w = maxX - minX;
        h = maxY - minY;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getW() {
        return w;
    }
    
    public int getH() {
        return h;
    }
    
    public boolean contains(int x, int y) {
        for(DrawingComponent child : children) {
            if (child.contains(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
    
    public boolean getSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public Color getLineColor() {
        return children.get(0).getLineColor();
    }
    
    public Color getFillColor() {
        return children.get(0).getFillColor();
    }
    
    public int getLineWidth() {
        return children.get(0).getLineWidth();
    }
    
    public boolean getIsDashed() {
        return children.get(0).getIsDashed();
    }
    
    public boolean getHasShadow() {
        return children.get(0).getHasShadow();
    }
    
    public float[] getDashPattern() {
        return children.get(0).getDashPattern();
    }
    
    public void setLineColor(Color lineColor) {
        // this.groupLineColor = lineColor;
        for (DrawingComponent child : children) {
            child.setLineColor(lineColor);
        }
    }
    
    public void setFillColor(Color fillColor) {
        // this.groupFillColor = fillColor;
        for (DrawingComponent child : children) {
            child.setFillColor(fillColor);
        }
    }
    
    public void setLineWidth(int lineWidth) {
        // this.groupLineWidth = lineWidth;
        for (DrawingComponent child : children) {
            child.setLineWidth(lineWidth);
        }
    }
    
    public void setIsDashed(boolean isDashed) {
        // this.groupIsDashed = isDashed;
        for (DrawingComponent child : children) {
            child.setIsDashed(isDashed);
        }
    }
    
    public void setHasShadow(boolean hasShadow) {
        // this.groupHasShadow = hasShadow;
        for (DrawingComponent child : children) {
            child.setHasShadow(hasShadow);
        }
    }
    
    public void setDashPattern(float[] dashPattern) {
        // this.groupDashPattern = dashPattern.clone();
        for (DrawingComponent child : children) {
            child.setDashPattern(dashPattern);
        }
    }
    
    public MyDrawingGroup clone() {
        MyDrawingGroup cloned = new MyDrawingGroup();
        cloned.isSelected = this.isSelected;
        // cloned.groupLineColor = this.groupLineColor;
        // cloned.groupFillColor = this.groupFillColor;
        // cloned.groupLineWidth = this.groupLineWidth;
        // cloned.groupIsDashed = this.groupIsDashed;
        // cloned.groupHasShadow = this.groupHasShadow;
        // cloned.groupDashPattern = this.groupDashPattern.clone();
        
        for (DrawingComponent child : children) {
            cloned.addChild(child.clone());
        }
        
        return cloned;
    }
    
    public boolean isGroup() {
        return true;
    }
    
    public void addChild(DrawingComponent child) {
        children.add(child);
    }
    
    public void removeChild(DrawingComponent child) {
        children.remove(child);
    }
    
    public Vector<DrawingComponent> getChildren() {
        return new Vector<>(children);
    }
    
    public int getChildCount() {
        return children.size();
    }
    
    // public Vector<DrawingComponent> flatten() {
    //     Vector<DrawingComponent> result = new Vector<>();
    //     for (DrawingComponent child : children) {
    //         if (child.isGroup()) {
    //             MyDrawingGroup group = (MyDrawingGroup) child;
    //             result.addAll(group.flatten());
    //         } else {
    //             result.add(child);
    //         }
    //     }
    //     return result;
    // }
}
