package drawing;

import java.awt.*;
import java.awt.geom.*;

import core.ResizeHandle;

public class MyText extends MyDrawing {
    private String text;
    private Font font;
    
    public MyText(String text, int x, int y, Color textColor, Font font) {
        super(x, y, 0, 0, textColor, Color.WHITE, 1);
        this.text = text;
        this.font = font != null ? font : new Font("SansSerif", Font.PLAIN, 12);
    }
    
    public MyText(String text, int x, int y) {
        this(text, x, y, Color.BLACK, null);
    }
    
    private void updateBounds(FontMetrics fm) {
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        super.setSize(width, height);
    }
    
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        updateBounds(fm);
        
        g2.setColor(getLineColor());
        g2.drawString(text, getX(), getY() + fm.getAscent());
        
        super.draw(g);
    }
    
    protected Shape createRegion() {
        return new Rectangle(getX(), getY(), getW(), getH());
    }
    
    public void setSize(int w, int h) {
        if (w > 0 && h > 0) {
            super.setSize(w, h);
        }
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public Font getFont() {
        return font;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public int getTextSize() {
        return font.getSize();
    }

    public void setTextSize(int size) {
        if (size > 0) {
            this.font = this.font.deriveFont((float) size);
        }
    }
    
    // public MyText clone() {
    //     MyText cloned = new MyText(text, getX(), getY(), getLineColor(), font);
    //     cloned.setSelected(getSelected());
    //     cloned.setFillColor(getFillColor());
    //     cloned.setLineWidth(getLineWidth());
    //     cloned.setIsDashed(getIsDashed());
    //     cloned.setHasShadow(getHasShadow());
    //     cloned.setDashPattern(getDashPattern());
    //     return cloned;
    // }
    
    public boolean isGroup() {
        return false;
    }
    
    public ResizeHandle getResizeHandle(int x, int y) {
        return ResizeHandle.NONE;
    }
}
