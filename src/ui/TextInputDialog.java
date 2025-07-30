package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// public class TextInputDialog extends JDialog {
//     private JTextField textField;
//     private JButton okButton;
//     private JButton cancelButton;
//     private TextInputListener listener;
//     private int targetX, targetY;
//     private String inputText = null;
    
//     public TextInputDialog(JFrame parent, TextInputListener listener, int x, int y) {
//         super(parent, "テキスト入力", true); // モーダルダイアログ
//         this.listener = listener;
//         this.targetX = x;
//         this.targetY = y;
        
//         initializeComponents();
//         setupLayout();
//         setupEventHandlers();
        
//         // ダイアログの位置を設定（クリック位置の近く）
//         setLocationRelative(parent);
//         Point parentLocation = parent.getLocationOnScreen();
//         setLocation(parentLocation.x + x + 10, parentLocation.y + y + 30);
        
//         // フォーカスをテキストフィールドに
//         SwingUtilities.invokeLater(() -> textField.requestFocus());
//     }
    
//     private void initializeComponents() {
//         textField = new JTextField(20);
//         okButton = new JButton("OK");
//         cancelButton = new JButton("キャンセル");
        
//         // ダイアログの基本設定
//         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//         setResizable(false);
//     }
    
//     private void setupLayout() {
//         setLayout(new BorderLayout());
        
//         // テキストフィールドパネル
//         JPanel textPanel = new JPanel(new FlowLayout());
//         textPanel.add(new JLabel("テキスト:"));
//         textPanel.add(textField);
        
//         // ボタンパネル
//         JPanel buttonPanel = new JPanel(new FlowLayout());
//         buttonPanel.add(okButton);
//         buttonPanel.add(cancelButton);
        
//         add(textPanel, BorderLayout.CENTER);
//         add(buttonPanel, BorderLayout.SOUTH);
        
//         pack();
//     }
    
//     private void setupEventHandlers() {
//         // OKボタンのアクション
//         ActionListener okAction = e -> handleOK();
//         okButton.addActionListener(okAction);
//         textField.addActionListener(okAction); // Enterキーでも実行
        
//         // キャンセルボタンのアクション
//         ActionListener cancelAction = e -> handleCancel();
//         cancelButton.addActionListener(cancelAction);
        
//         // ESCキーでキャンセル
//         getRootPane().registerKeyboardAction(
//             cancelAction,
//             KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
//             JComponent.WHEN_IN_FOCUSED_WINDOW
//         );
        
//         // ウィンドウクローズ時の処理
//         addWindowListener(new WindowAdapter() {
//             @Override
//             public void windowClosing(WindowEvent e) {
//                 handleCancel();
//             }
//         });
//     }
    
//     private void handleOK() {
//         inputText = textField.getText().trim();
//         if (!inputText.isEmpty()) {
//             if (listener != null) {
//                 listener.onTextInputCompleted(inputText, targetX, targetY);
//             }
//         } else {
//             if (listener != null) {
//                 listener.onTextInputCancelled();
//             }
//         }
//         dispose();
//     }
    
//     private void handleCancel() {
//         if (listener != null) {
//             listener.onTextInputCancelled();
//         }
//         dispose();
//     }
    
//     /**
//      * 入力されたテキストを取得（同期的な使用の場合）
//      */
//     public String getInputText() {
//         return inputText;
//     }
    
//     /**
//      * フォント選択機能付きの拡張版ダイアログを表示
//      */
//     public static class Extended extends TextInputDialog {
//         private JComboBox<String> fontComboBox;
//         private JSpinner fontSizeSpinner;
//         private JCheckBox boldCheckBox;
//         private JCheckBox italicCheckBox;
        
//         public Extended(JFrame parent, TextInputListener listener, int x, int y) {
//             super(parent, listener, x, y);
//             setTitle("テキスト入力 (詳細)");
//         }
        
//         @Override
//         protected void initializeComponents() {
//             super.initializeComponents();
            
//             // フォント関連のコンポーネント
//             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//             String[] fontNames = ge.getAvailableFontFamilyNames();
//             fontComboBox = new JComboBox<>(fontNames);
//             fontComboBox.setSelectedItem("SansSerif");
            
//             fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 72, 1));
//             boldCheckBox = new JCheckBox("太字");
//             italicCheckBox = new JCheckBox("斜体");
//         }
        
//         @Override
//         protected void setupLayout() {
//             setLayout(new BorderLayout());
            
//             // テキスト入力パネル
//             JPanel textPanel = new JPanel(new FlowLayout());
//             textPanel.add(new JLabel("テキスト:"));
//             textPanel.add(textField);
            
//             // フォント設定パネル
//             JPanel fontPanel = new JPanel(new GridLayout(2, 2, 5, 5));
//             fontPanel.setBorder(BorderFactory.createTitledBorder("フォント設定"));
//             fontPanel.add(new JLabel("フォント:"));
//             fontPanel.add(fontComboBox);
//             fontPanel.add(new JLabel("サイズ:"));
//             fontPanel.add(fontSizeSpinner);
            
//             JPanel stylePanel = new JPanel(new FlowLayout());
//             stylePanel.add(boldCheckBox);
//             stylePanel.add(italicCheckBox);
            
//             // ボタンパネル
//             JPanel buttonPanel = new JPanel(new FlowLayout());
//             buttonPanel.add(okButton);
//             buttonPanel.add(cancelButton);
            
//             JPanel centerPanel = new JPanel(new BorderLayout());
//             centerPanel.add(textPanel, BorderLayout.NORTH);
//             centerPanel.add(fontPanel, BorderLayout.CENTER);
//             centerPanel.add(stylePanel, BorderLayout.SOUTH);
            
//             add(centerPanel, BorderLayout.CENTER);
//             add(buttonPanel, BorderLayout.SOUTH);
            
//             pack();
//         }
        
//         public Font getSelectedFont() {
//             String fontName = (String) fontComboBox.getSelectedItem();
//             int fontSize = (Integer) fontSizeSpinner.getValue();
//             int style = Font.PLAIN;
//             if (boldCheckBox.isSelected()) style |= Font.BOLD;
//             if (italicCheckBox.isSelected()) style |= Font.ITALIC;
            
//             return new Font(fontName, style, fontSize);
//         }
//     }
// }
