package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import core.*;

public class TextSizeSpinner extends JSpinner {
    private StateManager stateManager;

    public TextSizeSpinner(StateManager stateManager) {
        super(new SpinnerNumberModel(stateManager.getTextSize(), 10, 100, 1));
        this.stateManager = stateManager;
        this.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = (int) getValue();
                stateManager.setTextSize(value);
            }
        });
        setMaximumSize(getPreferredSize());
    }
}
