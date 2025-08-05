package core;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import drawing.*;
import ui.*;

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
        TextButton textButton = new TextButton(stateManager, propertyPanel);
        shapePanel.add(textButton);
        ImageButton imageButton = new ImageButton(stateManager, propertyPanel);
        shapePanel.add(imageButton);
        SelectButton selectButton = new SelectButton(stateManager, propertyPanel);
        shapePanel.add(selectButton);
        
        ButtonGroup shapeButtonGroup = new ButtonGroup();
        shapeButtonGroup.add(rectButton);
        shapeButtonGroup.add(ovalButton);
        shapeButtonGroup.add(henButton);
        shapeButtonGroup.add(lineButton);
        shapeButtonGroup.add(textButton);
        shapeButtonGroup.add(selectButton);
        shapeButtonGroup.add(imageButton);

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

        canvas.addMouseListener(new CanvasMouseListener(canvas, stateManager));

        canvas.addMouseMotionListener(new CanvasMouseMotionListener(canvas, stateManager));
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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    canvas.getZoomManager().resetZoom();
                    canvas.repaint();
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
    
    class CanvasMouseListener extends MouseAdapter {
        private MyCanvas canvas;
        private StateManager stateManager;
        private ZoomManager zoomManager;
        private int logicalX, logicalY;

        CanvasMouseListener(MyCanvas canvas, StateManager stateManager) {
            this.canvas = canvas;
            this.stateManager = stateManager;
            this.zoomManager = canvas.getZoomManager();
        }

        public void mousePressed(MouseEvent e) {
            // DPI診断情報を追加
            GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
            AffineTransform defaultTransform = gc.getDefaultTransform();
            System.out.println("DPI Scale X: " + defaultTransform.getScaleX());
            System.out.println("DPI Scale Y: " + defaultTransform.getScaleY());
            System.out.println("DPI Translate : " + defaultTransform.getTranslateX() + ", " + defaultTransform.getTranslateY());
            System.out.println("Canvas size: " + canvas.getWidth() + "x" + canvas.getHeight());
            System.out.println("Canvas bounds: " + canvas.getBounds());
            
            // マウス座標の詳細確認
            Point canvasLocationOnScreen = canvas.getLocationOnScreen();
            Point frameLocationOnScreen = MyApplication.this.getLocationOnScreen();
            System.out.println("Canvas location on screen: " + canvasLocationOnScreen);
            System.out.println("Frame location on screen: " + frameLocationOnScreen);
            System.out.println("Mouse event source: " + e.getSource().getClass().getName());
            
            Point logicalPoint = zoomManager.getTransformedCoordinates(e.getX(), e.getY(), canvas);
            logicalX = logicalPoint.x;
            logicalY = logicalPoint.y;
            System.out.println("Logical coordinates: " + logicalX + ", " + logicalY);
            System.out.println("screen coordinates: " + e.getX() + ", " + e.getY());
            System.out.println("transformed : " + zoomManager.getTranslateX() + ", " + zoomManager.getTranslateY());
            canvas.requestFocusInWindow();
            if(SwingUtilities.isRightMouseButton(e) == false) {
                stateManager.mouseDown(logicalX, logicalY);
            } else {
                MyRectangle zoomRect = zoomManager.getZoomRect();
                zoomRect.setLocation(logicalX, logicalY);
                zoomRect.setSize(0, 0);
                zoomManager.setDragStart(logicalX, logicalY);
                canvas.getMediator().addDrawing(zoomRect);
                canvas.repaint();
                System.out.println("Zoom rectangle created at: " + zoomRect.getX() + ", " + zoomRect.getY());
            }
        }

        public void mouseReleased(MouseEvent e) {
            Point logicalPoint = zoomManager.getTransformedCoordinates(e.getX(), e.getY(), canvas);
            logicalX = logicalPoint.x;
            logicalY = logicalPoint.y;

            canvas.requestFocusInWindow();
            if(SwingUtilities.isRightMouseButton(e) == false) {
                System.out.println("mouse released");
                stateManager.mouseUP(logicalX, logicalY);
            } else {
                MyRectangle zoomRect = zoomManager.getZoomRect();
                System.out.println("Zoom rectangle color: " + zoomRect.getFillColor());
                canvas.getMediator().removeDrawing(zoomRect);
                zoomManager.zoomToRectangle(canvas.getWidth(), canvas.getHeight());
                canvas.repaint();
                System.out.println("Zoomed to rectangle: " + zoomRect.getX() + ", " + zoomRect.getY() + ", " + zoomRect.getW() + ", " + zoomRect.getH());
            }
        }
        
        // private void calcLogicalCoordinates(MouseEvent e) {
        //     AffineTransform transform = zoomManager.getTransform();
        //     try {
        //         Point2D logicalPoint = transform.inverseTransform(new Point2D.Double(e.getX(), e.getY()), null);
        //         logicalX = (int) logicalPoint.getX();
        //         logicalY = (int) logicalPoint.getY();
        //     } catch (NoninvertibleTransformException ex) {
        //         System.err.println("Error calculating logical coordinates: " + ex.getMessage());
        //     }
        // }
    }
    
    class CanvasMouseMotionListener extends MouseMotionAdapter {
        private MyCanvas canvas;
        private StateManager stateManager;
        private ZoomManager zoomManager;

        CanvasMouseMotionListener(MyCanvas canvas, StateManager stateManager) {
            this.canvas = canvas;
            this.stateManager = stateManager;
            this.zoomManager = canvas.getZoomManager();
        }

        public void mouseDragged(MouseEvent e) {
            int logicalX = zoomManager.getTransformedCoordinates(e.getX(), e.getY(), canvas).x;
            int logicalY = zoomManager.getTransformedCoordinates(e.getX(), e.getY(), canvas).y;
            canvas.requestFocusInWindow();
            if(SwingUtilities.isRightMouseButton(e) == false) {
                stateManager.mouseDrag(logicalX, logicalY);
            } else {
                MyRectangle zoomRect = zoomManager.getZoomRect();
                zoomRect.setLocation(zoomManager.getDragStartX(), zoomManager.getDragStartY());
                zoomRect.setSize(logicalX - zoomManager.getDragStartX(), logicalY - zoomManager.getDragStartY());
                canvas.repaint();
            }
        }
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.setSize(800, 500);
        app.setVisible(true);
    }
}