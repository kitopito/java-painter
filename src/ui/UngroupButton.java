package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.*;

public class UngroupButton extends JButton {
    private StateManager stateManager;

    public UngroupButton(StateManager stateManager) {
        super("グループ解除");
        this.stateManager = stateManager;
        //setToolTipText("選択した図形をグループ化します");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateManager.ungroupSelectedDrawings();
                System.out.println("図形をグループ解除しました");
            }
        });
    }
}
