import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    //建立變數存放最新一筆保養資料
    String last_km = null;
    String next_km = null;
    String last_data = null;

    public String main(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");

                if (line.contains("以下空白")) {
                    last_data = content.toString();
                    content.setLength(0); // 清空 StringBuilder
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return last_data;
    }

    public String getkm1(String filePath) {
        String last_km = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 检查是否包含"里程數:"，如果有，提取数值部分
                if (line.contains("里程數:")) {
                    String km = line.substring(line.indexOf("里程數:") + "里程數:".length()).trim();
                    // 提取里程数的实际数值部分
                    int endIndex = km.indexOf(" ");
                    if (endIndex != -1) {
                        km = km.substring(0, endIndex).trim();
                    } else {
                        endIndex = km.indexOf("下次保養里程:");
                        if (endIndex != -1) {
                            km = km.substring(0, endIndex).trim();
                        }
                    }
                    last_km = km;  // 更新最后的里程数
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return last_km;
    }

    // 讀取里程數
    String loadKMIntoMain(String directoryPath, String fileName) {
        File file = new File(directoryPath + fileName);
        if (file.exists() && file.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                    if (line.contains("里程數:")) {
                        String km = line.substring(line.indexOf("里程數:") + "里程數:".length()).trim();
                        // 提取里程数的实际数值部分
                        int endIndex = km.indexOf(" ");
                        if (endIndex != -1) {
                            km = km.substring(0, endIndex).trim();
                        } else {
                            endIndex = km.indexOf("下次保養里程:");
                            if (endIndex != -1) {
                                km = km.substring(0, endIndex).trim();
                            }
                        }
                        last_km = km;  // 更新最后的里程数
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("未找到保養紀錄");
        }
        return last_km;
    }

    //讀取下次更換里程
    String loadNextKMIntoMain(String directoryPath, String fileName) {
        File file = new File(directoryPath + fileName);
        if (file.exists() && file.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                    if (line.contains("下次保養里程:")) {
                        String next = line.substring(line.indexOf("下次保養里程:") + "下次保養里程:".length()).trim();
                        // 提取里程数的实际数值部分
                        int endIndex = next.indexOf(" ");
                        if (endIndex != -1) {
                            next = next.substring(0, endIndex).trim();
                        }
                        next_km = next;  // 更新最后的里程数
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("未找到保養紀錄");
        }
        return next_km;
    }

    //讀取上次更換項目
    String loadDATAIntoMain(String directoryPath, String fileName) {
        File file = new File(directoryPath + fileName);
        if (file.exists() && file.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                    if (line.contains("以下空白")) {
                        last_data = content.toString();
                        content.setLength(0); // 清空 StringBuilder
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("未找到保養紀錄");
        }
        return last_data;
    }

}
