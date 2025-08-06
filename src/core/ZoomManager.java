package core;

import java.awt.*;
import java.awt.geom.AffineTransform;
import drawing.*;
import ui.MyCanvas;

public class ZoomManager {
    private double scale = 1.0;
    private double translateX = 0;
    private double translateY = 0;
    private final double minZoom = 1.0;
    private MyRectangle zoomRect = new MyRectangle(0, 0, 0, 0);
    private int dragStartX, dragStartY;
    private MyCanvas canvas;

    public ZoomManager(MyCanvas canvas) {
        this.canvas = canvas;
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

    public Point getTransformedCoordinates(int screenX, int screenY, Component canvas) {
        return getTransformedCoordinates(screenX, screenY);

        // // DPIスケールを取得
        // GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
        // AffineTransform defaultTransform = gc.getDefaultTransform();
        // double dpiScaleX = defaultTransform.getScaleX();
        // double dpiScaleY = defaultTransform.getScaleY();
        
        // // デバッグ情報を出力
        // System.out.println("Original screen coordinates: " + screenX + ", " + screenY);
        // System.out.println("DPI scales: " + dpiScaleX + ", " + dpiScaleY);
        
        // // DPIスケールを考慮して座標変換
        // double adjustedX = screenX / dpiScaleX;
        // double adjustedY = screenY / dpiScaleY;
        // System.out.println("DPI adjusted coordinates: " + adjustedX + ", " + adjustedY);
        
        // double worldX = (adjustedX - translateX) / scale;
        // double worldY = (adjustedY - translateY) / scale;
        // System.out.println("Final world coordinates: " + worldX + ", " + worldY);
        // System.out.println("Zoom parameters - translateX: " + translateX + ", translateY: " + translateY + ", scale: " + scale);
        
        // return new Point((int) worldX, (int) worldY);
    }
    
    public Point getTransformedCoordinates(int screenX, int screenY) {
        double worldX = (screenX - translateX) / scale;
        double worldY = (screenY - translateY) / scale;
        return new Point((int) worldX, (int) worldY);
    }
}
