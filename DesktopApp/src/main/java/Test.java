import java.io.*;
import java.net.URL;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        String path = Test.class.getResource("/product_images").getFile();

        File file = new File(Test.class.getResource("/some.txt").getFile());
        File file1 = new File(path + "/some2.txt");

        /*try {
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println(file.renameTo(file1));
    }

    private static void readFile() {
        InputStream stream = Test.class.getResourceAsStream("/some.txt");
        Scanner scanner = new Scanner(stream);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
