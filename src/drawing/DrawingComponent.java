package drawing;

import java.awt.*;
import java.io.*;

public interface DrawingComponent extends Cloneable, Serializable {
    void draw(Graphics g);
    
    void move(int dx, int dy);
    void setLocation(int x, int y);
    void setSize(int w, int h);
    
    int getX();
    int getY();
    int getW();
    int getH();
    
    boolean contains(int x, int y);
    Rectangle getBounds();
    
    boolean getSelected();
    void setSelected(boolean isSelected);
    
    Color getLineColor();
    Color getFillColor();
    int getLineWidth();
    boolean getIsDashed();
    boolean getHasShadow();
    float[] getDashPattern();
    
    void setLineColor(Color lineColor);
    void setFillColor(Color fillColor);
    void setLineWidth(int lineWidth);
    void setIsDashed(boolean isDashed);
    void setHasShadow(boolean hasShadow);
    void setDashPattern(float[] dashPattern);
    
    DrawingComponent clone();
    
    boolean isGroup();
}
