package core;

import java.awt.*;
import java.awt.geom.AffineTransform;
import drawing.*;

public class ZoomManager {
    private double scale = 1.0;
    private double translateX = 0;
    private double translateY = 0;
    private final double minZoom = 1.0;
    private MyRectangle zoomRect = new MyRectangle(0, 0, 0, 0);
    private int dragStartX, dragStartY;
    
    public ZoomManager() {
        zoomRect.setLineColor(Color.MAGENTA);
        zoomRect.setFillColor(new Color(0, 0, 0, 0));
        zoomRect.setIsDashed(true);
    }
    
    public void setScale(double scale) {
        this.scale = Math.max(1, scale);
    }
    
    public void setZoomRectLocation(int x, int y) {
        zoomRect.setLocation(x, y);
    }
    public void setZoomRectSize(int width, int height) {
        zoomRect.setSize(width, height);
    }
    public MyRectangle getZoomRect() {
        return zoomRect;
    }
    
    public void zoomToRectangle(int canvasWidth, int canvasHeight) {
        if (zoomRect.getW() <= 0 || zoomRect.getH() <= 0) return;
        
        double zoomX = (double) canvasWidth / zoomRect.getW();
        double zoomY = (double) canvasHeight / zoomRect.getH();
        
        scale = Math.min(zoomX, zoomY);
        scale = Math.max(minZoom, scale);

        translateX = (canvasWidth / 2.0) - (zoomRect.getX() + zoomRect.getW() / 2.0) * scale;
        translateY = (canvasHeight / 2.0) - (zoomRect.getY() + zoomRect.getH() / 2.0) * scale;
    }
    
    public void resetZoom() {
        scale = 1.0;
        translateX = 0;
        translateY = 0;
    }
    
    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(translateX, translateY);
        transform.scale(scale, scale);
        return transform;
    }
    
    // public Point screenToWorld(Point screenPoint) {
    //     double worldX = (screenPoint.x - translateX) / scale;
    //     double worldY = (screenPoint.y - translateY) / scale;
    //     return new Point((int) worldX, (int) worldY);
    // }
    
    // public Point worldToScreen(Point worldPoint) {
    //     double screenX = worldPoint.x * scale + translateX;
    //     double screenY = worldPoint.y * scale + translateY;
    //     return new Point((int) screenX, (int) screenY);
    // }
    
    public double getScale() {
        return scale;
    }
    
    public void setTranslation(double x, double y) {
        this.translateX = x;
        this.translateY = y;
    }
    
    public double getTranslateX() {
        return translateX;
    }
    
    public double getTranslateY() {
        return translateY;
    }
    
    public int getDragStartX() {
        return dragStartX;
    }
    public int getDragStartY() {
        return dragStartY;
    }
    public void setDragStart(int x, int y) {
        this.dragStartX = x;
        this.dragStartY = y;
    }
}
