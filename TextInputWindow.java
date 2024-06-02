import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

class TextInputWindow extends JFrame {

    private WindowCloseCallback callback;
    private JTextField textField;

    public TextInputWindow(final WindowCloseCallback callback) {
        this.callback = callback;
        setTitle("里程輸入");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int frame_x = 550;
        int frame_y = 200;
        setSize(frame_x, frame_y);
        setLayout(null);
        setResizable(false);

        //設定預設字體
        Font basic_font = new Font("微軟正黑體", Font.BOLD, 24);

        //建立Jpanel
        JPanel panel = new JPanel();
        int BorderWidth = 20; // 邊框的內縮寬度
        panel.setBounds(BorderWidth, BorderWidth, (frame_x - 2 * BorderWidth - 5), frame_y - 3 * BorderWidth - 5);
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        add(panel);

        // 創建 JLabel 提示使用者輸入文字
        JLabel label = new JLabel("請輸入目前里程:", JLabel.CENTER);
        label.setFont(basic_font);
        panel.add(label);

        // 創建 JTextField 用於輸入里程
        textField = new JTextField();
        textField.setFont(basic_font);
        panel.add(textField);

        //限制textfield只能輸入數字
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

                } else {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });


        // 創建 JButton 用於提交輸入的文字
        JButton button = new JButton("提交");
        button.setFont(basic_font);

        // 設置按鈕的 ActionListener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                if (!inputText.equals("")) {
                    int result = JOptionPane.showConfirmDialog(TextInputWindow.this, "確認目前里程為:" + inputText + "?", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
//                        System.out.println("副程式內回傳:" + inputText);
                        callback.onWindowClosed(Integer.parseInt(inputText));
                        TextInputWindow.this.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "未輸入里程數", "注意", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel.add(button);

        JButton cancel = new JButton("取消");
        cancel.setFont(basic_font);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextInputWindow.this.dispose();
            }
        });
        panel.add(cancel);
    }
}
