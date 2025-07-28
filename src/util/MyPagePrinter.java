package util;

import java.awt.*;
import java.awt.print.*;
import ui.*;

public class MyPagePrinter implements Printable {
    private MyCanvas canvas;

    public MyPagePrinter(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public int print(Graphics g, PageFormat fmt, int pageIndex) {
        if(pageIndex >= 1) {
            return NO_SUCH_PAGE;
        }
        
        canvas.paint(g);
        
        return PAGE_EXISTS;
    }
}
