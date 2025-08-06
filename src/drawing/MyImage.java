package drawing;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.MyDashStroke;

public class MyImage extends MyDrawing {
    private transient BufferedImage image;  // transientでシリアライゼーション対象外
    private String imagePath;
    
    public MyImage(String imagePath, int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        super(x, y, w, h, lineColor, fillColor, lineWidth);
        this.imagePath = imagePath;
        loadImage();
    }
    
    public MyImage(String imagePath, int x, int y, int w, int h) {
        super(x, y, w, h, Color.black, Color.white, 1);
        this.imagePath = imagePath;
        loadImage();
    }
    
    public MyImage(String imagePath, int x, int y) {
        super();
        this.imagePath = imagePath;
        setLocation(x, y);
        loadImage();
        if (image != null) {
            setSize(image.getWidth(), image.getHeight());
        }
    }
    
    private void loadImage() {
        try {
            if (imagePath != null) {
                image = ImageIO.read(new File(imagePath));
            }
        } catch (IOException e) {
            System.err.println("画像の読み込みに失敗しました: " + imagePath);
            e.printStackTrace();
        }
    }
    
    // シリアライゼーション後に画像を再読み込み
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        loadImage();
    }
    
    // 元の画像サイズを取得するメソッド
    public int getOriginalWidth() {
        return image != null ? image.getWidth() : 100;
    }
    
    public int getOriginalHeight() {
        return image != null ? image.getHeight() : 100;
    }
    
    public double getAspectRatio() {
        if (image != null) {
            return (double) image.getWidth() / image.getHeight();
        }
        return 1.0; // デフォルトは正方形
    }
    
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();
        
        Graphics2D g2 = (Graphics2D) g;
        
        if (getHasShadow()) {
            int shadowX = x + 5;
            int shadowY = y + 5;
            g2.setColor(Color.black);
            g2.fillRect(shadowX, shadowY, w, h);
        }
        
        if (image != null) {
            g2.drawImage(image, x, y, w, h, null);
        } else {
            g2.setColor(Color.lightGray);
            g2.fillRect(x, y, w, h);
            g2.setColor(Color.black);
            g2.drawString("画像なし", x + 5, y + 15);
        }
        drawHandles(g2);
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        loadImage();
    }
    
    public BufferedImage getImage() {
        return image;
    }
}
