package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import core.*;

public class DashList extends JComboBox<String> {
    StateManager stateManager;
    static final String[] dashTypes = {"---", "- - -", "..."};

    public DashList(StateManager stateManager) {
        super(dashTypes);
        this.stateManager = stateManager;
        this.addActionListener(new DashSelectListener());
    }
    
    class DashSelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            int selectedIndex = cb.getSelectedIndex();
            String selectedItem = (String) cb.getSelectedItem();
            switch (selectedIndex) {
                case 0: // "---" 短い破線
                    stateManager.setDashPattern(new float[]{10, 5});
                    break;
                case 1: // "- - -" 長い破線
                    stateManager.setDashPattern(new float[]{15, 10});
                    break;
                case 2: // "..." 点線
                    stateManager.setDashPattern(new float[]{2, 2});
                    break;
            }
        }
    }
    
    public float[] getDashPattern(String dashType) {
        switch (dashType) {
            case "---":
                return new float[]{10, 5};
            case "- - -":
                return new float[]{15, 10};
            case "...":
                return new float[]{2, 2};
            default:
                return new float[]{10, 5};
        }
    }
    public String toString(float[] dashPattern) {
        if (dashPattern[0] == 10 && dashPattern[1] == 5) {
            return "---"; // 短い破線
        } else if (dashPattern[0] == 15 && dashPattern[1] == 10) {
            return "- - -"; // 長い破線
        } else if (dashPattern[0] == 2 && dashPattern[1] == 2) {
            return "..."; // 点線
        }
        return "---"; // デフォルトは短い破線
    }
}