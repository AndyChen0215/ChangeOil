import java.io.*;
import java.util.Date;

public class WriteFile {

    public void main(int km, String Number, int Cycle, String data) {
        // 指定保存文件的目錄
        String directoryPath = "./保養紀錄";

        try {
            // 创建目录对象
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 如果目錄不存在則創建
            }

            // 在这里修改文件名称并指定完整路径
            String FileName = directoryPath + File.separator + Number + "保養紀錄.txt";

            // 创建 FileWriter 对象，指定文件路径和追加模式
            FileWriter fWrite = new FileWriter(FileName, true);
            BufferedWriter fOut = new BufferedWriter(fWrite);
            Date date = new Date();
            fOut.write(date + " " + "保養紀錄");
            fOut.newLine();
            fOut.write("車牌號:" + Number + " ");
            fOut.write("里程數:" + km + " ");
            fOut.write("下次保養里程:" + (km + Cycle));
            fOut.newLine();
            fOut.write("保養項目:\n" + data);
            fOut.newLine();
            fOut.write("以下空白");

            fOut.newLine();
            fOut.newLine();
            fOut.flush();

            fOut.close();
            System.out.println("檔案寫入成功!");
        } catch (Exception e) {
            System.out.println("檔案處理有誤!");
        }
    }

}
