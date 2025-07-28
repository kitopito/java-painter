package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import core.*;

public class LineWidthSpinner extends JSpinner {
    private StateManager stateManager;

    public LineWidthSpinner(StateManager stateManager) {
        super(new SpinnerNumberModel(1, 1, 20, 1));
        this.stateManager = stateManager;
        this.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = (int) getValue();
                stateManager.setLineWidth(value);
            }
        });
        setMaximumSize(getPreferredSize());
    }
}
