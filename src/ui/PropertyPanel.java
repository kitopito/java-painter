package ui;

import java.awt.*;
import javax.swing.*;

import org.w3c.dom.Text;

import java.util.Vector;
import core.*;

public class PropertyPanel extends JPanel {
    private StateManager stateManager;
    private CardLayout cardLayout;
    
    public static final String DRAWING_MODE = "DrawingMode";
    public static final String RECT_MODE = "RectMode";
    public static final String OVAL_MODE = "OvalMode";
    public static final String HEN_MODE = "HendecagonalMode";
    public static final String LINE_MODE = "LineMode";
    public static final String SELECT_MODE = "SelectMode";
    public static final String TEXT_MODE = "TextMode";

    JPanel rectPanel = new JPanel();
    JPanel ovalPanel = new JPanel();
    JPanel henPanel = new JPanel();
    JPanel linePanel = new JPanel();
    JPanel selectPanel = new JPanel();
    JPanel textPanel = new JPanel();

    ShadowCheckBox shadowCheckBox;
    ColorChooserButton fillColorChooser;
    ColorChooserButton lineColorChooser;
    DashCheckBox dashCheckBox;
    DashList dashList;
    LineWidthSpinner lineWidthSpinner;
    TextSizeSpinner textSizeSpinner;

    public PropertyPanel(StateManager stateManager) {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        this.stateManager = stateManager;
        
        shadowCheckBox = new ShadowCheckBox(stateManager);
        fillColorChooser = new ColorChooserButton(stateManager, ColorChooserButton.FILL_MODE);
        lineColorChooser = new ColorChooserButton(stateManager, ColorChooserButton.LINE_MODE);
        dashCheckBox = new DashCheckBox(stateManager);
        dashList = new DashList(stateManager);
        lineWidthSpinner = new LineWidthSpinner(stateManager);
        textSizeSpinner = new TextSizeSpinner(stateManager);

        createRectPanel();
        this.add(rectPanel, RECT_MODE);
        this.add(ovalPanel, OVAL_MODE);
        this.add(henPanel, HEN_MODE);
        this.add(linePanel, LINE_MODE);
        this.add(selectPanel, SELECT_MODE);
        this.add(textPanel, TEXT_MODE);
    }
    
    public void setMode(String mode) {
        System.out.println("Setting mode to: " + mode);
        switch (mode) {
            case RECT_MODE:
                createRectPanel();
                break;
            case OVAL_MODE:
                createOvalPanel();
                break;
            case HEN_MODE:
                createHenPanel();
                break;
            case LINE_MODE:
                createLinePanel();
                break;
            case SELECT_MODE:
                createSelectPanel();
                break;
            case TEXT_MODE:
                createTextPanel();
                break;
            default:
                break;
        }
        cardLayout.show(this, mode);
    }
    
    private void createRectPanel() {
        rectPanel.removeAll();
        rectPanel.setLayout(new FlowLayout());
        rectPanel.add(shadowCheckBox);
        rectPanel.add(fillColorChooser);
        rectPanel.add(lineColorChooser);
        rectPanel.add(dashCheckBox);
        rectPanel.add(dashList);
        rectPanel.add(new JLabel("line width:"));
        rectPanel.add(lineWidthSpinner);
    }
    
    private void createOvalPanel() {
        ovalPanel.removeAll();
        ovalPanel.setLayout(new FlowLayout());
        ovalPanel.add(shadowCheckBox);
        ovalPanel.add(fillColorChooser);
        ovalPanel.add(lineColorChooser);
        ovalPanel.add(dashCheckBox);
        ovalPanel.add(dashList);
        ovalPanel.add(new JLabel("line width:"));
        ovalPanel.add(lineWidthSpinner);
    }

    private void createHenPanel() {
        henPanel.removeAll();
        henPanel.setLayout(new FlowLayout());
        henPanel.add(shadowCheckBox);
        henPanel.add(fillColorChooser);
        henPanel.add(lineColorChooser);
        henPanel.add(dashCheckBox);
        henPanel.add(dashList);
        henPanel.add(new JLabel("line width:"));
        henPanel.add(lineWidthSpinner);
    }

    private void createLinePanel() {
        linePanel.removeAll();
        linePanel.setLayout(new FlowLayout());
        linePanel.add(shadowCheckBox);
        linePanel.add(lineColorChooser);
        linePanel.add(dashCheckBox);
        linePanel.add(dashList);
        linePanel.add(new JLabel("line width:"));
        linePanel.add(lineWidthSpinner);
    }

    private void createSelectPanel() {
        selectPanel.removeAll();
        selectPanel.setLayout(new FlowLayout());
        selectPanel.add(shadowCheckBox);
        selectPanel.add(fillColorChooser);
        selectPanel.add(lineColorChooser);
        selectPanel.add(dashCheckBox);
        selectPanel.add(dashList);
        selectPanel.add(new JLabel("line width:"));
        selectPanel.add(lineWidthSpinner);
        selectPanel.add(new GroupButton(stateManager));
        selectPanel.add(new UngroupButton(stateManager));
        selectPanel.add(new JLabel("Text Size:"));
        selectPanel.add(textSizeSpinner);
    }
    
    public void createTextPanel() {
        textPanel.removeAll();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(lineColorChooser);
        textPanel.add(new JLabel("Text Size:"));
        textPanel.add(textSizeSpinner);
    }
}