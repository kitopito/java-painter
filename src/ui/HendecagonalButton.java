package ui;

import java.awt.event.*;
import javax.swing.*;
import drawing.*;
import core.*;
import ui.*;
import util.*;

public class HendecagonalButton extends JToggleButton {
    StateManager stateManager;
    PropertyPanel propertyPanel;

    public HendecagonalButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("11角");
        addActionListener(new HendecagonalListener());
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
    }

    class HendecagonalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isSelected()) {
                stateManager.setState(new HendecagonalState(stateManager));
                propertyPanel.setMode(PropertyPanel.HEN_MODE);
            }
        }
    }

    class HendecagonalState implements State {
        StateManager stateManager;
        int startX, startY;
        MyHendecagonal currentHendecagonal;

        public HendecagonalState(StateManager stateManager) {
            this.stateManager = stateManager;
        }

        public void mouseDown(int x, int y) {
            currentHendecagonal = new MyHendecagonal(x, y, 0, 0);
            stateManager.addDrawing(currentHendecagonal);
            this.startX = x;
            this.startY = y;
        }

        public void mouseUP(int x, int y) {
            // Handle mouse up event
        }

        public void mouseDrag(int x, int y) {
            currentHendecagonal.setLocation(startX, startY);
            currentHendecagonal.setSize(x - startX, y - startY);
        }
    }
}
