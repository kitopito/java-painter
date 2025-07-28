package ui;

import javax.swing.*;
import java.awt.event.*;
import core.*;

public class ShadowCheckBox extends JCheckBox {
    private StateManager stateManager;

    public ShadowCheckBox(StateManager stateManager) {
        super("Shadow");
        this.stateManager = stateManager;
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateManager.setShadowEnabled(isSelected());
            }
        });
    }
}
