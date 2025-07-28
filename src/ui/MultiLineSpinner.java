package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import core.*;

public class MultiLineSpinner extends JSpinner {
    private StateManager stateManager;

    public MultiLineSpinner(StateManager stateManager) {
        super(new SpinnerNumberModel(1, 1, 10, 1));
        this.stateManager = stateManager;
        this.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = (int) getValue();
                stateManager.setMultiLineCount(value);
            }
        });
        setMaximumSize(getPreferredSize());
    }
}
