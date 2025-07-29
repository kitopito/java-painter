package core;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import drawing.*;
import ui.*;

public class StateManager {
    private State currentState;
    private Mediator mediator;
    private boolean isDashed = false;
    private boolean shadowEnabled = false;
    private int lineWidth = 1;
    private int multiLineCount = 1;
    private float[] dashPattern = {10, 15};
    private Color fillColor = Color.BLACK;
    private Color lineColor = Color.BLACK;
    private List<SelectionListener> selectionListeners = new ArrayList<>();

    public StateManager(MyCanvas canvas) {
        this.mediator = canvas.getMediator();
        this.currentState = null;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void mouseDown(int x, int y) {
        if (currentState != null) {
            currentState.mouseDown(x, y);
            mediator.repaint();
        }
    }
    
    public void mouseUP(int x, int y) {
        if (currentState != null) {
            currentState.mouseUP(x, y);
            mediator.repaint();
        }
    }

    public void mouseDrag(int x, int y) {
        if (currentState != null) {
            currentState.mouseDrag(x, y);
            mediator.repaint();
        }
    }
    
    public void deleteKeyPressed() {
        mediator.delete();
    }
    
    public void copyKeyPressed() {
        mediator.copy();
    }
    
    public void cutKeyPressed() {
        mediator.cut();
    }
    
    public void pasteKeyPressed() {
        mediator.paste();
    }

    public void addDrawing(MyDrawing d) {
        d.setIsDashed(isDashed);
        d.setHasShadow(shadowEnabled);
        d.setLineWidth(lineWidth);
        d.setMultiLineCount(multiLineCount);
        d.setFillColor(fillColor);
        d.setLineColor(lineColor);
        if (isDashed) {
            d.setDashPattern(dashPattern);
        }
        mediator.addDrawing(d);
    }

    public void addSelectionRect(MyDrawing d) {
        mediator.addDrawing(d);
    }
    public void removeDrawing(DrawingComponent d) {
        mediator.removeDrawing(d);
    }
    
    public Vector<DrawingComponent> selectDrawing(int x, int y) {
        Vector<DrawingComponent> selected = mediator.setSelected(x, y);
        if (mediator.getSelectedDrawing() != null) {
            notifySelectionChanged(mediator.getSelectedDrawing());
        }
        return selected;
    }
    public Vector<DrawingComponent> selectDrawing(MyDrawing selectionRect) {
        Vector<DrawingComponent> selected = mediator.setSelected(selectionRect);
        if (mediator.getSelectedDrawing() != null) {
            notifySelectionChanged(mediator.getSelectedDrawing());
        }
        return selected;
    }
    
    public void clearSelection() {
        mediator.clearSelection();
        //notifySelectionChanged(null); // ぬるぽ
    }

    public void moveSelectedDrawing(int dx, int dy) {
        mediator.move(dx, dy);
        mediator.repaint();
    }
    
    public void setShadowEnabled(boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
        mediator.setHasShadow(shadowEnabled);
    }
    public boolean getShadowEnabled() {
        return shadowEnabled;
    }

    public boolean getIsDashed() {
        return isDashed;
    }
    public void setIsDashed(boolean isDashed) {
        this.isDashed = isDashed;
        mediator.setIsDashed(isDashed);
    }

    public int getLineWidth() {
        return lineWidth;
    }
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        mediator.setLineWidth(lineWidth);
    }

    public int getMultiLineCount() {
        return multiLineCount;
    }
    public void setMultiLineCount(int multiLineCount) {
        this.multiLineCount = multiLineCount;
    }

    public float[] getDashPattern() {
        return dashPattern;
    }
    public void setDashPattern(float[] dashPattern) {
        this.dashPattern = dashPattern;
        mediator.setDashPattern(dashPattern);
    }
    
    public Color getFillColor() {
        return fillColor;
    }
    public void setFillColor(Color color) {
        this.fillColor = color;
        mediator.setFillColor(color);
    }
    
    public Color getLineColor() {
        return lineColor;
    }
    public void setLineColor(Color color) {
        this.lineColor = color;
        mediator.setLineColor(color);
    }
    
    public void addSelectionListener(SelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void removeSelectionListener(SelectionListener listener) {
        selectionListeners.remove(listener);
    }

    public void notifySelectionChanged(Vector<DrawingComponent> selected) {
        for (SelectionListener listener : selectionListeners) {
            if (selected != null)
                listener.onSelectionChanged(selected.firstElement());
        }
    }
    
    public void groupSelectedDrawings() {
        mediator.groupSelectedDrawings();
    }
    public void ungroupSelectedDrawings() {
        mediator.ungroupSelectedDrawing();
    }
    
    public ResizeHandle getResizeHandle(int x, int y) {
        return mediator.getResizeHandle(x, y);
    }
}
