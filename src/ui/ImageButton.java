package ui;

import java.awt.event.*;
import javax.swing.*;
import core.*;
import drawing.*;

public class ImageButton extends JToggleButton {
    StateManager stateManager;
    PropertyPanel propertyPanel;

    public ImageButton(StateManager stateManager, PropertyPanel propertyPanel) {
        super("画像");
        addActionListener(new ImageListener());
        this.stateManager = stateManager;
        this.propertyPanel = propertyPanel;
    }
    
    class ImageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(isSelected()) {
                stateManager.setState(new ImageState(stateManager));
                propertyPanel.setMode(PropertyPanel.IMAGE_MODE); // 画像専用モードを使用
            }
        }
    }
    
    class ImageState implements State {
        StateManager stateManager;
        MyImage currentImage;
        int startX, startY;
        
        public ImageState(StateManager sm) {
            this.stateManager = sm;
        }

        public void mouseDown(int x, int y) {
            String imagePath = stateManager.getSelectedImagePath();
            if (imagePath == null || imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "画像を選択してください。", "画像未選択", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            startX = x;
            startY = y;
            
            currentImage = new MyImage(imagePath, x, y);
            stateManager.addDrawing(currentImage);
        }

        public void mouseDrag(int x, int y) {
            if (currentImage != null) {
                int deltaX = x - startX;
                int deltaY = y - startY;
                
                double aspectRatio = currentImage.getAspectRatio();
                
                int width, height;
                if (Math.abs(deltaX) / aspectRatio > Math.abs(deltaY)) {
                    width = Math.abs(deltaX);
                    height = (int) (width / aspectRatio);
                } else {
                    height = Math.abs(deltaY);
                    width = (int) (height * aspectRatio);
                }
                
                width = Math.max(10, width);
                height = Math.max(10, height);
                
                int newX, newY;
                if (deltaX < 0) {
                    newX = startX - width;
                } else {
                    newX = startX;
                }
                
                if (deltaY < 0) {
                    newY = startY - height;
                } else {
                    newY = startY;
                }
                
                currentImage.setLocation(newX, newY);
                currentImage.setSize(width, height);
                stateManager.getMediator().repaint();
            }
        }
        
        public void mouseUP(int x, int y) {
            currentImage = null;
        }
    }
}
