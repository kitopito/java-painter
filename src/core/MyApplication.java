package core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import drawing.*;
import ui.*;
import util.*;

public class MyApplication extends JFrame {
    public MyApplication() {
        super("My Painter");

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        this.getContentPane().add(jp);

        MyCanvas canvas = new MyCanvas();
        canvas.setFocusable(true);
        jp.add(BorderLayout.CENTER, canvas);

        StateManager stateManager = new StateManager(canvas);
        
        ShadowCheckBox shadowCheckbox = new ShadowCheckBox(stateManager);
        ColorChooserButton fillColorChooser = new ColorChooserButton(stateManager, ColorChooserButton.FILL_MODE);
        ColorChooserButton lineColorChooser = new ColorChooserButton(stateManager, ColorChooserButton.LINE_MODE);
        DashCheckBox dashCheckBox = new DashCheckBox(stateManager);
        DashList dashList = new DashList(stateManager);
        LineWidthSpinner lineWidthSpinner = new LineWidthSpinner(stateManager);

        PropertyPanel propertyPanel = new PropertyPanel(stateManager);
        jp.add(BorderLayout.NORTH, propertyPanel);

        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.Y_AXIS));
        jp.add(BorderLayout.WEST, shapePanel);

        RectButton rectButton = new RectButton(stateManager, propertyPanel);
        shapePanel.add(rectButton);
        OvalButton ovalButton = new OvalButton(stateManager, propertyPanel);
        shapePanel.add(ovalButton);
        HendecagonalButton henButton = new HendecagonalButton(stateManager, propertyPanel);
        shapePanel.add(henButton);
        LineButton lineButton = new LineButton(stateManager, propertyPanel);
        shapePanel.add(lineButton);
        SelectButton selectButton = new SelectButton(stateManager, propertyPanel);
        shapePanel.add(selectButton);
        
        ButtonGroup shapeButtonGroup = new ButtonGroup();
        shapeButtonGroup.add(rectButton);
        shapeButtonGroup.add(ovalButton);
        shapeButtonGroup.add(henButton);
        shapeButtonGroup.add(lineButton);
        shapeButtonGroup.add(selectButton);

        //JPanel linePanel = new JPanel();
        //linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.Y_AXIS));
        //jp.add(BorderLayout.EAST, linePanel);
        //linePanel.add(dashCheckBox);
        //linePanel.add(new DashList(stateManager));
        //linePanel.add(new JLabel("line width:"));
        //linePanel.add(lineWidthSpinner);
        // linePanel.add(new JLabel("multi line:"));
        // linePanel.add(new MultiLineSpinner(stateManager));
        
        MyMenu menu = new MyMenu(canvas.getMediator(), stateManager);
        this.setJMenuBar(menu);

        stateManager.addSelectionListener(new SelectionListener() {
            public void onSelectionChanged(DrawingComponent selectedDrawing) {
                System.out.println("*************************************");
                System.out.println("fill color" + selectedDrawing.getFillColor());
                System.out.println("line color" + selectedDrawing.getLineColor());
                System.out.println("line width " + selectedDrawing.getLineWidth());
                System.out.println("shadow " + selectedDrawing.getHasShadow());
                System.out.println("dashed " + selectedDrawing.getIsDashed());
                System.out.println("*************************************");
                shadowCheckbox.setSelected(selectedDrawing.getHasShadow());
                dashCheckBox.setSelected(selectedDrawing.getIsDashed());
                dashList.setSelectedItem(dashList.toString(selectedDrawing.getDashPattern()));

                fillColorChooser.setIgnoreActionEvent(true);
                lineColorChooser.setIgnoreActionEvent(true);
                fillColorChooser.setSelectedItem(selectedDrawing.getFillColor());
                lineColorChooser.setSelectedItem(selectedDrawing.getLineColor());
                fillColorChooser.setIgnoreActionEvent(false);
                lineColorChooser.setIgnoreActionEvent(false);

                lineWidthSpinner.setValue(selectedDrawing.getLineWidth());
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                canvas.requestFocusInWindow();
                stateManager.mouseDown(e.getX(), e.getY());
            }
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouse released");
                canvas.requestFocusInWindow();
                stateManager.mouseUP(e.getX(), e.getY());
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                canvas.requestFocusInWindow();
                stateManager.mouseDrag(e.getX(), e.getY());
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    stateManager.deleteKeyPressed();
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    stateManager.copyKeyPressed();
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
                    stateManager.cutKeyPressed();
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    stateManager.pasteKeyPressed();
                }
            }
        });

        this.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(1);
                    }
                });
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.setSize(800, 500);
        app.setVisible(true);
    }
}