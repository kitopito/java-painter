package drawing;

import java.awt.*;
import java.awt.geom.AffineTransform;

import util.MyDashStroke;

public class MyRectangle extends MyDrawing {
    public MyRectangle(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        super(xpt, ypt, w, h, lineColor, fillColor, lineWidth);
    }

    public MyRectangle(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h, Color.black, Color.white, 1);
    }

    public MyRectangle(int xpt, int ypt) {
        super();
        setLocation(xpt, ypt);
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();
        Point center = new Point(x + w/2, y + h/2);
        g2.rotate(getRotationAngle(), center.x, center.y);

        if (getHasShadow()) {
            int shadowX = x - getLineWidth() / 2 + 5;
            int shadowY = y - getLineWidth() / 2 + 5;
            g2.setColor(Color.black);
            g2.fillRect(shadowX, shadowY, w + getLineWidth(), h + getLineWidth());
        }

        if(getIsDashed())
            g2.setStroke(new MyDashStroke(getLineWidth(), getDashPattern()));
        else 
            g2.setStroke(new BasicStroke(getLineWidth()));
        
        g2.setColor(getFillColor());
        g2.fillRect(x, y, w, h);
        g2.setColor(getLineColor());
        g2.drawRect(x, y, w, h);
        drawHandles(g2);

        g2.setTransform(originalTransform);
    }
}
