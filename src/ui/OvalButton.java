package ui;

import java.awt.event.*;
import javax.swing.*;
import core.*;
import drawing.*;
import util.*;

public class OvalButton extends JToggleButton {
    StateManager stateManager;
    PropertyPanel propertyPanel;

    public OvalButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("楕円");
        addActionListener(new OvalListener());
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
    }

    class OvalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isSelected()) {
                stateManager.setState(new OvalState(stateManager));
                propertyPanel.setMode(PropertyPanel.OVAL_MODE);
            }
        }
    }
    
    class OvalState implements State {
        StateManager stateManager;
        int startX, startY;
        MyOval currentOval;

        public OvalState(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void mouseDown(int x, int y) {
            currentOval = new MyOval(x, y, 0, 0);
            stateManager.addDrawing(currentOval);
            this.startX = x;
            this.startY = y;
        }

        public void mouseUP(int x, int y) {
            // Handle mouse up event if needed
        }

        public void mouseDrag(int x, int y) {
            currentOval.setLocation(startX, startY);
            currentOval.setSize(x - startX, y - startY);
        }
    }
}
