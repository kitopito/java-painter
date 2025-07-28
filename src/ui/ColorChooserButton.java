package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import core.StateManager;

public class ColorChooserButton extends JComboBox<Color> {
    private StateManager stateManager;
    private Color selectedColor = Color.BLACK; // Default selected color
    private JComboBox<Color> colorComboBox;
    private String text;
    public static int FILL_MODE = 0;
    public static int LINE_MODE = 1;
    private int mode;
    private boolean ignoreActionEvent = false;

    private static final Color[] colorItems = {
        Color.BLACK,
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.CYAN,
        Color.MAGENTA,
        null // Other Colors
    };

    public ColorChooserButton(StateManager stateManager, int mode) {
        super(colorItems);
        this.stateManager = stateManager;
        this.addActionListener(new ColorSelectListener());
        this.setRenderer(new ColorRenderer());
        colorComboBox = this;
        this.mode = mode;
        this.text = (mode == FILL_MODE) ? "Fill Color" : "Line Color";
    }
    
    class ColorSelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (ignoreActionEvent) return; // Ignore action events if set
            JComboBox<Color> cb = (JComboBox<Color>) e.getSource();
            Color selected = (Color) cb.getSelectedItem();
            System.err.println("Selected color: " + selected);
            if (selected != null) {
                if(mode == FILL_MODE) {
                    stateManager.setFillColor(selected);
                } else if (mode == LINE_MODE) {
                    stateManager.setLineColor(selected);
                }
                selectedColor = selected;
            } else {
                Color chosen = JColorChooser.showDialog(cb, "Choose Color", Color.BLACK);
                System.err.println("Chosen color: " + chosen);
                if (chosen != null) {
                    if(mode == FILL_MODE) {
                        stateManager.setFillColor(chosen);
                    } else if (mode == LINE_MODE) {
                        System.out.println("Setting line color to: " + chosen);
                        stateManager.setLineColor(chosen);
                    }
                }
                cb.setSelectedIndex(0);
                selectedColor = chosen;
            }
        }
    }
    
   class ColorRenderer extends JLabel implements ListCellRenderer<Color> {
        public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index, boolean isSelected, boolean cellHasFocus) {
            setOpaque(true);
            if (value == null) {
                setBackground(Color.WHITE);
                setText("Other Colors");
                setIcon(new ColorIcon(Color.WHITE, 16));
            } else {
                //setBackground(value);
                String hex = String.format("#%02X%02X%02X", value.getRed(), value.getGreen(), value.getBlue());
                setText(hex);
                setIcon(new ColorIcon(value, 16));
            }
            if (isSelected) {
                setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            } else {
                setBorder(null);
            }
            // JComboBoxの非展開時（index == -1）も色とカラーコードを表示
            if (index == -1) {
                System.out.print("index=-1 selected color: " + selectedColor);
                setIcon(new ColorIcon(selectedColor, 16));
                // String hex = String.format("#%02X%02X%02X", 
                //     selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue());
                setText(text);
            }
            return this;
        }
    }

    class ColorIcon implements Icon {
        private final Color color;
        private final int size;
        public ColorIcon(Color color, int size) {
            this.color = color;
            this.size = size;
        }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, size, size);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, size-1, size-1);
        }
        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }
    }
    
    public void setIgnoreActionEvent(boolean ignore) {
        this.ignoreActionEvent = ignore;
    }
}