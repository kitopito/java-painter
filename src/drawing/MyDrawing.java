package drawing;

import java.awt.*;
import java.io.*;
import util.*;

public class MyDrawing implements DrawingComponent {
    private int x, y, w, h;
    private int lineWidth;
    private Color lineColor, fillColor;
    private boolean isDashed = false;
    private boolean hasShadow = false;
    private float[] dashPattern = {10, 15};
    private int multiLineCount = 1;
    private boolean isSelected;
    private Shape region;
    final int SELECTION_HANDLE_SIZE = 7;

    public MyDrawing() {
        this(0, 0, 40, 40, Color.black, Color.white, 1);
    }

    public MyDrawing(int x, int y, int w, int h) {
        this(x, y, w, h, Color.black, Color.white, 1);
    }

    public MyDrawing(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;

        if (w < 0) {
            x += w;
            w = -w;
        }
        if (h < 0) {
            y += h;
            h = -h;
        }
        
        setRegion(createRegion());
    }

    public void draw(Graphics g) {
        int x = this.x - lineWidth / 2;
        int y = this.y - lineWidth / 2;
        int w = this.w + lineWidth;
        int h = this.h + lineWidth;

        if(isSelected) {
            g.setColor(Color.black);
            // 8 handles: 4 corners and 4 sides' centers
            int[][] points = {
                {x, y}, // top-left
                {x + w / 2, y}, // top-center
                {x + w, y}, // top-right
                {x, y + h / 2}, // left-center
                {x + w, y + h / 2}, // right-center
                {x, y + h}, // bottom-left
                {x + w / 2, y + h}, // bottom-center
                {x + w, y + h} // bottom-right
            };
            for (int[] p : points) {
                g.fillRect(p[0] - SELECTION_HANDLE_SIZE / 2, p[1] - SELECTION_HANDLE_SIZE / 2, SELECTION_HANDLE_SIZE, SELECTION_HANDLE_SIZE);
            }
        }
    }
    
    public MyDrawing clone() {
        MyDrawing c = null;
        try {
            c = (MyDrawing) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void move(int x, int y) {
        setLocation(this.x + x, this.y + y);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        setRegion(createRegion());
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        if (w < 0) {
            this.x += w;
            this.w = -w;
        }
        if (h < 0) {
            this.y += h;
            this.h = -h;
        }
        setRegion(createRegion());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public int getLineWidth() {
        return this.lineWidth;
    }

    public Color getLineColor() {
        return this.lineColor;
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public boolean getIsDashed() {
        return this.isDashed;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        System.out.println("Line color set to: " + lineColor);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        System.out.println("Fill color set to: " + fillColor);
    }

    public void setIsDashed(boolean isDashed) {
        this.isDashed = isDashed;
    }

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    public boolean getHasShadow() {
        return hasShadow;
    }

    public void setDashPattern(float[] dashPattern) {
        this.dashPattern = dashPattern;
    }

    public float[] getDashPattern() {
        return dashPattern;
    }

    public void setMultiLineCount(int multiLineCount) {
        this.multiLineCount = multiLineCount;
    }

    public int getMultiLineCount() {
        return multiLineCount;
    }
    
    public boolean getSelected() {
        return isSelected;
    }
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
    
    public boolean isGroup() {
        return false;
    }
    
    public void setRegion(Shape region) {
        this.region = region;
    }
    protected Shape createRegion() {
        return new Rectangle(x, y, w, h);
    }
}