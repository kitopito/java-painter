package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import drawing.*;
import core.*;
import util.*;

public class SelectButton extends JToggleButton {
    StateManager stateManager;
    PropertyPanel propertyPanel;

    public SelectButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("選択");
        addChangeListener(new SelectListener());
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
    }
    
    class SelectListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (isSelected()) {
                stateManager.setState(new SelectState(stateManager));
                propertyPanel.setMode(PropertyPanel.SELECT_MODE);
            }
            else {
                stateManager.clearSelection();
            }
        }
    }
    
    enum Mode {
        SELECT, RANGE_SELECT, MOVE, RESIZE
    }
    
    class SelectState implements State {
        StateManager stateManager;
        int preMouseX, preMouseY;
        int startX, startY;
        MyDrawing selectionRect = null;
        Mode mode = Mode.SELECT;
        ResizeStrategy resizeStrategy;
        Vector<DrawingComponent> selectedDrawings = null;
        Rectangle originalBounds;
        
        public SelectState(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void mouseDown(int x, int y) {
            startX = x;
            startY = y;
            preMouseX = x;
            preMouseY = y;

            ResizeHandle resizeHandle = stateManager.getResizeHandle(x, y);
            System.err.println("Resize handle: " + resizeHandle);
            if (resizeHandle != ResizeHandle.NONE) {
                mode = Mode.RESIZE;
                resizeStrategy = new ResizeHandler().getResizeStrategy(resizeHandle);
                originalBounds = new Rectangle(
                    selectedDrawings.get(0).getX(), selectedDrawings.get(0).getY(),
                    selectedDrawings.get(0).getW(), selectedDrawings.get(0).getH()
                );
                return;
            }

            selectedDrawings = stateManager.selectDrawing(x, y);
            if (selectedDrawings != null && selectedDrawings.isEmpty() == false) {
                mode = Mode.MOVE;
                return;
            }

            mode = Mode.RANGE_SELECT;
            if(mode == Mode.RANGE_SELECT) {
                selectionRect = new MyRectangle(x, y, 0, 0);
                selectionRect.setFillColor(new Color(0, 0, 0, 0));
                selectionRect.setIsDashed(true);
                stateManager.addSelectionRect(selectionRect);
            }
        }
        
        public void mouseUP(int x, int y) {
            if(selectionRect != null) {
                selectedDrawings = stateManager.selectDrawing(selectionRect);
                stateManager.removeDrawing(selectionRect);
                selectionRect = null;
                mode = Mode.SELECT;
            }
        }

        public void mouseDrag(int x, int y) {
            if(mode == Mode.RANGE_SELECT) {
                selectionRect.setLocation(startX, startY);
                selectionRect.setSize(x - startX, y - startY);
            }
            else if(mode == Mode.MOVE) {
                stateManager.moveSelectedDrawing(x - preMouseX, y - preMouseY);
            }
            else if(mode == Mode.RESIZE && resizeStrategy != null) {
                int dx = x - preMouseX;
                int dy = y - preMouseY;
                resizeStrategy.resize(selectedDrawings.get(0), originalBounds, x - startX, y - startY);
            }
            preMouseX = x;
            preMouseY = y;

        }
    }
}