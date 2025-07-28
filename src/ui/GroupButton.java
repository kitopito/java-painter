package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.*;

public class GroupButton extends JButton {
    private StateManager stateManager;

    public GroupButton(StateManager stateManager) {
        super("グループ化");
        this.stateManager = stateManager;
        //setToolTipText("選択した図形をグループ化します");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateManager.groupSelectedDrawings();
                System.out.println("図形をグループ化しました");
            }
        });
    }
}