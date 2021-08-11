package IOTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class IOTest {

    public static void main(String[] args) {
//        FileInputTest file = new FileInputTest();
//        file.test();
        ReadFile readFile = new ReadFile();
        try (InputStream inputStream = new FileInputStream("resource/test.txt")) {
            String s = readFile.readFileContent(inputStream);
            System.out.println(s);
            String[] split = s.split("。");
            Arrays.stream(split).forEach(x -> System.out.println(x + "。"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        InputStream inputStream = new FileInputStream("resoure/test.txt");

    }

}

class FileInputTest {
    public void test() {
        File file = new File("resource/test.txt");
        if (!file.exists()) {   // 判断文件是否存在
            // 不存在则创建
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件的绝对路径：" + file.getAbsolutePath());
        System.out.println("文件写入前的大小：" + file.length());
//        file.delete();  // 刪除文件

        write(file);
        System.out.println("文件写入后的大小：" + file.length());
        System.out.println(read(file));
    }

    public void write(File file) {
//        OutputStream os = null;
//        try {
//            os = new FileOutputStream(file, true);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        String string = "松下问童子，言师采药去。只在此山中，云深不知处。";      // 要写入的字符串
        try (OutputStream os = new FileOutputStream(file, true)) {
            System.out.println(string.getBytes(StandardCharsets.UTF_8));
            os.write(string.getBytes());// 写入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(File file) {

        StringBuilder sb = null;
        try (InputStream in = new FileInputStream(file)) {
            byte[] bytes = new byte[1024];        // 一次性取多少个字节
            sb = new StringBuilder();// 用来接收读取的字节数组
            int length;   // 读取到的字节数组长度，为-1时表示没有数据
            while (!((length = in.read(bytes)) != -1)) {
                sb.append(new String(bytes, 0, length));// 将读取的内容转换成字符串
            }
        } catch (Exception e) {
            System.out.println("文件读取异常：" + e.getMessage());
        }
        return sb.toString();
    }
}

class ReadFile {

    public String readFileContent(InputStream fileStream) throws IOException {
//        String str = "";
        byte[] bytes = new byte[1024];
        StringBuilder sb = new StringBuilder();
//        int read = fileStream.read(bytes);
        while (true) {
            int read = fileStream.read(bytes);
            if (!(read > 0)) break;
            sb.append(new String(bytes, 0, read));
        }
        return sb.toString();
    }

}
