package ui;

import java.awt.event.*;
import javax.swing.*;
import core.*;
import drawing.*;

public class LineButton extends JToggleButton {
    StateManager stateManager;
    PropertyPanel propertyPanel;

    public LineButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("直線");
        addActionListener(new LineListener());
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
    }

    class LineListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isSelected()) {
                stateManager.setState(new LineState(stateManager));
                propertyPanel.setMode(PropertyPanel.LINE_MODE);
            }
        }
    }

    class LineState implements State {
        StateManager stateManager;
        int startX, startY;
        Myline currentLine;

        public LineState(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void mouseDown(int x, int y) {
            currentLine = new Myline(x, y, x, y);
            stateManager.addDrawing(currentLine);
            this.startX = x;
            this.startY = y;
        }

        public void mouseUP(int x, int y) {
            // Handle mouse up event
        }

        public void mouseDrag(int x, int y) {
            currentLine.setEndPoint(x, y);
        }
    }
}
