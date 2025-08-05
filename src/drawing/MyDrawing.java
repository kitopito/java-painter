package drawing;

import java.awt.*;
import java.io.*;
import util.*;
import core.*;
import java.awt.geom.*;

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
    final int HANDLE_SIZE = 10; // ハンドルサイズを大きく
    
    // 回転関連の変数
    private double rotationAngle = 0.0;
    private Point rotationCenter = null;

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
        setRotationCenter();
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();
        
        Point center = rotationCenter != null ? rotationCenter : new Point(x + w/2, y + h/2);
        g2.rotate(rotationAngle, center.x, center.y);
        drawHandles(g2);
        
        g2.setTransform(originalTransform);
    }
    
    protected void drawHandles(Graphics2D g2) {
        int x = this.x - lineWidth / 2;
        int y = this.y - lineWidth / 2;
        int w = this.w + lineWidth;
        int h = this.h + lineWidth;

        if(isSelected) {
            g2.setColor(Color.black);
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
                g2.fillRect(p[0] - HANDLE_SIZE / 2, p[1] - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
            }
            
            int rotateHandleX = x + w / 2;
            int rotateHandleY = y - 25;
            
            // 回転ハンドルへの線
            g2.setColor(Color.blue);
            g2.drawLine(x + w / 2, y, rotateHandleX, rotateHandleY);
            
            // 回転ハンドル
            g2.setColor(Color.blue);
            g2.fillOval(rotateHandleX - HANDLE_SIZE / 2, rotateHandleY - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
            g2.setColor(Color.white);
            g2.drawOval(rotateHandleX - HANDLE_SIZE / 2, rotateHandleY - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
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
        setRotationCenter();
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
        setRotationCenter();
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
    
    protected void setRotationCenter() {
        rotationCenter = new Point(x + w / 2, y + h / 2);
    }

    public ResizeHandle getResizeHandle(int mouseX, int mouseY) {
        int x = this.x - lineWidth / 2;
        int y = this.y - lineWidth / 2;
        int w = this.w + lineWidth;
        int h = this.h + lineWidth;

        int hs = HANDLE_SIZE / 2;
        Rectangle[] handles = {
            new Rectangle(x - hs, y - hs, HANDLE_SIZE, HANDLE_SIZE), // top-left
            new Rectangle(x + w / 2 - hs, y - hs, HANDLE_SIZE, HANDLE_SIZE), // top-center
            new Rectangle(x + w - hs, y - hs, HANDLE_SIZE, HANDLE_SIZE), // top-right
            new Rectangle(x - hs, y + h / 2 - hs, HANDLE_SIZE, HANDLE_SIZE), // left-center
            new Rectangle(x + w - hs, y + h / 2 - hs, HANDLE_SIZE, HANDLE_SIZE), // right-center
            new Rectangle(x - hs, y + h - hs, HANDLE_SIZE, HANDLE_SIZE), // bottom-left
            new Rectangle(x + w / 2 - hs, y + h - hs, HANDLE_SIZE, HANDLE_SIZE), // bottom-center
            new Rectangle(x + w - hs, y + h - hs, HANDLE_SIZE, HANDLE_SIZE) // bottom-right
        };

        ResizeHandle[] handleTypes = {
            ResizeHandle.TOP_LEFT, ResizeHandle.TOP_CENTER, ResizeHandle.TOP_RIGHT,
            ResizeHandle.LEFT, ResizeHandle.RIGHT,
            ResizeHandle.BOTTOM_LEFT, ResizeHandle.BOTTOM_CENTER, ResizeHandle.BOTTOM_RIGHT
        };
        
        for (int i = 0; i < handles.length; i++) {
            if (handles[i].contains(mouseX, mouseY)) {
                return handleTypes[i];
            }
        }
        return ResizeHandle.NONE;
    }
    
    public boolean isRotateHandle(int mouseX, int mouseY) {
        if (!isSelected) return false;
        
        int x = this.x - lineWidth / 2;
        int y = this.y - lineWidth / 2;
        int w = this.w + lineWidth;
        int h = this.h + lineWidth;
        
        int originalHandleX = x + w / 2;
        int originalHandleY = y - 25;
        
        Point center = rotationCenter != null ? rotationCenter : new Point(x + w/2, y + h/2);
        
        double rotatedHandleX = center.x + (originalHandleX - center.x) * Math.cos(rotationAngle) - (originalHandleY - center.y) * Math.sin(rotationAngle);
        double rotatedHandleY = center.y + (originalHandleX - center.x) * Math.sin(rotationAngle) + (originalHandleY - center.y) * Math.cos(rotationAngle);
        
        double dx = mouseX - rotatedHandleX;
        double dy = mouseY - rotatedHandleY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        return distance <= HANDLE_SIZE / 2;
    }
    
    public double getRotationAngle() {
        return rotationAngle;
    }
    
    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
    }
    
    public Point getRotationCenter() {
        return rotationCenter;
    }
    
    public void setRotationCenter(Point center) {
        this.rotationCenter = center;
    }
}