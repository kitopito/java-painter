package ui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TextInputBox extends JFrame
{
    JTextField textField = new JTextField(10);
    private int x, y;
    private int offsetX = 40;
    private int offsetY = 30;
    TextInputListener listener = null;

    TextInputBox(int x, int y) {
        setTitle("Text Input");
        setSize(300, 120);
        this.x = x + offsetX;
        this.y = y + offsetY;
        setLocation(x, y);

        getContentPane().add(textField);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    String inputText = textField.getText();
                    if (inputText.isEmpty() == false) {
                        listener.onTextInputCompleted(inputText, x, y);
                    } else {
                        listener.onTextInputCancelled();
                    }
                }
                dispose();
            }
        });
    }

    public void showInputBox() {
        textField.setText("");
        setVisible(true);
        textField.requestFocus();
    }
    
    public void setTextInputListener(TextInputListener listener) {
        this.listener = listener;
    }
    
    public void dispose() {
        super.dispose();
    }
}