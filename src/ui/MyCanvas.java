package ui;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import core.*;
import drawing.*;

public class MyCanvas extends JPanel {
    Mediator mediator;

    public MyCanvas() {
        this.mediator = new Mediator(this);
        setBackground(Color.white);
    }
    
    public Mediator getMediator() {
        return mediator;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Enumeration<DrawingComponent> e = mediator.drawingsElements();
        while (e.hasMoreElements()) {
            DrawingComponent d = e.nextElement();
            d.draw(g);
        }
    }
}
