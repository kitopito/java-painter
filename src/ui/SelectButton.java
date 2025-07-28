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
    
    class SelectState implements State {
        StateManager stateManager;
        int preMouseX, preMouseY;
        int startX, startY;
        MyDrawing selectionRect = null;
        boolean isRangeSelection = false;
        
        public SelectState(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void mouseDown(int x, int y) {
            Vector<DrawingComponent> selected = stateManager.selectDrawing(x, y);
            isRangeSelection = (selected == null || selected.isEmpty());
            if(isRangeSelection) {
                selectionRect = new MyRectangle(x, y, 0, 0);
                selectionRect.setFillColor(new Color(0, 0, 0, 0));
                selectionRect.setIsDashed(true);
                stateManager.addSelectionRect(selectionRect);
            }

            startX = x;
            startY = y;
            preMouseX = x;
            preMouseY = y;
        }
        
        public void mouseUP(int x, int y) {
            if(selectionRect != null) {
                stateManager.selectDrawing(selectionRect);
                stateManager.removeDrawing(selectionRect);
                selectionRect = null;
                isRangeSelection = false;
            }
        }

        public void mouseDrag(int x, int y) {
            if(isRangeSelection) {
                selectionRect.setLocation(startX, startY);
                selectionRect.setSize(x - startX, y - startY);
            }
            else {
                stateManager.moveSelectedDrawing(x - preMouseX, y - preMouseY);
                preMouseX = x;
                preMouseY = y;
            }

        }
    }
}