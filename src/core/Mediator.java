package core;

import java.util.Enumeration;
import java.util.Vector;
import java.awt.*;
import java.io.*;
import drawing.*;
import ui.*;

public class Mediator {
    Vector<DrawingComponent> drawings;
    MyCanvas canvas;
    Vector<DrawingComponent> selectedDrawing = null;
    Vector<DrawingComponent> buffer = null;
    
    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        this.drawings = new Vector<DrawingComponent>();
    }
    
    public Enumeration<DrawingComponent> drawingsElements() {
        return drawings.elements();
    }
    
    public void addDrawing(DrawingComponent d) {
        drawings.add(d);
        // d.setFillColor(color);
        // d.setLineColor(color);
        //setSelectedDrawing(d);
        repaint();
    }
    
    public void removeDrawing(DrawingComponent d) {
        drawings.remove(d);
    }
    public void removeDrawings(Vector<DrawingComponent> ds) {
        Enumeration<DrawingComponent> e = ds.elements();
        while (e.hasMoreElements()) {
            DrawingComponent d = e.nextElement();
            removeDrawing(d);
        }
    }
    
    public Vector<DrawingComponent> getSelectedDrawing() {
        return selectedDrawing;
    }

    public void move(int dx, int dy) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.move(dx, dy);
            }
        }
    }
    
    public void repaint() {
        canvas.repaint();
    }

    public Vector<DrawingComponent> setSelected(int x, int y) {
        if (selectedDrawing != null) {
            for (int i = selectedDrawing.size() - 1; i >= 0; i--) {
                DrawingComponent d = selectedDrawing.get(i);
                if (d.contains(x, y)) {
                    return selectedDrawing; // Already selected
                }
            }
        }

        for (int i = drawings.size() - 1; i >= 0; i--) {
            DrawingComponent d = drawings.get(i);
            d.setSelected(false);
        }

        for (int i = drawings.size() - 1; i >= 0; i--) {
            DrawingComponent d = drawings.get(i);
            if (d.contains(x, y)) {
                setSelectedDrawing(d);
                d.setSelected(true);
                return selectedDrawing;
            }
        }
        setSelectedDrawing(null);
        return null;
    }

    public Vector<DrawingComponent> setSelected(MyDrawing selectionRect) {
        for (int i = drawings.size() - 1; i >= 0; i--) {
            DrawingComponent d = drawings.get(i);
            d.setSelected(false);
        }

        Vector<DrawingComponent> selectedDrawings = new Vector<DrawingComponent>();
        for (int i = drawings.size() - 1; i >= 0; i--) {
            DrawingComponent d = drawings.get(i);
            if (d != selectionRect &&
                d.getX() >= selectionRect.getX() &&
                d.getY() >= selectionRect.getY() &&
                d.getX() + d.getW() <= selectionRect.getX() + selectionRect.getW() &&
                d.getY() + d.getH() <= selectionRect.getY() + selectionRect.getH()) {

                selectedDrawings.add(d);
                d.setSelected(true);
            }
        }
        if(selectedDrawings.size() > 0) {
            selectedDrawing = selectedDrawings;
        } else {
            selectedDrawing = null;
        }
        canvas.repaint();
        return selectedDrawing;
    }

    public void setSelectedDrawing(DrawingComponent d) {
        if (d == null) {
            selectedDrawing = null;
        }
        else {
            selectedDrawing = new Vector<DrawingComponent>();
            selectedDrawing.add(d);
        }
        canvas.repaint();
    }
    
    public void clearSelection() {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setSelected(false);
            }
        }
        selectedDrawing = null;
        canvas.repaint();
    }
    
    public void setFillColor(Color color) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setFillColor(color);
            }
        }
        canvas.repaint();
    }
    public void setLineColor(Color color) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setLineColor(color);
            }
        }
        canvas.repaint();
    }
    public void setLineWidth(int width) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setLineWidth(width);
            }
        }
        canvas.repaint();
    }
    public void setIsDashed(boolean isDashed) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setIsDashed(isDashed);
            }
        }
        canvas.repaint();
    }
    public void setDashPattern(float[] dashPattern) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setDashPattern(dashPattern);
            }
        }
        canvas.repaint();
    }
    public void setHasShadow(boolean hasShadow) {
        if (selectedDrawing != null) {
            Enumeration<DrawingComponent> e = selectedDrawing.elements();
            while (e.hasMoreElements()) {
                DrawingComponent d = e.nextElement();
                d.setHasShadow(hasShadow);
            }
        }
        canvas.repaint();
    }
    public void setTextSize(int size) {
        if (selectedDrawing != null) {
            for(DrawingComponent d : selectedDrawing) {
                if (d instanceof MyText) {
                    ((MyText)d).setTextSize(size);
                }
            }
        }
        canvas.repaint();
    }
    public void setTextFont(Font font) {
        if (selectedDrawing != null) {
            for(DrawingComponent d : selectedDrawing) {
                if (d instanceof MyText) {
                    ((MyText)d).setFont(font);
                }
            }
        }
        canvas.repaint();
    }
    
    public void clearBuffer() {
        buffer = null;
    }
    public void copy() {
        clearBuffer();
        if (selectedDrawing != null) {
            buffer = cloneDrawings(selectedDrawing);
        }
    }
    
    private Vector<DrawingComponent> cloneDrawings(Vector<DrawingComponent> drawings) {
        Vector<DrawingComponent> clone = new Vector<DrawingComponent>();
        Enumeration<DrawingComponent> e = drawings.elements();
        while (e.hasMoreElements()) {
            DrawingComponent d = e.nextElement();
            clone.add((DrawingComponent)d.clone());
        }
        return clone;
    }
    
    public void cut() {
        clearBuffer();
        if (selectedDrawing != null) {
            buffer = cloneDrawings(selectedDrawing);
            removeDrawings(selectedDrawing);
        }
        repaint();
    }
    
    public void paste() {
        if (buffer == null)
            return;

        Vector<DrawingComponent> clone = cloneDrawings(buffer);
        if (clone != null) {
            Enumeration<DrawingComponent> e = clone.elements();
            while (e.hasMoreElements()) {
                DrawingComponent cd = e.nextElement();
                cd.setLocation(cd.getX() + 10, cd.getY() + 10);
                addDrawing(cd);
            }
            repaint();
        }
    }
    
    public void delete() {
        if (selectedDrawing != null) {
            removeDrawings(selectedDrawing);
            selectedDrawing = null;
            repaint();
        }
    }

    public void saveToFile(File file) {
         try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(drawings);
            out.flush();

            fout.close();
        } catch (Exception ex) {
            System.err.println("Error saving to file: " + ex.getMessage());
        }
    }
    public void loadFromFile(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fin);

            drawings = (Vector<DrawingComponent>)in.readObject();
            fin.close();
        } catch (Exception ex) {
            System.err.println("Error loading from file: " + ex.getMessage());
        }
        repaint();
    }
    
    public MyCanvas getCanvas() {
        return canvas;
    }
    
    public void groupSelectedDrawings() {
        if (selectedDrawing == null || selectedDrawing.size() < 2) {
            return;
        }

        MyDrawingGroup group = new MyDrawingGroup(selectedDrawing);
        for(DrawingComponent d : selectedDrawing) {
            d.setSelected(false);
        }
        removeDrawings(selectedDrawing);
        addDrawing(group);
        setSelectedDrawing(group);
        group.setSelected(true);
        repaint();
    }
    
    public void ungroupSelectedDrawing() {
        if (selectedDrawing == null || selectedDrawing.size() != 1) {
            return;
        }

        DrawingComponent d = selectedDrawing.get(0);
        if (d.isGroup()) {
            MyDrawingGroup group = (MyDrawingGroup) d;
            Vector<DrawingComponent> children = group.getChildren();
            removeDrawing(group);
            for (DrawingComponent child : children) {
                addDrawing(child);
                child.setSelected(true);
            }
            selectedDrawing = children;
            repaint();
        }
    }
    
    public ResizeHandle getResizeHandle(int x, int y) {
        if (selectedDrawing != null && selectedDrawing.size() == 1) {
            DrawingComponent d = selectedDrawing.get(0);
            return d.getResizeHandle(x, y);
        }
        return ResizeHandle.NONE;
    }
}