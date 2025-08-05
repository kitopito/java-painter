package drawing;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import util.MyDashStroke;

public class MyOval extends MyDrawing {
    public MyOval(int x, int y, int w, int h) {
        this(x, y, w, h, Color.black, Color.white, 1);
    }

    public MyOval(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        super(xpt, ypt, w, h, lineColor, fillColor, lineWidth);
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
            g2.fillOval(shadowX, shadowY, w + getLineWidth(), h + getLineWidth());
        }

        if (getIsDashed() == true)
            g2.setStroke(new MyDashStroke(getLineWidth(), getDashPattern()));
        else
            g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillOval(x, y, w, h);
        g2.setColor(getLineColor());
        g2.drawOval(x, y, w, h);

        g2.setTransform(originalTransform);
        super.draw(g);
    }

    protected Shape createRegion() {
        return new Ellipse2D.Float(getX(), getY(), getW(), getH());
    }
}
