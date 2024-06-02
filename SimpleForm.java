import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SimpleForm implements WindowCloseCallback {

    public int last_km; //讀取txt檔內數值
    public JLabel last = new JLabel(String.valueOf(last_km), JLabel.CENTER);    //上次保養里程數
    public int next_km;
    public JLabel now = new JLabel("尚未輸入", JLabel.CENTER);  //目前里程數
    public JLabel def_title = new JLabel("", JLabel.CENTER);
    public JLabel def = new JLabel("(用於顯示上次保養所更換物品區)", JLabel.CENTER);
    public JComboBox ComboBox = new JComboBox();
    JButton Updete = new JButton("更新目前里程");


    public SimpleForm() {
        JFrame frame = new JFrame("維修保養紀錄");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        Font basic_font = new Font("微軟正黑體", Font.BOLD, 24);
        Font button_font = new Font("微軟正黑體", Font.BOLD, 20);
        UIManager.put("OptionPane.messageFont", basic_font);
        UIManager.put("OptionPane.buttonFont", button_font);

        //Jpanel內物件
        JLabel Select_Label = new JLabel("選擇車輛:", JLabel.CENTER);
        Select_Label.setFont(basic_font);
        Select_Label.setBounds(Gap_X, Gap_Y, 155, 40);
        mainPanel.add(Select_Label);

        ComboBox.addItem("請選擇");
        ComboBox.setFont(basic_font);
        ComboBox.setBounds(Gap_X + 160, Gap_Y, 155, 40);
        mainPanel.add(ComboBox);
        ComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ComboBox.getSelectedIndex() != 0) {
//                    System.out.println((String) ComboBox.getSelectedItem() + "保養紀錄.txt");
                    Readthedata();
                    now.setText("尚未輸入");
                    now.setForeground(Color.black);
                    Updete.setEnabled(true);
                } else {
                    clear();
                    now.setText("尚未輸入");
                    now.setForeground(Color.black);
                    Updete.setEnabled(false);
                }

            }
        });


        JLabel Tlast = new JLabel("上次保養里程:", JLabel.CENTER);
        Tlast.setFont(basic_font);
        Tlast.setBounds(Gap_X, Gap_Y + 70, 155, 40);
//        Tlast.setBorder(new LineBorder(Color.black));
        mainPanel.add(Tlast);

        last.setFont(basic_font);
        last.setBorder(new LineBorder(Color.black));
        last.setBounds(Tlast.getX() + 160, Tlast.getY(), 155, 40);
        mainPanel.add(last);

        JLabel Tnow = new JLabel("目前里程:", JLabel.CENTER);
        Tnow.setFont(basic_font);
//        Tnow.setBorder(new LineBorder(Color.black));
        Tnow.setBounds(Gap_X, last.getY() + 70, 155, 40);
        mainPanel.add(Tnow);

        now.setFont(basic_font);
        now.setBorder(new LineBorder(Color.black));
        now.setBounds(Tnow.getX() + 160, Tnow.getY(), 155, 40);
        mainPanel.add(now);

        final JButton creat = new JButton("新增保養紀錄");
        creat.setFont(button_font);
        creat.setBounds(Tnow.getX() + 160, now.getY() + 70, 155, 100);
        creat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateWindow();
            }
        });
        mainPanel.add(creat);

        Updete.setEnabled(false);
        Updete.setFont(button_font);
        Updete.setBounds(Gap_X, now.getY() + 70, 155, 100);
        Updete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubWindow();
            }
        });
        mainPanel.add(Updete);

        //建立用於顯示上次更換項目之Panel
        JPanel change = new JPanel();
        int change_x = creat.getX() + 175;
        int change_y = Gap_Y;
        change.setBounds(change_x, change_y, mainPanel.getWidth() - change_x - Gap_X, mainPanel.getHeight() - Gap_Y * 2);
        change.setBorder(new LineBorder(Color.black));
        change.setLayout(new BorderLayout());

        mainPanel.add(change);


        def_title.setFont(basic_font);
        change.add(def_title, BorderLayout.NORTH);


        def.setFont(basic_font);
        change.add(def, BorderLayout.CENTER);

        // 添加主面板到框架
        frame.add(mainPanel);
        frame.setVisible(true);

        loadFilesIntoComboBox("./保養紀錄/");
        if (ComboBox.getItemAt(1) == null) {
            JOptionPane.showMessageDialog(null, "未找到任何保養紀錄，請先建立一個", "警告", JOptionPane.ERROR_MESSAGE);
            openCreateWindow();
        }

    }

    private void openSubWindow() {
        TextInputWindow subWindow = new TextInputWindow(this);
        subWindow.setVisible(true);
    }

    //當輸入完目前里程後，回傳使用者所輸入的里程變數數值到主程式的方法。
    @Override
    public void onWindowClosed(int returnValue) {
        // 接收副視窗回傳的變數
//        System.out.println("副視窗已關閉，回傳值：" + returnValue);

        //接收到數值後，判斷是否超過兩千公里，如果超過，則跳出提示，並改變顏色。
        if (returnValue >= next_km) {
            now.setText(String.valueOf(returnValue));
            now.setForeground(Color.red);
            JOptionPane.showMessageDialog(null, "已超過保養里程" + (returnValue - next_km) + "公里!!", "注意", JOptionPane.WARNING_MESSAGE);
        } else if (returnValue < last_km) {
            JOptionPane.showMessageDialog(null, "輸入的里程小於上次保養里程!!", "注意", JOptionPane.WARNING_MESSAGE);
        } else {
            now.setText(String.valueOf(returnValue));
            now.setForeground(Color.black);
        }


    }

    public void openCreateWindow() {
        Create_log creWindow = new Create_log(this);
        creWindow.getNumber((String) ComboBox.getSelectedItem());
    }

    @Override
    public void create_logWindowColsed(int km, String number, int next, String data) {
        //儲存完保養紀錄後，傳送數值到主畫面。
        clear();

        last_km = km;
        next_km = next;
//        System.out.println("下次保養里程:"+ next_km);
        last.setText(String.valueOf(km));
        String htmlText = "<html>" + data.replace("\n", "<br>") + "</html>";
        def.setText(htmlText);

// 檢查 JComboBox 中是否已經存在與變數相同的名稱的選項
        boolean exists = false;
        for (int i = 0; i < ComboBox.getItemCount(); i++) {
            String item = (String) ComboBox.getItemAt(i);
            if (item.equals(number)) {
                exists = true;
                break;
            }
        }

// 如果不存在相同名稱的選項，則新增一個
        if (!exists) {
            ComboBox.addItem(number);
        }

        ComboBox.setSelectedItem(number);
        Readthedata();
        def_title.setText("");
    }

    //讀取檔案內的保養資訊，放入右側panel
    public void Readthelog() {
        String FilePath = "保養紀錄.txt"; //檔案位址
        ReadFile readFile = new ReadFile();
        String htmlText = "<html>" + readFile.main(FilePath).replace("\n", "<br>") + "</html>"; //取得更換項目資訊
        String KmText = "<html>" + readFile.getkm1(FilePath).replace("\n", "<br>") + "</html>";  //取得里程資訊
        def.setText(htmlText);
        last.setText(KmText);
    }

    public void Readthedata() {
        ReadFile read = new ReadFile();
        String htmlText = "<html>" + read.loadDATAIntoMain("./保養紀錄/", (String) ComboBox.getSelectedItem() + "保養紀錄.txt").replace("\n", "<br>") + "</html>"; //取得更換項目資訊
        String KmText = "<html>" + read.loadKMIntoMain("./保養紀錄/", (String) ComboBox.getSelectedItem() + "保養紀錄.txt").replace("\n", "<br>").replace("\n", "<br>") + "</html>";  //取得里程資訊
        String NextKm = read.loadNextKMIntoMain("./保養紀錄/", (String) ComboBox.getSelectedItem() + "保養紀錄.txt");  //取得下次里程
        def.setText(htmlText);
        last.setText(KmText);
        next_km = Integer.parseInt(NextKm);
//        System.out.println(next_km);
    }

    // 將讀取到的txt檔名放入combobox
    private void loadFilesIntoComboBox(String directoryPath) {
        File folder = new File(directoryPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.isFile() && file.getName().toLowerCase().endsWith("保養紀錄.txt")) {
//                        System.out.println(file);
                        ComboBox.addItem(file.getName().replace("保養紀錄.txt", ""));
                    }
                }
            }
        } else {
            System.err.println("未找到保養紀錄");
        }
    }

    public void clear() {
        last.setText(String.valueOf(last_km));
        now.setText("尚未輸入");
        def_title.setText("");
        def.setText("用於顯示上次保養所更換物品區");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleForm();
            }
        });
    }
}
