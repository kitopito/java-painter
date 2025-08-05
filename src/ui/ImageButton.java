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
            // PropertyPanelで選択された画像パスを取得
            String imagePath = stateManager.getSelectedImagePath();
            if (imagePath == null || imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "画像を選択してください。", "画像未選択", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                // 開始位置を記録
                startX = x;
                startY = y;
                
                // 選択された画像パスでMyImageを作成
                currentImage = new MyImage(imagePath, x, y);
                
                // キャンバスに描画を追加
                stateManager.addDrawing(currentImage);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "画像の読み込みに失敗しました。", "エラー", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        public void mouseDrag(int x, int y) {
            if (currentImage != null) {
                // ドラッグでサイズを調整（縦横比を保持、反転対応）
                int deltaX = x - startX;
                int deltaY = y - startY;
                
                // 縦横比を取得
                double aspectRatio = currentImage.getAspectRatio();
                
                // より大きな変化量に基づいてサイズを決定
                int width, height;
                if (Math.abs(deltaX) / aspectRatio > Math.abs(deltaY)) {
                    // 横の変化が大きい場合
                    width = Math.abs(deltaX);
                    height = (int) (width / aspectRatio);
                } else {
                    // 縦の変化が大きい場合
                    height = Math.abs(deltaY);
                    width = (int) (height * aspectRatio);
                }
                
                // 最小サイズの制限
                width = Math.max(10, width);
                height = Math.max(10, height);
                
                // 位置の調整（反転に対応）
                int newX, newY;
                if (deltaX < 0) {
                    // 左方向にドラッグ
                    newX = startX - width;
                } else {
                    // 右方向にドラッグ
                    newX = startX;
                }
                
                if (deltaY < 0) {
                    // 上方向にドラッグ
                    newY = startY - height;
                } else {
                    // 下方向にドラッグ
                    newY = startY;
                }
                
                currentImage.setLocation(newX, newY);
                currentImage.setSize(width, height);
                stateManager.getMediator().repaint();
            }
        }
        
        public void mouseUP(int x, int y) {
            // ドラッグ完了
            currentImage = null;
        }
    }
}
