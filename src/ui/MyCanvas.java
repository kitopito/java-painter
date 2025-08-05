package ui;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import core.*;
import drawing.*;

public class MyCanvas extends JPanel {
    Mediator mediator;
    ZoomManager zoomManager;

    public MyCanvas() {
        this.mediator = new Mediator(this);
        this.zoomManager = new ZoomManager();
        setBackground(Color.white);
    }
    
    public Mediator getMediator() {
        return mediator;
    }
    
    public ZoomManager getZoomManager() {
        return zoomManager;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d); //これの場所大事　setTransformの後に書いたらバグった

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setTransform(zoomManager.getTransform());
        // System.out.println("Painting canvas with zoom level: " + zoomManager.getTransform().getScaleX());
        
        Enumeration<DrawingComponent> e = mediator.drawingsElements();
        while (e.hasMoreElements()) {
            DrawingComponent d = e.nextElement();
            d.draw(g2d);
        }
    }
}
