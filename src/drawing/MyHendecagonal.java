package drawing;

import java.awt.*;
import java.awt.geom.AffineTransform;

import util.*;

public class MyHendecagonal extends MyDrawing {
    private int nPoints = 11;
    private double size = 0;

    public MyHendecagonal(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        super(xpt, ypt, w, h, lineColor, fillColor, lineWidth);
        setSize(w, h);
        size = getW() < getH() ? getW() : getH();
    }

    public MyHendecagonal(int xpt, int ypt, int w, int h) {
        this(xpt, ypt, w, h, Color.black, Color.white, 1);
    }
    
    public void setSize(int w, int h) {
        if (Math.abs(w) < Math.abs(h)) {
            h = w;
        } else {
            w = h;
        }
        super.setSize(w, h);
        size = w;
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();
        
        int[][] points = getHendecagonalPoints(x, y, w, h);
        int xPoints[] = points[0];
        int yPoints[] = points[1];

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();
        Point center = new Point(x + w/2, y + h/2);
        g2.rotate(getRotationAngle(), center.x, center.y);

        if (getHasShadow()) {
            int shadowX = x - getLineWidth() / 2 + 5;
            int shadowY = y - getLineWidth() / 2 + 5;
            g2.setColor(Color.black);
            int[][] shadowPoints = getHendecagonalPoints(shadowX, shadowY, w + getLineWidth(), h + getLineWidth());
            int xPointsShadow[] = shadowPoints[0];
            int yPointsShadow[] = shadowPoints[1];
            g2.fillPolygon(xPointsShadow, yPointsShadow, nPoints);
        }

        if (getIsDashed() == true)
            g2.setStroke(new MyDashStroke(getLineWidth(), getDashPattern()));
        else
            g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillPolygon(xPoints, yPoints, nPoints);
        g2.setColor(getLineColor());
        g2.drawPolygon(xPoints, yPoints, nPoints);

        g2.setTransform(originalTransform);
        super.draw(g);
    }
    
    protected Shape createRegion() {
        int[][] points = getHendecagonalPoints(getX(), getY(), getW(), getH());
        int xPoints[] = points[0];
        int yPoints[] = points[1];
        return new Polygon(xPoints, yPoints, nPoints);
    }
    
    private int[][] getHendecagonalPoints(int x, int y, int w, int h) {
        int size = w < h ? w : h;

        int centerX = x + w / 2;
        int centerY = y + h / 2;

        int xPoints[] = new int[nPoints];
        int yPoints[] = new int[nPoints];
        for (int i = 0; i < nPoints; i++) {
            double tmpX = centerX + (size / 2) * Math.cos(i * (2 * Math.PI / nPoints));
            double tmpY = centerY + (size / 2) * Math.sin(i * (2 * Math.PI / nPoints));
            xPoints[i] = (int) tmpX;
            yPoints[i] = (int) tmpY;
        }
        
        return new int[][] { xPoints, yPoints };
    }
}
