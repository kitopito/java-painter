package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.StateManager;

public class DashCheckBox extends JCheckBox {
    StateManager stateManager;

    public DashCheckBox(StateManager stateManager) {
        super("dash");
        addItemListener(new DashListener());
        this.stateManager = stateManager;
    }
    
    class DashListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                stateManager.setIsDashed(true);
            } else {
                stateManager.setIsDashed(false);
            }
        }
    }
}