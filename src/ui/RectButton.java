package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.*;
import drawing.*;

public class RectButton extends JToggleButton {
    StateManager stateManager;
    PropertyPanel propertyPanel;

    public RectButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("四角");
        addActionListener(new RectListener());
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
    }
    
    class RectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isSelected()) {
                stateManager.setState(new RectState(stateManager));
                propertyPanel.setMode(PropertyPanel.RECT_MODE);
            }
        }
    }
    
    class RectState implements State {
        StateManager stateManager;
        int startX, startY;
        MyDrawing currentRectangle;
        
        public RectState(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void mouseDown(int x, int y) {
            currentRectangle = new MyRectangle(x, y, 0, 0);
            stateManager.addDrawing(currentRectangle);
            this.startX = x;
            this.startY = y;
        }
        
        public void mouseUP(int x, int y) {
            // Handle mouse up event
        }
        public void mouseDrag(int x, int y) {
            currentRectangle.setLocation(startX, startY);
            currentRectangle.setSize(x - startX, y - startY);
        }
    }
}