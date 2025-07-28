package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.print.*;
import core.*;
import util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class MyMenu extends JMenuBar {
    private Mediator mediator;
    private StateManager stateManager;
    private JFileChooser fc = new JFileChooser();

    public MyMenu(Mediator mediator, StateManager stateManager) {
        this.mediator = mediator;
        this.stateManager = stateManager;

        JMenu fileMenu = new JMenu("ファイル");
        JMenuItem saveItem = new JMenuItem("保存");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    mediator.saveToFile(file);
                    System.out.println("ファイルに保存しました。");
                }
            }
        });
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("読み込み");
        loadItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    mediator.loadFromFile(file);
                    System.out.println("ファイルを読み込みました。");
                }
            }
        });
        fileMenu.add(loadItem);
        
        JMenu printItem = new JMenu("印刷");
        JMenuItem pdfItem = new JMenuItem("PDF");
        pdfItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new MyPagePrinter(mediator.getCanvas()));
                if(job.printDialog()) {
                    try {
                        job.print();
                    } catch (PrinterException ex) {
                        System.err.println("印刷中にエラーが発生しました: " + ex.getMessage());
                    }
                }
            }
        });
        printItem.add(pdfItem);
        
        JMenuItem pngItem = new JMenuItem("PNG");
        pngItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.getName().endsWith(".png") == false) {
                        file = new File(file.getAbsolutePath() + ".png");
                    }
                    saveCanvasAsImage(file, "png");
                    System.out.println("PNGファイルに保存しました。");
                }
            }
        });
        printItem.add(pngItem);
        
        JMenuItem jpgItem = new JMenuItem("JPG");
        jpgItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.getName().endsWith(".jpg") == false) {
                        file = new File(file.getAbsolutePath() + ".jpg");
                    }
                    saveCanvasAsImage(file, "jpg");
                    System.out.println("JPGファイルに保存しました。");
                }
            }
        });
        printItem.add(jpgItem);

        add(fileMenu);
        add(printItem);
    }

    private void saveCanvasAsImage(File file, String format) {
        try {
            MyCanvas canvas = mediator.getCanvas();
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            
            canvas.paint(g2d);
            g2d.dispose();
            
            ImageIO.write(image, format, file);
            
        } catch (Exception ex) {
            System.err.println("画像保存中にエラーが発生しました: " + ex.getMessage());
        }
    }
}