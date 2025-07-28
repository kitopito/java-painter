package drawing;

import java.awt.*;
import util.MyDashStroke;

public class Myline extends MyDrawing {
    private int x1, x2, y1, y2;

    public Myline(int x1, int y1, int x2, int y2, Color lineColor, int lineWidth) {
        super(x1, y1, x2 - x1, y2 - y1, lineColor, Color.black, lineWidth);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Myline(int x1, int y1, int x2, int y2) {
        this(x1, y1, x2, y2, Color.black, 1);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (getIsDashed() == true)
            g2.setStroke(new MyDashStroke(getLineWidth(), getDashPattern()));
        else
            g2.setStroke(new BasicStroke(getLineWidth()));

        if (getHasShadow()) {
            g2.setColor(Color.black);
            g2.drawLine(this.x1+5, this.y1+5, this.x2+5, this.y2+5);
        }
        g2.setColor(getLineColor());
        g2.drawLine(this.x1, this.y1, this.x2, this.y2);
        
        // try {
        //     g2.drawPolygon((Polygon)createRegion());;
        // } catch (Exception e) {
        //     System.err.println("Error drawing region: " + e.getMessage());
        // }
        
        super.draw(g);
    }
    
    public int[] getPoints() {
        return new int[]{x1, y1, x2, y2};
    }

    public int[] getStartPoint() {
        return new int[]{x1, y1};
    }
    public int[] getEndPoint() {
        return new int[]{x2, y2};
    }
    
    public void setStartPoint(int x, int y) {
        this.x1 = x;
        this.y1 = y;
        super.setLocation(Math.min(x1, x2), Math.min(y1, y2));
        setSize(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
    public void setEndPoint(int x, int y) {
        this.x2 = x;
        this.y2 = y;
        super.setLocation(Math.min(x1, x2), Math.min(y1, y2));
        setSize(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
    public void setLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        super.setLocation(Math.min(x1, x2), Math.min(y1, y2));
        setSize(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
    
    protected Shape createRegion() {
        if (x1 == x2 && y1 == y2) {
            return new Rectangle(x1, y1, 0, 0);
        }

        int[] px = new int[4];
        int[] py = new int[4];
        int tolerance = 8 + getLineWidth() / 2;
        
        double[] normalVector = new double[]{y1 - y2, x2 - x1};
        double normnorm = Math.sqrt(normalVector[0] * normalVector[0] + normalVector[1] * normalVector[1]);
        normalVector[0] /= normnorm;
        normalVector[1] /= normnorm;
        
        px[0] = (int) (x1 + normalVector[0] * tolerance);
        py[0] = (int) (y1 + normalVector[1] * tolerance);
        px[1] = (int) (x1 - normalVector[0] * tolerance);
        py[1] = (int) (y1 - normalVector[1] * tolerance);
        px[2] = (int) (x2 - normalVector[0] * tolerance);
        py[2] = (int) (y2 - normalVector[1] * tolerance);
        px[3] = (int) (x2 + normalVector[0] * tolerance);
        py[3] = (int) (y2 + normalVector[1] * tolerance);

        Polygon p = new Polygon(px, py, 4);
        return p;
    }
    
    public void move(int dx, int dy) {
        setStartPoint(x1 + dx, y1 + dy);
        setEndPoint(x2 + dx, y2 + dy);
    }
    
    public void setLocation(int x, int y) {
        int dx = x - Math.min(x1, x2);
        int dy = y - Math.min(y1, y2);
        setStartPoint(x1 + dx, y1 + dy);
        setEndPoint(x2 + dx, y2 + dy);
    }
}
