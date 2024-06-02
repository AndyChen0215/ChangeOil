import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Create_log extends JFrame {
    private WindowCloseCallback callback;

    String defNumber = "";
    final JTextField Number = new JTextField();

    public Create_log(final WindowCloseCallback callback) {
        this.callback = callback;
        final JFrame frame = new JFrame("新增保養紀錄");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int frame_x = 1024;
        int frame_y = 500;
        frame.setSize(frame_x, frame_y);
        frame.setLayout(null);
        frame.setResizable(false);

        // 創建Jpanel
        JPanel mainPanel = new JPanel();
        int BorderWidth = 20; // 邊框的內縮寬度
        int Gap_X = 30; //物件與邊界的x距離
        int Gap_Y = 30;
        mainPanel.setBounds(BorderWidth, BorderWidth, (frame_x - 2 * BorderWidth - 5), frame_y - 3 * BorderWidth - 5);
        mainPanel.setBorder(new LineBorder(Color.BLACK)); // 設置黑色邊框
        mainPanel.setLayout(null); // 設置 JPanel 使用絕對佈局

        //設定預設字體
        final Font basic_font = new Font("微軟正黑體", Font.BOLD, 24);
        final Font button_font = new Font("微軟正黑體", Font.BOLD, 20);
        UIManager.put("OptionPane.messageFont", basic_font);
        UIManager.put("OptionPane.buttonFont", button_font);

        // 添加主面板到框架
        frame.add(mainPanel);
        frame.setVisible(true);

        JLabel Label1 = new JLabel("里程數:");
        Label1.setFont(basic_font);
        Label1.setBounds(Gap_X, Gap_Y, 155, 40);
        mainPanel.add(Label1);

        final JTextField KM = new JTextField();
        KM.setFont(basic_font);
        KM.setBounds(Gap_X + 100, Label1.getY(), 155, 40);
        mainPanel.add(KM);

        JLabel LabelNum = new JLabel("車牌號:");
        LabelNum.setFont(basic_font);
        LabelNum.setBounds(KM.getX() + 175, KM.getY(), 155, 40);
        mainPanel.add(LabelNum);

        Number.setFont(basic_font);
        Number.setBounds(LabelNum.getX() + 100, LabelNum.getY(), 155, 40);
        mainPanel.add(Number);

        JButton save = new JButton("儲存");
        save.setFont(button_font);
        save.setBounds(Number.getX() + 175, KM.getY(), 155, 40);
        mainPanel.add(save);

        JButton cancel = new JButton("取消");
        cancel.setFont(button_font);
        cancel.setBounds(save.getX() + 200, KM.getY(), 155, 40);
        mainPanel.add(cancel);

        JLabel Label2 = new JLabel("本次更換項目:");
        Label2.setFont(basic_font);
        Label2.setBounds(Gap_X, Gap_Y + 60, 155, 40);
        mainPanel.add(Label2);

        JLabel LabelCycle = new JLabel("保養週期:");
        LabelCycle.setFont(basic_font);
        LabelCycle.setBounds(LabelNum.getX() - 20, LabelNum.getY() + 60, 155, 40);
        mainPanel.add(LabelCycle);

        final JTextField Cycle = new JTextField();
        Cycle.setFont(basic_font);
        Cycle.setBounds(Number.getX(), LabelCycle.getY(), 155, 40);
        mainPanel.add(Cycle);

        final JTextArea change = new JTextArea();
        change.setFont(basic_font);
        change.setBounds(Gap_X, Label2.getY() + 60, 924, 260);
        change.setBorder(new LineBorder(Color.black));
        mainPanel.add(change);

        // 確保組件已添加後重新繪製
        frame.revalidate();
        frame.repaint();


        //事件傾聽區，因變數生命週期關係，寫在最後。
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("儲存按鈕被點擊");
                if (KM.getText().length() != 0 && Number.getText().length() != 0 && Cycle.getText().length() != 0 && change.getText().length() != 0) {
                    int The_km = Integer.parseInt(KM.getText());
                    String The_Number = Number.getText();
                    int The_Cycle = Integer.parseInt(Cycle.getText());
                    String The_change = change.getText();
                    int The_next = Integer.parseInt(Cycle.getText()) + The_km;
                    WriteFile write = new WriteFile();
                    write.main(The_km, The_Number, The_Cycle, The_change);

                    callback.create_logWindowColsed(The_km, The_Number, The_next, The_change);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "所有欄位都必須填寫", "注意", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

    }

    public void getNumber(String dNumber) {
        if (dNumber != "請選擇") {
            Number.setText(dNumber);
        }
    }

}
