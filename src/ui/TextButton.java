package ui;

import core.*;
import javax.swing.*;
import java.awt.event.*;
import ui.*;
import drawing.*;

public class TextButton extends JToggleButton {
    private Mediator mediator;
    private StateManager stateManager;
    private PropertyPanel propertyPanel;
    
    public TextButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("文字");
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
        addActionListener(new TextListener());
        //setToolTipText("テキスト入力モード");
    }
    
    class TextListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isSelected()) {
                stateManager.setState(new TextState(stateManager));
                propertyPanel.setMode(PropertyPanel.TEXT_MODE);
            }
        }
    }
    
    public static class TextState implements State, TextInputListener {
        private StateManager stateManager;
        
        public TextState(StateManager stateManager) {
            this.stateManager = stateManager;
        }
        
        public void mouseDown(int x, int y) {
            TextInputBox textInputBox = new TextInputBox(x, y);
            textInputBox.setTextInputListener(this);
            textInputBox.showInputBox();
        }

        public void mouseUP(int x, int y) {
        }

        public void mouseDrag(int x, int y) {
        }

        public void onTextInputCompleted(String text, int x, int y) {
            MyText myText = new MyText(text, x, y);
            stateManager.addDrawing(myText);
        }
        
        public void onTextInputCancelled() {
        }
    }
}
